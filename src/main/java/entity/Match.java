package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "matches")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Match {
    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    @ToString.Exclude
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "user1")
    @ToString.Exclude
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2")
    @ToString.Exclude
    private User user2;

    @OneToOne
    @JoinColumn(name = "next_match")
    private Match nextMatch;

    @OneToMany(mappedBy = "match")
    @ToString.Exclude
    private List<Round> rounds;

}
