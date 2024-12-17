package fact.it.supporterservice.service;

import fact.it.supporterservice.dto.PlayerResponse;
import fact.it.supporterservice.dto.SupporterRequest;
import fact.it.supporterservice.dto.SupporterResponse;
import fact.it.supporterservice.dto.TeamResponse;
import fact.it.supporterservice.model.Supporter;
import fact.it.supporterservice.repository.SupporterRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class SupporterService {
    private final SupporterRepository supporterRepository;
    private final WebClient webClient;
    @Value("${teamservice.baseurl}")
    private String teamServiceBaseUrl;
    @Value("${playerservice.baseurl}")
    private String playerServiceBaseUrl;
    @PostConstruct
    public void loadData() {
        if (supporterRepository.count() == 0) {
            List<Supporter> supporters = List.of(
                    Supporter.builder()
                            .supporterCode(UUID.randomUUID().toString())  // random UUID for supporterCode
                            .firstName("John")
                            .lastName("Doe")
                            .birthDate(LocalDate.of(1985, 5, 20))
                            .email("johndoe@example.com")
                            .teamCode("b9e4d4b8-3c5a-4b5d-bae3-1f3c74d94c4b")
                            .playerCode("c1f8e5d4-9a3d-4b8e-8f3a-2c6d7b1e9f4c")
                            .build(),
                    Supporter.builder()
                            .supporterCode(UUID.randomUUID().toString())
                            .firstName("Jane")
                            .lastName("Smith")
                            .birthDate(LocalDate.of(1990, 8, 15))
                            .email("janesmith@example.com")
                            .teamCode("a5a0e8e4-1a2b-4f6c-8e3e-cd1a2f9b28e6")
                            .playerCode("a5e7c8d4-3b9a-4f6e-9c3d-6d1a2f8b7e9c")
                            .build(),
                    Supporter.builder()
                            .supporterCode(UUID.randomUUID().toString())
                            .firstName("Alex")
                            .lastName("Brown")
                            .birthDate(LocalDate.of(1995, 12, 5))
                            .email("alexbrown@example.com")
                            .teamCode("a7b9c4d6-2e1a-489c-8f9e-3b4d6f7a8c9e")
                            .playerCode("f3a6b8e9-2c7d-4b9f-8d3a-9e5c1f7a2b4d")
                            .build(),
                    Supporter.builder()
                            .supporterCode(UUID.randomUUID().toString())
                            .firstName("Emily")
                            .lastName("Wilson")
                            .birthDate(LocalDate.of(1987, 3, 30))
                            .email("emilywilson@example.com")
                            .teamCode("c7d2f312-2b8d-4014-9028-637fe3a49e56")
                            .playerCode("d1c8e7f9-3b4d-4c2a-8e1f-7a5d9f2b6e8c")
                            .build(),
                    Supporter.builder()
                            .supporterCode(UUID.randomUUID().toString())
                            .firstName("Michael")
                            .lastName("Johnson")
                            .birthDate(LocalDate.of(2000, 11, 25))
                            .email("michaeljohnson@example.com")
                            .teamCode("e4f8bc7b-b9d8-4c19-a91d-0b31a2c8e9b2")
                            .playerCode("e4f8d1a6-9c3a-4b8e-8f7c-1d2b6a9e5f3c")
                            .build(),
                    Supporter.builder()
                            .supporterCode(UUID.randomUUID().toString())
                            .firstName("Sophia")
                            .lastName("Davis")
                            .birthDate(LocalDate.of(1992, 7, 18))
                            .email("sophiadavis@example.com")
                            .teamCode("d0934c69-85a3-4638-947c-df4f3c8b7dc9")
                            .playerCode("d8e7b1f9-6c3a-4f2e-8d7c-9a1b5e3f6a8d")
                            .build(),
                    Supporter.builder()
                            .supporterCode(UUID.randomUUID().toString())
                            .firstName("Daniel")
                            .lastName("Garcia")
                            .birthDate(LocalDate.of(1998, 9, 10))
                            .email("danielgarcia@example.com")
                            .teamCode("a5a0e8e4-1a2b-4f6c-8e3e-cd1a2f9b28e6")
                            .playerCode("f1a6e9b8-4c3d-2f7e-8d1b-9a5d3c7e6f8b")
                            .build(),
                    Supporter.builder()
                            .supporterCode(UUID.randomUUID().toString())
                            .firstName("Isabella")
                            .lastName("Martinez")
                            .birthDate(LocalDate.of(1985, 6, 4))
                            .email("isabellamartinez@example.com")
                            .teamCode("b4c6d7e8-1f9a-4c3d-8e1b-2c5a6f7e8b9c")
                            .playerCode("e9f7c1a6-3d8b-4b2e-8f3c-6a9d5e7b1f4d")
                            .build(),
                    Supporter.builder()
                            .supporterCode(UUID.randomUUID().toString())
                            .firstName("James")
                            .lastName("Lopez")
                            .birthDate(LocalDate.of(1993, 10, 8))
                            .email("jameslopez@example.com")
                            .teamCode("b9e4d4b8-3c5a-4b5d-bae3-1f3c74d94c4b")
                            .playerCode("d9e8f1b7-2c4a-4f6e-8b3d-7a9c5e1f8a2d")
                            .build(),
                    Supporter.builder()
                            .supporterCode(UUID.randomUUID().toString())
                            .firstName("Olivia")
                            .lastName("Taylor")
                            .birthDate(LocalDate.of(1990, 2, 14))
                            .email("oliviataylor@example.com")
                            .teamCode("c7d2f312-2b8d-4014-9028-637fe3a49e56")
                            .playerCode("f3a6b8e9-2c7d-4b9f-8d3a-9e5c1f7a2b4d")
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

    public SupporterResponse getSupporterBySupporterCode(String supporterCode) {
        Optional<Supporter> supporter = supporterRepository.findSupporterBySupporterCode(supporterCode);
        return supporter.map(this::mapToSupporterResponse).orElse(null);
    }

    public SupporterResponse createSupporter(SupporterRequest supporterRequest) {
        Supporter supporter = Supporter.builder()
                .supporterCode(UUID.randomUUID().toString())
                .firstName(supporterRequest.getFirstName())
                .lastName(supporterRequest.getLastName())
                .birthDate(supporterRequest.getBirthDate())
                .email(supporterRequest.getEmail())
                .teamCode(supporterRequest.getTeamCode())
                .playerCode(supporterRequest.getPlayerCode())
                .build();

        Supporter savedSupporter = supporterRepository.save(supporter);
        return mapToSupporterResponse(savedSupporter);
    }

    // Edit an existing supporter
    public SupporterResponse editSupporter(String supporterCode, SupporterRequest supporterRequest) {
        Supporter existingSupporter = supporterRepository.findSupporterBySupporterCode(supporterCode)
                .orElseThrow(() -> new RuntimeException("Supporter not found"));

        updateIfNotNull(supporterRequest.getFirstName(), existingSupporter::setFirstName);
        updateIfNotNull(supporterRequest.getLastName(), existingSupporter::setLastName);
        updateIfNotNull(supporterRequest.getBirthDate(), existingSupporter::setBirthDate);
        updateIfNotNull(supporterRequest.getEmail(), existingSupporter::setEmail);
        updateIfNotNull(supporterRequest.getTeamCode(), existingSupporter::setTeamCode);
        updateIfNotNull(supporterRequest.getPlayerCode(), existingSupporter::setPlayerCode);

        Supporter updatedSupporter = supporterRepository.save(existingSupporter);
        return mapToSupporterResponse(updatedSupporter);
    }

    private <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    // Remove a supporter
    public boolean removeSupporter(String supporterCode) {
        Optional<Supporter> optionalSupporter = supporterRepository.findSupporterBySupporterCode(supporterCode);
        if (optionalSupporter.isPresent()) {
            supporterRepository.delete(optionalSupporter.get());
            return true;
        } else {
            return false;
        }
    }

    private TeamResponse getTeamByCode(String teamCode) {
        return webClient.get()
                .uri("http://" + teamServiceBaseUrl + "/api/teams/by-id/" + teamCode)
                .retrieve()
                .bodyToMono(TeamResponse.class)
                .block();
    }
    private PlayerResponse getPlayerByCode(String playerCode) {
        return webClient.get()
                .uri("http://" + playerServiceBaseUrl + "/api/players/by-id/" + playerCode)
                .retrieve()
                .bodyToMono(PlayerResponse.class)
                .block();
    }

    private SupporterResponse mapToSupporterResponse(Supporter supporter) {
        return SupporterResponse.builder()
                .supporterCode(supporter.getSupporterCode())
                .firstName(supporter.getFirstName())
                .lastName(supporter.getLastName())
                .birthDate(supporter.getBirthDate())
                .email(supporter.getEmail())
                .teamName(getTeamByCode(supporter.getTeamCode()))
                .favoritePlayer(getPlayerByCode(supporter.getPlayerCode()))
                .build();
    }
}
