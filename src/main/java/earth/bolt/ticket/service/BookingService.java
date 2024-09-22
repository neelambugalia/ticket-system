package earth.bolt.ticket.service;

import earth.bolt.ticket.entity.Booking;
import earth.bolt.ticket.entity.BuyEventTicketsRequest;
import earth.bolt.ticket.entity.BuyEventTicketsResponse;
import earth.bolt.ticket.entity.Event;
import earth.bolt.ticket.exception.ExceptionType;
import earth.bolt.ticket.exception.TicketException;
import earth.bolt.ticket.repository.BookingRepository;
import earth.bolt.ticket.repository.EventRepository;
import java.awt.print.Book;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingService {

  private final BookingRepository bookingRepository;
  private final EventRepository eventRepository;

  public Long getAvailableTickets(UUID eventId) {
    Optional<Event> eventOp = eventRepository.findById(eventId);
    if (!eventOp.isPresent()) {
      throw new TicketException(ExceptionType.EVENT_NOT_FOUND);
    }
    Event event = eventOp.get();
    return event.getAvailableTickets();
  }

  @Transactional
  public BuyEventTicketsResponse buyTickets(UUID eventId, BuyEventTicketsRequest request) {
    Optional<Event> eventOp = eventRepository.findById(eventId);

    // Check event exists
    if (!eventOp.isPresent()) {
      throw new TicketException(ExceptionType.EVENT_NOT_FOUND);
    }
    Event event = eventOp.get();

    // Check current date is before end of event
    if(LocalDateTime.now().isAfter(event.getEndDateTime())) {
      throw new TicketException(ExceptionType.EVENT_ALREADY_OVER);
    }

    Long availableTickets = event.getAvailableTickets();
    Long requestTickets = request.getNumOfTickets();
    // Check enough tickets available
    if (availableTickets < request.getNumOfTickets()) {
      throw new TicketException(ExceptionType.TICKETS_NOT_AVAILABLE);
    }

    // One user can only buy 10 tickets a time
    if (requestTickets > 10) {
      throw new TicketException(ExceptionType.TICKET_LIMIT_EXCEEDED);
    }

    List<Booking> bookings = new ArrayList<>();
    while (requestTickets > 0) {
      Booking booking = new Booking();
      booking.setUserId(request.getUserId());
      booking.setEventId(eventId);
      booking.setSeat(availableTickets);
      booking.setBookedAt(OffsetDateTime.now());
      booking.setPrice(event.getPricePerTicket());
      Booking savedBooking = bookingRepository.save(booking);
      bookings.add(savedBooking);
      availableTickets--;
      requestTickets--;
    }
    event.setAvailableTickets(availableTickets);
    eventRepository.save(event);

    BuyEventTicketsResponse response = new BuyEventTicketsResponse();
    response.setBookings(bookings);
    return response;
  }
}
