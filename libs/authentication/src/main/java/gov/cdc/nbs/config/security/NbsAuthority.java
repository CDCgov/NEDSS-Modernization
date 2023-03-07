package gov.cdc.nbs.config.security;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Used in place of default
 * {@link org.springframework.security.core.authority.SimpleGrantedAuthority#SimpleGrantedAuthority
 * SimpleGrantedAuthority}. The expanded functionality allows us to capture relevant security information such as
 * program area and jurisdiction
 */
@Getter
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode
public class NbsAuthority implements GrantedAuthority {
    private final String businessOperation;
    private final String businessObject;
    private final String programArea;
    private final Integer programAreaUid;
    private final String jurisdiction;
    private final String authority;
}
