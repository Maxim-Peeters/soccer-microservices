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

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<SupporterResponse> getAllSupporters(){
        return supporterService.getAllSupporters();
    }

    @GetMapping("/by-id/{supporterCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SupporterResponse> getSupporterBySupporterCode(@PathVariable String supporterCode) {
        SupporterResponse supporterResponse = supporterService.getSupporterBySupporterCode(supporterCode);
        if (supporterResponse != null) {
            return ResponseEntity.ok(supporterResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public String createSupporter(@RequestBody SupporterRequest supporterRequest) {
        SupporterResponse createdSupporter = supporterService.createSupporter(supporterRequest);
        return ((createdSupporter != null) ? "Supporter " + createdSupporter.getFirstName() + " " + createdSupporter.getLastName()
                + " is created successfully" : "Creating supporter failed");
    }

    @PutMapping("/edit/{supporterCode}")
    @ResponseStatus(HttpStatus.OK)
    public String editSupporter(@PathVariable String supporterCode, @RequestBody SupporterRequest supporterRequest) {
        SupporterResponse updatedSupporter = supporterService.editSupporter(supporterCode, supporterRequest);
        return ((updatedSupporter != null) ? "Supporter " + updatedSupporter.getFirstName() + " " + updatedSupporter.getLastName()
                + " is edited successfully" : "Editing supporter failed");
    }

    @DeleteMapping("/remove/{supporterCode}")
    @ResponseStatus(HttpStatus.OK)
    public String removeSupporter(@PathVariable String supporterCode) {
        SupporterResponse deletedSupporter = supporterService.removeSupporter(supporterCode);
        return ((deletedSupporter != null) ? "Supporter " + deletedSupporter.getFirstName() + " " + deletedSupporter.getLastName()
                + " is removed successfully" : "Deleting supporter failed");
    }

}
