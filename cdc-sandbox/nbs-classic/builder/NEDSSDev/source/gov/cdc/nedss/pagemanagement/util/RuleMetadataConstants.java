package gov.cdc.nedss.pagemanagement.util;

/**
 * RuleMetadatConstants - constants used for Page rule
 * @author Pradeep Sharma
 * @Copyright: Copyright (c) 2010
 * @Company: Computer Sciences Corporation
 * Date March 02, 2010
 * @version 4.0
 */
public interface RuleMetadataConstants{
	public static final String SP =" ";
	public static final String EQ =" = ";
	public static final String NEQ =" != ";
	public static final String CB =" ) ";//Closing bracket
	public static final String OB =" ( ";//Opening bracket
	public static final String GT =" > ";
	public static final String LT =" < ";
	public static final String LTEQ =" <= "; //Less than or equal to
	public static final String GTEQ =" >= ";//GT or equal to
	public static final String ADD =" = ";//Add
	public static final String REM =" = ";//Remove
	public static final String OR ="||";
	public static final String AND =" && ";
	public static final String EN =" EN ";//Enable
	public static final String DI =" DI ";//Disable
	public static final String VV =" VV ";//Valid Value
	public static final String VL =" VL ";//Valid Length
	public static final String SEP =" ^ "; 
	public static final String COMP =" == "; //comparator to show left side "="(== in rule code) right side. Used to differentiate 
											// from EQ that can be used multiple times on the left side of the argument.
	public static final String COM =","; 
	public static final String DISABLE ="D"; //Disable
	public static final String ENABLE ="E"; //Enable
	public static final String REQUIRE ="R"; //Enable
	public static final String DATE_COMPARE ="DT"; //Date compare
	public static final String HIDE ="H"; //Hide
	public static final String UNHIDE ="UH"; //UnHide
	public static final String VISIBLE="V"; //visible
	
}