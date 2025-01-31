package gov.cdc.nedss.deduplication.helper;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.sql.Timestamp;
import java.util.Date;

import gov.cdc.nedss.entity.person.vo.PersonVO;
import gov.cdc.nedss.entity.person.dt.PersonDT;
import gov.cdc.nedss.entity.person.dt.PersonNameDT;
import gov.cdc.nedss.entity.person.dt.PersonMergeDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.deduplication.vo.MergeConfirmationVO;
import gov.cdc.nedss.systemservice.nbssecurity.NBSSecurityObj;
import gov.cdc.nedss.webapp.nbs.logicsheet.helper.CachedDropDownValues;

/**
*
*/
public class DeDuplicationHelper
{
    static final LogUtils logger = new LogUtils(DeDuplicationHelper.class.getName());
    private Collection<Object> mergeConfirmationVo = new ArrayList<Object> ();
    private Collection<Object> discardedCollection  = new ArrayList<Object> ();
    private static PropertyUtil propUtil = PropertyUtil.getInstance();

    private PersonVO survivorPVo;
        /**
        * @roseuid 3E6CD19403E2
        */
        public DeDuplicationHelper()
        {

        }

        /**
        * Given a collection of MPR's finds the surviving MPR amongst the elements of the collection
        * by employing business rules.
        * @param personVOCollection
        * @param specifiedSurvivor 
        * @return personVO
        * @roseuid 3E665D6300DA
        */
        public PersonVO findSurvivor(Collection<Object> personVOCollection, String specifiedSurvivorPatientId) throws Exception
        {
            ArrayList<Object>  arrayList;
            survivorPVo = null;
            
            arrayList = buildArrayListOutofCollection(personVOCollection);

            if(arrayList == null) throw new Exception("null ArrayList<Object> ");
            try
            {
            	String seedValue = propUtil.getSeedValue();  
                //if the user specified a survivor, use that one
            	if (specifiedSurvivorPatientId != null && !specifiedSurvivorPatientId.isEmpty()) {
            		 String survivorDisplayLocalID = String.valueOf( (new Long(specifiedSurvivorPatientId).longValue()) +
                             (new Long(seedValue).longValue()));
                 	
                	logger.debug("Information DeDuplicationHelper.findSurvivor with specifiedSurvivorPatientId:"+ specifiedSurvivorPatientId);
                	logger.debug("Information in DeDuplicationHelper.findSurvivor with survivorDisplayLocalID "+survivorDisplayLocalID);

            		for (int i=0; i<arrayList.size(); ++i) {
            			 PersonVO thisPersonVO = (PersonVO) arrayList.get(i);
            			 if (thisPersonVO.getThePersonDT().getLocalId().contains(survivorDisplayLocalID))
            				 survivorPVo = thisPersonVO;
            			 else {
            				 discardedCollection.add(thisPersonVO);
            			 }
            		} 
            		//remove discarded from arrayList
       			    if (survivorPVo != null) {
    				   arrayList = new ArrayList<Object> ();
    				   arrayList.add((Object)survivorPVo);
    			    } else {
            			logger.error("DeDuplicationHelper.findSurvivor() --> Specified Survivor not in collection??");
            			throw new Exception("DeDuplication--> Specified Survivor not in collection??");
            	    }
            	} else {
            		//survivor was not specified - find newest name or address to use as survivor
            		while(arrayList.size() > 1) {
            			int index = determineDiscard(arrayList);
            			discardedCollection.add(arrayList.get(index));
            			arrayList.remove(index);
            			}
            	}

                //Appending all the discarded MPR's general comments to the survivor's general comments...
                int repeatAppend = 0;
                Iterator<Object> iterator = discardedCollection.iterator();
                ArrayList<Object>  suvivorDiscardedArrayList= new ArrayList<Object> ();

                suvivorDiscardedArrayList.add(0, arrayList.get(0));

                while(iterator.hasNext())
                {
                    suvivorDiscardedArrayList.add(1, iterator.next());
                    appendDiscarded196And215ToSurvivor196(suvivorDiscardedArrayList, 1, repeatAppend++);
                }
                if (survivorPVo == null)
                	survivorPVo = (PersonVO)arrayList.get(0);
                buildConfirmation();
                return survivorPVo;
            }
            catch(Exception e)
            {
            	logger.error("Exception in DeDuplicationHelper.findSurvivor", e);
                throw new Exception(e.toString());
            }
        }


