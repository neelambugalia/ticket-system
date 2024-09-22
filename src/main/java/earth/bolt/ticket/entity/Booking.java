package earth.bolt.ticket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;

@Entity
@Table(name = "booking")
@Data
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID bookingId;

  @Column(nullable = false)
  private UUID userId;

  @Column(nullable = false)
  private UUID eventId;

  @Column(nullable = false)
  private OffsetDateTime bookedAt;

  @Column(nullable = false)
  private Long seat;

  @Column(nullable = false)
  private Long price;
}
