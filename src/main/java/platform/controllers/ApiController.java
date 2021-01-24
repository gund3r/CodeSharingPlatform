package platform.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.entities.Code;
import platform.services.SnippetsService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class ApiController {

    final Logger log = org.slf4j.LoggerFactory.getLogger(ApiController.class);

    @Autowired
    SnippetsService snippetsService;

    public ApiController(){}

    @GetMapping(path = "api/code/{snippetId}", produces = "application/JSON")
    public ResponseEntity<Code> getCodeJSON(@PathVariable(value = "snippetId") String snippetId) {
        log.debug("REST request to getCode: {}", snippetId);
        UUID uuid = UUID.fromString(snippetId);
        Code code = snippetsService.getCodeFromRepository(uuid);
        if (code.isTimeLimit()) {
            snippetsService.updateTimeById(uuid);
        }
        if (code.isViewsLimit()) {
            snippetsService.updateViewsById(uuid);
        }
        Code updatedCode = snippetsService.getCodeFromRepository(uuid);
        return new ResponseEntity<>(updatedCode, HttpStatus.OK);
    }

    @PostMapping(path = "api/code/new", consumes = "application/JSON", produces = "application/JSON")
    public ResponseEntity<String> addCode(@Valid @RequestBody Code code) {
        log.debug("REST request to addCode: {}", code);
        return new ResponseEntity<>(snippetsService.addCode(code), HttpStatus.OK);
    }

    @GetMapping(path = "api/code/latest", produces = "application/JSON")
    public Code[] getLatestCode() {
        log.debug("REST request to getLatestCode");
        return snippetsService.getLatestCode();
    }

}
