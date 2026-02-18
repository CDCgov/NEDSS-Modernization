package gov.cdc.nbs.authentication.token;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import gov.cdc.nbs.authentication.NBSToken;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;

@Component
public class NBSTokenValidator {
  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer ";

  private final JWTVerifier verifier;

  public NBSTokenValidator(final JWTVerifier verifier) {
    this.verifier = verifier;
  }

  // Check for an `Authorization` header or nbs_token cookie, return the status of the token
  public TokenValidation validate(final HttpServletRequest request) {
    Optional<String> token =
        resolveTokenFromAuth(request).or(() -> resolveTokenFromCookie(request));

    if (token.isPresent()) {
      try {
        DecodedJWT decoded = verifier.verify(token.get());
        return new TokenValidation(TokenStatus.VALID, decoded.getSubject());
      } catch (TokenExpiredException ex) {
        return new TokenValidation(TokenStatus.EXPIRED);
      } catch (JWTVerificationException ex) {
        return new TokenValidation(TokenStatus.INVALID);
      }
    } else {
      return new TokenValidation(TokenStatus.UNSET);
    }
  }

  // Checks for the presence of a token in the nbs_token cookie
  private Optional<String> resolveTokenFromCookie(final HttpServletRequest request) {
    return NBSToken.resolve(request.getCookies()).map(NBSToken::value);
  }

  // Checks for the presence of an Authorization header and pulls the token from that
  private Optional<String> resolveTokenFromAuth(final HttpServletRequest request) {
    try {
      return Optional.ofNullable(request.getHeader(AUTHORIZATION))
          .filter(Predicate.not(String::isBlank))
          .map(s -> s.substring(BEARER.length()));
    } catch (StringIndexOutOfBoundsException ex) {
      return Optional.empty();
    }
  }

  public record TokenValidation(TokenStatus status, String user) {
    public TokenValidation(TokenStatus status) {
      this(status, null);
    }
  }

  public enum TokenStatus {
    VALID,
    EXPIRED,
    INVALID,
    UNSET
  }
}
