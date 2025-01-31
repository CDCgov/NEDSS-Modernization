

package gov.cdc.nedss.act.observation.dt;

import java.math.BigDecimal;
import java.sql.Timestamp;
import  gov.cdc.nedss.util.*;

   /**
    * Title:            ObsValueNumericDT is a class
    * Description:	This is a class which sets/gets(retrieves)
    *                   all the fields to the database table
    * Copyright:	Copyright (c) 2001
    * Company: 	        Computer Sciences Corporation
    * @author           NEDSS TEAM
    * @version	        1.0
    */
 
public class ObsValueNumericDT extends AbstractVO
{
	private static final long serialVersionUID = 1L;
    private String numericValue;

		private Long observationUid;

    private Integer obsValueNumericSeq;

    private String highRange;

    private String lowRange;

    private String comparatorCd1;

    private BigDecimal numericValue1;

    private BigDecimal numericValue2;

    private String numericUnitCd;

    private String separatorCd;

     private String progAreaCd = null;

     private String jurisdictionCd = null;

     private Long programJurisdictionOid = null;

     private String sharedInd = null;
     
     private Integer numericScale1;
     
     private Integer numericScale2;

    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;
    /**commented out on feb 11
    private String hiddenNumericUnitCd;
    private String hiddenNumericResultVal;
    private String hiddenRangeVal;
*/
		public String getNumericValue(){
			return this.numericValue;
		}

		public void setNumericValue(String numericValue){
			this.numericValue = numericValue;
		}


    /**
    * Access method for the ObservationUid.
    *
    * @return String  the current value of the ObservationUid
    */
    public Long getObservationUid()
    {
        return observationUid;
    }
    /**
    * Sets the value of the ObservationUid.
    *
    * @param Long the aObservationUid
    */
    public void setObservationUid(Long aObservationUid)
    {
        observationUid = aObservationUid;
        setItDirty(true);
    }

    /**
    * Access method for the ObsValueNumericSeq.
    *
    * @return Integer  the current value of the ObsValueNumericSeq
    */
    public Integer getObsValueNumericSeq()
    {
        return obsValueNumericSeq;
    }

    /**
    * Sets the value of the ObsValueNumericSeq.
    *
    * @param Integer the aObsValueNumericSeq
    */
    public void setObsValueNumericSeq(Integer aObsValueNumericSeq)
    {
        obsValueNumericSeq = aObsValueNumericSeq;
        setItDirty(true);
    }

    /**
    * Access method for the HighRange.
    *
    * @return String  the current value of the HighRange
    */
    public String getHighRange()
    {
        return highRange;
    }

    /**
    * Sets the value of the HighRange.
    *
    * @param String the aHighRange
    */
    public void setHighRange(String aHighRange)
    {
        highRange = aHighRange;
        setItDirty(true);
    }

    /**
    * Access method for the LowRange.
    *
    * @return String  the current value of the LowRange
    */
    public String getLowRange()
    {
        return lowRange;
    }

    /**
    * Sets the value of the LowRange.
    *
    * @param String the aLowRange
    */
    public void setLowRange(String aLowRange)
    {
        lowRange = aLowRange;
        setItDirty(true);
    }

    /**
    * Access method for the ComparatorCd1.
    *
    * @return String  the current value of the ComparatorCd1
    */
    public String getComparatorCd1()
    {
        return comparatorCd1;
    }

    /**
    * Sets the value of the ComparatorCd1.
    *
    * @param String the aComparatorCd1
    */
    public void setComparatorCd1(String aComparatorCd1)
    {
        comparatorCd1 = aComparatorCd1;
        setItDirty(true);
    }

    /**
    * Access method for the NumericValue1.
    *
    * @return Double  the current value of the NumericValue1
    */
	public BigDecimal getNumericValue1() {
		// Get the precision scale from the database and then translate the
		// value read from database to right precision
		// It will fix the issue where entering 2.0 in the front end was getting
		// translated to 2.00000 in SQL Server and to 2 in case of oracle for
		// both numeric_value_1 and numeric_value_2.
		if (numericValue1 != null && this.getNumericScale1() != null
				&& this.getNumericScale1().intValue() <= 5)
			return numericValue1.setScale(this.getNumericScale1()
					.intValue());
		return numericValue1;
	}

