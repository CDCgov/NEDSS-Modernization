package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.systemservice.util.DropDownCodeDT;

import java.util.ArrayList;
import java.util.List;

public class RulesUtility {
	
	public static boolean areValuesEqual(Object value1, Object value2) {
		if (value1 == null && value2 == null) return true;
		if (value1 == null && value2 != null || value1 != null && value2 == null) return false;

		return value1.equals(value2);
	}
	
	public boolean removeItemFromDropDownList(List<?>values, String item) {
		int index = -1;
		for (int i = 0; values != null && i < values.size(); i++) {
			DropDownCodeDT cachedDD = (DropDownCodeDT) values.get(i);
			if (cachedDD != null && areValuesEqual(cachedDD.getKey(), item)) {
				index = i;
				break;
			}
		}
		if (index >= 0) {
			values.remove(index);
		}
		return index >= 0;
	}
	
	public List<Object> getNewDropDownList(List<?> values, String item){

	    List<Object> list = new ArrayList<Object> ();
		for (int i = 0; values != null && i < values.size(); i++) {
			DropDownCodeDT cachedDD = (DropDownCodeDT) values.get(i);
			if (cachedDD != null && !(areValuesEqual(cachedDD.getKey(), item))) {
				list.add(cachedDD);
			}
	}
		return list;
	}
	
	

}
