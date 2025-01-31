package gov.cdc.nedss.webapp.nbs.action.place;

import gov.cdc.nedss.entity.place.vo.PlaceSearchVO;
import gov.cdc.nedss.entity.place.vo.PlaceVO;
import gov.cdc.nedss.locator.dt.EntityLocatorParticipationDT;
import gov.cdc.nedss.proxy.ejb.entityproxyejb.bean.EntityProxyHome;
import gov.cdc.nedss.locator.dt.PostalLocatorDT;
import gov.cdc.nedss.locator.dt.TeleLocatorDT;
import gov.cdc.nedss.systemservice.ejb.mainsessionejb.bean.MainSessionCommand;
import gov.cdc.nedss.systemservice.util.MainSessionHolder;
import gov.cdc.nedss.util.JNDINames;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.util.PropertyUtil;
import gov.cdc.nedss.util.StringUtils;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

public class PlaceUtil
{
    static final LogUtils logger = new LogUtils(PlaceUtil.class.getName());

    public static PlaceVO getThePlaceVO(Long placeUid, HttpSession session)
    { 
        try
        {
            MainSessionCommand msCommand = null;
            String sBeanJndiName = JNDINames.EntityControllerEJB;
            String sMethod = "getPlace";
            Object[] oParams = { placeUid };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            ArrayList<?> resultUIDArr = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            PlaceVO p = (PlaceVO) resultUIDArr.get(0);
            return p;
        }
        catch (Exception e)
        {
            logger.error("Exception in finding Place()::", e);
            e.printStackTrace();
        }
        return null;
    }

    public static Map getThePlaceParticipation(String uids, HttpSession session)
    {
        String placeStr = "";
        StringBuilder sb = new StringBuilder();
        
        try
        {
            String[] arrUids = uids.split("\\^");
            Long placeUid = null;
            Long postUid = null;
            Long telUid = null; 
            
            if( arrUids.length > 0 )
            {
                placeUid = new Long( arrUids[0] ); 
                sb.append(placeUid); 
            } 
            //address
            if (arrUids.length > 1 && !"-1".equals(arrUids[1]) && !StringUtils.isEmpty(arrUids[1]))
            {
                postUid = new Long(arrUids[1]);
            } else if (arrUids.length < 2)
            	postUid = new Long(-1L);
            //telephone
            if (arrUids.length > 2 && !"-1".equals(arrUids[2]) && !StringUtils.isEmpty(arrUids[2]))
            {
                telUid = new Long(arrUids[2]);
            } else if (arrUids.length < 3)
            	telUid = new Long(-1L);

            PlaceVO pvo = getThePlaceVO(placeUid, session);
            if (pvo != null)
            {
                String nm = pvo.getNm();
                String ad = "";
                String te = "";
                Iterator iter = pvo.getTheEntityLocatorParticipationDTCollection().iterator();
                while(iter.hasNext())
                {
                    EntityLocatorParticipationDT elp = (EntityLocatorParticipationDT)iter.next();
                    if( isEqual(elp.getLocatorUid(), postUid) && isEqual(elp.getRecordStatusCd(), NEDSSConstants.RECORD_STATUS_ACTIVE))
                    {
                        PlaceSearchVO pv = new PlaceSearchVO();
                        BeanUtils.copyProperties(pv, elp.getThePostalLocatorDT());
                        BeanUtils.copyProperties(pv, elp);
                        ad = pv.getEntitySearchAddress(); 
                    }
                    if( isEqual(elp.getLocatorUid(), telUid) && isEqual(elp.getRecordStatusCd(), NEDSSConstants.RECORD_STATUS_ACTIVE))
                    {
						if (elp.getTheTeleLocatorDT() != null
								&& elp.getTheTeleLocatorDT().getPhoneNbrTxt() != null
								&& !elp.getTheTeleLocatorDT().getPhoneNbrTxt()
										.trim().isEmpty()) {
							PlaceSearchVO pv = new PlaceSearchVO();
							BeanUtils.copyProperties(pv,
									elp.getTheTeleLocatorDT());
							BeanUtils.copyProperties(pv, elp);
							te = pv.getEntitySearchTelephone();
                    	}
                    }
                }
                sb.append("^");
                if( !StringUtils.isEmpty(ad) )
                {
                    sb.append(postUid);
                }
                sb.append("^");
                if( !StringUtils.isEmpty(te) )
                {
                    sb.append(telUid);
                }
                String[] pArr = { nm, ad, te };
                placeStr = StringUtils.combine(pArr, ", "); 
                
                String[] pArrType = {placeStr,pvo.getTypeDesc()};
                placeStr = StringUtils.combine(pArrType, "</br>"); 
            }
            else 
            {
                sb.append("^^");
            }
        }
        catch (Exception e)
        {
            logger.error("Exception occurred in finding Place()::", e);
            e.printStackTrace();
        }
        Map m = new HashMap();
        m.put("placeStr", placeStr);
        m.put("uidStr", sb.toString() );
        return m;
    }

    public static List getPlaceResults(PlaceSearchVO placeSearch, HttpSession session)
    {
        List placeList = new ArrayList();
        try
        {
            MainSessionCommand msCommand = null;
            String sBeanJndiName = JNDINames.ENTITY_PROXY_EJB;
            String sMethod = "findPlace";
            Object[] oParams = new Object[] { placeSearch, new Integer(PropertyUtil.getInstance().getNumberOfRows()),
                    new Integer(0) };
            MainSessionHolder holder = new MainSessionHolder();
            msCommand = holder.getMainSessionCommand(session);
            ArrayList<?> arrList = msCommand.processRequest(sBeanJndiName, sMethod, oParams);
            placeList = (ArrayList<?>) arrList.get(0);
        }
        catch (Exception e)
        {
            logger.error("getPlaceResults()::", e);
            e.printStackTrace();
        }
        return placeList;
    }
    
    private static boolean isEqual(Object obj1, Object obj2)
    {
        return (obj1 != null && obj2 != null && obj1.equals(obj2));
    }
    
    public static String getEntitySearchAddress(PostalLocatorDT plDT)
    {
        StringBuilder sb = new StringBuilder();
        String st = plDT.getStateCd();
        if (!StringUtils.isEmpty(st))
        {
            st = CachedDropDowns.getCodeDescTxtForCd(st, NEDSSConstants.STATE_LIST);
        }
        String[] str = {plDT.getStreetAddr1(), plDT.getStreetAddr2(), plDT.getCityDescTxt(), st };
        sb.append(StringUtils.combine(str, ", "));
        if(plDT.getZipCd()!=null)
        	sb.append(" ").append(plDT.getZipCd());
        return sb.toString();
    }
    
    public static String getEntitySearchTelephone(TeleLocatorDT tlDT)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.combine(new String[] { tlDT.getPhoneNbrTxt() }, ", "));
        if(tlDT.getExtensionTxt()!= null)
        	sb.append(" ext. "+tlDT.getExtensionTxt());
        
        return sb.toString();
    }
}