    /**
    * Sets the value of the NumericValue1.
    *
    * @param Double the aNumericValue1
    */
	public void setNumericValue1(BigDecimal aNumericValue1) {
		// set the precision scale from the input value and then translate the
		// value read from database to right precision.
		// It will fix the issue where entering 2.0 in the front end was getting
		// translated to 2.00000 in SQL Server and to 2 in case of oracle for
		// both numeric_value_1 and numeric_value_2.
		if (aNumericValue1 != null && this.getNumericScale1() == null) {
			this.setNumericScale1(new Integer(aNumericValue1.scale()));
		}
		numericValue1 = aNumericValue1;
		setItDirty(true);
	}

    /**
    * Sets the value of the NumericValue1.
    *
    * @param String the aNumericValue1
    */
	public void setNumericValue1_s(String sNumericValue1) {
		// set the precision scale from the input value and then translate the
		// value read from database to right precision.
		// It will fix the issue where entering 2.0 in the front end was getting
		// translated to 2.00000 in SQL Server and to 2 in case of oracle for
		// both numeric_value_1 and numeric_value_2.
		if (sNumericValue1 == null || sNumericValue1.trim().equals(""))
			this.setNumericValue1(null);
		else {
			this.setNumericValue1(new BigDecimal(sNumericValue1));
			this.setNumericScale1(new Integer(
					new BigDecimal(sNumericValue1).scale()));
		}
	}

    /**
    * Access method for the NumericValue2.
    *
    * @return Double  the current value of the NumericValue2
    */
	public BigDecimal getNumericValue2() {
		// Get the precision scale from the database and then translate the
		// value read from database to right precision.
		// It will fix the issue where entering 2.0 in the front end was getting
		// translated to 2.00000 in SQL Server and to 2 in case of oracle for
		// both numeric_value_1 and numeric_value_2.
		if (numericValue2 != null && this.getNumericScale2() != null
				&& this.getNumericScale2().intValue() <= 5)
			return numericValue2.setScale(this.getNumericScale2()
					.intValue());
		return numericValue2;
	}

    /**
    * Sets the value of the NumericValue2.
    *
    * @param Double the aNumericValue2
    */
    public void setNumericValue2(BigDecimal aNumericValue2)
    {
    	// set the precision scale from the input value and then translate the
		// value read from database to right precision.
		// It will fix the issue where entering 2.0 in the front end was getting
		// translated to 2.00000 in SQL Server and to 2 in case of oracle for
		// both numeric_value_1 and numeric_value_2.
    	if (aNumericValue2 != null && this.getNumericScale2() == null) {
			this.setNumericScale2(new Integer(aNumericValue2.scale()));
		}
        numericValue2 = aNumericValue2;
        setItDirty(true);
    }

    /**
    * Access method for the NumericUnitCd.
    *
    * @return String  the current value of the NumericUnitCd
    */
    public String getNumericUnitCd()
    {
        return numericUnitCd;
    }

    /**
    * Sets the value of the NumericUnitCd.
    *
    * @param String the aNumericUnitCd
    */
    public void setNumericUnitCd(String aNumericUnitCd)
    {
        numericUnitCd = aNumericUnitCd;
        setItDirty(true);
    }

    /**
    * Access method for the SeparatorCd.
    *
    * @return String  the current value of the SeparatorCd
    */
    public String getSeparatorCd()
    {
        return separatorCd;
    }

    /**
    * Sets the value of the SeparatorCd.
    *
    * @param String the aSeparatorCd
    */
    public void setSeparatorCd(String aSeparatorCd)
    {
        separatorCd = aSeparatorCd;
        setItDirty(true);
    }

      /**
    * Access method for the ProgAreaCd.
    *
    * @return String  the current value of the ProgAreaCd
    */
       public String getProgAreaCd()
   {
      return progAreaCd;
   }

