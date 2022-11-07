package gov.cdc.nbs.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.cdc.nbs.model.EncryptionResponse;
import gov.cdc.nbs.service.EncryptionService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/encryption")
@RequiredArgsConstructor
public class EncryptionController {
    private final EncryptionService encryptionService;

    @PostMapping("/encrypt")
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public EncryptionResponse encrypt(@RequestBody Object object) {
        return encryptionService.handleEncryption(object);
    }

    @PostMapping("/decrypt")
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public Object decrypt(@RequestBody String encryptedString) {
        return encryptionService.handleDecryption(encryptedString);
    }
}
