package gov.cdc.nbs.questionbank.valueset.util;

public class ValueSetConstants {

  private ValueSetConstants() {}

  // create value set messages
  public static final String VALUE_SET_NAME_EXISTS = "This Value Set Name Already Exists";

  public static final String CODE_SET_GRP_TEXT_NAME_EXISTS =
      "CodeSet MetaData Short Description Text and Name Exists For Value Set";
  public static final String CODE_SET_TYPE_NOT_VALID = "ValueSet Type Not Valid";

  public static final String SUCCESS_MESSAGE = "Successfully created Value Set";

  public static final String CREATE_CLASS_CD = "code_value_general";

  //  value set state manager messages
  public static final String EMPTY_CODE_SET_NM = "Provided CodeSetNm cannot be null";
  public static final String DELETE_SUCCESS_MESSAGE = "ValueSet made inactive.";
  public static final String DELETE_FAILURE_MESSAGE = "Was not able to inactivate valueset";
  public static final String ACTIVATE_SUCCESS_MESSAGE = "ValueSet made active.";
  public static final String ACTIVATE_FAILURE_MESSAGE = "Was not able to activate valueset";

  // update value set messages
  public static final String CODE_SET_NOT_FOUND = "A value set not found for given codeSetNm";
  public static final String UPDATE_SUCCESS_MESSAGE = "ValueSet updated.";
  public static final String UPDATE_FAILURE_MESSAGE = "Was not able to update valueset";
}
