package gov.cdc.nbs.support.util;

import gov.cdc.nbs.entity.odse.AuthUser;
import gov.cdc.nbs.repository.AuthUserRepository;

public class UserUtil {
    public static AuthUser insertIfNotExists(AuthUser user, AuthUserRepository repository) {
        var existingUser = repository.findByUserId(user.getUserId());
        if (existingUser.isPresent()) {
            return existingUser.get();
        } else {
            return repository.save(user);
        }
    }
}
