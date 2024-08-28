package entity;

import enums.Status;
import enums.TournamentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tournaments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Tournament {
    @Id
    @UuidGenerator
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TournamentType type;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate date;

    private Integer stage;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<Extension> extensions;

    @OrderBy("user.rating DESC")
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<TournamentParticipant> participants;

    @OneToMany(mappedBy = "tournament")
    private List<Match> matches;
}
