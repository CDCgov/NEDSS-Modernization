/*
 * Created on Feb 4, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.dt;


import java.util.Comparator;

/**
 * @author xzheng
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DefinedFieldMetaDataDisplayOrderComparator implements Comparator {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		if(arg0 == null && arg1 == null) {
			return 0;
		}
		if(arg0 == null && arg1 != null) {
			// null appears last
			return 1;
		}
		if(arg0 != null && arg1 == null) {
			// null appears last
			return -1;
		}
		Integer dispNbr0 = ((StateDefinedFieldMetaDataDT)arg0).getDisplayOrderNbr();
		Integer dispNbr1 = ((StateDefinedFieldMetaDataDT)arg1).getDisplayOrderNbr();

		if(dispNbr0 == null && dispNbr1 == null) {
			return 0;
		}
		if(dispNbr0 == null && dispNbr1 != null) {
			// null appears last
			return 1;
		}
		if(dispNbr0 != null && dispNbr1 == null) {
			// null appears last
			return -1;
		}
		if(dispNbr0.intValue() > dispNbr1.intValue()){
			return 1;
		}
		if(dispNbr0.intValue() < dispNbr1.intValue()){
			return -1;
		}
		return 0;
	}

}