  /**
   * See the seq diagram for popoulation of a PersonMergeDT.
   * The attributes of a personMergeDT do not in all cases represent the name.
   * That being values named superceded may actually be revision data and surviving data
   * may actually be data from a revision.
   * @param pvoRevision
   * @param sprPvo
   * @param secObj
   * @param mergeDate
   * @return
   */
        public PersonMergeDT buildPersonMergeDt(PersonVO pvoRevision, PersonVO sprPvo, NBSSecurityObj secObj, Timestamp mergeDate) {
          PersonMergeDT dt = new PersonMergeDT();
          dt.setItNew(true);
          dt.setMergeTime(mergeDate);
          dt.setMergeUserId(secObj.getTheUserProfile().getTheUser().getUserID());
          dt.setRecordStatusCd(NEDSSConstants.PER_MERGE);
          dt.setRecordStatusTime(mergeDate);
          dt.setSupercededParentUid(pvoRevision.getThePersonDT().getPersonParentUid());
          dt.setSupercedPersonUid(pvoRevision.getThePersonDT().getPersonUid());
         // if(dt.getSupercededParentUid().longValue() == dt.getSupercedPersonUid().longValue())
           // dt.setSupercededVersionCtrlNbr(new Integer(pvoRevision.getThePersonDT().getVersionCtrlNbr().intValue() + 1));
          //else
            dt.setSupercededVersionCtrlNbr(pvoRevision.getThePersonDT().getVersionCtrlNbr());
          dt.setSurvivingParentUid(sprPvo.getThePersonDT().getPersonParentUid());
          dt.setSurvivingPersonUid(sprPvo.getThePersonDT().getPersonUid());
          dt.setSurvivingVersionCtrlNbr(new Integer(sprPvo.getThePersonDT().getVersionCtrlNbr().intValue() + 1));

          return dt;
        }

        /**
         * Builds the collection of MergeConfirmationVO objects.
         */
        private void buildConfirmation() {
          //First iterate through the superceded collection.
          Iterator<Object> supercededIt = discardedCollection.iterator();
          while(supercededIt.hasNext()) {
            PersonVO pvo = (PersonVO)supercededIt.next();
            MergeConfirmationVO mcVo = new MergeConfirmationVO();
            mcVo.setLocalId(pvo.getThePersonDT().getLocalId());
            mcVo.setSurvivor(new Boolean(false));
            if(pvo.getThePersonNameDTCollection().size() != 0)
              determineDisplayName(pvo.getThePersonNameDTCollection(), mcVo);
            mergeConfirmationVo.add(mcVo);
          }//end of while
          //next create a MergeConfirmationVO for the Survivor
          MergeConfirmationVO mcVo = new MergeConfirmationVO();
          mcVo.setLocalId(survivorPVo.getThePersonDT().getLocalId());
          mcVo.setSurvivor(new Boolean(true));
          if(survivorPVo.getThePersonNameDTCollection().size() != 0)
              determineDisplayName(survivorPVo.getThePersonNameDTCollection(), mcVo);
          mergeConfirmationVo.add(mcVo);
        }//end of buildConfirmation()

