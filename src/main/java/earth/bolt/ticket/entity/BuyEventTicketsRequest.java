package earth.bolt.ticket.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Data;

@Data
public class BuyEventTicketsRequest {
  @JsonProperty(required = true)
  UUID userId;

  @JsonProperty(required = true)
  Long numOfTickets;
}
