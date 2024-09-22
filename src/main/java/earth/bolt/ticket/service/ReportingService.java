package earth.bolt.ticket.service;

import earth.bolt.ticket.entity.Booking;
import earth.bolt.ticket.entity.Event;
import earth.bolt.ticket.repository.BookingRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportingService {

  private final BookingRepository bookingRepository;
  private final EventService eventService;

  public Long getNumSoldTickets(UUID eventId) {
    Event event = eventService.getEventById(eventId);
    List<Booking> allBookings = bookingRepository.findAll();
    return allBookings.stream().filter(b -> b.getEventId().equals(eventId)).count();
  }

  public Long getNumSoldTickets() {
    return bookingRepository.count();
  }

  public Long getRevenue() {
    List<Booking> allBookings = bookingRepository.findAll();
    return allBookings.stream().map(b -> b.getPrice()).reduce(Long::sum).orElse(0L);
  }

  public Long getRevenue(UUID eventId) {
    List<Booking> allBookings = bookingRepository.findAll();
    return allBookings.stream()
        .filter(b -> b.getEventId().equals(eventId))
        .map(b -> b.getPrice())
        .reduce(Long::sum)
        .orElse(0L);
  }
}
