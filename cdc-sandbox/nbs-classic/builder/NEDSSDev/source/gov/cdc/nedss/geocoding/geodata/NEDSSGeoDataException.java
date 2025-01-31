package gov.cdc.nedss.geocoding.geodata;

import gov.cdc.nedss.geocoding.exception.NEDSSGeoCodingException;

public class NEDSSGeoDataException extends NEDSSGeoCodingException {
	private static final long serialVersionUID = 1L;
	public NEDSSGeoDataException() {
		super();
	}
	public NEDSSGeoDataException(String errorCd) {
		super(errorCd);
	}
}
