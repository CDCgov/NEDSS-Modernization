package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
class PersonEntityIdsResolver {

    @SchemaMapping(typeName = "Person", field = "entityIds")
    List<EntityId> resolve(final Person person) {
        //  EntityIds was moved from Person to NBSEntity.  Clients expect the identifications to be accessible via the
        //  entityIds field.  This resolver ensures the data is where the client expects.
        return person.identifications();
    }

}
