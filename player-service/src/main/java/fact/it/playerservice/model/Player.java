package fact.it.playerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Document(value="player")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Player {
    private String id;
    private String playerCode;
    private String firstName;
    private String lastName;
    private String position;
    private String teamCode;
    private LocalDate birthDate;
    private String nationality;

}
