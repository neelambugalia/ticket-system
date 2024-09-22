package earth.bolt.ticket.service;

import earth.bolt.ticket.entity.CreateEventRequest;
import earth.bolt.ticket.entity.Event;
import earth.bolt.ticket.entity.UpdateEventRequest;
import earth.bolt.ticket.exception.ExceptionType;
import earth.bolt.ticket.exception.TicketException;
import earth.bolt.ticket.repository.EventRepository;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {

  private final EventRepository eventRepository;

  /**
   * Add an event.
   *
   * @param request the request to create event
   * @return the persisted entity
   */
  public Event createEvent(CreateEventRequest request) {
    Event event = new Event();
    event.setName(request.getName());
    event.setEventType(request.getEventType());
    event.setVenue(request.getVenue());
    event.setAvailableTickets(request.getAvailableTickets());
    event.setPricePerTicket(request.getPricePerTicket());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    event.setStartDateTime(LocalDateTime.parse(request.getStartDateTime(), formatter));
    event.setEndDateTime(LocalDateTime.parse(request.getEndDateTime(), formatter));
    return eventRepository.save(event);
  }

  /**
   * Get all the events.
   *
   * @return the list of entities
   */
  public List<Event> getAllEvents() {
    return eventRepository.findAll();
  }

  /**
   * Get the event by ID.
   *
   * @param id the ID of the entity
   * @return the entity
   */
  public Event getEventById(UUID id) {
    return eventRepository
        .findById(id)
        .orElseThrow(() -> new TicketException(ExceptionType.EVENT_NOT_FOUND));
  }

  /**
   * Update an event.
   *
   * @param id the ID of the entity
   * @param request the update request
   * @return the updated entity
   */
  public Event updateEvent(UUID id, UpdateEventRequest request) {
    Optional<Event> existingEvent = eventRepository.findById(id);
    if (existingEvent.isPresent()) {
      Event event = existingEvent.get();
      event.setName(request.getName());
      event.setEventType(request.getEventType());
      event.setAvailableTickets(request.getAvailableTickets());
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      event.setStartDateTime(LocalDateTime.parse(request.getStartDateTime(), formatter));
      event.setEndDateTime(LocalDateTime.parse(request.getEndDateTime(), formatter));
      event.setVenue(request.getVenue());
      event.setPricePerTicket(request.getPricePerTicket());
      return eventRepository.save(event);
    } else {
      throw new TicketException(ExceptionType.EVENT_NOT_FOUND);
    }
  }

  /**
   * Delete the event by ID.
   *
   * @param id the ID of the entity
   */
  public void deleteEvent(UUID id) {
    eventRepository.deleteById(id);
  }
}
