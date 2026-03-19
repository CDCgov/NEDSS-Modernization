package gov.cdc.nbs.patient.identifier;

import java.util.OptionalLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class PatientShortIdentifierResolver {

  private final PatientIdentifierSettings settings;
  private final Pattern pattern;

  public PatientShortIdentifierResolver(final PatientIdentifierSettings settings) {
    this.settings = settings;
    String regex = "(?:%s)(?<id>\\d+)(?:%s)".formatted(settings.type(), settings.suffix());
    this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
  }

  public OptionalLong resolve(final String local) {
    Matcher matcher = this.pattern.matcher(local);

    return (matcher.matches())
        ? OptionalLong.of(resolveFromMatcher(matcher))
        : OptionalLong.empty();
  }

  private long resolveFromMatcher(final Matcher matcher) {
    long number = Long.parseLong(matcher.group(1));
    return number - settings.initial();
  }
}
