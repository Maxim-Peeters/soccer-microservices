package fact.it.matchservice.model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value="match")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Match {
    private String id;
    private String matchCode;
    private String homeTeamCode; // Can be team ID or name
    private String awayTeamCode; // Can be team ID or name
    private LocalDateTime dateTime;
    private String location;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
}
