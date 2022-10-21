package gov.cdc.nbs.model;

import lombok.Data;

@Data
public class LoginResponse {
    private final String username;
    private final String token;
}
