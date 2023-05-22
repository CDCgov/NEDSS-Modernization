package gov.cdc.nbs.authorization;

import gov.cdc.nbs.authentication.entity.AuthUserRepository;
import gov.cdc.nbs.authentication.entity.SecurityLog;
import gov.cdc.nbs.authentication.enums.SecurityEventType;
import gov.cdc.nbs.authentication.repository.SecurityLogRepository;
import gov.cdc.nbs.support.TestActive;
import gov.cdc.nbs.support.UserMother;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.support.util.UserUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

public class AuthorizationSteps {

    @Autowired
    SecurityLogRepository securityLogRepository;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    TestActive<SessionCookie> activeSession;

    @Autowired
    TestActiveUser activeUser;

    @Before
    public void reset() {
        activeSession.reset();
        activeUser.reset();
    }

    @Given("I am logged into NBS and a security log entry exists")
    public void i_am_logged_into_nbs_and_a_security_log_entry_exists() {
        // make sure user exists
        var user = UserMother.clerical();
        UserUtil.insertIfNotExists(user, authUserRepository);
        // insert security_log event with sessionId
        String session = RandomUtil.getRandomString(40);

        var log = new SecurityLog();
        log.setId(securityLogRepository.getMaxId() + 1);
        log.setEventTypeCd(SecurityEventType.LOGIN_SUCCESS);
        log.setEventTime(Instant.now());
        log.setSessionId(session);
        log.setNedssEntryId(user.getNedssEntryId());
        log.setFirstNm(user.getUserFirstNm());
        log.setLastNm(user.getUserLastNm());
        securityLogRepository.save(log);


        activeUser.active(user.getUserId());
        activeSession.active(new SessionCookie(session));
    }

    @Given("A sessionId is not set")
    public void a_session_id_is_not_set() {
        activeSession.reset();
        activeUser.reset();
    }
}
