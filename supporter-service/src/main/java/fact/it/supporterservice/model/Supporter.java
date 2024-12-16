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
    private String supporterCode;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String teamCode;
    private String playerCode;
}
