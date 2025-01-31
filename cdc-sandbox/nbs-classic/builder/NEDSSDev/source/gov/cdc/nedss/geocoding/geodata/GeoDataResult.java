package gov.cdc.nedss.geocoding.geodata;

/**
 * Encapsulates a geodata result including output addresses
 * and geodata provider return value.
 * 
 * @author rdodge
 *
 */
public class GeoDataResult {

	// Constants //
	public static final String RESULT_TYPE_NONE = "N";
	public static final String RESULT_TYPE_ADDRESS_MATCH = "A";
	public static final String RESULT_TYPE_ZIP_MATCH = "Z";
	public static final String RESULT_TYPE_ERROR = "E";

	public static final String MATCH_COUNT_CLASS_ZERO = "Z";
	public static final String MATCH_COUNT_CLASS_SINGLE = "S";
	public static final String MATCH_COUNT_CLASS_MULTI = "M";

	// Members //
	protected GeoDataOutputAddress[] outputAddresses;
	protected int numOutputAddresses;
	protected int returnValue;

	protected String resultType;
	protected String matchCountClass;

	public int getNumOutputAddresses() {
		return numOutputAddresses;
	}

	/** Default constructor. */
	public GeoDataResult() {
		setResultTypeNone();
	}

	public GeoDataOutputAddress[] getOutputAddresses() {
		return outputAddresses;
	}
	public void setOutputAddresses(GeoDataOutputAddress[] outputAddresses) {
		this.outputAddresses = outputAddresses;
		this.numOutputAddresses = outputAddresses.length;
	}
	public int getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}

	public String getResultType() {
		return resultType;
	}

	/** True iff result type is None. */
	public boolean isResultTypeNone() {
		return RESULT_TYPE_NONE.equals(resultType);
	}

	/** True iff result type is Address Match. */
	public boolean isResultTypeAddressMatch() {
		return RESULT_TYPE_ADDRESS_MATCH.equals(resultType);
	}

	/** True iff result type is Zip Match. */
	public boolean isResultTypeZipMatch() {
		return RESULT_TYPE_ZIP_MATCH.equals(resultType);
	}

	/** True iff result type is Error. */
	public boolean isResultTypeError() {
		return RESULT_TYPE_ERROR.equals(resultType);
	}


	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	/** Sets Result Type to None. */
	public void setResultTypeNone() {
		setResultType(RESULT_TYPE_NONE);
	}

	/** Sets Result Type to None. */
	public void setResultTypeAddressMatch() {
		setResultType(RESULT_TYPE_ADDRESS_MATCH);
	}

	/** Sets Result Type to None. */
	public void setResultTypeZipMatch() {
		setResultType(RESULT_TYPE_ZIP_MATCH);
	}

	/** Sets Result Type to None. */
	public void setResultTypeError() {
		setResultType(RESULT_TYPE_ERROR);
	}

	public String getMatchCountClass() {
		return matchCountClass;
	}

	/** True iff match count class is Zero. */
	public boolean isMatchCountClassZero() {
		return MATCH_COUNT_CLASS_ZERO.equals(matchCountClass);
	}

	/** True iff match count class is Single. */
	public boolean isMatchCountClassSingle() {
		return MATCH_COUNT_CLASS_SINGLE.equals(matchCountClass);
	}

	/** True iff match count class is Multi. */
	public boolean isMatchCountClassMulti() {
		return MATCH_COUNT_CLASS_MULTI.equals(matchCountClass);
	}

	public void setMatchCountClass(String matchCountClass) {
		this.matchCountClass = matchCountClass;
	}

	/** Sets match count class to Zero. */
	public void setMatchCountClassZero() {
		setMatchCountClass(MATCH_COUNT_CLASS_ZERO);
	}

	/** Sets match count class to Single. */
	public void setMatchCountClassSingle() {
		setMatchCountClass(MATCH_COUNT_CLASS_SINGLE);
	}

	/** Sets match count class to Multi. */
	public void setMatchCountClassMulti() {
		setMatchCountClass(MATCH_COUNT_CLASS_MULTI);
	}
}
