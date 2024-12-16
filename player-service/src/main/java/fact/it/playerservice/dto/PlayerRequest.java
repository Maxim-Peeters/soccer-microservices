package fact.it.playerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRequest {
    private String firstName;
    private String playerCode;
    private String lastName;
    private String position;
    private String teamCode;
    private LocalDate birthDate;
    private String nationality;
}
