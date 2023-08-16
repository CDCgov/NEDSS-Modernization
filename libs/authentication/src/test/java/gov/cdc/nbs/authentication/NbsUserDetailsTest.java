package gov.cdc.nbs.authentication;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class NbsUserDetailsTest {

    @Test
    void has_permission() {
        NbsAuthority authority = NbsAuthority.builder()
                .businessObject("TestObject")
                .businessOperation("TestOperation")
                .build();
        NbsUserDetails userDetails = NbsUserDetails.builder()
                .authorities(Collections.singleton(authority))
                .build();

        assertTrue(userDetails.hasPermission("TestObject", "TestOperation"));
    }

    @Test
    void does_not_have_permission() {
        NbsAuthority authority = NbsAuthority.builder()
                .businessObject("Wrong Permission")
                .businessOperation("TestOperation")
                .build();
        NbsUserDetails userDetails = NbsUserDetails.builder()
                .authorities(Collections.singleton(authority))
                .build();

        assertFalse(userDetails.hasPermission("TestObject", "TestOperation"));
    }

    @Test
    void does_not_have_permission_null() {
        NbsUserDetails userDetails = NbsUserDetails.builder().build();

        assertFalse(userDetails.hasPermission("TestObject", "TestOperation"));
    }
}
