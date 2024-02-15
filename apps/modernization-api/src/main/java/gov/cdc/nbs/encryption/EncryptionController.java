package gov.cdc.nbs.encryption;

import io.swagger.annotations.ApiImplicitParam;
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
  @ApiImplicitParam(
      name = "Authorization",
      required = true,
      paramType = "header",
      dataTypeClass = String.class)
  EncryptionResponse encrypt(@RequestBody Object object) {
    return new EncryptionResponse(encryptionService.handleEncryption(object));
  }

  @PostMapping("/decrypt")
  @ApiImplicitParam(
      name = "Authorization",
      required = true,
      paramType = "header",
      dataTypeClass = String.class)
  Object decrypt(@RequestBody String encryptedString) {
    return encryptionService.handleDecryption(encryptedString);
  }
}
