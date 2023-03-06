package gov.cdc.nbs.message;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnvelopeRequest {
    String requestId;
    List<TemplateInput> vars;

    public static EnvelopeRequest createEnvelope(String requestId, List<TemplateInput> vars) {
        return new EnvelopeRequest(requestId, vars);
    }

    public String getRequestId() {
        return this.requestId;
    }
}
