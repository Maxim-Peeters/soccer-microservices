package fact.it.supporterservice.controller;

import fact.it.supporterservice.dto.SupporterResponse;
import fact.it.supporterservice.service.SupporterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/supporters")
@RequiredArgsConstructor
public class SupporterController {
    private final SupporterService supporterService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<SupporterResponse> getAllSupporters(){
        return supporterService.getAllSupporters();
    }
}
