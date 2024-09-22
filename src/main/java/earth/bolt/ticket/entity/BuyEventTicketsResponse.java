package earth.bolt.ticket.entity;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class BuyEventTicketsResponse {
  List<Booking> bookings;
}
