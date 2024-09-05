package entity;

import enums.Status;
import enums.TournamentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Extension> extensions = new ArrayList<>();

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<TournamentParticipant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Match> matches = new ArrayList<>();
}
