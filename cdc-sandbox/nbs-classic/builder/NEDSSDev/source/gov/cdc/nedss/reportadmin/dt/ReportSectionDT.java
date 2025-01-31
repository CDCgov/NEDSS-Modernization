package gov.cdc.nedss.reportadmin.dt;

import gov.cdc.nedss.systemservice.util.RootDTInterface;
import gov.cdc.nedss.util.AbstractVO;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Represents the Report_Section table.
 * @author Karthik Chinnayan
 */
public class ReportSectionDT extends AbstractVO implements RootDTInterface
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4138814710202650434L;

	/**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(ReportSectionDT.class);

    private Long report_section_uid;
    private Long section_cd;
    private String section_desc_txt;
    private String comments;
    private Long add_user_uid;
    private Timestamp add_time;
    private Long last_chg_user_id;
    private Timestamp last_chg_time;
    private String status_cd;
    private String duplicateError;

    public String getDuplicateError() {
		return duplicateError;
	}

	public void setDuplicateError(String duplicateError) {
		this.duplicateError = duplicateError;
	}

	public long getReportSectionUID(){
        return (report_section_uid == null) ? 0L : report_section_uid.longValue();
    }

    public String getReportUID_s() {
        return (report_section_uid == null) ? "" : report_section_uid.toString();
    }

    public void setReportSectionUID(long l) {
        report_section_uid = new Long(l);
    }

    public void setReportSectionUID(String s) {
        if(s == null) {
            report_section_uid = null;
            return;
        }
        try {
            report_section_uid = Long.valueOf(s);
        }
        catch(Exception ex) {
            logger.error(ex, ex);
        }
    }
    
    public long getSectionCd() {
    	return (section_cd == null) ? 0L : section_cd.longValue();
    }
    
    public String getSectionCd_s() {
    	return (section_cd == null) ? "" : section_cd.toString();
    }
    
    public void setSectionCd(String s) {
    	 if(s == null) {
    		 section_cd = null;
             return;
         }
         try {
        	 section_cd = Long.valueOf(s);
         }
         catch(Exception ex) {
             logger.error(ex, ex);
         }
    }
    public void setSectionCd(long s) {
    	section_cd = new Long(s);
    }

    public String getSectionDescTxt() {
        return (section_desc_txt);
    }

    public void setSectionDescTxt(String s) {
        section_desc_txt = s;
    }

    public String getComments() {
        return (comments);
    }

    public void setComments(String s) {
        comments = s;
    }

	public long getAddUserUID() {
    	return (add_user_uid == null) ? 0L : add_user_uid.longValue();
    }

    public void setAddUserUID(long l) {
        add_user_uid = new Long(l);
    }

	public Timestamp getAddTime() {
		return add_time;
	}

	public void setAddTime(Timestamp add_time) {
		this.add_time = add_time;
	}
    
    public long getLastChgUserUID() {
    	return (last_chg_user_id == null) ? 0L : last_chg_user_id.longValue();
    }

    public void setLastChgUserUID(long l) {
    	last_chg_user_id = new Long(l);
    }

	public Timestamp getLastChgTime() {
		return last_chg_time;
	}

	public void setLastChgTime(Timestamp last_chg_time) {
		this.last_chg_time = last_chg_time;
	}
	
	public void setStatusCd(String s) {
        status_cd = s;
    }

    public String getStatusCd() {
        return (status_cd);
    }

    /**
     * Validates the data.
     * @throws IllegalStateException if required data is missing or incorrect.
     */
    public void validate() throws IllegalStateException {
    	 if(this.getSectionDescTxt().equalsIgnoreCase(""))
         {
             throw new IllegalStateException("Please enter all the required fields.");
         }
         if(this.getComments().equalsIgnoreCase(""))
         {
             throw new IllegalStateException("Please enter all the required fields.");
         }
    	 if(this.getDuplicateError() != null && this.getDuplicateError().equalsIgnoreCase("Duplicate"))
         {
             throw new IllegalStateException("Please enter unique section Name.");
         }
    	 last_chg_time = null;
    	 status_cd = null;
    }

	@Override
	public Long getLastChgUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgUserId(Long aLastChgUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJurisdictionCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJurisdictionCd(String aJurisdictionCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProgAreaCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgAreaCd(String aProgAreaCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLocalId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLocalId(String aLocalId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getAddUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddUserId(Long aAddUserId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLastChgReasonCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLastChgReasonCd(String aLastChgReasonCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRecordStatusCd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusCd(String aRecordStatusCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getRecordStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRecordStatusTime(Timestamp aRecordStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Timestamp getStatusTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatusTime(Timestamp aStatusTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSuperclass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getProgramJurisdictionOid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProgramJurisdictionOid(Long aProgramJurisdictionOid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSharedInd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSharedInd(String aSharedInd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getVersionCtrlNbr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
}
