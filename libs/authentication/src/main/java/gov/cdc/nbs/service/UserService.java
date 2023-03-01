package gov.cdc.nbs.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import gov.cdc.nbs.config.security.NbsAuthority;
import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityProperties;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.AuthProgAreaAdmin;
import gov.cdc.nbs.entity.odse.AuthUser;
import gov.cdc.nbs.entity.odse.QAuthBusObjRt;
import gov.cdc.nbs.entity.odse.QAuthBusObjType;
import gov.cdc.nbs.entity.odse.QAuthBusOpRt;
import gov.cdc.nbs.entity.odse.QAuthBusOpType;
import gov.cdc.nbs.entity.odse.QAuthPermSet;
import gov.cdc.nbs.entity.odse.QAuthUser;
import gov.cdc.nbs.entity.odse.QAuthUserRole;
import gov.cdc.nbs.entity.srte.QProgramAreaCode;
import gov.cdc.nbs.exception.BadTokenException;
import gov.cdc.nbs.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final Algorithm algorithm;
    private final SecurityProperties properties;
    private final AuthUserRepository authUserRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public NbsUserDetails loadUserByUsername(String username) {
        return authUserRepository
                .findByUserId(username)
                .map(authUser -> buildUserDetails(authUser, createToken(authUser)))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public boolean isAuthorized(final long user, final String... permissions) {
        return authUserRepository.findById(user)
                // introduce query to bypass unnecessary mapping of entire object
                // should only need the authorities or even do the entire check in JPQL
                .map(
                        authUser -> getUserPermissions(authUser)
                                .stream()
                                .anyMatch(allowsAny(permissions)))
                .orElse(false);
    }

    private Predicate<NbsAuthority> allows(final String permission) {
        return authority -> Objects.equals(authority.getAuthority(), permission);
    }

    private Predicate<NbsAuthority> allowsAny(final String... permissions) {
        return Arrays.stream(permissions)
                .map(this::allows)
                .reduce(ignored -> false, Predicate::or);
    }

    /**
     * Lookup AuthUser in database using the JWT subject. Convert database entity to
     * a new JWTUserDetails and return
     */
    @Transactional
    public NbsUserDetails findUserByToken(DecodedJWT jwt) {
        return authUserRepository
                .findByUserId(jwt.getSubject())
                .map(authUser -> buildUserDetails(authUser, createToken(authUser)))
                .orElseThrow(BadTokenException::new);
    }

    private NbsUserDetails buildUserDetails(AuthUser authUser, String token) {
        return NbsUserDetails
                .builder()
                .id(authUser.getId())
                .firstName(authUser.getUserFirstNm())
                .lastName(authUser.getUserLastNm())
                .isMasterSecurityAdmin(authUser.getMasterSecAdminInd().equals('T'))
                .isProgramAreaAdmin(authUser.getProgAreaAdminInd().equals('T'))
                .adminProgramAreas(authUser.getAdminProgramAreas()
                        .stream()
                        .map(AuthProgAreaAdmin::getProgAreaCd)
                        .collect(Collectors.toSet()))
                .username(authUser.getUserId())
                .password(null)
                .authorities(getUserPermissions(authUser))
                .isEnabled(authUser.getRecordStatusCd().equals(RecordStatus.ACTIVE))
                .token(token)
                .build();
    }

    private String createToken(AuthUser user) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(Duration.ofMillis(properties.getTokenExpirationMillis()));
        return JWT
                .create()
                .withIssuer(properties.getTokenIssuer())
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .withSubject(user.getUserId())
                .sign(algorithm);
    }

    /**
     * Queries the database for a list of user permissions
     * For each distinct permission, create a {@link NbsAuthority} if the permission
     * applies.
     *
     * <p>
     * Permissions apply to user if:
     *
     * <ul>
     * <li>user has the 'user' role, and the permission applies to 'user'
     * <li>user has the 'guest' role and the permission applies to 'guest'.
     * </ul>
     * The Authority string is set to BusinessOperation-businessObject,
     * e.g.: VIEW-PATIENT
     */
    private Set<NbsAuthority> getUserPermissions(AuthUser user) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        var authUser = QAuthUser.authUser;
        var authRole = QAuthUserRole.authUserRole;
        var opType = QAuthBusOpType.authBusOpType;
        var objType = QAuthBusObjType.authBusObjType;
        var permSet = QAuthPermSet.authPermSet;
        var objRt = QAuthBusObjRt.authBusObjRt;
        var opRt = QAuthBusOpRt.authBusOpRt;
        var programAreaCode = QProgramAreaCode.programAreaCode;
        var query = queryFactory
                .selectDistinct(
                        authRole.roleGuestInd,
                        opRt.busOpGuestRt,
                        opRt.busOpUserRt,
                        opType.busOpNm,
                        objType.busObjNm,
                        authRole.progAreaCd,
                        programAreaCode.nbsUid,
                        authRole.jurisdictionCd)
                .from(authUser)
                .join(authRole)
                .on(authRole.authUserUid.id.eq(authUser.id))
                .join(permSet)
                .on(authRole.authPermSetUid.id.eq(permSet.id))
                .join(objRt)
                .on(objRt.authPermSetUid.id.eq(permSet.id))
                .join(objType)
                .on(objRt.authBusObjTypeUid.id.eq(objType.id))
                .join(opRt)
                .on(opRt.authBusObjRtUid.id.eq(objRt.id))
                .join(opType)
                .on(opType.id.eq(opRt.authBusOpTypeUid.id))
                .leftJoin(programAreaCode)
                .on(authRole.progAreaCd.eq(programAreaCode.id))
                .where(authUser.id.eq(user.getId()));

        return query.fetch().stream().map(this::buildNbsAuthority).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * Helper method for {@link UserService#getUserPermissions}
     */
    private NbsAuthority buildNbsAuthority(Tuple t) {
        var userIsGuest = t.get(QAuthUserRole.authUserRole.roleGuestInd);
        var guestRight = t.get(QAuthBusOpRt.authBusOpRt.busOpGuestRt);
        var userRight = t.get(QAuthBusOpRt.authBusOpRt.busOpUserRt);
        var businessOperation = t.get(QAuthBusOpType.authBusOpType.busOpNm);
        var businessObject = t.get(QAuthBusObjType.authBusObjType.busObjNm);
        var progArea = t.get(QAuthUserRole.authUserRole.progAreaCd);
        var progAreaCode = t.get(QProgramAreaCode.programAreaCode.nbsUid);
        var jurisdiction = t.get(QAuthUserRole.authUserRole.jurisdictionCd);
        // if current user is assigned 'user' role and perm is valid for 'user' role
        // or current user has 'guest' role and perm is valid for 'guest' role
        if ((userIsGuest.equals('F') && userRight != null && userRight.equals('T'))
                || (userIsGuest.equals('T') && guestRight != null && guestRight.equals('T'))) {
            return NbsAuthority.builder()
                    .businessObject(businessObject)
                    .businessOperation(businessOperation)
                    .programArea(progArea)
                    .programAreaUid(progAreaCode)
                    .jurisdiction(jurisdiction)
                    .authority(businessOperation + "-" + businessObject)
                    .build();
        } else {
            return null;
        }
    }

}
