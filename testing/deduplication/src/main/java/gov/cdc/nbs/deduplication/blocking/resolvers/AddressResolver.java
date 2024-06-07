package gov.cdc.nbs.deduplication.blocking.resolvers;

import org.springframework.stereotype.Component;
import gov.cdc.nbs.deduplication.blocking.request.BlockTransformer;
import gov.cdc.nbs.deduplication.blocking.response.BlockResponse;

@Component
public class AddressResolver {
  private final StreetAddressResolver streetAddressResolver;
  private final CityResolver cityResolver;
  private final StateResolver stateResolver;
  private final ZipResolver zipResolver;

  public AddressResolver(
      final StreetAddressResolver streetAddressResolver,
      final CityResolver cityResolver,
      final StateResolver stateResolver,
      final ZipResolver zipResolver) {
    this.streetAddressResolver = streetAddressResolver;
    this.cityResolver = cityResolver;
    this.stateResolver = stateResolver;
    this.zipResolver = zipResolver;
  }

  public BlockResponse resolveStreetAddressBlock(BlockTransformer transformer, String value) {
    return streetAddressResolver.resolve(transformer, value);
  }

  public BlockResponse resolveCityBlock(BlockTransformer transformer, String value) {
    return cityResolver.resolve(transformer, value);
  }

  public BlockResponse resolveStateBlock(BlockTransformer transformer, String value) {
    return stateResolver.resolve(transformer, value);
  }

  public BlockResponse resolveZipBlock(BlockTransformer transformer, String value) {
    return zipResolver.resolve(transformer, value);
  }
}
