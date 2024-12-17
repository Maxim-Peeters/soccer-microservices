package fact.it.supporterservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupporterRequest {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String teamCode;
    private String playerCode;
}
