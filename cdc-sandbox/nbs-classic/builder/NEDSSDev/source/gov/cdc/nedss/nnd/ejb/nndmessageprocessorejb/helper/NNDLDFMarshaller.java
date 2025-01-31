package gov.cdc.nedss.nnd.ejb.nndmessageprocessorejb.helper;

import gov.cdc.nedss.messageframework.notificationmastermessage.CDFData;
import gov.cdc.nedss.messageframework.notificationmastermessage.DataValue;
import gov.cdc.nedss.messageframework.notificationmastermessage.MetaDataObject;
import gov.cdc.nedss.messageframework.notificationmastermessage.NedssEvent;
import gov.cdc.nedss.ldf.vo.LdfBaseVO;
import gov.cdc.nedss.proxy.ejb.investigationproxyejb.vo.InvestigationProxyVO;
import gov.cdc.nedss.proxy.ejb.interventionproxyejb.vo.VaccinationProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.LabResultProxyVO;
import gov.cdc.nedss.proxy.ejb.observationproxyejb.vo.MorbidityProxyVO;
import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.organization.vo.OrganizationVO;

import gov.cdc.nedss.nnd.exception.NNDException;
import gov.cdc.nedss.ldf.dt.*;
import gov.cdc.nedss.nnd.util.NNDUtils;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NNDLDFMarshaller {

  public NNDLDFMarshaller() {
  }

  public static  Collection<Object>  stateDefinedFieldMetaDataDTColl = null;
  private Collection<Object>  stateDefinedFieldDataDTColl = new ArrayList<Object> ();
  private NNDUtils nndUtil = new NNDUtils();


  /**The method extracts and returns a  NedssEvent with CDFData populated
   * @param LDFBaseVO
   * @param NedssEvent
   * @param Map<Object, Object> localIdMap
   * @return Object NedssEvent
   */

  public Object marshallLDF(LdfBaseVO nbsProxyObj, NedssEvent nedssEvent,
                            Map<Object, Object> localIdMap) throws
      NNDException {

    if (localIdMap == null )

    {


//         System.out.println(" The localIDMap is null " );
         NNDException nnde = new NNDException(" The localIDMap is null ");
         nnde.setModuleName("populateCastorCDFDataObj");
         throw nnde;



     }
 else
 {
   boolean cdfDataExists = false;
   boolean cdfMetaDataExists = false;



   buildLDFDataValues(nbsProxyObj);

   Collection<Object>  cdfDataValueColl = populateCastorCDFDataObj(localIdMap);

   if ( !(cdfDataValueColl == null))
       if  (  cdfDataValueColl.size() > 0)
            cdfDataExists = true;


   Hashtable<Object, Object> cdfMetaDataMap = (Hashtable<Object, Object>) populateCastorCDFMetaDataObj();

   if ( ! (cdfMetaDataMap == null))
     if (cdfMetaDataMap.size() > 0)
        cdfMetaDataExists = true;

      if  (cdfDataExists && cdfMetaDataExists)



   {
     try {
       CDFData cdfData = new CDFData();
       MetaDataObject[] metaDataObjArray = new MetaDataObject[cdfMetaDataMap.
           size()];
       Collection<Object>  tempMetaObjColl = cdfMetaDataMap.values();
      Iterator<Object>  itr = tempMetaObjColl.iterator();
       int i = 0;
       while (itr.hasNext()) {
         MetaDataObject metaDataObj = (MetaDataObject) (itr.next());
         if (! (metaDataObj == null)) {
           metaDataObjArray[i] = metaDataObj;
           i++;
         }
       }
       cdfData.setMetaDataObject(metaDataObjArray);
       DataValue[] dataValueArray = new DataValue[cdfDataValueColl.size()];
      Iterator<Object>  dataItr = cdfDataValueColl.iterator();
       int j = 0;
       while (dataItr.hasNext()) {
         DataValue dataValue = (DataValue) dataItr.next();
         if (! (dataValue == null)) {
           dataValueArray[j] = dataValue;
           j++;

         }
       }
       cdfData.setDataValue(dataValueArray);

       nedssEvent.setCDFData(cdfData);

     }


     catch (Exception e) {
       e.printStackTrace();
       NNDException nnde = new NNDException(
           " Exception while setting CDFData  " + e.getMessage());
       nnde.setModuleName("marshallLDF");
       throw nnde;

     }
   }


   }
   return nedssEvent;
 }



  private void buildLDFDataValues(LdfBaseVO nbsProxyVO) {

	  /**
       * @TBD Release 6.0, Commented out as LDF will be planned out as new type of answers
    
    if (nbsProxyVO instanceof LabResultProxyVO) {
      addLDFDataValues( (nbsProxyVO.getTheStateDefinedFieldDataDTCollection()));
      extractEntityLDFDataValues( ( (LabResultProxyVO) (nbsProxyVO)).
                                 getThePersonVOCollection());
      extractEntityLDFDataValues( ( (LabResultProxyVO) (nbsProxyVO)).
                                 getTheOrganizationVOCollection());
    }
    else*/
    if (nbsProxyVO instanceof MorbidityProxyVO) {

      addLDFDataValues( (nbsProxyVO.getTheStateDefinedFieldDataDTCollection()));
      extractEntityLDFDataValues( ( (MorbidityProxyVO) (nbsProxyVO)).
                                 getThePersonVOCollection());
      extractEntityLDFDataValues( ( (MorbidityProxyVO) (nbsProxyVO)).
                                 getTheOrganizationVOCollection());

    }
    else
    if (nbsProxyVO instanceof InvestigationProxyVO) {
      addLDFDataValues( (nbsProxyVO.getTheStateDefinedFieldDataDTCollection()));
      extractEntityLDFDataValues( ( (InvestigationProxyVO) (nbsProxyVO)).
                                 getThePersonVOCollection());
      extractEntityLDFDataValues( ( (InvestigationProxyVO) (nbsProxyVO)).
                                 getTheOrganizationVOCollection());

    }
    else
    if (nbsProxyVO instanceof VaccinationProxyVO) {
      addLDFDataValues( (nbsProxyVO.getTheStateDefinedFieldDataDTCollection()));
      extractEntityLDFDataValues( ( (VaccinationProxyVO) (nbsProxyVO)).
                                 getThePersonVOCollection());
      extractEntityLDFDataValues( ( (VaccinationProxyVO) (nbsProxyVO)).
                                 getTheOrganizationVOCollection());

    }

  }

	private void addLDFDataValues(Collection<Object> ldfDataValueCollection) {

		if (!(ldfDataValueCollection == null)) {
			Iterator<Object> itr = ldfDataValueCollection.iterator();
			while (itr.hasNext()) {
				StateDefinedFieldDataDT stateDefinedDataDT = (StateDefinedFieldDataDT) itr.next();

				/*
				 * System.out.println("stateDefinedDataDT LDF UID = " +
				 * stateDefinedDataDT.getLdfUid());
				 * System.out.println("stateDefinedDataDT Bus Name = " +
				 * stateDefinedDataDT.getBusinessObjNm());
				 * System.out.println("stateDefinedDataDT Bus UID  = " +
				 * stateDefinedDataDT.getBusinessObjUid());
				 */
				if (stateDefinedFieldMetaDataDTColl == null)
					return;
				Iterator<Object> metaDataItr = stateDefinedFieldMetaDataDTColl.iterator();
				while (metaDataItr.hasNext()) {
					StateDefinedFieldMetaDataDT stateDefinedMetaDataDT = (StateDefinedFieldMetaDataDT) metaDataItr
							.next();
					if (stateDefinedMetaDataDT.getLdfUid().equals(stateDefinedDataDT.getLdfUid())) {

						stateDefinedFieldDataDTColl.add(stateDefinedDataDT);
						break;
					}
				}

			}

		}

	}

  private void extractEntityLDFDataValues(Collection<Object> entityVOCollection) {
    if (! (entityVOCollection  == null)) {

     Iterator<Object>  itr = entityVOCollection.iterator();

      while (itr.hasNext()) {

        Object entityVO = itr.next();
        if (entityVO instanceof PersonVO) {
          addLDFDataValues( ( (PersonVO) (entityVO)).
                           getTheStateDefinedFieldDataDTCollection());

        }
        else

        if (entityVO instanceof OrganizationVO) {
          Collection<Object>  organizationLDFDataColl =  ( (OrganizationVO) (entityVO)).
                           getTheStateDefinedFieldDataDTCollection();
/*          System.out.println
        ("Organization LDF DT COLL SIZE = " +  ( (OrganizationVO)entityVO).getTheStateDefinedFieldDataDTCollection().size() + "ORG ID  = " + ( (OrganizationVO)entityVO).getTheOrganizationDT().getLocalId() );             */
          addLDFDataValues( organizationLDFDataColl);

        }

      }
    }

  }






  private Collection<Object>  getLDFMetaData(Collection<Object> stateDefinedFieldDataColl) {
    Collection<Object>  cdfMetaDataColl = new ArrayList<Object> ();
    if (! (stateDefinedFieldDataColl == null)) {
     Iterator<Object>  dataItr = stateDefinedFieldDataColl.iterator();
      while (dataItr.hasNext()) {
        StateDefinedFieldDataDT stateDefinedFieldDT = (StateDefinedFieldDataDT)
            dataItr.next();
        Long ldfUId = stateDefinedFieldDT.getLdfUid();
       Iterator<Object>  metaDataItr = stateDefinedFieldMetaDataDTColl.iterator();
        while (metaDataItr.hasNext()) {

          StateDefinedFieldMetaDataDT stateDefinedMetaDataDT = (
              StateDefinedFieldMetaDataDT) metaDataItr.next();
/*          System.out.println("meta data ldf business obj name  " + stateDefinedMetaDataDT.getBusinessObjNm());
          System.out.println("meta data ldf uid " + stateDefinedMetaDataDT.getLdfUid());*/
          if (stateDefinedMetaDataDT.getLdfUid().equals(ldfUId)) {
            //System.out.println("meta data ldf uid " + stateDefinedMetaDataDT.getLdfUid());
            //System.out.println("meta data ldf business obj name  " + stateDefinedMetaDataDT.getBusinessObjNm());
            cdfMetaDataColl.add(stateDefinedMetaDataDT);
            break;


          }
        }

      }

    }

    return cdfMetaDataColl;
  }

  private Collection<Object>  populateCastorCDFDataObj(Map<Object, Object> localIdMap) throws
      NNDException {

    Collection<Object>  cdfDataValueColl = new ArrayList<Object> ();
    ArrayList<Object> ldfDataCollection  = (ArrayList<Object> ) getStateDefinedFieldDataDTColl();
    if (! (ldfDataCollection  == null))
    {
     Iterator<Object>  ldfDataItr = ldfDataCollection.iterator();

      while (ldfDataItr.hasNext())

      {

        DataValue cdfDataValue = new DataValue();


        StateDefinedFieldDataDT stateDefinedDataDT =  (StateDefinedFieldDataDT) ldfDataItr.next();

        cdfDataValue = (DataValue) nndUtil.copyObjects(stateDefinedDataDT,
            cdfDataValue);

       if ( !(stateDefinedDataDT == null) )
       {
/*         System.out.println("LDFUid  " + stateDefinedDataDT.getLdfUid());
         System.out.println("Business Obj Name " + stateDefinedDataDT.getBusinessObjNm());
         System.out.println("Business Obj Uid  " + stateDefinedDataDT.getBusinessObjUid());*/
         if ( (localIdMap.containsKey(stateDefinedDataDT.getBusinessObjUid()))) {

           String localID = (localIdMap.get(stateDefinedDataDT.
                                            getBusinessObjUid()).
                             toString());

           cdfDataValue.setBusinessObjectTempId(localID);
          }
       else
           {

//              System.out.println(" The localID for businessObjUid  " + stateDefinedDataDT.getBusinessObjUid().toString() + " is null " + "localid map is empty = " +  localIdMap.isEmpty());
              NNDException nnde = new NNDException(" The localID for businessObjUid  " + stateDefinedDataDT.getBusinessObjUid().toString() + " is null " + "BUSINESS OBJECT NAME " + stateDefinedDataDT.getBusinessObjNm());
              nnde.setModuleName("populateCastorCDFDataObj");
              throw nnde;

           }

       }

        else
        {

//          System.out.println(" The stateDefinedDataDT is null ");
          NNDException nnde = new NNDException(" The stateDefinedDataDT is null " );
          nnde.setModuleName("populateCastorCDFDataObj");
          throw nnde;

        }

        cdfDataValueColl.add(cdfDataValue);

      }
    }
  else
  {
    NNDException nnde = new NNDException(" There is no state defined data   ");
    nnde.setModuleName("populateCastorCDFDataObj");
    throw nnde;



  }
    return cdfDataValueColl;

  }

  private Map<Object, Object> populateCastorCDFMetaDataObj()

      throws NNDException {

    Hashtable<Object, Object> cdfMetaDataMap = null;

    Collection<Object>  cdfMetaDataColl = getLDFMetaData(stateDefinedFieldDataDTColl);

    if (!(cdfMetaDataColl == null))
    {

    cdfMetaDataMap = new Hashtable<Object, Object>();
   Iterator<Object>  metaDataItr = cdfMetaDataColl.iterator();
    while (metaDataItr.hasNext()) {
      MetaDataObject cdfMetaData = new MetaDataObject();
      StateDefinedFieldMetaDataDT stateDefinedMetaData = new
          StateDefinedFieldMetaDataDT();
      stateDefinedMetaData = (StateDefinedFieldMetaDataDT) metaDataItr.next();

      cdfMetaData = (MetaDataObject) nndUtil.copyObjects(stateDefinedMetaData,
          cdfMetaData);

      if (! (cdfMetaDataMap.containsKey(stateDefinedMetaData.getLdfUid()))) {
        cdfMetaDataMap.put(stateDefinedMetaData.getLdfUid(), cdfMetaData);

      }
     }
    }
    else
    {
        NNDException nnde = new NNDException ("No CDFMetaData Found for CDFData ");
        nnde.setModuleName("populateCastorCDFMetaDataObj");
        throw nnde;
    }
    return cdfMetaDataMap;

  }
  private Collection<Object>  getStateDefinedFieldDataDTColl() {
    return this.stateDefinedFieldDataDTColl;
  }

}
