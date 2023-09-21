package gov.cdc.nbs.patient.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;

public class PatientHelper {
	
	private PatientHelper() {}
	
	public static List<Person> distinctNumbers(List<Person> input) {
		input.stream().forEach(aPerson -> {
			List<EntityLocatorParticipation> results = new ArrayList<>();
			Map<String, TeleEntityLocatorParticipation> mapping = new HashMap<>();
			List<EntityLocatorParticipation> entityLocators = aPerson.getNbsEntity().getEntityLocatorParticipations();
			for (EntityLocatorParticipation one : entityLocators) {
				if (aPerson.getNbsEntity().isPhoneNumber(one)) {
					TeleEntityLocatorParticipation number = (TeleEntityLocatorParticipation) one;
					TeleLocator pl = number.getLocator();
					if (!mapping.containsKey(pl.getPhoneNbrTxt())) {
						results.add(number);
						mapping.put(pl.getPhoneNbrTxt(), number);
					}
				} else {
					results.add(one);

				}

			}
			aPerson.getNbsEntity().getEntityLocatorParticipations().clear();
			aPerson.getNbsEntity().getEntityLocatorParticipations().addAll(results);
		});

		return input;
	}


}
