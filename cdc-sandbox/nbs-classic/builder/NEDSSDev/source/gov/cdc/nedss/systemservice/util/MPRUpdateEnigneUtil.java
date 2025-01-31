//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\gov\\cdc\\nedss\\systemservice\\mprupdateengine\\util\\MPRUpdateEnigneUtil.java

//Source file: C:\\projects\\dev\\DesignSkeleton\\src\\nedss\\NEDSSBusinessServicesLayer\\CDM\\MPRUpdateEngine\\MPRUpdateEngineDesign\\util\\MPRUpdateEnigneUtil.java

package gov.cdc.nedss.systemservice.util;

import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.controller.ejb.entitycontrollerejb.bean.EntityController;
import gov
	.cdc
	.nedss
	.controller
	.ejb
	.entitycontrollerejb
	.bean
	.EntityControllerHome;
import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.person.dt.*;
import gov.cdc.nedss.entity.person.vo.*;
import gov.cdc.nedss.locator.dt.*;
import gov.cdc.nedss.systemservice.exception.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.*;

import javax.ejb.CreateException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 This is the utility class for MPR Update Engine
 */
public class MPRUpdateEnigneUtil {

	private static final LogUtils logger =
		new LogUtils((MPRUpdateEnigneUtil.class).getName());
	private static HashMap<Object,Object> actionInitFiles = new HashMap<Object,Object>();

	static {
		actionInitFiles.put(
			MPRUpdateEngineConstants.SINGLE_FIELD_INIT_ID,
			MPRUpdateEngineConstants.SINGLE_FIELD_UPDATE_CONFIG_FILENAME);
		actionInitFiles.put(
			MPRUpdateEngineConstants.MULTI_FIELD_INIT_ID,
			MPRUpdateEngineConstants.MULTI_FIELD_UPDATE_CONFIG_FILENAME);
	}

	/**
	     This method returns the configuration file name for an Action object based on a
	  String key.
	 */
	public static String getActionInitFileName(String key) {
		return (String) actionInitFiles.get(key);
	}

	//***********************************************************/
	//BEGIN: getParentMPR(PersonVO personVO, NBSSecurityObj secObj)
	//***********************************************************/
	/**
	  Returns parent MPR for personVO.  If personVO is MPR, returns null.
	 */
	public static PersonVO getParentMPR(
		PersonVO personVO,
		NBSSecurityObj secObj)
		throws MPRUpdateException {
		if (personVO == null) {
			throw new MPRUpdateException(
				"Please provide a not null person object, personvo is: "
					+ personVO);
		}

		if (isMPR(personVO)) {
			logger.debug("Check mpr null");
			return null;
		} else {
			//TODO: use parentUID in personVO to retrieve the MPR from database

			PersonDT pDT = personVO.getThePersonDT();

			if (pDT != null) {
				Long personUid = pDT.getPersonParentUid();
				if (pDT.getPersonUid() != null) {
					logger.debug("Retrieve mpr.............");
					return retrieveMprFromDatabase(personUid, secObj);
				} else {
					logger.fatal(
						"Cannot retrieve mpr, person uid is: " + personUid);
					throw new MPRUpdateException("Error: No person uid available to retrieve a parent mpr");
				}
			} else {
				logger.fatal(
					"Cannot retrieve mpr, no person info available, person dt is: "
						+ pDT);
				throw new MPRUpdateException(
					"Error: No person info available for retrieving mpr, persondt is: "
						+ pDT);
			}
		}
	}

	private static PersonVO retrieveMprFromDatabase(
		Long personUid,
		NBSSecurityObj secObj)
		throws MPRUpdateException {
		NedssUtils nu = new NedssUtils();
		try {
			Object obj = nu.lookupBean(JNDINames.EntityControllerEJB);
			EntityControllerHome home =
				(EntityControllerHome) javax.rmi.PortableRemoteObject.narrow(
					obj,
					EntityControllerHome.class);
			EntityController eController = home.create();

			return eController.getMPR(personUid, secObj);
		} catch (RemoteException rex) {
			logger.fatal("Error: cannot retrieve mpr.", rex);
			throw new MPRUpdateException("Error: cannot retrieve mpr.", rex);
		} catch (CreateException cex) {
			logger.fatal("Error: cannot create an EntityController object.");
			throw new MPRUpdateException("Error: cannot create an EntityController object.", cex);
		}
	}

	//############################################################/
	//END: getParentMPR(PersonVO personVO, NBSSecurityObj secObj)
	//############################################################/

	/**
	  Checks whether personVO represents an MPR.
	 */
	public static boolean isMPR(PersonVO personVO) {
		//TODO: check the parentUID with UID, if identical, returns true
		if (personVO.getThePersonDT() != null
			&& personVO.getThePersonDT().getPersonParentUid() != null
			&& personVO.getThePersonDT().getPersonUid() != null
			&& personVO.getThePersonDT().getPersonParentUid().compareTo(
				personVO.getThePersonDT().getPersonUid())
				== 0) {
			logger.debug("Check true mpr");
			return true;
		} else {
			logger.debug("Check false mpr");
			return false;
		}
	}

