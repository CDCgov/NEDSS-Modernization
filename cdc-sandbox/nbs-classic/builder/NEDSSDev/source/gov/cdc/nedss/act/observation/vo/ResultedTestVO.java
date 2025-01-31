package gov.cdc.nedss.act.observation.vo;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author
 * @version 1.0
 */

import gov.cdc.nedss.entity.organization.vo.OrganizationVO;
import gov.cdc.nedss.util.AbstractVO;
import gov.cdc.nedss.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;




public class ResultedTestVO extends AbstractVO {
	private static final long serialVersionUID = 1L;
	static final LogUtils logger = new LogUtils(ResultedTestVO.class.getName());
	public ObservationVO theLab329VO;
	public ObservationVO theResultedTestVO;
	public OrganizationVO thePerformingLabVO;
	public ObservationVO theIsolateVO;
	public ObservationVO theSusceptibilityVO;
    public ObservationVO theTrackIsolateVO;
    public Map<Object,Object> theTrackIsolateVOHaspMap;
    
	public Collection<Object> theSusceptibilityCollection;
	public Collection<Object> theTrackIsolateCollection;
	public Collection<Object> theActivityLocatorParticipationDTCollection;
	//Collections added for Participation and Activity Relationship object association
	public Collection<Object> theParticipationDTCollection;
	public Collection<Object> theActRelationshipDTCollection;
	private String statuscode;
        private boolean itOld;

    public ResultedTestVO() {
    }

	public ResultedTestVO(ObservationVO theResultedTestVO,
                       OrganizationVO thePerformingLabVO,
                       Collection<Object> theSusceptibilityCollection,
					   Collection<Object> theIsolateCollection,
					   Collection<Object> theActivityLocatorParticipationDTCollection,
					   Collection<Object> theTrackIsolateCollection)
   {
	  this.thePerformingLabVO = thePerformingLabVO;
	  this.theResultedTestVO  = theResultedTestVO;
	  this.theTrackIsolateVO = theTrackIsolateVO;
	  this.theSusceptibilityCollection  = theSusceptibilityCollection;
	  this.theActivityLocatorParticipationDTCollection  = theActivityLocatorParticipationDTCollection;
      this.theTrackIsolateCollection  = theTrackIsolateCollection;
	  setItNew(true);
   }

   public void reset()
   {
     thePerformingLabVO= null;
     theResultedTestVO = null;
     thePerformingLabVO = null;
     theIsolateVO = null;
     theSusceptibilityVO = null;
     theTrackIsolateVO=null;
     theSusceptibilityCollection  = null;
     theActivityLocatorParticipationDTCollection  = null;
     theParticipationDTCollection  = null;
     theActRelationshipDTCollection  = null;
     statuscode = null;


   }
  public Collection<Object> getTheActivityLocatorParticipationDTCollection() {
	    return theActivityLocatorParticipationDTCollection;
	}
	public void setTheActivityLocatorParticipationDTCollection(Collection<Object> theActivityLocatorParticipationDTCollection) {
		this.theActivityLocatorParticipationDTCollection  = theActivityLocatorParticipationDTCollection;
		setItDirty(true);
	}

	public ObservationVO getTheResultedTestVO() {

		if(this.theResultedTestVO == null)
			this.theResultedTestVO = new ObservationVO();
		return theResultedTestVO;
	}

	public ObservationVO getTheIsolateVO() {
		if(this.theIsolateVO == null)
			this.theIsolateVO = new ObservationVO();
		return theIsolateVO;
	}
	public void setTheIsolateVO(ObservationVO aTheIsolateVO) {
		theIsolateVO = aTheIsolateVO;
		setItDirty(true);
	}

        public ObservationVO getTheSusceptibilityVO() {
                if(this.theSusceptibilityVO == null)
                        this.theSusceptibilityVO = new ObservationVO();
                return theSusceptibilityVO;
        }
        public void setTheSusceptibilityVO(ObservationVO aTheSusceptibilityVO) {
                theSusceptibilityVO = aTheSusceptibilityVO;
                setItDirty(true);
        }


	public void setTheResultedTestVO(ObservationVO aTheResultedTestVO) {
		theResultedTestVO = aTheResultedTestVO;
		setItDirty(true);
	}

	public OrganizationVO getThePerformingLabVO() {
		logger.info("Inside getThePerformingLabVO method ----------------------------");
		if(this.thePerformingLabVO == null)
			this.thePerformingLabVO = new OrganizationVO();
		return thePerformingLabVO;
	}

	public void setThePerformingLabVO(OrganizationVO aThePerformingLabVO) {
		thePerformingLabVO = aThePerformingLabVO;
		setItDirty(true);
	}

    public Collection<Object> getTheSusceptibilityCollection() {
		return theSusceptibilityCollection;
    }


