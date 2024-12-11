package fact.it.supporterservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "supporter")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Supporter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String clubName;
    private String favoritePlayer;
}
