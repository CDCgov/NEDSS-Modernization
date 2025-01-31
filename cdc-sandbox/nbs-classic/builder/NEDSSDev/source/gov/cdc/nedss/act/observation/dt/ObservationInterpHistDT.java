

package gov.cdc.nedss.act.observation.dt;

import java.sql.Timestamp;
import gov.cdc.nedss.util.*;

public class ObservationInterpHistDT extends AbstractVO
{
	private static final long serialVersionUID = 1L;

    private Long observationUid;

    private String interpretationCd;

    private Integer versionCtrlNbr;

    private String interpretationTxt;

     private String progAreaCd = null;

     private String jurisdictionCd = null;

     private Long programJurisdictionOid = null;

     private String sharedInd = null;

    private boolean itDirty = false;

    private boolean itNew = true;

    private boolean itDelete = false;


    public Long getObservationUid()
    {
        return observationUid;
    }
    public void setObservationUid(Long aObservationUid)
    {
        observationUid = aObservationUid;
        setItDirty(true);
    }

    public String getInterpretationCd()
    {
        return interpretationCd;
    }
    public void setInterpretationCd(String aInterpretationCd)
    {
        interpretationCd = aInterpretationCd;
        setItDirty(true);
    }

    public Integer getVersionCtrlNbr()
    {
        return versionCtrlNbr;
    }
    public void setVersionCtrlNbr(Integer aVersionCtrlNbr)
    {
        versionCtrlNbr = aVersionCtrlNbr;
        setItDirty(true);
    }

    public String getInterpretationTxt()
    {
        return interpretationTxt;
    }
    public void setInterpretationTxt(String aInterpretationTxt)
    {
        interpretationTxt = aInterpretationTxt;
        setItDirty(true);
    }

       public String getProgAreaCd()
   {
      return progAreaCd;
   }
      public void setProgAreaCd(String aProgAreaCd)
   {
      progAreaCd = aProgAreaCd;
      setItDirty(true);
   }

       public String getJurisdictionCd ()
   {
      return jurisdictionCd ;
   }
      public void setJurisdictionCd (String aJurisdictionCd )
   {
      jurisdictionCd = aJurisdictionCd;
      setItDirty(true);
   }

       public Long getProgramJurisdictionOid ()
   {
      return programJurisdictionOid;
   }
      public void setProgramJurisdictionOid (Long aProgramJurisdictionOid )
   {
      programJurisdictionOid = aProgramJurisdictionOid;
      setItDirty(true);
   }

       public String getSharedInd()
   {
      return sharedInd;
   }
      public void setSharedInd(String aSharedInd)
   {
      sharedInd = aSharedInd;
      setItDirty(true);
   }


    public boolean isEqual(java.lang.Object objectname1, java.lang.Object objectname2, Class<?> voClass) {
    voClass =  (( ObservationInterpHistDT) objectname1).getClass();
    NedssUtils compareObjs = new NedssUtils();
    return (compareObjs.equals(objectname1,objectname2,voClass));
     }


   public void setItDirty(boolean itDirty) {
      this.itDirty = itDirty;
   }


   public boolean isItDirty() {
     return itDirty;
   }


   public void setItNew(boolean itNew) {
     this.itNew = itNew;
   }


   public boolean isItNew() {
     return itNew;
   }
   public boolean isItDelete() {
     return itDelete;
   }


    public void setItDelete(boolean itDelete) {
      this.itDelete = itDelete;
   }


}
