/**
 *
 */
package gov.cdc.nedss.nbsactentity.dt;


import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * @author psharma
 *DT for Pam CaseEntity
 */
public class NbsActEntityDT extends AbstractVO
implements RootDTInterface{

	/**
	 *Name:		NbsAnswerDT.java
	 * Description:	DT for NBS Pam Case Entity.
	 * Copyright:	Copyright (c) 2008
	 * Company: 	Computer Sciences Corporation
	 * @author	Pradeep Sharma
	 */
	private static final long serialVersionUID = 1L;
	
	private Long nbsActEntityUid; 
	private Timestamp addTime;
	private Long addUserId; 
	private Long entityUid; 
	private Integer entityVersionCtrlNbr; 
	private Timestamp lastChgTime;
	private Long lastChgUserId; 
	private String recordStatusCd;
	private Timestamp recordStatusTime; 
	private String  typeCd;
	private Long actUid;
	private boolean itDirty = false;
	private boolean itNew = true;
	private boolean itDelete = false;
	
	
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	public Long getAddUserId() {
		return addUserId;
	}
	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}
	public Timestamp getLastChgTime() {
		return lastChgTime;
	}
	public void setLastChgTime(Timestamp lastChgTime) {
		this.lastChgTime = lastChgTime;
	}
	public Long getLastChgUserId() {
		return lastChgUserId;
	}
	public void setLastChgUserId(Long lastChgUserId) {
		this.lastChgUserId = lastChgUserId;
	}

	public String getTypeCd() {
		return typeCd;
	}
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub

	}
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub

	}
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub

	}
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub

	}
	public String getStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusCd(String aStatusCd) {
		// TODO Auto-generated method stub

	}
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub

	}
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub

	}
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = NbsActEntityDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub

	}
	public void setItDirty(boolean itDirty) {
        this.itDirty = itDirty;
    }

    /**
     *
     * @return boolean
     */
    public boolean isItDirty() {

        return itDirty;
    }

    /**
     *
     * @param itNew
     */
    public void setItNew(boolean itNew) {
        this.itNew = itNew;
    }

    /**
     *
     * @return boolean
     */
    public boolean isItNew() {

        return itNew;
    }

    /**
     *
     * @return boolean
     */
    public boolean isItDelete() {

        return itDelete;
    }

    /**
     *
     * @param itDelete
     */
    public void setItDelete(boolean itDelete) {
        this.itDelete = itDelete;
    }
	public Long getNbsActEntityUid() {
		return nbsActEntityUid;
	}
	public void setNbsActEntityUid(Long nbsActEntityUid) {
		this.nbsActEntityUid = nbsActEntityUid;
	}
	public Long getEntityUid() {
		return entityUid;
	}
	public void setEntityUid(Long entityUid) {
		this.entityUid = entityUid;
	}
	public Integer getEntityVersionCtrlNbr() {
		return entityVersionCtrlNbr;
	}
	public void setEntityVersionCtrlNbr(Integer entityVersionCtrlNbr) {
		this.entityVersionCtrlNbr = entityVersionCtrlNbr;
	}
	public String getRecordStatusCd() {
		return recordStatusCd;
	}
	public void setRecordStatusCd(String recordStatusCd) {
		this.recordStatusCd = recordStatusCd;
	}
	public Timestamp getRecordStatusTime() {
		return recordStatusTime;
	}
	public void setRecordStatusTime(Timestamp recordStatusTime) {
		this.recordStatusTime = recordStatusTime;
	}
	public Long getActUid() {
		return actUid;
	}
	public void setActUid(Long actUid) {
		this.actUid = actUid;
	}

	/**
	 * This  method will prepare the clone for the object
	 * @return deepCopy -- the clone Object
	 * @throws CloneNotSupportedException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Object deepCopy() throws CloneNotSupportedException, IOException, ClassNotFoundException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object deepCopy = ois.readObject();

        return  deepCopy;
    }
}
