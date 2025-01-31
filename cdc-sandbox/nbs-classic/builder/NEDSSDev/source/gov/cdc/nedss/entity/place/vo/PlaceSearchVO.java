package gov.cdc.nedss.entity.place.vo;

import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.io.Serializable;
import java.sql.Timestamp;

public class PlaceSearchVO implements Serializable
{ 
    private static final long serialVersionUID = -5111047515954628582L;
    private Long   placeUid;
    private Long   locatorUid;

    private String nm;
    private String typeCd;

    private String classCd;
    private String useCd;

    private String streetAddr1;
    private String streetAddr2;
    private String city;
    private String zip;
    private String state;

    private String phoneNbrTxt;
    private String email;
    private String extensionTxt;

    private String nmOperator;
    private String cityOperator;
    private String streetAddr1Operator;

    private String asOfDate;
    private String rootExtensionTxt;
    private String cdDescTxt;

    public String getNm()
    {
        return nm;
    }

    public void setNm(String nmTxt)
    {
        this.nm = nmTxt;
    }

    public String getStreetAddr1()
    {
        return streetAddr1;
    }

    public void setStreetAddr1(String streetAddr1)
    {
        this.streetAddr1 = streetAddr1;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String cityDescTxt)
    {
        this.city = cityDescTxt;
    }

    public void setCityCd(String aCityCd)
    {
        this.city = aCityCd;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zipCd)
    {
        this.zip = zipCd;
    }

    public void setZipCd(String zipCd)
    {
        this.zip = zipCd;
    }

    public String getTypeCd()
    {
        return typeCd;
    }

    public void setTypeCd(String typeCd)
    {
        this.typeCd = typeCd;
    }
    
    public void setCd(String cd)
    {
        this.setTypeCd(cd);
    }
    
    public String getCd()
    {
        return getTypeCd();
    }
    
    public void setCdDescTxt(String desc)
    {
        cdDescTxt = desc;
    }
    
    public String getCdDescTxt()
    {
        return cdDescTxt;
    }
    
    public String getNmOperator()
    {
        return nmOperator;
    }

    public void setNmOperator(String nmTxtOperator)
    {
        this.nmOperator = nmTxtOperator;
    }

    public String getCityOperator()
    {
        return cityOperator;
    }

    public void setCityOperator(String cityDescTxtOperator)
    {
        this.cityOperator = cityDescTxtOperator;
    }

    public String getPhoneNbrTxt()
    {
        return phoneNbrTxt;
    }

    public void setPhoneNbrTxt(String phoneNbrTxt)
    {
        this.phoneNbrTxt = phoneNbrTxt;
    }

    public String getStreetAddr1Operator()
    {
        return streetAddr1Operator;
    }

    public void setStreetAddr1Operator(String streetAddr1Operator)
    {
        this.streetAddr1Operator = streetAddr1Operator;
    }

    public String getStreetAddr2()
    {
        return streetAddr2;
    }

    public void setStreetAddr2(String streetAddr2)
    {
        this.streetAddr2 = streetAddr2;
    }

    public Long getPlaceUid()
    {
        return placeUid;
    }

    public void setPlaceUid(Long placeUid)
    {
        this.placeUid = placeUid;
    }

    public String getClassCd()
    {
        return classCd;
    }

    public void setClassCd(String classCd)
    {
        this.classCd = classCd;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setStateCd(String state)
    {
        this.state = state;
    }

    public String getUseCd()
    {
        return useCd;
    }

    public void setUseCd(String useCd)
    {
        this.useCd = useCd;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setAsOfDate(Timestamp ts)
    {
        if (ts != null)
            asOfDate = StringUtils.formatDate(ts);
    }

    public Long getLocatorUid()
    {
        return locatorUid;
    }

    public void setLocatorUid(Long locatorUid)
    {
        this.locatorUid = locatorUid;
    }

    public String getAsOfDate()
    {
        return asOfDate;
    }

    public void setAsOfDate(String asOfDate)
    {
        this.asOfDate = asOfDate;
    }

    public String getRootExtensionTxt()
    {
        return rootExtensionTxt;
    }

    public void setRootExtensionTxt(String rootExtensionTxt)
    {
        this.rootExtensionTxt = rootExtensionTxt;
    }

    public String getTelephone()
    {
        StringBuilder sb = new StringBuilder();

        if (!StringUtils.isEmpty(getUseCd()))
        {
            sb.append("<i>");
            sb.append(CachedDropDowns.getCodeDescTxtForCd(getUseCd(), "EL_USE_TELE_PLC"));
            sb.append("</i>");
            sb.append("<br/>");
        }
        sb.append(StringUtils.combine(new String[] { getPhoneNbrTxt() }, ", "));
        sb.append("<br/>");
        return sb.toString();
    }

    public String getAddress()
    {
        StringBuilder sb = new StringBuilder();

        if (!StringUtils.isEmpty(getUseCd()))
        {
            sb.append("<i>");
            sb.append(CachedDropDowns.getCodeDescTxtForCd(getUseCd(), "EL_USE_PST_PLC"));
            sb.append("</i>");
            sb.append("<br/>");
        }
        sb.append(StringUtils.combine(new String[] { getStreetAddr1(), getStreetAddr2() }, ", "));
        sb.append("<br/>");
        String st = getState();
        if (!StringUtils.isEmpty(st))
        {
            st = CachedDropDowns.getCodeDescTxtForCd(st, NEDSSConstants.STATE_LIST);
        }
        String[] str = { getCity(), st, getZip() };
        sb.append(StringUtils.combine(str, ", "));
        sb.append("<br/>");
        return sb.toString();
    }
    
    public String getEntitySearchTelephone()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.combine(new String[] { getPhoneNbrTxt() }, ", "));
        if(getExtensionTxt()!= null)
        	sb.append(" ext. "+getExtensionTxt());
        
        return sb.toString();
    }
    
    public String getEntitySearchAddress()
    {
        StringBuilder sb = new StringBuilder();
        String st = getState();
        if (!StringUtils.isEmpty(st))
        {
            st = CachedDropDowns.getCodeDescTxtForCd(st, NEDSSConstants.STATE_LIST);
        }
        String[] str = {getStreetAddr1(), getStreetAddr2(), getCity(), st };
        sb.append(StringUtils.combine(str, ", "));
        if(getZip()!=null)
        	sb.append(" ").append(getZip());
        return sb.toString();
    }

	public String getExtensionTxt() {
		return extensionTxt;
	}

	public void setExtensionTxt(String extensionTxt) {
		this.extensionTxt = extensionTxt;
	}
}
