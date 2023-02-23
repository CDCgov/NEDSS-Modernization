package gov.cdc.nbs.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestStatus {
    private boolean successful;
    private String message;
    private String requestId;
    private Long entityId;
}
