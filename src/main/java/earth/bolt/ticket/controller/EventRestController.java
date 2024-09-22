package earth.bolt.ticket.controller;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import earth.bolt.ticket.entity.BuyEventTicketsRequest;
import earth.bolt.ticket.entity.BuyEventTicketsResponse;
import earth.bolt.ticket.entity.CreateEventRequest;
import earth.bolt.ticket.entity.Event;
import earth.bolt.ticket.entity.UpdateEventRequest;
import earth.bolt.ticket.exception.TicketException;
import earth.bolt.ticket.service.BookingService;
import earth.bolt.ticket.service.EventService;
import earth.bolt.ticket.service.ReportingService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EventRestController {

  private final EventService eventService;
  private final BookingService bookingService;
  private final ReportingService reportingService;

  /**
   * Get all events.
   *
   * @return the ResponseEntity with status 200 (OK) and with body of the list of events
   */
  @GetMapping("/events")
  public List<Event> getAllEvents() {
    return eventService.getAllEvents();
  }

  /**
   * Create a new event.
   *
   * @param request the event to create
   * @return the ResponseEntity with status 200 (OK) and with body of the created event
   */
  @PostMapping("/events")
  public ResponseEntity<Event> createEvent(@RequestBody CreateEventRequest request) {
    try {
      Event createdEvent = eventService.createEvent(request);
      return ResponseEntity.ok(createdEvent);
    } catch (TicketException ex) {
      return ex.mapException().build();
    }
  }

  /**
   * Get an event by ID.
   *
   * @param id the ID of the event to read
   * @return the ResponseEntity with status 200 (OK) and with body of the event, or with status 404
   *     (Not Found) if the event does not exist
   */
  @GetMapping("/events/{id}")
  public ResponseEntity<Event> getEventById(@PathVariable UUID id) {
    try {
      Event event = eventService.getEventById(id);
      return ResponseEntity.ok(event);
    } catch (TicketException ex) {
      return ex.mapException().build();
    }
  }

  /**
   * Update an event by ID.
   *
   * @param id the ID of the event to update
   * @param request the updated event request
   * @return the ResponseEntity with status 200 (OK) and with body of the updated event, or with
   *     status 404 (Not Found) if the event does not exist
   */
  @PutMapping("/events/{id}")
  public ResponseEntity<Event> updateEvent(
      @PathVariable UUID id, @RequestBody UpdateEventRequest request) {
    Event updatedEvent = eventService.updateEvent(id, request);
    return ResponseEntity.ok(updatedEvent);
  }

  /**
   * Delete an event by ID.
   *
   * @param id the ID of the event to delete
   * @return the ResponseEntity with status 200 (OK).
   */
  @DeleteMapping("/events/{id}")
  public ResponseEntity<Empty> deleteEvent(@PathVariable UUID id) {
    eventService.deleteEvent(id);
    return ResponseEntity.ok().build();
  }

  /**
   * Get available tickets for an event
   *
   * @param eventId the ID of the event
   * @return the number of available tickets
   */
  @GetMapping("/events/{eventId}/available-tickets")
  public ResponseEntity<Long> getAvailableTicketsForEvent(@PathVariable UUID eventId) {
    try {
      Long availableTickets = bookingService.getAvailableTickets(eventId);
      return ResponseEntity.ok(availableTickets);
    } catch (TicketException ex) {
      return ex.mapException().build();
    }
  }

  /**
   * Buy tickets for an event
   *
   * @param eventId the ID of the event
   */
  @PostMapping("/events/{eventId}/buy-tickets")
  public ResponseEntity<BuyEventTicketsResponse> buyEventTickets(
      @PathVariable UUID eventId, @RequestBody BuyEventTicketsRequest request) {
    try {
      BuyEventTicketsResponse response = bookingService.buyTickets(eventId, request);
      return ResponseEntity.ok(response);
    } catch (TicketException ex) {
      return ex.mapException().build();
    }
  }

  /** Get total tickets sold. */
  @GetMapping("/events/sold-tickets")
  public ResponseEntity<Long> getTotalSoldTickets() {
    try {
      Long soldTickets = reportingService.getNumSoldTickets();
      return ResponseEntity.ok(soldTickets);
    } catch (TicketException ex) {
      return ex.mapException().build();
    }
  }

  /** Get tickets sold for an event. */
  @GetMapping("/events/{eventId}/sold-tickets")
  public ResponseEntity<Long> getTotalSoldTickets(@PathVariable UUID eventId) {
    try {
      Long soldTickets = reportingService.getNumSoldTickets(eventId);
      return ResponseEntity.ok(soldTickets);
    } catch (TicketException ex) {
      return ex.mapException().build();
    }
  }

  /** Get total revenue. */
  @GetMapping("/events/revenue")
  public ResponseEntity<Long> getTotalRevenue() {
    try {
      Long revenue = reportingService.getRevenue();
      return ResponseEntity.ok(revenue);
    } catch (TicketException ex) {
      return ex.mapException().build();
    }
  }

  /** Get revenue for an event. */
  @GetMapping("/events/{eventId}/revenue")
  public ResponseEntity<Long> getTotalRevenue(@PathVariable UUID eventId) {
    try {
      Long revenue = reportingService.getRevenue(eventId);
      return ResponseEntity.ok(revenue);
    } catch (TicketException ex) {
      return ex.mapException().build();
    }
  }
}
