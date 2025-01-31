 package gov.cdc.nedss.reportadmin.dt;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

/**
 * Represents the USER_PROFILE table.
 * @author Ed Jenkins
 */
public class UserProfileDT implements Serializable
{

    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(UserProfileDT.class);

    /**
     * String Buffer Size.
     */
    private static final int STRING_BUFFER_SIZE = 1024;

    private Long NEDSS_ENTRY_ID;
    private String FIRST_NM;
    private Timestamp LAST_UPD_TIME;
    private String LAST_NM;
    private String FULL_NM;

   	/**
     * Constructor.
     */
    public UserProfileDT()
    {
    }

    public long getNEDSS_ENTRY_ID()
    {
        return (NEDSS_ENTRY_ID == null) ? 0L : NEDSS_ENTRY_ID.longValue();
    }

    public String getNEDSS_ENTRY_ID_s()
    {
        return (NEDSS_ENTRY_ID == null) ? "" : NEDSS_ENTRY_ID.toString();
    }

    public void setNEDSS_ENTRY_ID(long l)
    {
        NEDSS_ENTRY_ID = new Long(l);
    }

    public void setNEDSS_ENTRY_ID(String s)
    {
        if(s == null)
        {
            NEDSS_ENTRY_ID = null;
            return;
        }
        try
        {
            NEDSS_ENTRY_ID = Long.valueOf(s);
        }
        catch(Exception ex)
        {
            logger.error(ex, ex);
        }
    }

    public String getFIRST_NM()
    {
        return (FIRST_NM == null) ? "" : FIRST_NM;
    }

    public void setFIRST_NM(String s)
    {
        FIRST_NM = s;
    }

    public long getLAST_UPD_TIME()
    {
        return (LAST_UPD_TIME == null) ? 0L : LAST_UPD_TIME.getTime();
    }

    public void setLAST_UPD_TIME(long l)
    {
        LAST_UPD_TIME = new Timestamp(l);
    }

    public String getLAST_NM()
    {
        return (LAST_NM == null) ? "" : LAST_NM;
    }

    public void setLAST_NM(String s)
    {
        LAST_NM = s;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer(STRING_BUFFER_SIZE);
        if(FIRST_NM != null)
        {
            sb.append(FIRST_NM);
        }
        if( (FIRST_NM != null) || (LAST_NM != null) )
        {
            sb.append(" ");
        }
        if(LAST_NM != null)
        {
            sb.append(LAST_NM);
        }
        String s = sb.toString();
        return s;
    }
    
    public String getFULL_NM() {
    	StringBuffer buff= new StringBuffer();
    	if(getLAST_NM()!=null && getLAST_NM()!="")
    		buff.append(getLAST_NM());
    	if(getLAST_NM()!=null && getFIRST_NM()!=null && getLAST_NM()!="" && getFIRST_NM()!="" )
    		buff.append(",   ");
    	if(getFIRST_NM()!=null && getFIRST_NM()!="")
    		buff.append(getFIRST_NM());
    	FULL_NM=buff.toString();
		return FULL_NM;
	}

	public void setFULL_NM(String full_nm) {
		FULL_NM = full_nm;
	}
    /**
     * Validates the data.
     * @throws IllegalStateException if required data is missing or incorrect.
     */
    public void validate() throws IllegalStateException
    {
        if(NEDSS_ENTRY_ID == null)
        {
            throw new IllegalStateException("Please specify a NEDSS Entry ID.");
        }
    }

}
