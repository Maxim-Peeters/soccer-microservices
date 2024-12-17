package fact.it.supporterservice.dto;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayerResponse {
    private String firstName;
    private String lastName;
    private String position;
    private LocalDate birthDate;
    private String nationality;

}