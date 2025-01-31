package gov.cdc.nedss.proxy.ejb.pamproxyejb.vo;

import gov.cdc.nedss.act.ctcontact.dt.CTContactDT;
import gov.cdc.nedss.act.ctcontact.vo.CTContactVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.proxy.ejb.queue.dt.MessageLogDT;
import gov.cdc.nedss.util.AbstractVO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
/**
 * Name: CTContactProxyVO 
 * Description: CTContactProxyVO Object for contacts 
 * Copyright(c) 2009 
 * Company: Computer Sciences Corporation
 * @author Pradeep Sharma
 * @version: NBS3.1 contact tracing
 */

public class CTContactProxyVO extends AbstractVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CTContactVO cTContactVO= null;
	private PersonVO contactPersonVO;
	private Collection<Object> personVOCollection;
	private Collection<Object> organizationVOCollection;
	private Map<String,MessageLogDT> messageLogDtMap = new HashMap<String,MessageLogDT>();

	public PersonVO getContactPersonVO() {
		return contactPersonVO;
	}
	public void setContactPersonVO(PersonVO contactPersonVO) {
		this.contactPersonVO = contactPersonVO;
	}
	public Collection<Object> getPersonVOCollection() {
		return personVOCollection;
	}
	public void setPersonVOCollection(Collection<Object> personVOCollection) {
		this.personVOCollection = personVOCollection;
	}
	public Collection<Object> getOrganizationVOCollection() {
		return organizationVOCollection;
	}
	public void setOrganizationVOCollection(
			Collection<Object> organizationVOCollection) {
		this.organizationVOCollection = organizationVOCollection;
	}
	public CTContactVO getcTContactVO() {
		if(cTContactVO==null)
			cTContactVO = new CTContactVO();
		return cTContactVO;
	}
	public void setcTContactVO(CTContactVO cTContactVO) {
		this.cTContactVO = cTContactVO;
	}
	
	public Map getMessageLogDtMap() {
		return messageLogDtMap;
	}
	public void setMessageLogDtMap(Map messageLogDtMap) {
		this.messageLogDtMap = messageLogDtMap;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 *
	 * @param aItDirty
	 */
	public void setItDirty(boolean aItDirty) {
		itDirty = aItDirty;
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
	 * @param aItNew
	 */
	public void setItNew(boolean aItNew) {
		itNew = aItNew;
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
	 * @param aItDelete
	 */
	public void setItDelete(boolean aItDelete) {
		itDelete = aItDelete;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append(this.getClass().getName() + "\r\n");
			Field[] f = CTContactDT.class.getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				if (f[i] != null)
					sb.append(f[i].getName() + ":" + f[i].get(this) + "\r\n");
			}
		} catch (Exception ignore) {
		}
		return sb.toString();
	}
	
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
