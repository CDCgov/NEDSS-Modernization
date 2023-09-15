package gov.cdc.nbs;

import gov.cdc.nbs.authentication.NBSUserDetailsResolver;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authorization.ActiveUser;
import gov.cdc.nbs.support.TestActive;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.function.Supplier;

@Component
public class TestAuthentication {


    private final TestActive<ActiveUser> active;
    private final NBSUserDetailsResolver resolver;
    private final EntityManager entityManager;

    public TestAuthentication(
        final TestActive<ActiveUser> active,
        final NBSUserDetailsResolver resolver,
        final EntityManager entityManager
    ) {
        this.active = active;
        this.resolver = resolver;
        this.entityManager = entityManager;
    }

    public void reset() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void apply() {
        ActiveUser activeUser = this.active.active();

        AuthUser authUser = this.entityManager.find(AuthUser.class, activeUser.id());

        NbsUserDetails details = resolver.resolve(authUser, activeUser.token().value());

        PreAuthenticatedAuthenticationToken authentication =
            new PreAuthenticatedAuthenticationToken(
                details,
                null,
                details.getAuthorities()
            );

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    /**
     * Executes the given {@code action} ensuring that the {@link SecurityContextHolder} is configured to be
     * authenticated with the Active User.
     *
     * @param action The action to perform while authenticated.
     * @param <T>    The type of the return value of the {@code action}
     * @return The result of the action
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T authenticated(final Supplier<T> action) {
        apply();

        try {
            return action.get();
        } finally {
            reset();
        }

    }
}