        /**
         * Will iterate through the personNameDt collection to determine which
         * user name to display.
         * @param pnDtColl
         * @param mcVo
         */
        private void determineDisplayName(Collection<Object> pnDtColl, MergeConfirmationVO mcVo) {
          Vector<Object> candidateLNVector = new Vector<Object>();
          Vector<Object> candidateFNVector = new Vector<Object>();
          Vector<Object> candidateLegalNameVector = new Vector<Object>();
          CachedDropDownValues srtc = new CachedDropDownValues();
          Object obj[] = pnDtColl.toArray();
          //Select instances of personNameDT where personNameDT.lastNm IS NOT NULL
          //This is Subset A
          for(int z = 0; z < obj.length; z++) {
            PersonNameDT dt = (PersonNameDT)obj[z];
            if(dt.getLastNm() != null && dt.getLastNm() != "" && dt.getLastNm() != " ")
              candidateLNVector.addElement(dt);
          }//end of while
          //IF none found, Pick one instance of PersonNameDT and return
          if(candidateLNVector.size() == 0) {
            PersonNameDT dt = (PersonNameDT)obj[0];
            mcVo.setFirstName(dt.getFirstNm());
            mcVo.setMiddleName(dt.getMiddleNm());
            mcVo.setLastName(dt.getLastNm());
            String nmDesc = srtc.getDescForCode("P_NM_USE", mcVo.getNmUseCd());
            mcVo.setNmUseCdDesc(nmDesc);
            return;
          }

          //From Subset A, select instances where personNameDT.firstNm IS NOT NULL
          //This is Subset B
          for(int k = 0; k < candidateLNVector.size(); k++) {
            PersonNameDT dt = (PersonNameDT)candidateLNVector.elementAt(k);
            if(dt.getFirstNm() != null && !dt.getFirstNm().equals("") && !dt.getFirstNm().equals(" "))
              candidateFNVector.addElement(dt);
          }//end of for

          // From Subset B, select instances where PersonNameDT.nmUseCd = L
          //This is Subset C
          for(int k = 0; k < candidateFNVector.size(); k++) {
            PersonNameDT dt = (PersonNameDT)candidateFNVector.elementAt(k);
            if(dt.getNmUseCd() != null && dt.getNmUseCd().equals("L"))
              candidateLegalNameVector.addElement(dt);
          }//end of for

          //Based upon Subset available (Subset A or Subset B or Subset C) select the instance of
          //personName with the latest personName.statusTime
          Vector<Object> toUse = candidateLegalNameVector.size() > 0?candidateLegalNameVector:candidateFNVector.size() > 0?candidateFNVector:candidateLNVector;
            PersonNameDT dt = (PersonNameDT)toUse.elementAt(0);
            for(int x = 0; x < toUse.size(); x++) {
              PersonNameDT nmDt = (PersonNameDT)toUse.elementAt(x);
              if(nmDt.getAsOfDate()!=null && dt.getAsOfDate()!=null && dt.getAsOfDate().compareTo(nmDt.getAsOfDate()) <= 0)
                dt = nmDt;
            }//end of for

            mcVo.setFirstName(dt.getFirstNm());
            mcVo.setMiddleName(dt.getMiddleNm());
            mcVo.setLastName(dt.getLastNm());
            mcVo.setNmUseCd(dt.getNmUseCd());
            //see line 360 of MergeCandidateListLoad.java
            String nmDesc = srtc.getDescForCode("P_NM_USE", mcVo.getNmUseCd());
            mcVo.setNmUseCdDesc(nmDesc);
        }//end of determineDisplayName(....)

