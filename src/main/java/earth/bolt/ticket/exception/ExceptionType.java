package earth.bolt.ticket.exception;

import lombok.Data;

public enum ExceptionType {
  EVENT_NOT_FOUND("Event not found."),
  BOOKING_NOT_FOUND("Booking not found."),
  TICKETS_NOT_AVAILABLE("Enough tickets not available."),
  TICKET_LIMIT_EXCEEDED("Single user can not book more than 10 tickets."),
  EVENT_ALREADY_OVER("Event is already over.");

  private String message;

  private ExceptionType(String message) {
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }
};
