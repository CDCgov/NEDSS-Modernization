package gov.cdc.nbs.encryption;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/encryption")
class EncryptionController {
  private final EncryptionService encryptionService;

  EncryptionController(final EncryptionService encryptionService) {
    this.encryptionService = encryptionService;
  }

  @PostMapping("/encrypt")
  EncryptionResponse encrypt(@RequestBody Object object) {
    return new EncryptionResponse(encryptionService.encrypt(object));
  }

  @PostMapping(value = "/decrypt", consumes = MediaType.TEXT_PLAIN_VALUE)
  Object decrypt(@RequestBody String encryptedString) {
    return encryptionService.decrypt(encryptedString);
  }
}
