package gov.cdc.nbs.authorization;

import gov.cdc.nbs.authentication.NBSToken;
import gov.cdc.nbs.authentication.SessionCookie;
import gov.cdc.nbs.authentication.TokenCreator;
import gov.cdc.nbs.authentication.entity.SecurityLog;
import gov.cdc.nbs.authentication.enums.SecurityEventType;
import gov.cdc.nbs.authentication.repository.SecurityLogRepository;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.authorization.ActiveUserMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Transactional
public class AuthorizationSteps {

  @Autowired
  TokenCreator tokenCreator;

  @Autowired
  SecurityLogRepository securityLogRepository;

  @Autowired
  Active<SessionCookie> activeSession;

  @Autowired
  Active<ActiveUser> activeUser;

  @Autowired
  ActiveUserMother mother;

  @Before
  public void reset() {
    activeSession.reset();
    mother.reset();
  }

  @Given("I am logged into NBS")
  @Given("I am logged into NBS and a security log entry exists")
  public void i_am_logged_into_nbs_and_a_security_log_entry_exists() {
    // make sure user exists
    ActiveUser user = mother.create();

    // insert security_log event with sessionId
    String session = RandomUtil.getRandomString(40);

    SecurityLog log = new SecurityLog();
    log.setId(securityLogRepository.getMaxId() + 1);
    log.setEventTypeCd(SecurityEventType.LOGIN_SUCCESS);
    log.setEventTime(Instant.now());
    log.setSessionId(session);
    log.setNedssEntryId(user.nedssEntry());
    log.setFirstNm("Integration");
    log.setLastNm("Testing");
    securityLogRepository.save(log);

    NBSToken token = this.tokenCreator.forUser(user.username());

    ActiveUser currentUser = new ActiveUser(user.id(), user.username(), user.nedssEntry(), token);
    activeUser.active(currentUser);
    activeSession.active(new SessionCookie(session));
  }

  @Given("A sessionId is not set")
  public void a_session_id_is_not_set() {
    activeSession.reset();
    activeUser.reset();
  }
}
