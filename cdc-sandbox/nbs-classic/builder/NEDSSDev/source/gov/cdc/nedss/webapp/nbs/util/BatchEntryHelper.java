//Source file: C:\\Project Stuff\\RationalRoseDevelopment\\gov\\cdc\\nedss\\wum\\helpers\\ObservationVO.java

/**
* Name:        ObservationVO.java
* Description:    This is a value object used for identifying an observation
*               and its associated dependent objects.
* Copyright:    Copyright (c) 2001
* Company:     Computer Sciences Corporation
* @author    Brent Chen & NEDSS Development Team
* @version    1.0
*/
package gov.cdc.nedss.webapp.nbs.util;

import gov.cdc.nedss.util.*;
import gov.cdc.nedss.act.attachment.vo.AttachmentVO;
import gov.cdc.nedss.act.observation.vo.*;
import gov.cdc.nedss.act.treatment.vo.*;

import java.util.*;


public class BatchEntryHelper
    extends AbstractVO
{

    private Collection<ObservationVO>  theObservationVOCollection;
    private Collection<Object>  theTreatmentVOCollection;
    private Collection<Object>  theAttachmentCollection;
    
    private String statusCd;
    private Long uid;
    private TreeMap<Object,Object> treeMap;
    private TreeMap<Object,Object> obsTreeMap;

    public BatchEntryHelper()
    {
    }

    public ObservationVO getObservationVO_s(int index)
    {
	if (this.theObservationVOCollection  == null)
	    this.theObservationVOCollection  = new ArrayList<ObservationVO> ();

	int currentSize = this.theObservationVOCollection.size();

	if (index < currentSize)
	{
	    try
	    {
		Object[] tempArray = this.theObservationVOCollection.toArray();
		Object tempObj = tempArray[index];
		ObservationVO tempVO = (ObservationVO)tempObj;

		return tempVO;
	    }
	    catch (Exception e)
	    {
		// do nothing just continue
	    }
	}

	ObservationVO tempVO = null;

	for (int i = currentSize; i < index + 1; i++)
	{
	    tempVO = new ObservationVO();
	    this.theObservationVOCollection.add(tempVO);
	}

	return tempVO;
    }

    /**
     * Treatment BatchEntry
     * @param index
     */
    public TreatmentVO getTreatmentVO_s(int index)
    {
        if (this.theTreatmentVOCollection  == null)
            this.theTreatmentVOCollection  = new ArrayList<Object> ();

        int currentSize = this.theTreatmentVOCollection.size();

        if (index < currentSize)
        {
            try
            {
                Object[] tempArray = this.theTreatmentVOCollection.toArray();
                Object tempObj = tempArray[index];
                TreatmentVO tempVO = (TreatmentVO)tempObj;

                return tempVO;
            }
            catch (Exception e)
            {
                // do nothing just continue
            }
        }

        TreatmentVO tempVO = null;

        for (int i = currentSize; i < index + 1; i++)
        {
            tempVO = new TreatmentVO();
            this.theTreatmentVOCollection.add(tempVO);
        }

        return tempVO;
    }
    

    /**
     * Attachment BatchEntry
     * @param index
     */
    public AttachmentVO getAttachmentVO_s(int index)
    {
        if (this.theAttachmentCollection  == null)
            this.theAttachmentCollection  = new ArrayList<Object> ();

        int currentSize = this.theAttachmentCollection.size();

        if (index < currentSize)
        {
            try
            {
                Object[] tempArray = this.theAttachmentCollection.toArray();
                Object tempObj = tempArray[index];
                AttachmentVO tempVO = (AttachmentVO)tempObj;

                return tempVO;
            }
            catch (Exception e)
            {
                // do nothing just continue
            }
        }

        AttachmentVO tempVO = null;

        for (int i = currentSize; i < index + 1; i++)
        {
            tempVO = new AttachmentVO();
            this.theAttachmentCollection.add(tempVO);
        }

        return tempVO;
    }
    

    public void setObservationVOCollection(Collection<ObservationVO> atheObservationVOCollection)
    {
	this.theObservationVOCollection  = atheObservationVOCollection;
    }

    public void setTreatmentVOCollection(Collection<Object> atheTreatmentVOCollection)
    {
        this.theTreatmentVOCollection  = atheTreatmentVOCollection;
    }

    public Collection<ObservationVO>  getObservationVOCollection()
    {
	return this.theObservationVOCollection;
    }

    public Collection<Object>  getTreatmentVOCollection()
    {
        return this.theTreatmentVOCollection;
    }

    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2,
			   Class<?> voClass)
    {

	return true;
    }

    public void setItDirty(boolean itDirty)
    {
	this.itDirty = itDirty;
    }

    public boolean isItDirty()
    {

	return itDirty;
    }

    public void setItNew(boolean itNew)
    {
	this.itNew = itNew;
    }

    public boolean isItNew()
    {

	return itNew;
    }

    public boolean isItDelete()
    {

	return itDelete;
    }

    public void setItDelete(boolean itDelete)
    {
	this.itDelete = itDelete;
    }

    public String getStatusCd()
    {

	return statusCd;
    }

    public void setStatusCd(String aStatusCd)
    {
	statusCd = aStatusCd;
	setItDirty(true);
    }

    public Long getUid()
    {

	return uid;
    }

    public void setUid(Long aUid)
    {
	uid = aUid;
	setItDirty(true);
    }

    public void setTreeMap(TreeMap aTreeMap)
    {
      treeMap = aTreeMap;
    }

    public TreeMap<Object,Object> getTreeMap()
    {
      return treeMap;
    }

    public void setObsTreeMap(TreeMap aObsTreeMap)
    {
      obsTreeMap = aObsTreeMap;
    }

    public TreeMap<Object,Object> getObsTreeMap()
    {
      return obsTreeMap;
    }

	public Collection<Object> getTheAttachmentCollection() {
		return theAttachmentCollection;
	}

	public void setTheAttachmentCollection(
			Collection<Object> theAttachmentCollection) {
		this.theAttachmentCollection = theAttachmentCollection;
	}

}