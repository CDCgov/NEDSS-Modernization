package gov.cdc.nbs.message;

import java.time.Instant;

import gov.cdc.nbs.message.enums.Gender;

public record UpdateSexAndBirth(
		Instant asOf,
		Instant dateOfBirth,
		Gender birthGender,
		Gender currentGender,
		Gender additionalGender,
		Gender transGenderInfo,
		String birthCity,
		String birthCntry,
		String birthState,
		Short birthOrderNbr,
		String multipleBirth,
		String sexunknown,
		String currentAge,
		Instant ageReportedTime
		) {

}