        /**
        * Given a collection of two MPR's determines which needs to be discarded.
        * @param inArrayList
        * @return int
        * @throws Exception
        */
        private int determineDiscard(ArrayList<Object>   inArrayList) throws Exception
        {
            PersonVO personVOAtIndexZero = (PersonVO)inArrayList.get(0);
            PersonVO personVOAtIndexOne = (PersonVO)inArrayList.get(1);
            int result;

            try
            {
                result = findDiscardableMPR(personVOAtIndexZero, personVOAtIndexOne);
            }
            catch(Exception e)
            {
                throw new Exception(e.toString());
            }

            return result;
        }
        /**
        * Builds a ArrayList<Object>  given a collection
        * @param inColl
        * @return ArrayList<Object> 
        */
        private ArrayList<Object>  buildArrayListOutofCollection(Collection<Object> inColl)
        {
            ArrayList<Object>  arrayList = new ArrayList<Object> ();

            if(inColl.isEmpty()) return null;

            Iterator<Object> iterator = inColl.iterator();

            while(iterator.hasNext())
            {
                arrayList.add(iterator.next());
            }

            return arrayList;
        }
        /**
        * Given a ArrayList<Object>  prints out the contents(curr. impl. works for String elements only)
        * @param inArrayList
        *
        */
        private void printArrayList(ArrayList<Object>  inArrayList)
        {
            Iterator<Object> iterator = inArrayList.iterator();

            while(iterator.hasNext())
            {
                System.out.println("The element is " + (String)iterator.next());
            }

            return;
        }
        /**
         *  When passed in two MPR's determines which one to keep based on business rules.
          * @param inPersonVOAtIndexZero
         * @param inPersonVOAtIndexOne
         * @return int
         * @throws Exception
         */
        private int findDiscardableMPR(PersonVO inPersonVOAtIndexZero, PersonVO inPersonVOAtIndexOne) throws Exception
        {
            ArrayList<Object>  arrayListZero;
            ArrayList<Object>  arrayListOne;
            String addrString = NEDSSConstants.POSTAL;
            String teleString = NEDSSConstants.TELE;
            String lnameString = NEDSSConstants.PHYSICAL;
            String homeAddrTeleUseCode = "H";
            String personNameUseCode = "L";
            String acrossMPRs = "ACROSS";
            String amongstMPRs = "AMONGST";
            int result;
            ArrayList<Object>  arrayListFinal = new ArrayList<Object> ();

            try
            {
                arrayListZero = buildAddressOrTeleOrNameArrayListOutofCollection(inPersonVOAtIndexZero.getThePersonNameDTCollection(), lnameString, personNameUseCode);
                arrayListOne = buildAddressOrTeleOrNameArrayListOutofCollection(inPersonVOAtIndexOne.getThePersonNameDTCollection(), lnameString, personNameUseCode);

                if(arrayListZero != null && arrayListOne != null)
                {
                    if((arrayListZero.size() > 0) && (arrayListOne.size() > 0))
                    {
                        findLatestHomeAddressOrTeleOrNameForAMPR(arrayListZero, "", amongstMPRs);
                        findLatestHomeAddressOrTeleOrNameForAMPR(arrayListOne, "", amongstMPRs);

                        arrayListFinal.add(arrayListZero.get(0));
                        arrayListFinal.add(arrayListOne.get(0));

                        result =  determineDiscardableAddressOrTeleOrName(arrayListFinal, "", acrossMPRs);
                        if(result >= 0) return result;
                    }
                }

                // If result is -1 means there is a tie between the MPR's(wrt2 AOD LName) proceed to Compare AOD for Home Address...
                arrayListZero = buildAddressOrTeleOrNameArrayListOutofCollection(inPersonVOAtIndexZero.getTheEntityLocatorParticipationDTCollection(), addrString, homeAddrTeleUseCode);
                arrayListOne = buildAddressOrTeleOrNameArrayListOutofCollection(inPersonVOAtIndexOne.getTheEntityLocatorParticipationDTCollection(), addrString, homeAddrTeleUseCode);

                if(arrayListZero != null && arrayListOne != null)
                {
                    if((arrayListZero.size() > 0) && (arrayListOne.size() > 0))
                    {
                        findLatestHomeAddressOrTeleOrNameForAMPR(arrayListZero, amongstMPRs);
                        findLatestHomeAddressOrTeleOrNameForAMPR(arrayListOne, amongstMPRs);

                        arrayListFinal.clear();
                        arrayListFinal.add(arrayListZero.get(0));
                        arrayListFinal.add(arrayListOne.get(0));

                        result =  determineDiscardableAddressOrTeleOrName(arrayListFinal, acrossMPRs);
                        if(result >= 0) return result;
                    }
                }



                // If result is -1 means there is a tie between the two MPR's(wrt2 AOD Addr) proceed to Compare AOD for home telephone number...
                arrayListZero = buildAddressOrTeleOrNameArrayListOutofCollection(inPersonVOAtIndexZero.getTheEntityLocatorParticipationDTCollection(), teleString, homeAddrTeleUseCode);
                arrayListOne = buildAddressOrTeleOrNameArrayListOutofCollection(inPersonVOAtIndexOne.getTheEntityLocatorParticipationDTCollection(), teleString, homeAddrTeleUseCode);

                if(arrayListZero != null && arrayListOne != null)
                {
                    if((arrayListZero.size() > 0) && (arrayListOne.size() > 0))
                    {
                        findLatestHomeAddressOrTeleOrNameForAMPR(arrayListZero, amongstMPRs);
                        findLatestHomeAddressOrTeleOrNameForAMPR(arrayListOne, amongstMPRs);

                        arrayListFinal.clear();
                        arrayListFinal.add(arrayListZero.get(0));
                        arrayListFinal.add(arrayListOne.get(0));

                        result =  determineDiscardableAddressOrTeleOrName(arrayListFinal, acrossMPRs);
                        if(result >= 0) return result;
                    }
                }

            }
            catch(Exception e)
            {
                throw new Exception(e.toString());
            }
            // If result is -1 means there is a tie between the MPR's (wrt2 AOD LName) randomly determine which needs to be kept...
            // let us discard the one at index zero...
            return 0;
        }
        /**
         * Given a ArrayList<Object>  finds the latest HomeAddress Or tele Or Name for a MPR
         * @param inArrayList
         * @param inString
         * @throws Exception
         */
        private void findLatestHomeAddressOrTeleOrNameForAMPR(ArrayList<Object>  inArrayList, String inString) throws Exception
        {
            try
            {
                int size = inArrayList.size();

                while(size-- > 1)
                {
                    int index = determineDiscardableAddressOrTeleOrName(inArrayList, inString);
                    inArrayList.remove(index);
                }
            }
            catch(Exception e)
            {
                throw new Exception(e.toString());
            }
        }

