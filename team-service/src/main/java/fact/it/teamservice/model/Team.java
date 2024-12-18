package fact.it.teamservice.model;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String teamCode;
    private String name;
    private String city;
    private String country;
}