	public void setTheSusceptibilityCollection(Collection<Object> aTheSusceptibilityCollection) {
      theSusceptibilityCollection  = aTheSusceptibilityCollection;
      setItDirty(true);
    }

	public boolean isItNew() {
        return itNew;
    }
    public boolean isItDirty() {
      return itDirty;
    }
    public boolean isItDelete() {
        return itDelete;
    }

    public void setItNew(boolean itNew) {
      this.itNew = itNew;
    }

	public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
        return true;
    }

	public void setItDelete(boolean itDelete) {
		this.itDelete = itDelete;
    }
    public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
    }

    public Collection<Object> getTheActRelationshipDTCollection() {
     return theActRelationshipDTCollection;
    }

    public void setTheActRelationshipDTCollection(Collection<Object> aTheActRelationshipDTCollection) {
      theActRelationshipDTCollection  = aTheActRelationshipDTCollection;
    }

    public Collection<Object> getTheParticipationDTCollection() {
      return theParticipationDTCollection;
    }

    public void setTheParticipationDTCollection(Collection<Object> aTheParticipationDTCollection) {
     theParticipationDTCollection  = aTheParticipationDTCollection;
    }


    public ObservationVO getSusceptibility(int index) {
        // this should really be in the constructor
		logger.debug("*********** Inside getSusceptibility");
        if (this.getTheSusceptibilityCollection() == null)
            this.theSusceptibilityCollection  = new ArrayList<Object> ();
        int currentSize = this.theSusceptibilityCollection.size();

        // check if we have a this many DTs
        if (index < currentSize)
        {
          try {
            Object[] tempArray = this.theSusceptibilityCollection.toArray();

            Object tempObj  = tempArray[index];

            ObservationVO tempVO = (ObservationVO) tempObj;

            return tempVO;
          }
          catch (Exception e) {
             //##!! System.out.println(e);
          } // do nothing just continue
        }

         ObservationVO tempVO = null;

          for (int i = currentSize; i < index+1; i++)
          {
            tempVO = new ObservationVO();
            tempVO.setItNew(true);  // this should be done in the constructor of the DT
            this.theSusceptibilityCollection.add(tempVO);
          }

          return tempVO;
    }
	public void setItOld(boolean itOld) {
		this.itOld = itOld;
	}
	public boolean getItOld() {
		return itOld;
	}
	public void setStatusCode(String code) {
		this.statuscode = code;
	}
	public String getStatusCode() {
		return this.statuscode;
	}

	public Collection<Object> getTheTrackIsolateCollection() {
		return theTrackIsolateCollection;
	}

	public void setTheTrackIsolateCollection(Collection<Object> theTrackIsolateCollection) {
		this.theTrackIsolateCollection  = theTrackIsolateCollection;
		setItDirty(true);
	}

	public ObservationVO getTheTrackIsolateVO() {
		if(theTrackIsolateVO==null)
			this.theTrackIsolateVO = new ObservationVO();
		return theTrackIsolateVO;
	}

	public void setTheTrackIsolateVO(ObservationVO theTrackIsolateVO) {
		this.theTrackIsolateVO = theTrackIsolateVO;
		setItDirty(true);
	}
	   public ObservationVO getTheTrackIsolate(int index) {
	        logger.debug("*********** Inside getTheTrackIsolate");
	        if (this.getTheTrackIsolateCollection() == null)
	            this.theTrackIsolateCollection= new ArrayList<Object> ();
	        int currentSize = this.theTrackIsolateCollection.size();

	        // check if we have a this many DTs
	        if (index < currentSize)
	        {
	          try {
	            Object[] tempArray = this.theTrackIsolateCollection.toArray();

	            Object tempObj  = tempArray[index];

	            ObservationVO tempVO = (ObservationVO) tempObj;

	            return tempVO;
	          }
	          catch (Exception e) {
	             //##!! System.out.println(e);
	          } // do nothing just continue
	        }

	         ObservationVO tempVO = null;

	          for (int i = currentSize; i < index+1; i++)
	          {
	            tempVO = new ObservationVO();
	            tempVO.setItNew(true);  
	            this.theTrackIsolateCollection.add(tempVO);
	          }

	          return tempVO;
	    }

	public ObservationVO getTheLab329VO() {
		return theLab329VO;
	}

	public void setTheLab329VO(ObservationVO theLab329VO) {
		this.theLab329VO = theLab329VO;
	}

	public Map<Object,Object> getTheTrackIsolateVOHaspMap() {
		return theTrackIsolateVOHaspMap;
	}

	public void setTheTrackIsolateVOHaspMap(Map<Object,Object> theTrackIsolateVOHaspMap) {
		this.theTrackIsolateVOHaspMap = theTrackIsolateVOHaspMap;
	}

}//ResultedTestVO