package gov.cdc.nbs.me;

import java.util.List;

record Me(long identifier, String firstName, String lastName, List<String> permissions) {

    Me(long identifier, String firstName, String lastName) {
        this(identifier, firstName, lastName, List.of());
    }

}
