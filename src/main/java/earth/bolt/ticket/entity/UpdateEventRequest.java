package earth.bolt.ticket.entity;

import java.util.UUID;
import lombok.Data;

@Data
public class UpdateEventRequest {
  private UUID id;
  private String name;
  private EventType eventType;
  // ISO format: "2024-12-20 5:00 PM"
  private String startDateTime;
  private String endDateTime;
  private Long availableTickets;
  private String venue;
  private Long pricePerTicket;
}
