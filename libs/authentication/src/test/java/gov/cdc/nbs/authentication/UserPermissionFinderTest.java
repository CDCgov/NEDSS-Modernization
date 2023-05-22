package gov.cdc.nbs.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.authentication.entity.QAuthBusObjType;
import gov.cdc.nbs.authentication.entity.QAuthBusOpRt;
import gov.cdc.nbs.authentication.entity.QAuthBusOpType;
import gov.cdc.nbs.authentication.entity.QAuthProgramAreaCode;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import gov.cdc.nbs.authentication.entity.QAuthUserRole;
import gov.cdc.nbs.authentication.util.AuthObjectUtil;

class UserPermissionFinderTest {

    @Mock
    private JPAQueryFactory factory;

    @InjectMocks
    private UserPermissionFinder finder;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_find() {
        var tuple = mockJpaQueryCall();
        List<Tuple> results = validPermission();
        when(tuple.fetch()).thenReturn(results);

        // Test
        Set<NbsAuthority> authorities = finder.getUserPermissions(AuthObjectUtil.authUser());

        // Validate
        assertEquals(1, authorities.size());
        var authority = authorities.iterator().next();
        assertEquals("ADD-CT_CONTACT", authority.getAuthority());
        assertEquals("CT_CONTACT", authority.getBusinessObject());
        assertEquals("ADD", authority.getBusinessOperation());
        assertEquals("ALL", authority.getJurisdiction());
        assertEquals("STD", authority.getProgramArea());
        assertEquals(15, authority.getProgramAreaUid());
    }

    @Test
    void should_not_find_because_user_is_guest() {
        var tuple = mockJpaQueryCall();
        List<Tuple> results = nonGuestPermission();
        when(tuple.fetch()).thenReturn(results);

        // Test
        Set<NbsAuthority> authorities = finder.getUserPermissions(AuthObjectUtil.authUser());

        // Validate
        assertEquals(0, authorities.size());
    }

    @Test
    void should_not_find_because_permission_is_guest_only() {
        var tuple = mockJpaQueryCall();
        List<Tuple> results = guestOnlyPermission();
        when(tuple.fetch()).thenReturn(results);

        // Test
        Set<NbsAuthority> authorities = finder.getUserPermissions(AuthObjectUtil.authUser());

        // Validate
        assertEquals(0, authorities.size());
    }

    @SuppressWarnings("unchecked")
    private JPAQuery<Tuple> mockJpaQueryCall() {
        // Mock
        JPAQuery<Tuple> tuple = Mockito.mock(JPAQuery.class);
        var authUser = QAuthUser.authUser;
        var authRole = QAuthUserRole.authUserRole;
        var opType = QAuthBusOpType.authBusOpType;
        var objType = QAuthBusObjType.authBusObjType;
        var opRt = QAuthBusOpRt.authBusOpRt;
        var authProgramAreaCode = QAuthProgramAreaCode.authProgramAreaCode;
        when(factory.selectDistinct(authRole.roleGuestInd,
                opRt.busOpGuestRt,
                opRt.busOpUserRt,
                opType.busOpNm,
                objType.busObjNm,
                authRole.progAreaCd,
                authProgramAreaCode.nbsUid,
                authRole.jurisdictionCd)).thenReturn(tuple);

        when(tuple.from(authUser)).thenReturn(tuple);
        when(tuple.join(Mockito.any(EntityPathBase.class))).thenReturn(tuple);
        when(tuple.leftJoin(Mockito.any(EntityPathBase.class))).thenReturn(tuple);
        when(tuple.on(Mockito.any(BooleanExpression.class))).thenReturn(tuple);
        when(tuple.where(Mockito.any(BooleanExpression.class))).thenReturn(tuple);
        return tuple;
    }

    private List<Tuple> validPermission() {
        Tuple mockTuple = Mockito.mock(Tuple.class);
        when(mockTuple.get(QAuthUserRole.authUserRole.roleGuestInd)).thenReturn('T');
        when(mockTuple.get(QAuthBusOpRt.authBusOpRt.busOpGuestRt)).thenReturn('T');
        when(mockTuple.get(QAuthBusOpRt.authBusOpRt.busOpUserRt)).thenReturn('T');
        when(mockTuple.get(QAuthBusOpType.authBusOpType.busOpNm)).thenReturn("ADD");
        when(mockTuple.get(QAuthBusObjType.authBusObjType.busObjNm)).thenReturn("CT_CONTACT");
        when(mockTuple.get(QAuthUserRole.authUserRole.progAreaCd)).thenReturn("STD");
        when(mockTuple.get(QAuthProgramAreaCode.authProgramAreaCode.nbsUid)).thenReturn(15);
        when(mockTuple.get(QAuthUserRole.authUserRole.jurisdictionCd)).thenReturn("ALL");
        return Collections.singletonList(mockTuple);
    }

    private List<Tuple> nonGuestPermission() {
        Tuple mockTuple = Mockito.mock(Tuple.class);
        when(mockTuple.get(QAuthUserRole.authUserRole.roleGuestInd)).thenReturn('T');
        when(mockTuple.get(QAuthBusOpRt.authBusOpRt.busOpGuestRt)).thenReturn('F');
        when(mockTuple.get(QAuthBusOpRt.authBusOpRt.busOpUserRt)).thenReturn('T');
        when(mockTuple.get(QAuthBusOpType.authBusOpType.busOpNm)).thenReturn("ADD");
        when(mockTuple.get(QAuthBusObjType.authBusObjType.busObjNm)).thenReturn("CT_CONTACT");
        when(mockTuple.get(QAuthUserRole.authUserRole.progAreaCd)).thenReturn("STD");
        when(mockTuple.get(QAuthProgramAreaCode.authProgramAreaCode.nbsUid)).thenReturn(15);
        when(mockTuple.get(QAuthUserRole.authUserRole.jurisdictionCd)).thenReturn("ALL");
        return Collections.singletonList(mockTuple);
    }

    private List<Tuple> guestOnlyPermission() {
        Tuple mockTuple = Mockito.mock(Tuple.class);
        when(mockTuple.get(QAuthUserRole.authUserRole.roleGuestInd)).thenReturn('F');
        when(mockTuple.get(QAuthBusOpRt.authBusOpRt.busOpGuestRt)).thenReturn('T');
        when(mockTuple.get(QAuthBusOpRt.authBusOpRt.busOpUserRt)).thenReturn('F');
        when(mockTuple.get(QAuthBusOpType.authBusOpType.busOpNm)).thenReturn("ADD");
        when(mockTuple.get(QAuthBusObjType.authBusObjType.busObjNm)).thenReturn("CT_CONTACT");
        when(mockTuple.get(QAuthUserRole.authUserRole.progAreaCd)).thenReturn("STD");
        when(mockTuple.get(QAuthProgramAreaCode.authProgramAreaCode.nbsUid)).thenReturn(15);
        when(mockTuple.get(QAuthUserRole.authUserRole.jurisdictionCd)).thenReturn("ALL");
        return Collections.singletonList(mockTuple);
    }


}