        /**
         * Given a ArrayList<Object>  finds the latest Name for a MPR
         * @param inArrayList
         * @param inStr1
         * @param inStr2
         * @throws Exception
         */
        private void findLatestHomeAddressOrTeleOrNameForAMPR(ArrayList<Object>  inArrayList, String inStr1, String inStr2) throws Exception
        {
            try
            {
                int size = inArrayList.size();

                while(size-- > 1)
                {
                    int index = determineDiscardableAddressOrTeleOrName(inArrayList, inStr1, inStr2);
                    inArrayList.remove(index);
                }
            }
            catch(Exception e)
            {
                throw new Exception(e.toString());
            }
        }
        /**
         * Builds a ArrayList<Object>  of Addresses Or Teles Or Names provided a Collection<Object> and
         * what specific ArrayList<Object>  needs to be built.
         * @param inColl
         * @param inStr
         * @return ArrayList<Object> 
         */
        private ArrayList<Object>  buildAddressOrTeleOrNameArrayListOutofCollection(Collection<Object> inColl, String inStr, String inUseCode)
        {
            ArrayList<Object>  arrayList = new ArrayList<Object> ();

            if(inColl.isEmpty()) return null;

            Iterator<Object> iterator = inColl.iterator();

            if(inStr.equals(NEDSSConstants.PHYSICAL))
            {
                while(iterator.hasNext())
                {
                    Object obj = iterator.next();

                    if(((PersonNameDT)obj).getNmUseCd() != null)
                    {
                        if(((PersonNameDT)obj).getNmUseCd().equals(inUseCode))
                        {
                            arrayList.add(obj);
                        }
                    }
                }
                return arrayList;
            }

            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                EntityLocatorParticipationDT elpDT = (EntityLocatorParticipationDT)obj;

                if(elpDT.getClassCd() != null && elpDT.getUseCd() != null)
                {
                    if(elpDT.getClassCd().equals(inStr) && (elpDT).getUseCd().equals(inUseCode))
                    {
                        arrayList.add(obj);
                    }
                }
            }

