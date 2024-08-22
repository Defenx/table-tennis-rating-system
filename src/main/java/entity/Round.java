package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "rounds")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Round {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "round_number")
    private Integer roundNumber;

    private Integer score1;

    private Integer score2;

    @ManyToOne
    @JoinColumn(name = "match_id")
    @ToString.Exclude
    private Match match;

}