package fact.it.supporterservice.service;

import fact.it.supporterservice.dto.SupporterResponse;
import fact.it.supporterservice.model.Supporter;
import fact.it.supporterservice.repository.SupporterRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupporterService {
    private final SupporterRepository supporterRepository;

    @PostConstruct
    public void loadData() {
        if (supporterRepository.count() == 0) {
            List<Supporter> supporters = List.of(
                    Supporter.builder()
                            .firstName("John")
                            .lastName("Doe")
                            .birthDate(LocalDate.of(1985, 5, 20))
                            .email("johndoe@example.com")
                            .clubName("FC Barcelona")
                            .favoritePlayer("Lionel Messi")
                            .build(),
                    Supporter.builder()
                            .firstName("Jane")
                            .lastName("Smith")
                            .birthDate(LocalDate.of(1990, 8, 15))
                            .email("janesmith@example.com")
                            .clubName("Real Madrid")
                            .favoritePlayer("Cristiano Ronaldo")
                            .build(),
                    Supporter.builder()
                            .firstName("Alex")
                            .lastName("Brown")
                            .birthDate(LocalDate.of(1995, 12, 5))
                            .email("alexbrown@example.com")
                            .clubName("Liverpool")
                            .favoritePlayer("Virgil van Dijk")
                            .build(),
                    Supporter.builder()
                            .firstName("Emily")
                            .lastName("Wilson")
                            .birthDate(LocalDate.of(1987, 3, 30))
                            .email("emilywilson@example.com")
                            .clubName("Manchester City")
                            .favoritePlayer("Kevin De Bruyne")
                            .build(),
                    Supporter.builder()
                            .firstName("Michael")
                            .lastName("Johnson")
                            .birthDate(LocalDate.of(2000, 11, 25))
                            .email("michaeljohnson@example.com")
                            .clubName("Paris Saint-Germain")
                            .favoritePlayer("Kylian Mbappé")
                            .build(),
                    Supporter.builder()
                            .firstName("Sophia")
                            .lastName("Davis")
                            .birthDate(LocalDate.of(1992, 7, 18))
                            .email("sophiadavis@example.com")
                            .clubName("Bayern Munich")
                            .favoritePlayer("Manuel Neuer")
                            .build(),
                    Supporter.builder()
                            .firstName("Daniel")
                            .lastName("Garcia")
                            .birthDate(LocalDate.of(1998, 9, 10))
                            .email("danielgarcia@example.com")
                            .clubName("Real Madrid")
                            .favoritePlayer("Luka Modric")
                            .build(),
                    Supporter.builder()
                            .firstName("Isabella")
                            .lastName("Martinez")
                            .birthDate(LocalDate.of(1985, 6, 4))
                            .email("isabellamartinez@example.com")
                            .clubName("Chelsea")
                            .favoritePlayer("Neymar Jr")
                            .build(),
                    Supporter.builder()
                            .firstName("James")
                            .lastName("Lopez")
                            .birthDate(LocalDate.of(1993, 10, 8))
                            .email("jameslopez@example.com")
                            .clubName("Barcelona")
                            .favoritePlayer("Robert Lewandowski")
                            .build(),
                    Supporter.builder()
                            .firstName("Olivia")
                            .lastName("Taylor")
                            .birthDate(LocalDate.of(1990, 2, 14))
                            .email("oliviataylor@example.com")
                            .clubName("Manchester United")
                            .favoritePlayer("Bruno Fernandes")
                            .build()
            );

            supporterRepository.saveAll(supporters);
            System.out.println("10 supporters have been added to the database.");
        }
    }

    public List<SupporterResponse> getAllSupporters(){
        List<Supporter> events = supporterRepository.findAll();
        return events.stream().map(this::mapToSupporterResponse).toList();
    }
    private SupporterResponse mapToSupporterResponse(Supporter supporter) {
        return SupporterResponse.builder()
                .id(supporter.getId())
                .firstName(supporter.getFirstName())
                .lastName(supporter.getLastName())
                .birthDate(supporter.getBirthDate())
                .email(supporter.getEmail())
                .clubName(supporter.getClubName())
                .favoritePlayer(supporter.getFavoritePlayer())
                .build();
    }
}
