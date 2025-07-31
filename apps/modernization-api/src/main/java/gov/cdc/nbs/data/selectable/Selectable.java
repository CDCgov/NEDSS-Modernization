package gov.cdc.nbs.data.selectable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Selectable(
    @JsonProperty(required = true)
    String value,
    @JsonProperty(required = true)
    String name
) {
    public Selectable {
        name = name == null ? value : name;
    }

    public static Optional<String> maybeValue(final Selectable selectable) {
        return selectable == null ? Optional.empty() : Optional.of(selectable.value);
    }
}