	public static EntityLocatorParticipationDT createPersonAddress(PersonVO personVO) {
		Long personUID = personVO.getThePersonDT().getPersonUid();
		ArrayList<Object> arrELP =
			(ArrayList<Object> ) personVO.getTheEntityLocatorParticipationDTCollection();

		if (arrELP == null) {
			arrELP = new ArrayList<Object> ();

		}
		EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
		elp.setClassCd("PST");
		PostalLocatorDT pl = new PostalLocatorDT();
		pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		pl.setItNew(true);
		pl.setItDirty(false);
		pl.setItDelete(false);
		elp.setThePostalLocatorDT(pl);
		elp.setEntityUid(personUID);
		elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		elp.setItNew(true);
		elp.setItDirty(false);
		elp.setItDelete(false);
		arrELP.add(elp);
		personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
		return elp;
	}

	public static EntityLocatorParticipationDT createPersonTelephone(PersonVO personVO) {
		Long personUID = personVO.getThePersonDT().getPersonUid();
		ArrayList<Object> arrELP =
			(ArrayList<Object> ) personVO.getTheEntityLocatorParticipationDTCollection();

		if (arrELP == null) {
			arrELP = new ArrayList<Object> ();

		}
		EntityLocatorParticipationDT elp = new EntityLocatorParticipationDT();
		elp.setClassCd("TELE");
		TeleLocatorDT tl = new TeleLocatorDT();
		tl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		tl.setItNew(true);
		tl.setItDirty(false);
		tl.setItDelete(false);
		elp.setTheTeleLocatorDT(tl);
		elp.setEntityUid(personUID);
		elp.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		elp.setItNew(true);
		elp.setItDirty(false);
		elp.setItDelete(false);
		arrELP.add(elp);
		personVO.setTheEntityLocatorParticipationDTCollection(arrELP);
		return elp;
	}

	public static EntityIdDT createEntityId(PersonVO personVO) {
		Long personUID = personVO.getThePersonDT().getPersonUid();
		ArrayList<Object> arrEID = (ArrayList<Object> ) personVO.getTheEntityIdDTCollection();

		if (arrEID == null) {
			arrEID = new ArrayList<Object> ();

		}
		EntityIdDT eid = new EntityIdDT();
		eid.setEntityIdSeq(new Integer(getMaxEntityIdSeqNbr(personVO) + 1));
		eid.setEntityUid(personUID);
		eid.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		eid.setItNew(true);
		eid.setItDirty(false);
		eid.setItDelete(false);
		arrEID.add(eid);
		personVO.setTheEntityIdDTCollection(arrEID);
		return eid;
	}

	public static void appendPersonAddress(
		PersonVO personVO,
		EntityLocatorParticipationDT element) {

		Long personUID = personVO.getThePersonDT().getPersonUid();
		ArrayList<Object> aList =
			(ArrayList<Object> ) personVO.getTheEntityLocatorParticipationDTCollection();
		if (aList == null) {
			aList = new ArrayList<Object> ();
		}
		element.setClassCd("PST");
		PostalLocatorDT pl = element.getThePostalLocatorDT();
		if (pl == null) {
			pl = new PostalLocatorDT();
		}
		pl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		pl.setItNew(true);
		pl.setItDirty(false);
		pl.setItDelete(false);
		element.setThePostalLocatorDT(pl);
		element.setEntityUid(personUID);
		element.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		element.setItNew(true);
		element.setItDirty(false);
		element.setItDelete(false);
		aList.add(element);
		personVO.setTheEntityLocatorParticipationDTCollection(aList);
	}

	public static void appendPersonTelephone(
		PersonVO personVO,
		EntityLocatorParticipationDT element) {

		Long personUID = personVO.getThePersonDT().getPersonUid();
		ArrayList<Object> aList =
			(ArrayList<Object> ) personVO.getTheEntityLocatorParticipationDTCollection();
		if (aList == null) {
			aList = new ArrayList<Object> ();
		}
		element.setClassCd("TELE");
		TeleLocatorDT tl = element.getTheTeleLocatorDT();
		if (tl == null) {
			tl = new TeleLocatorDT();
		}
		tl.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		tl.setItNew(true);
		tl.setItDirty(false);
		tl.setItDelete(false);
		element.setTheTeleLocatorDT(tl);
		element.setEntityUid(personUID);
		element.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		element.setItNew(true);
		element.setItDirty(false);
		element.setItDelete(false);
		aList.add(element);
		personVO.setTheEntityLocatorParticipationDTCollection(aList);
	}

