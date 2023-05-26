package gov.cdc.nbs.questionbank.kafka.message.util;

public class Constants {
	private Constants() {}
	// Prefix Application ID associated with Kafka requestID
	public static final String APP_ID = "QUESTION-BANK";

	// question deleted aka marked as inactive
	public static final String DELETE_SUCCESS_MESSAGE = "Question was successfully made inactive";

	// was unable to delete aka mark a question as inactive
	public static final String DELETE_FAILURE_MESSAGE = "Was not able to inactive question";

}
