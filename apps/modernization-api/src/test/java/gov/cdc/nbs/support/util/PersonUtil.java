package gov.cdc.nbs.support.util;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;

import java.util.List;
import java.util.stream.Collectors;

public class PersonUtil {

    public static List<TeleLocator> getTeleLocators(Person person) {
        return person.getNbsEntity().getEntityLocatorParticipations().stream()
                .filter(TeleEntityLocatorParticipation.class::isInstance)
                .map(elp -> (TeleLocator) elp.getLocator())
                .collect(Collectors.toList());
    }


    public static List<PostalLocator> getPostalLocators(Person person) {
        return person.getNbsEntity().getEntityLocatorParticipations().stream()
                .filter(PostalEntityLocatorParticipation.class::isInstance)
                .map(elp -> (PostalLocator) elp.getLocator())
                .collect(Collectors.toList());
    }


}
