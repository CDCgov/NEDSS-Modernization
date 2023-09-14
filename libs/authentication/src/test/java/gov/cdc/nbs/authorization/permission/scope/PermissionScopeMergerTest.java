package gov.cdc.nbs.authorization.permission.scope;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PermissionScopeMergerTest {

    @Test
    void should_merge_area_scopes() {

        PermissionScopeMerger merger = new PermissionScopeMerger();

        PermissionScope actual = merger.merge(
            new PermissionScope(List.of(2699L, 2579L), List.of()),
            new PermissionScope(List.of(3251L, 1783L, 1409L), List.of())
        );

        assertThat(actual.any()).contains(2699L, 2579L, 3251L, 1783L, 1409L);
    }

    @Test
    void should_merge_shared_scopes() {

        PermissionScopeMerger merger = new PermissionScopeMerger();

        PermissionScope actual = merger.merge(
            new PermissionScope(List.of(), List.of(2699L, 2579L)),
            new PermissionScope(List.of(), List.of(3251L, 1783L, 1409L))
        );

        assertThat(actual.shared()).contains(2699L, 2579L, 3251L, 1783L, 1409L);
    }

    @Test
    void should_merge_area_and_shared_scopes() {

        PermissionScopeMerger merger = new PermissionScopeMerger();

        PermissionScope actual = merger.merge(
            new PermissionScope(List.of(2239L), List.of(1993L, 1381L)),
            new PermissionScope(List.of(3251L, 1783L), List.of( 1409L))
        );

        assertThat(actual.any()).contains(2239L, 3251L, 1783L);
        assertThat(actual.shared()).contains(1993L, 1381L, 1409L);
    }
}
