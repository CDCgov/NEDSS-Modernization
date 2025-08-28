package gov.cdc.nbs.soundex;

import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import org.apache.commons.codec.language.Soundex;
import org.springframework.stereotype.Component;

@Component
class ApacheSoundexResolver implements SoundexResolver {

  @Override
  public String resolve(final String value) {
    if (value == null || value.isEmpty()) {
      return null;
    }

    try {
      return Soundex.US_ENGLISH.encode(value);

    } catch (IllegalArgumentException e) {
      //  contains a character that is not mapped.
      return null;
    }


  }
}
