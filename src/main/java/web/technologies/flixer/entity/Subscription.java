package web.technologies.flixer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private SubscriptionPlan plan;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private SubscriptionStatus status;

    @JsonProperty("subscribed_at")
    private Instant subscribedAt = Instant.now();

    @JsonProperty("renewed_at")
    private Instant renewedAt;

    @JsonProperty("started_at")
    private Instant startedAt;

    @JsonProperty("ended_at")
    private Instant endedAt;
}
