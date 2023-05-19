package gov.cdc.nbs.authentication;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.authentication.entity.SecurityLog;
import gov.cdc.nbs.authentication.enums.SecurityEventType;
import gov.cdc.nbs.authentication.repository.SecurityLogRepository;

class AuthorizedUserResolverTest {

    @Mock
    private SecurityLogRepository securityLogRepository;

    @Mock
    private AuthUserRepository authUserRepository;

    @InjectMocks
    private AuthorizedUserResolver resolver;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_resolve_user() {
        when(securityLogRepository.findBySessionIdOrderByEventTimeDesc(Mockito.anyString()))
            .thenReturn(Collections.singletonList(logEntry(SecurityEventType.LOGIN_SUCCESS)));
        when(authUserRepository.findByNedssEntryId(Mockito.anyLong())).thenReturn(authUser());
        var optional = resolver.resolve("test");
        assertTrue(optional.isPresent());
    }

    @Test
    void should_not_resolve_user_logout() {
        when(securityLogRepository.findBySessionIdOrderByEventTimeDesc(Mockito.anyString()))
            .thenReturn(Collections.singletonList(logEntry(SecurityEventType.LOGOUT)));
        when(authUserRepository.findByNedssEntryId(Mockito.anyLong())).thenReturn(authUser());
        
        var optional = resolver.resolve("test");
        assertTrue(optional.isEmpty());
    }

    @Test
    void should_not_resolve_user_login_then_logout() {
        when(securityLogRepository.findBySessionIdOrderByEventTimeDesc(Mockito.anyString()))
            .thenReturn(Arrays.asList(logEntry(SecurityEventType.LOGIN_SUCCESS), logEntry(SecurityEventType.LOGOUT)));
        when(authUserRepository.findByNedssEntryId(Mockito.anyLong())).thenReturn(authUser());

        var optional = resolver.resolve("test");
        assertTrue(optional.isEmpty());
    }

    private Optional<AuthUser> authUser() {
        var user = new AuthUser();
        user.setId(1L);
        user.setUserId("authUserId");
        return Optional.of(user);
    }

    private SecurityLog logEntry(SecurityEventType eventType) {
        return new SecurityLog(null,
                "authUserId",
                eventType,
                null,
                null,
                null,
                1L,
                null,
                null);
    }

}
