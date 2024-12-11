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
public class MatchRequest {
    private String homeTeam; // Can be team ID or name
    private String awayTeam; // Can be team ID or name
    private LocalDateTime dateTime;
    private String location;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
}
