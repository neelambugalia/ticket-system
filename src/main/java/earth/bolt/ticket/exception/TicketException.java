package earth.bolt.ticket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;

public class TicketException extends RuntimeException {

  private final ExceptionType type;

  public TicketException(ExceptionType type) {
    super(type.name());
    this.type = type;
  }

  public ExceptionType getType() {
    return this.type;
  }

  public BodyBuilder mapException() {
    return ResponseEntity.status(status())
        .header("Error Code", this.type.name())
        .header("Error Message", this.type.getMessage());
  }

  private HttpStatus status() {
    return switch (this.type) {
      case EVENT_NOT_FOUND, TICKETS_NOT_AVAILABLE, BOOKING_NOT_FOUND -> HttpStatus.NOT_FOUND;
      case EVENT_ALREADY_OVER, TICKET_LIMIT_EXCEEDED  -> HttpStatus.BAD_REQUEST;
    };
  }
}
