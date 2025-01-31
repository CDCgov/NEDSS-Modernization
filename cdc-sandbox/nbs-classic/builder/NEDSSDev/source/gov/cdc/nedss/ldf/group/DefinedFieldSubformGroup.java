/*
 * Created on Jan 30, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.group;

import gov.cdc.nedss.exception.NEDSSAppException;

import java.io.Serializable;
import java.util.*;
/**
 * @author xzheng
 *
 * This class represents a group of defined field and subform uids.
 */
public class DefinedFieldSubformGroup implements Serializable{

	private List<Long> definedFieldUids = new ArrayList<Long> ();
	private List<Long> subformUids = new ArrayList<Long> ();
	private String name;

	public DefinedFieldSubformGroup(List<Object>definedFieldUids, List<Object> subformUids)
		throws NEDSSAppException {;
		if(definedFieldUids != null){
			for (Iterator<Object> iter = definedFieldUids.iterator(); iter.hasNext();) {
				Long element = (Long) iter.next();
				if(element != null && !this.definedFieldUids.contains(element)){
					this.definedFieldUids.add(element);
				}
			}
			try {
				Collections.sort(this.definedFieldUids);
			}
			catch(ClassCastException e){
				throw new NEDSSAppException("Can not create DefinedFieldSubformGroup.  The defined field uid list contains inconsistent data types.");
			}
		}
		if(subformUids != null){
			for (Iterator<Object> iter = subformUids.iterator(); iter.hasNext();) {
				Long element = (Long) iter.next();
				if(element != null && !this.subformUids.contains(element)){
					this.subformUids.add(element);
				}
			}
			try {
				Collections.sort(this.subformUids);
			}
			catch(ClassCastException e){
				throw new NEDSSAppException("Can not create DefinedFieldSubformGroup.  The subform uid list contains inconsistent data types.");
			}
		}
		generateName();
	}

	public DefinedFieldSubformGroup(String name) throws NEDSSAppException {
		this.name = name;
		generateUids();
	}

	/**
	 * This method generates the uids
	 */
	private void generateUids() throws NEDSSAppException {
		if (name != null) {
			try {

				// get the uids for defined field and subform
				String dfUids = null;
				String sfUids = null;
				if (name.indexOf("||") != -1) {
					throw new NEDSSAppException("Invalid defined field and subform group name.");
				} else if (name.indexOf("|") == -1) {
					dfUids = name;
				} else if (name.startsWith("|")) {
					sfUids = name.substring(1);
				} else {
					StringTokenizer tokenizer = new StringTokenizer(name, "|");

					dfUids = tokenizer.nextToken();
					if(tokenizer.hasMoreTokens()){
						sfUids = tokenizer.nextToken();
					}
				}

				// generate definedFieldUids
				if (dfUids != null) {
					definedFieldUids = processUidsString(dfUids);
				}
				// generate definedFieldUids
				if (sfUids != null) {
					subformUids = processUidsString(sfUids);
				}

			} catch (ClassCastException e) {
				throw new NEDSSAppException("Invalid subform or defiend field uid.");
			}

		}
	}

	private List<Long> processUidsString(String uids) throws NEDSSAppException {
		List<Long> aList = new ArrayList<Long> ();
		try {
			if(uids == null) {
				return null;
			} else if (uids.indexOf("__") != -1) {
				throw new NEDSSAppException("Invalid defined field or subform group name.");
			} else if (uids.indexOf("_") == -1) {
				if(uids.trim().length() > 0){
					aList.add(new Long(uids));
				}
			} else {
				StringTokenizer tokenizer = new StringTokenizer(uids, "_");
				while (tokenizer.hasMoreTokens()) {
					aList.add(new Long(tokenizer.nextToken()));
				}
			}
		} catch (NumberFormatException e) {
			throw new NEDSSAppException("Defined field or subform group uid must be a Long.");

		}
		Collections.sort(aList);
		return aList;

	}

	/**
	 * This method generates name using definedFieldUids and subformUids
	 */
	private void generateName() throws NEDSSAppException {

		StringBuffer buffer = new StringBuffer();
		try {
			if (definedFieldUids != null) {
				buffer.append(processUidList(definedFieldUids));
			}
			if (subformUids != null) {
				buffer.append("|");
				buffer.append(processUidList(subformUids));
			}
			this.name = buffer.toString();
		} catch (ClassCastException e) {
			throw new NEDSSAppException("Invalid subform or defiend field uid.");
		}
	}

	private String processUidList(List<Long>uids) throws NEDSSAppException {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < uids.size(); i++) {
			Long uid = (Long) uids.get(i);
			if (uid == null || uid.longValue() < 1) {
				throw new NEDSSAppException("Invalid defined field uid.");
			}
			buffer.append(uid);
			if (i != uids.size() - 1) {
				buffer.append("_");
			}
		}
		return buffer.toString();
	}

	/**
	 * @return
	 */
	public List<Long> getDefinedFieldUids() {
		return definedFieldUids;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public List<Long> getSubformUids() {
		return subformUids;
	}

	public String toString(){
		return name;
	}

}
