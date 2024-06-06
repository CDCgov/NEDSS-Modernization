package gov.cdc.nbs.deduplication.blocking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.deduplication.blocking.request.BlockRequest.BlockTransformer;
import gov.cdc.nbs.deduplication.blocking.resolvers.AddressResolver;
import gov.cdc.nbs.deduplication.blocking.resolvers.BirthDateResolver;
import gov.cdc.nbs.deduplication.blocking.resolvers.FirstNameResolver;
import gov.cdc.nbs.deduplication.blocking.resolvers.GenderResolver;
import gov.cdc.nbs.deduplication.blocking.resolvers.IdentificationResolver;
import gov.cdc.nbs.deduplication.blocking.resolvers.LastNameResolver;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse;

@RestController
@RequestMapping("/block")
public class BlockController {

  private final FirstNameResolver firstNameResolver;
  private final LastNameResolver lastNameResolver;
  private final BirthDateResolver birthDateResolver;
  private final GenderResolver genderResolver;
  private final AddressResolver addressResolver;
  private final IdentificationResolver identificationResolver;

  public BlockController(
      final FirstNameResolver firstNameResolver,
      final LastNameResolver lastNameResolver,
      final BirthDateResolver birthDateResolver,
      final GenderResolver genderResolver,
      final AddressResolver addressResolver,
      final IdentificationResolver identificationResolver) {
    this.firstNameResolver = firstNameResolver;
    this.lastNameResolver = lastNameResolver;
    this.birthDateResolver = birthDateResolver;
    this.genderResolver = genderResolver;
    this.addressResolver = addressResolver;
    this.identificationResolver = identificationResolver;
  }

  @GetMapping("/firstName")
  public BlockResponse getFirstNameBlock(
      @RequestParam BlockTransformer transformer,
      @RequestParam String value) {
    return firstNameResolver.resolve(transformer, value);
  }

  @GetMapping("/lastName")
  public BlockResponse getLastNameBlock(
      @RequestParam BlockTransformer transformer,
      @RequestParam String value) {
    return lastNameResolver.resolve(transformer, value);
  }

  @GetMapping("/birthdate")
  public BlockResponse getBirthdateBlock(
      @RequestParam String value) {
    return birthDateResolver.resolve(value);
  }

  @GetMapping("/gender")
  public BlockResponse getGenderBlock(
      @RequestParam String value) {
    return genderResolver.resolve(value);
  }

  @GetMapping("/streetAddress")
  public BlockResponse getStreetAddressBlock(
      @RequestParam BlockTransformer transformer,
      @RequestParam String value) {
    return addressResolver.resolveStreetAddressBlock(transformer, value);
  }

  @GetMapping("/city")
  public BlockResponse getCityBlock(
      @RequestParam BlockTransformer transformer,
      @RequestParam String value) {
    return addressResolver.resolveCityBlock(transformer, value);
  }

  @GetMapping("/state")
  public BlockResponse getStateBlock(
      @RequestParam BlockTransformer transformer,
      @RequestParam String value) {
    return addressResolver.resolveStateBlock(transformer, value);
  }

  @GetMapping("/zip")
  public BlockResponse getZipBlock(
      @RequestParam BlockTransformer transformer,
      @RequestParam String value) {
    return addressResolver.resolveZipBlock(transformer, value);
  }

  @GetMapping("/identification")
  public BlockResponse getIdentificationBlock(
      @RequestParam BlockTransformer transformer,
      @RequestParam String identificationType,
      @RequestParam String value) {
    return identificationResolver.resolve(transformer, identificationType, value);
  }
}
