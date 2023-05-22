package gov.cdc.nbs.authentication;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.QAuthBusObjRt;
import gov.cdc.nbs.authentication.entity.QAuthBusObjType;
import gov.cdc.nbs.authentication.entity.QAuthBusOpRt;
import gov.cdc.nbs.authentication.entity.QAuthBusOpType;
import gov.cdc.nbs.authentication.entity.QAuthPermSet;
import gov.cdc.nbs.authentication.entity.QAuthProgramAreaCode;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import gov.cdc.nbs.authentication.entity.QAuthUserRole;

@Component
public class UserPermissionFinder {
    private final JPAQueryFactory queryFactory;

    public UserPermissionFinder(final JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * Queries the database for a list of user permissions For each distinct permission, create a {@link NbsAuthority}
     * if the permission applies.
     *
     * <p>
     * Permissions apply to user if:
     *
     * <ul>
     * <li>user has the 'user' role, and the permission applies to 'user'
     * <li>user has the 'guest' role and the permission applies to 'guest'.
     * </ul>
     * The Authority string is set to BusinessOperation-businessObject, e.g.: VIEW-PATIENT
     */
    public Set<NbsAuthority> getUserPermissions(AuthUser user) {
        return buildUserPermissionQuery(user)
                .fetch()
                .stream()
                .map(this::buildNbsAuthority)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private JPAQuery<Tuple> buildUserPermissionQuery(AuthUser user) {
        var authUser = QAuthUser.authUser;
        var authRole = QAuthUserRole.authUserRole;
        var opType = QAuthBusOpType.authBusOpType;
        var objType = QAuthBusObjType.authBusObjType;
        var permSet = QAuthPermSet.authPermSet;
        var objRt = QAuthBusObjRt.authBusObjRt;
        var opRt = QAuthBusOpRt.authBusOpRt;
        var authProgramAreaCode = QAuthProgramAreaCode.authProgramAreaCode;
        return queryFactory
                .selectDistinct(
                        authRole.roleGuestInd,
                        opRt.busOpGuestRt,
                        opRt.busOpUserRt,
                        opType.busOpNm,
                        objType.busObjNm,
                        authRole.progAreaCd,
                        authProgramAreaCode.nbsUid,
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
                .leftJoin(authProgramAreaCode)
                .on(authRole.progAreaCd.eq(authProgramAreaCode.id))
                .where(authUser.id.eq(user.getId()));
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
        var progAreaCode = t.get(QAuthProgramAreaCode.authProgramAreaCode.nbsUid);
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
