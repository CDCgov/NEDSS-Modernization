package gov.cdc.nedss.report.javaRepot.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.cdc.nedss.util.AbstractVO;
/**
 * Util class for Pa3 report
 * @author Pradeep Kumar Sharma
 *
 */
public class Pa3VO  extends AbstractVO implements java.io.Serializable{


	private static final long serialVersionUID = -9008405583280896219L;

	
	private Collection<Object> numberOfCasesColl =  new ArrayList<Object>();
	private Collection<Object> ipsColl =  new ArrayList<Object>();
	private Collection<Object> internetOcByContType =  new ArrayList<Object>();
	
	
	
	
	public Collection<Object> getNumberOfCasesColl() {
		return numberOfCasesColl;
	}

	public void setNumberOfCasesColl(Collection<Object> numberOfCasesColl) {
		this.numberOfCasesColl = numberOfCasesColl;
	}

	
	public Collection<Object> getInternetOcByContType() {
		return internetOcByContType;
	}

	public void setInternetOcByContType(Collection<Object> internetOcByContType) {
		this.internetOcByContType = internetOcByContType;
	}

	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public Collection<Object> getIpsColl() {
		return ipsColl;
	}

	public void setIpsColl(Collection<Object> ipsColl) {
		this.ipsColl = ipsColl;
	}

}
