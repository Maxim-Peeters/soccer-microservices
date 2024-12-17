package fact.it.supporterservice.controller;

import fact.it.supporterservice.dto.SupporterRequest;
import fact.it.supporterservice.dto.SupporterResponse;
import fact.it.supporterservice.service.SupporterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SupporterResponse> createSupporter(@RequestBody SupporterRequest supporterRequest) {
        SupporterResponse createdSupporter = supporterService.createSupporter(supporterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSupporter);
    }

    @PutMapping("/edit/{supporterCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SupporterResponse> editSupporter(@PathVariable String supporterCode, @RequestBody SupporterRequest supporterRequest) {
        SupporterResponse updatedSupporter = supporterService.editSupporter(supporterCode, supporterRequest);
        if (updatedSupporter != null) {
            return ResponseEntity.ok(updatedSupporter);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/remove/{supporterCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> removeSupporter(@PathVariable String supporterCode) {
        boolean isRemoved = supporterService.removeSupporter(supporterCode);
        if (isRemoved) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
