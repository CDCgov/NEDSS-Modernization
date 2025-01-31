package gov.cdc.nedss.webapp.nbs.form.place;

import gov.cdc.nedss.entity.entityid.dt.EntityIdDT;
import gov.cdc.nedss.entity.place.vo.PlaceSearchVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.NedssUtils;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.beans.PropertyDescriptor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PlaceForm extends BaseForm
{
    public static final String[] ENT_PROPS        = { "cd", "useCd", "locatorUid", "entityUid", "asOfDate", "locatorDescTxt", "cdDescTxt" };
    public static final String[] POSTAL_PROPS     = { "postalLocatorUid", "streetAddr1", "streetAddr2", "cityCd", "cityDescTxt",
            "stateCd", "cntyCd", "zipCd" , "censusTract", "cntryCd", "cntyDescTxt"};
    public static final String[] TEL_PROPS        = { "teleLocatorUid", "phoneNbrTxt", "emailAddress", "extensionTxt", "cntryCd", "urlAddress" };

    private static final long    serialVersionUID = 6609834655518984845L;

    private ArrayList<Object>    stateList;

    private PlaceVO              place;
    private PlaceSearchVO        placeSearch      = new PlaceSearchVO();
    private EntityIdDT           quick            = new EntityIdDT();

    // Searchable fields As these can be multiple.
    private String               state;
    private String               streetName;
    private String               city;
    private String               zip;
    private String               county;
    private String               country;
    private String               phone;

    private String               addrAsOf;
    private String               addrType;
    private String               addrUse;

    private String               phoneAsOf;
    private String               phoneType;
    private String               phoneUse;
    private String               phoneExt;
    private String               email;
    private String 				reasonForEdit;
    
    private String 	contextAction;

    private ArrayList            dwrCounties      = new ArrayList();

    public ArrayList getDwrCounties()
    {
        return dwrCounties;
    }

    public ArrayList<Object> getDwrCountiesForState(String state)
    {
        this.dwrCounties = CachedDropDowns.getCountyCodes(state);
        return dwrCounties;
    }

    public Object getCodedValue(String key)
    {
        return CachedDropDowns.getCodedValueForType(key);
    }

    public Object getCodedValueNoBlnk(String key)
    {
        ArrayList<?> list = (ArrayList<?>) CachedDropDowns.getCodedValueForType(key);
        if (list != null && list.size() > 0)
        {
            DropDownCodeDT dt = (DropDownCodeDT) list.get(0);
            if (dt.getKey() != null && dt.getKey().equals("") && dt.getValue() != null && dt.getValue().equals(""))
                list.remove(0);
        }
        return list;
    }

    public ArrayList<Object> getStateList()
    {
        if( stateList == null )
            this.stateList = CachedDropDowns.getStateList();
        return stateList;
    }

    public PlaceVO getPlace()
    {
        if (place == null)
            place = new PlaceVO();
        return place;
    }

    public void setPlace(PlaceVO place)
    {
        this.place = place;
    }

    public EntityIdDT getQuick()
    {
        return quick;
    }

    public void setQuick(EntityIdDT ent)
    {
        this.quick = ent;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getStreetName()
    {
        return streetName;
    }

    public void setStreetName(String streetName)
    {
        this.streetName = streetName;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String ph)
    {
        this.phone = ph;
    }

    public String getAddrAsOf()
    {
        return addrAsOf;
    }

    public void setAddrAsOf(String addrAsOf)
    {
        this.addrAsOf = addrAsOf;
    }

    public String getAddrType()
    {
        return addrType;
    }

    public void setAddrType(String addrType)
    {
        this.addrType = addrType;
    }

    public String getAddrUse()
    {
        return addrUse;
    }

    public void setAddrUse(String addrUse)
    {
        this.addrUse = addrUse;
    }

    public EntityLocatorParticipationDT getELP(int index)
    {
        return getPlace().getEntityLocatorParticipationDT_s(index);
    }

    public EntityIdDT getEntity(int index)
    {
        return getPlace().getEntityIdDT_s(index);
    }

    public String getPhoneAsOf()
    {
        return phoneAsOf;
    }

    public void setPhoneAsOf(String phoneAsOf)
    {
        this.phoneAsOf = phoneAsOf;
    }

    public String getPhoneType()
    {
        return phoneType;
    }

    public void setPhoneType(String phoneType)
    {
        this.phoneType = phoneType;
    }

    public String getPhoneUse()
    {
        return phoneUse;
    }

    public void setPhoneUse(String phoneUse)
    {
        this.phoneUse = phoneUse;
    }

    public String getPhoneExt()
    {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt)
    {
        this.phoneExt = phoneExt;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getCounty()
    {
        return county;
    }

    public void setCounty(String county)
    {
        this.county = county;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public ArrayList<Object> getCountryList()
    {
        return CachedDropDowns.getCountryList();
    }

    public String getJson(EntityLocatorParticipationDT dt, String classCd)
    {
        JsonObject jo = getJsonObject(dt, classCd);
        return (jo != null ? jo.toString() : "");
    }

    public JsonObject getJsonObject(EntityLocatorParticipationDT dt, String classCd)
    {
        String[] nameArr = getCombineProperties(classCd);

        JsonObject jo = toJson(dt, nameArr);
        JsonElement je = jo.get("thePostalLocatorDT.cntyCd");
        String c = ( je == null || je.isJsonNull() ) ?  null : je.getAsString() ;
        if( !StringUtils.isEmpty( c ) )
        {
            je = jo.get("thePostalLocatorDT.stateCd");
            if( je != null && !je.isJsonNull() )
            {
                ArrayList  ac  = getDwrCountiesForState( je.getAsString() );
                jo.addProperty("thePostalLocatorDT.cntyDescTxt", findInDropdown(c, ac) );  
            } 
        }
        return jo;
    }

    private String[] getCombineProperties(String classCd)
    {
        String[] nameArr = null;
        if (NEDSSConstants.POSTAL.equalsIgnoreCase(classCd))
        {
            nameArr = Arrays.copyOf(ENT_PROPS, ENT_PROPS.length + POSTAL_PROPS.length);
            int i = ENT_PROPS.length;
            for (String s : POSTAL_PROPS)
            {
                nameArr[i++] = "thePostalLocatorDT." + s;
            }
        }
        else if (NEDSSConstants.TELE.equalsIgnoreCase(classCd))
        {
            nameArr = Arrays.copyOf(ENT_PROPS, ENT_PROPS.length + TEL_PROPS.length);
            int i = ENT_PROPS.length;
            for (String s : TEL_PROPS)
            {
                nameArr[i++] = "theTeleLocatorDT." + s;
            }
        }
        return nameArr;
    }

    public List getAddressCollection()
    {
        Collection c = getPlace().getTheEntityLocatorParticipationDTCollection();
        List a = new ArrayList();
        Iterator ci = c.iterator();
        String[] keys = getCombineProperties(NEDSSConstants.POSTAL);
        try
        {
            while (ci.hasNext())
            {
                EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) ci.next();
                if (NEDSSConstants.POSTAL.equals(elp.getClassCd()) && NEDSSConstants.STATUS_ACTIVE.equalsIgnoreCase(elp.getStatusCd()))
                {
                    Map m = new HashMap();
                    String stCd = null;
                    for (String name : keys)
                    {
                        PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(elp, name);
                        if ("java.sql.Timestamp".equalsIgnoreCase(pd.getPropertyType().getName()))
                        {
                            Timestamp t = (Timestamp) PropertyUtils.getProperty(elp, name);
                            m.put(name, StringUtils.formatDate(t));
                        }
                        else
                        {
                            m.put(name, BeanUtils.getProperty(elp, name));
                        }
                        if ("cd".equals(name))
                        {
                            m.put(name, CachedDropDowns.getCodeDescTxtForCd((String) m.get(name), "EL_TYPE_PST_PLC"));
                        }
                        else if ("useCd".equals(name))
                        {
                            m.put(name, CachedDropDowns.getCodeDescTxtForCd((String) m.get(name), "EL_USE_PST_PLC"));
                        }
                        else if (name.endsWith("stateCd"))
                        {
                            stCd = (String) m.get(name);
                            m.put(name, getStateName(stCd));  
                        }
                        else if( name.endsWith("cntryCd"))
                        {
                            m.put(name, getCountryName( (String)m.get(name) ) );
                        }
                    }
                    m.put("thePostalLocatorDT.cntyCd", getCounty(stCd, (String) m.get("thePostalLocatorDT.cntyCd"))); 
                    a.add(m);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return a;
    }

    public List getTelephoneCollection()
    {
        Collection c = getPlace().getTheEntityLocatorParticipationDTCollection();
        List a = new ArrayList();
        Iterator ci = c.iterator();
        String[] keys = getCombineProperties(NEDSSConstants.TELE);
        try
        {
            while (ci.hasNext())
            {
                EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT) ci.next();
                if (NEDSSConstants.TELE.equalsIgnoreCase(elp.getClassCd()) && NEDSSConstants.STATUS_ACTIVE.equalsIgnoreCase(elp.getStatusCd()) )
                {
                    Map m = new HashMap();
                    for (String name : keys)
                    {
                        PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(elp, name);
                        if ("java.sql.Timestamp".equalsIgnoreCase(pd.getPropertyType().getName()))
                        {
                            Timestamp t = (Timestamp) PropertyUtils.getProperty(elp, name);
                            m.put(name, StringUtils.formatDate(t));
                        }
                        else
                        {
                            m.put(name, BeanUtils.getProperty(elp, name));
                        }
                        if ("cd".equals(name))
                        {
                            m.put(name, CachedDropDowns.getCodeDescTxtForCd((String) m.get(name), "EL_TYPE_TELE_PLC"));
                        }
                        else if ("useCd".equals(name))
                        {
                            m.put(name, CachedDropDowns.getCodeDescTxtForCd((String) m.get(name), "EL_USE_TELE_PLC"));
                        } 
                    }
                    a.add(m);
                }
            }
        }
        catch (Exception e)
        {

        }
        return a;
    }

    public void setPlaceSearch(PlaceSearchVO vo)
    {
        placeSearch = vo;
    }

    public PlaceSearchVO getPlaceSearch()
    {
        return placeSearch;
    }

    public void reset()
    {
    	reasonForEdit="overwrite";
        place = new PlaceVO();
        placeSearch = new PlaceSearchVO();
        quick = new EntityIdDT();
    }
    
    public void resetWithoutsearch()
    {
    	reasonForEdit="overwrite";
        place = new PlaceVO();
        quick = new EntityIdDT();
    }
    private String getStateName(String st)
    {
        getStateList();
        String name = "";
        if( !StringUtils.isEmpty(st))
        {
            return findInDropdown(st, stateList);

        }
        return name; 
    } 
    
    public EntityLocatorParticipationDT getLatestAddress()
    {
        List a = getLatest(NEDSSConstants.POSTAL);
        return (EntityLocatorParticipationDT)a.get(0);
    }
    
    public EntityLocatorParticipationDT getLatestPhone()
    {
        List a = getLatest(NEDSSConstants.TELE);
        return (EntityLocatorParticipationDT)a.get(0);
    }
    
    public String getReasonForEdit() {
		return reasonForEdit;
	}

	public void setReasonForEdit(String reasonForEdit) {
		this.reasonForEdit = reasonForEdit;
	}

	private List getLatest(String classCd)
    {
        Collection c = getPlace().getTheEntityLocatorParticipationDTCollection();
        List a = new ArrayList();
        Iterator cIter = c.iterator();
        while(cIter.hasNext())
        {
           EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)cIter.next();
           if( classCd.equalsIgnoreCase(elp.getClassCd()))
           {
               a.add( elp );
           }
        }
        NedssUtils u = new NedssUtils();
        u.sortObjectByColumn("getAsOfDate", a, false);
        return a;
    }
    
    private String getCounty(String stCd, String ctCd )
    {
        ArrayList  ac  = getDwrCountiesForState( stCd );
        return findInDropdown(ctCd, ac);
    }
    
    private String getCountryName(String cd)
    {
        ArrayList a = getCountryList();
        return findInDropdown(cd, a);
    }
    
    public String getContextAction() {
		return contextAction;
	}

	public void setContextAction(String contextAction) {
		this.contextAction = contextAction;
	}

	private String findInDropdown(String cd, ArrayList  ac)
    { 
        for( int i = 0; i < ac.size(); i++ )
        {
            DropDownCodeDT d = (DropDownCodeDT)ac.get(i);
            if( d.getKey().equalsIgnoreCase( cd ))
            {
                return d.getValue();
            }
        }   
        return "";
    }
}
