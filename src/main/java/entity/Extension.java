package entity;

import enums.ExtensionName;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "extensions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Extension {
    @Id
    @UuidGenerator
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ExtensionName name;

    private String value;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    @ToString.Exclude
    private Tournament tournament;

}
