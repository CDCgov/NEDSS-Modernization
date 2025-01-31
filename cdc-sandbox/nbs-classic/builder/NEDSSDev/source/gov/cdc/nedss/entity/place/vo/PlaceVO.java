/**
 * Title: PlaceVO helper class.
 * Description: A helper class for place value objects
 * Copyright:    Copyright (c) 2001
 * Company: Computer Sciences Corporation
 * @author NEDSS Development Team
 * @version 1.0
 */

package gov.cdc.nedss.entity.place.vo;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.place.dt.PlaceDT;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PlaceVO extends AbstractVO implements java.io.Serializable
{
    private static final long    serialVersionUID                          = 1L;
    protected PlaceDT            thePlaceDT                                = new PlaceDT();
    protected Collection<Object> theEntityLocatorParticipationDTCollection = new ArrayList<Object>();
    protected Collection<Object> theEntityIdDTCollection                   = new ArrayList<Object>();
    protected Collection<Object> theParticipationDTCollection              = new ArrayList<Object>();
    protected Collection<Object> theRlDTCollection=null;
    private String localIdentifier;

    /**
     * Sets the property value of the PlaceVO object.
     * 
     * @param thePlaceDT
     *            the new value of the thePlaceDT property
     * @param theEntityLocatorParticipationDTCollection
     *            the new value of the theEntityLocatorParticipationDTCollection
     *            property
     * @param theEntityIdDTCollection
     *            the new value of the theEntityIdDTCollection property
     * @param theParticipationDTCollection
     *            the new value of the theParticipationDTCollection property
     * @param theRoleDTCollection
     *            the new value of the theRoleDTCollection property
     */
    public PlaceVO(PlaceDT thePlaceDT, Collection<Object> theEntityLocatorParticipationDTCollection,
            Collection<Object> theEntityIdDTCollection, Collection<Object> theParticipationDTCollection,
            Collection<Object> theRoleDTCollection)
    {
        this.thePlaceDT = thePlaceDT;
        this.theEntityLocatorParticipationDTCollection = theEntityLocatorParticipationDTCollection;
        this.theEntityIdDTCollection = theEntityIdDTCollection;
        this.theParticipationDTCollection = theParticipationDTCollection;
        this.theRlDTCollection = theRoleDTCollection;
        this.setItNew(true);
    }

    /**
         *
         */
    public PlaceVO()
    {
        this.setItNew(true);
    }

    /**
     * Sets the value of the thePlaceDT property.
     * 
     * @param pdt
     *            the new value of the thePlaceDT property
     */
    public void setThePlaceDT(PlaceDT pdt)
    {
        thePlaceDT = pdt;
    }

    /**
     * Sets the value of the itDirty property.
     * 
     * @param state
     *            the new value of the itDirty property
     */
    public void setItDirty(boolean state)
    {
        itDirty = state;
    }

    /**
     * Access method for the itNew property.
     * 
     * @return the current value of the itNew property
     */
    public boolean isItNew()
    {
        return itNew;
    }

    // @TODO Method has been altered, but solution should be verified
    /**
     * This method compares two objects and returns the results
     * 
     * @param objectname1
     * @param objectname2
     * @param voClass
     * @return the result of the comparison
     */
    public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass)
    {
        Class<? extends Object> theClass = objectname1.getClass();
        NedssUtils compareObjs = new NedssUtils();
        return (compareObjs.equals(objectname1, objectname2, theClass));
    }

    /**
     * Sets the value of the itNew property.
     * 
     * @param state
     *            the new value of the itNew property
     */
    public void setItNew(boolean state)
    {
        itNew = state;
    }

    /**
     * Access method for the itDirty property.
     * 
     * @return the current value of the itDirty property
     */
    public boolean isItDirty()
    {
        return itDirty;
    }

    /**
     * Access method for the thePlaceDT property.
     * 
     * @return the current value of the thePlaceDT property
     */
    public PlaceDT getThePlaceDT()
    {
        return thePlaceDT;
    }

    /**
     * Access method for the itDelete property.
     * 
     * @return the current value of the itDelete property
     */
    public boolean isItDelete()
    {
        return itDelete;
    }

    /**
     * Sets the value of the itDelete property.
     * 
     * @param itDelete
     *            the new value of the itDelete property
     */
    public void setItDelete(boolean itDelete)
    {
        this.itDelete = itDelete;
    }

    /**
     * Access method for the theEntityLocatorParticipationDTCollection property.
     * 
     * @return the current value of the
     *         theEntityLocatorParticipationDTCollection property
     */
    public Collection<Object> getTheEntityLocatorParticipationDTCollection()
    {
        return theEntityLocatorParticipationDTCollection;
    }

    /**
     * Sets the value of both theEntityLocatorParticipationDTCollection and
     * itDirty properties.
     * 
     * @param theEntityLocatorParticipationDT
     *            the new value of the theEntityLocatorParticipationDTCollection
     *            property
     */
    public void setTheEntityLocatorParticipationDTCollection(Collection<Object> theEntityLocatorParticipationDT)
    {
        this.theEntityLocatorParticipationDTCollection = theEntityLocatorParticipationDT;
        this.setItDirty(true);
    }

    /**
     * Access method for the theEntityIdDTCollection property.
     * 
     * @return the current value of the theEntityIdDTCollection property
     */
    public Collection<Object> getTheEntityIdDTCollection()
    {
        return theEntityIdDTCollection;
    }

    /**
     * Sets the value of both theEntityIdDTCollection and itDirty properties.
     * 
     * @param theEntityIdDT
     *            the new value of the theEntityIdDTCollection property
     */
    public void setTheEntityIdDTCollection(Collection<Object> theEntityIdDT)
    {
        this.theEntityIdDTCollection = theEntityIdDT;
        this.setItDirty(true);
    }

    /**
     * Access method for the theParticipationDTCollection property.
     * 
     * @return the current value of the theParticipationDTCollection property
     */
    public Collection<Object> getTheParticipationDTCollection()
    {
        return theParticipationDTCollection;
    }

    /**
     * Sets the value of the theParticipationDTCollection and itDirty
     * properties.
     * 
     * @param aTheParticipationDTCollection
     *            the new value of the theParticipationDTCollection property
     */
    public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection)
    {
        theParticipationDTCollection = aTheParticipationDTCollection;
        this.setItDirty(true);
    }

    /**
     * Access method for the theRoleDTCollection property.
     * 
     * @return the current value of the theRoleDTCollection property
     */
    public Collection<Object> getTheRoleDTCollection()
    {
        return theRlDTCollection;
    }

    /**
     * Sets the value of the theRoleDTCollection and itDirty properties.
     * 
     * @param aTheRoleDTCollection
     *            the new value of the theRoleDTCollection property
     */
    public void setTheRoleDTCollection(Collection<Object> aTheRoleDTCollection)
    {
        theRlDTCollection = aTheRoleDTCollection;
        this.setItDirty(true);
    }

    /**
     * Access method for a selected EntityLocatorParticipationDT object
     * 
     * @param index
     *            the index value of the EntityLocatorParticipationDT object in
     *            theEntityLocatorParticipationDTCollection property
     * @return the selected EntityLocatorParticipationDT object
     */
    public EntityLocatorParticipationDT getEntityLocatorParticipationDT_s(int index)
    {
        int currentSize = this.theEntityLocatorParticipationDTCollection.size();

        // check if we have a this many personNameDTs
        if (index < currentSize)
        {
            try
            {
                Object[] tempArray = this.theEntityLocatorParticipationDTCollection.toArray();

                Object tempObj = tempArray[index];

                EntityLocatorParticipationDT tempDT = (EntityLocatorParticipationDT) tempObj;
                if(tempDT.getThePostalLocatorDT()==null){
                	tempDT.setThePostalLocatorDT(new PostalLocatorDT());
                }
                if(tempDT.getTheTeleLocatorDT()==null){
                	tempDT.setTheTeleLocatorDT(new TeleLocatorDT());
                }

                return tempDT;
            }
            catch (Exception e)
            {
                // ##!! System.out.println(e);
            } // do nothing just continue
        }

        EntityLocatorParticipationDT tempDT = null;

        for (int i = currentSize; i < index + 1; i++)
        {
            tempDT = new EntityLocatorParticipationDT();
            PostalLocatorDT p = new PostalLocatorDT();
            tempDT.setThePostalLocatorDT(p);
            TeleLocatorDT t = new TeleLocatorDT();
            tempDT.setTheTeleLocatorDT(t);
            tempDT.setItNew(true);
            this.theEntityLocatorParticipationDTCollection.add(tempDT);
        }

        return tempDT;
    }

    /**
     * Access method for a selected EntityIdDT object
     * 
     * @param index
     *            the index value of the EntityIdDT object in
     *            theEntityIdDTCollection property
     * @return the selected EntityIdDT object
     */
    public EntityIdDT getEntityIdDT_s(int index)
    {
        int currentSize = this.theEntityIdDTCollection.size();

        // check if we have a this many personNameDTs
        if (index < currentSize)
        {
            try
            {
                Object[] tempArray = this.theEntityIdDTCollection.toArray();

                Object tempObj = tempArray[index];

                EntityIdDT tempDT = (EntityIdDT) tempObj;

                return tempDT;
            }
            catch (Exception e)
            {
            } // do nothing just continue
        }

        EntityIdDT tempDT = null;

        for (int i = currentSize; i < index + 1; i++)
        {
            tempDT = new EntityIdDT();
            tempDT.setItNew(true);
            this.theEntityIdDTCollection.add(tempDT);
        }

        return tempDT;
    }

    // Convenient methods for setting/getting children data.

    public String getNm()
    {
        return thePlaceDT.getNm();
    }

    public void setNm(String aNm)
    {
        thePlaceDT.setNm(aNm);
    }

    public void setPlaceUid(Long placeUid)
    {
        thePlaceDT.setPlaceUid(placeUid);
    }

    public Long getPlaceUid()
    {
        return thePlaceDT.getPlaceUid();
    }
    
    public void setCd(String aCd)
    {
        thePlaceDT.setCd(aCd);
    }
    
    public String getCd()
    {
        return thePlaceDT.getCd();
    }
    
    public void setCdDescTxt(String aCdDescTxt)
    {
        thePlaceDT.setCdDescTxt(aCdDescTxt);
    }
    
    public String getCdDescTxt()
    {
        return thePlaceDT.getCdDescTxt();
    }

    public String getAddress()
    {
        StringBuffer sb = new StringBuffer(); 
        List<Object> list = getLatest(NEDSSConstants.POSTAL);
        boolean first = true;
        for(int i = 0; i < list.size(); i++ )
        {
            PlaceSearchVO p = (PlaceSearchVO) list.get(i);
            if( p.getPlaceUid()  != null )
            {
                String id = "pl" + i + "_" + p.getPlaceUid();
                sb.append( "<span id='" + id + "'>");
                sb.append( p.getAddress() );
                sb.append( "</span>");
                if (first)
                {
                    sb.append("<span id='" + id + "u" + "' style='display:none'>" + p.getLocatorUid() + "</span>");
                    first = false;
                }
            } 
        } 
        return sb.toString();
    }

    public String getTelephone()
    {
        StringBuffer sb = new StringBuffer(); 
        List<Object> list = getLatest(NEDSSConstants.TELE);
        boolean first = true;
        for(int i = 0; i < list.size(); i++ )
        {
            PlaceSearchVO p = (PlaceSearchVO) list.get(i);
            if( p.getPlaceUid() != null )
            {
                String id = "tl" + i + "_" + p.getPlaceUid();
                sb.append( "<span id='" + id + "'>");
                sb.append( p.getTelephone()  );
                sb.append( "</span>");
                if (first)
                {
                    sb.append("<span id='" + id + "u" + "' style='display:none'>" + p.getLocatorUid() + "</span>");
                    first = false;
                } 
            } 
        } 
        return sb.toString(); 
    }
    
    public String getTelephone(Long teleLocatorUid)
    {
        StringBuffer sb = new StringBuffer();
        if (theParticipationDTCollection != null)
        {
            Iterator<Object> iter = theParticipationDTCollection.iterator();
            while (iter.hasNext())
            {
                PlaceSearchVO p = (PlaceSearchVO) iter.next();
                if (NEDSSConstants.TELE.equalsIgnoreCase(p.getClassCd()))
                {
                   if( p.getLocatorUid() != null && p.getLocatorUid().equals(teleLocatorUid) )
                       sb.append( p.getTelephone() );
                }
            }
        }
        return sb.toString();
    }
    
    public String getAddress(Long postalLocatorUid)
    {
        StringBuffer sb = new StringBuffer();

        if (theParticipationDTCollection != null)
        {
            Iterator<Object> iter = theParticipationDTCollection.iterator();
            while (iter.hasNext())
            {
                PlaceSearchVO p = (PlaceSearchVO) iter.next();
                if (NEDSSConstants.POSTAL.equalsIgnoreCase(p.getClassCd()))
                {
                    if( p.getLocatorUid() != null && p.getLocatorUid().equals(postalLocatorUid))
                        sb.append( p.getAddress() );
                }
            }
        }
        return sb.toString();
    }
    

    public String getQuickEntryCode()
    {
        Iterator<?> iter = getTheEntityIdDTCollection().iterator();
        while (iter.hasNext())
        {
            EntityIdDT eid = (EntityIdDT) iter.next();
            return eid.getRootExtensionTxt();
        }
        return "";
    }

    public String getTypeName()
    {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isEmpty(thePlaceDT.getCd()))
        {
            sb.append(thePlaceDT.getNm());
        }
        else
        {
            String desc = CachedDropDowns.getCodeDescTxtForCd(thePlaceDT.getCd(), "PLACE_TYPE");;
            if (NEDSSConstants.OTHER.equalsIgnoreCase(thePlaceDT.getCd()))
            {
              if( !StringUtils.isEmpty(thePlaceDT.getCdDescTxt()))
              {
                  desc += " (" + thePlaceDT.getCdDescTxt() + ")";
              }
            } 

            sb.append("<i>" + desc + "</i><br/>");
            sb.append(thePlaceDT.getNm());
        }
        return sb.toString();
    }
    
    public String getTypeDesc()
    {
        StringBuffer sb = new StringBuffer();
        if (!StringUtils.isEmpty(thePlaceDT.getCd()))
        { 
            String desc = CachedDropDowns.getCodeDescTxtForCd(thePlaceDT.getCd(), "PLACE_TYPE");
            if( NEDSSConstants.OTHER.equalsIgnoreCase(thePlaceDT.getCd()))
            {
                sb.append("<i>" + thePlaceDT.getCd() + " - "+ desc );
                if( !StringUtils.isEmpty(thePlaceDT.getCdDescTxt()) )
                {
                    sb.append("(" + thePlaceDT.getCdDescTxt() + ")");
                } 
            }
            else
            {
                sb.append("<i>" + thePlaceDT.getCd() + " - "+ desc + "</i>");
            } 
        }
        return sb.toString();
    }

    public PlaceSearchVO getLatestAddress()
    {
        List<Object> a = getLatest(NEDSSConstants.POSTAL);
        return (PlaceSearchVO)a.get(0) ;
        
    }

    public PlaceSearchVO getLatestPhone()
    {
        List<Object> a = getLatest(NEDSSConstants.TELE);
        return (PlaceSearchVO) a.get(0);
    }

    private List<Object> getLatest(String classCd)
    {
        Collection<?> c = getTheParticipationDTCollection();
        List<Object> a = new ArrayList<Object>();
        Iterator<?> cIter = c.iterator();
        while (cIter.hasNext())
        {
            PlaceSearchVO ps = (PlaceSearchVO) cIter.next();
            if (classCd.equalsIgnoreCase(ps.getClassCd()))
            {
                if(StringUtils.isEmpty(ps.getState()))
                {
                    ps.setState(CachedDropDowns.getStateName( ps.getState()) );
                }
                a.add(ps);
            }
        }
        NedssUtils u = new NedssUtils();
        if (a.size() > 0)
        {
            u.sortObjectByColumn("getAsOfDate", a, false);
        }
        else
        {
            PlaceSearchVO p = new PlaceSearchVO();
            p.setLocatorUid( new Long(-1) );
            a.add(p);
        }
        return a;
    }

	public String getLocalIdentifier() {
		return localIdentifier;
	}

	public void setLocalIdentifier(String localIdentifier) {
		this.localIdentifier = localIdentifier;
	}
}