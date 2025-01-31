package gov.cdc.nedss.webapp.nbs.form.util;

import org.apache.struts.action.ActionForm;
import org.apache.struts.validator.ValidatorForm;

import java.util.*;

import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.util.LogUtils;

/**
 * <p>Title: CommonForm </p>
 * <p>Description: This form class is base class for all forms
 * requiring to use LDF.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: CSC</p>
 * @author Shailesh Desai
 * @version 1.0
 */

public class CommonForm  extends ValidatorForm {
	private static final LogUtils logger = new LogUtils(CommonForm.class.getName());
	//This attribute is added for QueueNotification.
	private Map<Object,Object> attributeMap = new HashMap<Object,Object>();
	private ArrayList<Object> ldfCollection  = null;

	private ArrayList<Object> noDataArray = new ArrayList<Object> ();

	public ArrayList<Object> getNoDataArray() {
		return noDataArray;
	}

	public void setNoDataArray(ArrayList<Object> noDataArray) {
		this.noDataArray = noDataArray;
	}
	
	public ArrayList<Object> getLdfCollection() {
          if(ldfCollection  != null && ldfCollection.size()>0)
          {
           Iterator<Object>  it = ldfCollection.iterator();
            while (it.hasNext()) {
              StateDefinedFieldDataDT stateDT = (StateDefinedFieldDataDT) it.next();
              if (stateDT.getBusinessObjNm() == null)
                it.remove();
            }
          }
		return ldfCollection  ;

	}


	public void setLdfCollection(ArrayList<Object>  aLdfCollection) {
		this.ldfCollection  = aLdfCollection;
	}
	/**
	 * retrieve ldf dt from index
	 * @param index
	 * @return
	 */
	public StateDefinedFieldDataDT getLdf(int index) {
		// this should really be in the constructor
		if (this.ldfCollection  == null) {
			this.ldfCollection  = new ArrayList<Object> ();
		}

		int currentSize = this.ldfCollection.size();

		// check if we have a this many organizationNameDTs
		if (index < currentSize) {
			try {
				Object[] tempArray = this.ldfCollection.toArray();

				Object tempObj = tempArray[index];

				StateDefinedFieldDataDT tempDT = (StateDefinedFieldDataDT) tempObj;

				return tempDT;
			}
			catch (Exception e) {
				logger.debug(e);
			} // do nothing just continue
		}
		StateDefinedFieldDataDT tempDT = null;

		for (int i = currentSize; i < index + 1; i++) {
			tempDT = new StateDefinedFieldDataDT();
			this.ldfCollection.add(tempDT);
		}
		return tempDT;
	}

	protected void resetLDF() {
		ldfCollection  = null;
	}


	public Map<Object,Object> getAttributeMap() {
		return attributeMap;
	}


	public void setAttributeMap(Map<Object,Object> attributeMap) {
		this.attributeMap = attributeMap;
	}

}