	public static void appendPersonRace(
		PersonVO personVO,
		PersonRaceDT element) {

		// cache old raceDT
		ArrayList<Object> aList = (ArrayList<Object> ) personVO.getThePersonRaceDTCollection();
		HashMap<Object,Object> hm = new HashMap<Object,Object>();
		if (aList == null) {
			aList = new ArrayList<Object> ();
		} else {
			Iterator<Object> iter = aList.iterator();
			if (iter != null) {
				while (iter.hasNext()) {
					PersonRaceDT raceDT = (PersonRaceDT) iter.next();
					//put them in a hashmap
					hm.put(raceDT.getRaceCd(), raceDT);
				}
			}
		}

		String raceCd = element.getRaceCd();
		PersonRaceDT raceDT = (PersonRaceDT) hm.get(raceCd);
		Long personUID = personVO.getThePersonDT().getPersonUid();
		element.setPersonUid(personUID);
		element.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		if (raceDT != null) {
			// the raceCd is already there.
			if (raceDT.isItNew()) {
				// this is a new DT, so we have not store it inthe database
				// yet. we can simply remove it from the collection, then the new
				// one coming in should be new
				aList.remove(raceDT);
				element.setItDelete(false);
				element.setItNew(true);
				element.setItDirty(false);
			} else {
				// we need to change the flags for raceDT so it is marked as
				// deleted, then the new one should be dirty
				raceDT.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_INACTIVE);
				raceDT.setItDelete(true);
				raceDT.setItNew(false);
				raceDT.setItDirty(false);
				element.setItNew(false);
				element.setItDirty(true);
				element.setItDelete(false);
			}
		} else {
			// append the new one
			element.setItNew(true);
			element.setItDirty(false);
			element.setItDelete(false);
		}

		aList.add(element);
		personVO.setThePersonRaceDTCollection(aList);
	}

	public static void appendEntityId(PersonVO personVO, EntityIdDT element) {
		Long personUID = personVO.getThePersonDT().getPersonUid();
		ArrayList<Object> arrEID = (ArrayList<Object> ) personVO.getTheEntityIdDTCollection();

		if (arrEID == null) {
			arrEID = new ArrayList<Object> ();

		}
		element.setEntityIdSeq(new Integer(getMaxEntityIdSeqNbr(personVO) + 1));
		element.setEntityUid(personUID);
		element.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		element.setItNew(true);
		element.setItDirty(false);
		element.setItDelete(false);
		arrEID.add(element);
		personVO.setTheEntityIdDTCollection(arrEID);
	}

	public static void appendPersonName(
		PersonVO personVO,
		PersonNameDT element) {
		Long personUID = personVO.getThePersonDT().getPersonUid();
		ArrayList<Object> aList = (ArrayList<Object> ) personVO.getThePersonNameDTCollection();

		if (aList == null) {
			aList = new ArrayList<Object> ();

		}
		
		if(getMaxPersonNameSeqNbr(personVO)==-1)//Fix ND-3024
			element.setPersonNameSeq(1);
		else
			element.setPersonNameSeq(
			new Integer(getMaxPersonNameSeqNbr(personVO) + 1));
		element.setPersonUid(personUID);
		element.setRecordStatusCd(NEDSSConstants.RECORD_STATUS_ACTIVE);
		element.setItNew(true);
		element.setItDirty(false);
		element.setItDelete(false);
		aList.add(element);
		personVO.setThePersonNameDTCollection(aList);
	}

	public static int getMaxEntityIdSeqNbr(PersonVO vo) {
		int retVal = -1;
		Collection<Object>  col = vo.getTheEntityIdDTCollection();
		if (col == null) {
			return retVal;
		}
		for (Iterator<Object> iter = col.iterator(); iter.hasNext();) {
			EntityIdDT element = (EntityIdDT) iter.next();
			int tmpVal = element.getEntityIdSeq().intValue();
			if (tmpVal > retVal) {
				retVal = tmpVal;
			}
		}
		return retVal;
	}

	public static int getMaxPersonNameSeqNbr(PersonVO vo) {
		int retVal = -1;
		Collection<Object>  col = vo.getThePersonNameDTCollection();
		if (col == null) {
			return retVal;
		}
		for (Iterator<Object> iter = col.iterator(); iter.hasNext();) {
			PersonNameDT element = (PersonNameDT) iter.next();
			int tmpVal = element.getPersonNameSeq().intValue();
			if (tmpVal > retVal) {
				retVal = tmpVal;
			}
		}
		return retVal;
	}

	public static boolean hasNewerAOD(Timestamp mprAOD, Timestamp newAOD) {
		return (mprAOD == null || (newAOD != null && !newAOD.before(mprAOD)));
	}

	public static Object cloneVO(Object objectVO) {
		Object clonePersonVO = null;
		if (objectVO != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = null;
			try {
				oos = new ObjectOutputStream(baos);

				oos.writeObject(objectVO);
				ByteArrayInputStream bais =
					new ByteArrayInputStream(baos.toByteArray());
				ObjectInputStream ois = new ObjectInputStream(bais);
				clonePersonVO = ois.readObject();
			} catch (ClassNotFoundException ex1) {
				ex1.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			return clonePersonVO;
		} else
			return objectVO;
	}

}
