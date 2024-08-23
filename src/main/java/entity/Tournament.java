package entity;

import enums.Status;
import enums.TournamentType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @OneToMany(mappedBy = "tournament")
    @ToString.Exclude
    private List<Extension> extensions;

    @OneToMany(mappedBy = "tournament")
    @ToString.Exclude
    private List<TournamentParticipant> participants;

    @OneToMany(mappedBy = "tournament")
    @ToString.Exclude
    private List<Match> matches;
}