package fact.it.matchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchResponse {
    private String matchCode;
    private TeamResponse homeTeam;
    private TeamResponse awayTeam;
    private LocalDateTime dateTime;
    private String location;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
}