            return arrayList;

        }
        /**
         * Given a ArrayList<Object>  of Addresses or Teles or Names determines the element that needs to be
         * discarded, based on business rules. Also the discard is picked amongst the first and second
         * elements of the ArrayList<Object> .
         * @param inArrayList
         * @return int
         * @throws Exception
         */
        private int determineDiscardableAddressOrTeleOrName(ArrayList<Object>  inArrayList, String inString) throws Exception
        {
            EntityLocatorParticipationDT elpDTAtIndexZero = (EntityLocatorParticipationDT)inArrayList.get(0);
            EntityLocatorParticipationDT elpDTAtIndexOne = (EntityLocatorParticipationDT)inArrayList.get(1);

            try
            {
                if(elpDTAtIndexZero.getAsOfDate() == null) return 0;

                if(elpDTAtIndexOne.getAsOfDate() == null) return 1;

                if(elpDTAtIndexZero.getAsOfDate().before(elpDTAtIndexOne.getAsOfDate())) return 0;

                if(elpDTAtIndexZero.getAsOfDate().after(elpDTAtIndexOne.getAsOfDate())) return 1;

                if(inString.equals("ACROSS"))
                {
                    if(elpDTAtIndexZero.getAsOfDate().equals(elpDTAtIndexOne.getAsOfDate())) return -1;
                }

                if(elpDTAtIndexZero.getAsOfDate().equals(elpDTAtIndexOne.getAsOfDate())) return 0;

            }
            catch(Exception e)
            {
                throw new Exception(e.toString());
            }

            return 0;
        }
        /**
         * GIven two PersonNameDT objects determines the one to be discarded.
         * @param inArrayList
         * @param inStr
         * @return int
         * @throws Exception
         */
        private int determineDiscardableAddressOrTeleOrName(ArrayList<Object>  inArrayList, String inStr, String inStr1) throws Exception
        {
            PersonNameDT pnDTAtIndexZero = (PersonNameDT)inArrayList.get(0);
            PersonNameDT pnDTAtIndexOne = (PersonNameDT)inArrayList.get(1);

            try
            {
                if(pnDTAtIndexZero.getAsOfDate() == null ) return 0;

                if(pnDTAtIndexOne.getAsOfDate() == null ) return 1;

                if(pnDTAtIndexZero.getAsOfDate().before(pnDTAtIndexOne.getAsOfDate())) return 0;

                if(pnDTAtIndexZero.getAsOfDate().after(pnDTAtIndexOne.getAsOfDate())) return 1;

                if(inStr1.equals("ACROSS"))
                {
                    if(pnDTAtIndexZero.getAsOfDate().equals(pnDTAtIndexOne.getAsOfDate())) return -1;
                }

                if(pnDTAtIndexZero.getAsOfDate().equals(pnDTAtIndexOne.getAsOfDate())) return 0;

            }
            catch(Exception e)
            {
                throw new Exception(e.toString());
            }

            return 0;
        }

        /**
         * Appends DEM196+DEM215 of discarded MPR to DEM196+DEM215 of surviving MPR and places the whole string
         * in DEM196 of surviving MPR.
         * @param inArrayList
         * @param inIndex
          */
        private void appendDiscarded196And215ToSurvivor196(ArrayList<Object>  inArrayList, int inIndex, int repeatAppend)
        {
            PersonDT personDTAtIndexZero;
            personDTAtIndexZero = ((PersonVO)inArrayList.get(0)).getThePersonDT();
            PersonDT personDTAtIndexOne;
            personDTAtIndexOne = ((PersonVO)inArrayList.get(1)).getThePersonDT();
            StringBuffer stringBuffer;


            if(inIndex == 1)
            {
                // If the appending object's description is more than 2000 chars ignore the append
                // return;
                if(personDTAtIndexOne.getDescription() != null)
                {
                    if(personDTAtIndexOne.getDescription().length() >= 2000) return;
                }

                // do something... append index1's 196&215 to index0's 196&215
                // place in index0's 196.
                stringBuffer = new StringBuffer();

                if(personDTAtIndexZero.getAsOfDateAdmin() != null && repeatAppend == 0) stringBuffer.append(StringUtils.formatDate(personDTAtIndexZero.getAsOfDateAdmin()) + " ");

                if(personDTAtIndexZero.getDescription() != null) stringBuffer.append(personDTAtIndexZero.getDescription() + " ");

                if(stringBuffer.length() > 0 && (personDTAtIndexOne.getAsOfDateAdmin() != null || personDTAtIndexOne.getDescription() != null))
                  stringBuffer.append("| ");

                if(personDTAtIndexOne.getAsOfDateAdmin() != null) stringBuffer.append(StringUtils.formatDate(personDTAtIndexOne.getAsOfDateAdmin()) + " ");

                if(personDTAtIndexOne.getDescription() != null) stringBuffer.append(personDTAtIndexOne.getDescription()+ " ");

                if(stringBuffer.length() > 2000) return;

                personDTAtIndexZero.setDescription(stringBuffer.toString());

                return;
           }

            // If the appending object's description is more than 2000 chars ignore the append
            // return;
            if(personDTAtIndexZero.getDescription() != null)
            {
                if(personDTAtIndexZero.getDescription().length() >= 2000) return;
            }

            // do something... append index0's 196&215 to index1's 196&215
            // place in index1's 196.
            stringBuffer = new StringBuffer();

            if(personDTAtIndexOne.getAsOfDateAdmin() != null) stringBuffer.append(StringUtils.formatDate(personDTAtIndexOne.getAsOfDateAdmin())+ " ");

            if(personDTAtIndexOne.getDescription() != null) stringBuffer.append(personDTAtIndexOne.getDescription()+ " ");

            if(stringBuffer.length() > 0 && (personDTAtIndexZero.getAsOfDateAdmin() != null || personDTAtIndexZero.getDescription() != null))
                  stringBuffer.append("| ");

            if(personDTAtIndexZero.getAsOfDateAdmin() != null) stringBuffer.append(StringUtils.formatDate(personDTAtIndexZero.getAsOfDateAdmin())+ " ");

            if(personDTAtIndexZero.getDescription() != null) stringBuffer.append(personDTAtIndexZero.getDescription()+ " ");

            if(stringBuffer.length() > 2000) return;

            personDTAtIndexOne.setDescription(stringBuffer.toString());

            return;
        }
        /**
         * Method to get the Superceded MPR collection.
         * @return Collection<Object>
         */
        public Collection<Object> getSupercededMPRCollection()
        {
            return discardedCollection;
        }

        /**
         * Generates a collection of MergeConfirmationVO objects.
         * @param survivorPvo
         * @param supercededColl
         * @return
         */
      public Collection<Object> getMergeConfirmationVO() {
        return mergeConfirmationVo;
      }//end of getMergeConfirmationVO()

}
