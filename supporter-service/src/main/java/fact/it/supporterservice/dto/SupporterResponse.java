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
public class SupporterResponse {
    private String supporterCode;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private TeamResponse teamName;
    private PlayerResponse favoritePlayer;
}