   /**
    * Sets the value of the ProgAreaCd.
    *
    * @param String  the aProgAreaCd
    */
      public void setProgAreaCd(String aProgAreaCd)
   {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
   }

   /**
    * Access method for the JurisdictionCd.
    *
    * @return String  the current value of the JurisdictionCd
    */
       public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }

   /**
    * Sets the value of the JurisdictionCd
    *
    * @param String the JurisdictionCd
    */
      public void setJurisdictionCd (String aJurisdictionCd )
   {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
   }

   /**
    * Access method for the ProgramJurisdictionOid.
    *
    * @return Long the current value of the ProgramJurisdictionOid
    */
       public Long getProgramJurisdictionOid ()
   {
      return programJurisdictionOid;
   }

   /**
    * Sets the value of the ProgramJurisdictionOid.
    *
    * @param Long the aProgramJurisdictionOid
    */
      public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
   {
      programJurisdictionOid = aProgramJurisdictionOid;
      setItDirty(true);
   }

   /**
    * Access method for the SharedInd.
    *
    * @return String  the current value of the SharedInd
    */
       public String getSharedInd()
   {
      return sharedInd;
   }
   /**
    * Sets the value of the SharedInd.
    *
    * @param String the aSharedInd
    */
      public void setSharedInd(String aSharedInd)
   {
      sharedInd = aSharedInd;
      setItDirty(true);
   }

   /**
    * get the value of the boolean property.
    * @param objectname1 Object the object name
    * @param objectname2 Object the object name
    * @param voClass Class the class
    * @return isEqual the value of the boolean value
    */
    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( ObsValueNumericDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }


      /**
    * Sets the value of the itDirty property.
    *
    * @param itDirty boolean the new value of the ItDirty property
    */
   public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }


   /**
    * get the value of the boolean property.
    *
    * @return ItDirty the value of the boolean vlue
    */
   public boolean isItDirty() {
     return itDirty;
   }


   /**
    * Sets the value of the ItNew property.
    *
    * @param itNew boolean the new value of the boolean property
    */
   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }


    /**
    * get the value of the itNew property.
    *
    * @return itNew the value of the boolean property
    */
   public boolean isItNew() {
     return itNew;
   }

   /**
    * gets the value of the ItDelete property.
    *
    * @return ItDelete the new value of the boolean property
    */
   public boolean isItDelete() {
     return itDelete;
   }


   /**
    * Sets the value of the ItDelete property.
    *
    * @param itDelete boolean the value of the boolean property
    */
    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }
/**commented out on feb 11   public String getHiddenNumericUnitCd()
{
   return hiddenNumericUnitCd;
}

 public void setHiddenNumericUnitCd(String aHiddenNumericUnitCd)
 {
   hiddenNumericUnitCd = aHiddenNumericUnitCd;
   setItDirty(true);
 }

 public String getHiddenNumericResultVal()
 {
   return hiddenNumericResultVal;
  }

  public void setHiddenNumericResultVal(String aHiddenNumericResultVal)
  {
    hiddenNumericResultVal = aHiddenNumericResultVal;
    setItDirty(true);
  }

  public String getHiddenRangeVal()
 {
     return hiddenRangeVal;
 }

 public void setHiddenRangeVal(String aHiddenRangeVal)
 {
     hiddenRangeVal = aHiddenRangeVal;
     setItDirty(true);
 }

*/

/**
 * @return the numericPercision1
 */
public Integer getNumericScale1() {
	return numericScale1;
}

/**
 * @param numericPercision1 the numericPercision1 to set
 */
public void setNumericScale1(Integer numericPercision1) {
	this.numericScale1 = numericPercision1;
}

/**
 * @return the numericPercision2
 */
public Integer getNumericScale2() {
	return numericScale2;
}

/**
 * @param numericPercision2 the numericPercision2 to set
 */
public void setNumericScale2(Integer numericPercision2) {
	this.numericScale2 = numericPercision2;
}

}
