package gov.cdc.nedss.webapp.nbs.action.util;

import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.util.HTMLEncoder;
import gov.cdc.nedss.util.LogUtils;
import gov.cdc.nedss.util.NEDSSConstants;
import gov.cdc.nedss.webapp.nbs.action.myinvestigation.util.StringComparator;
import gov.cdc.nedss.webapp.nbs.helper.CachedDropDowns;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QueueUtil
{
    static final LogUtils      logger    = new LogUtils(QueueUtil.class.getName());
    public static final String delimiter = " | ";

    public ArrayList<Object> getStartDateDropDownValues()
    {

        ArrayList<Object> filterColl = new ArrayList<Object>();
        DropDownCodeDT cdDT = new DropDownCodeDT();
        cdDT.setKey(NEDSSConstants.DATE_BLANK_KEY);
        cdDT.setValue(NEDSSConstants.BLANK_VALUE);
        filterColl.add(cdDT);
        ArrayList<Object> list = CachedDropDowns.getCodedValueOrderdByNbsUid("NBS_DATE_FILTER");
        filterColl.addAll(list);
        return filterColl;
    }

    public ArrayList<Object> getUniqueValueFromMap(Map<Object, Object> filterMap)
    {
        ArrayList<Object> filterColl = new ArrayList<Object>();
        Collection<Object> invColl = filterMap.keySet();
        if (invColl != null)
        {
            Iterator<Object> iIte = invColl.iterator();
            while (iIte.hasNext())
            {
                String keyVal = (String) iIte.next();
                DropDownCodeDT cdDT = new DropDownCodeDT();
                cdDT.setKey(keyVal);
                cdDT.setValue((String) filterMap.get(keyVal));
                filterColl.add(cdDT);
            }

        }
        try
        {
            Comparator<Object> comp = new StringComparator();
            Collections.sort(filterColl, comp);
        }
        catch (Exception e)
        {
            logger.error("Error in String Comparison:" + e.getMessage());
            e.printStackTrace();

        }
        return filterColl;
    }

    public Collection<Object> convertMaptoColl(Map<Object, Object> map)
    {
        Collection<Object> resColl = new ArrayList<Object>();
        if (map != null && map.size() > 0)
        {
            Collection<Object> keyColl = map.keySet();
            Iterator<Object> iter = keyColl.iterator();
            while (iter.hasNext())
            {
                String key = (String) iter.next();
                resColl.add(map.get(key));
            }
        }
        return resColl;
    }

    public boolean isDateinRange(Timestamp startDate, String dateRange)
    {
        // Decode dateRange
        dateRange = decodeNbsDateFilter(dateRange);

        boolean isRange = false;
        long range = Long.parseLong(dateRange);
        Date currentDate = new Date();
        Date sDate = new Date();
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            formatter.setLenient(false);
            String ssDate = formatter.format(startDate);
            sDate = formatter.parse(ssDate);
        }
        catch (ParseException ex)
        {
            return false;
        }
        long diff = currentDate.getTime() - sDate.getTime();
        long days;
        if (range == 1)
            days = (long) (diff / (1000 * 60 * 60 * 12));
        else
            days = (long) (diff / (1000 * 60 * 60 * 24));
        if (range != 30)
        {
            if (days <= range)
                isRange = true;
            else
                isRange = false;
        }
        else if (range == 30)
        {
            if (days < range)
                isRange = false;
            else
                isRange = true;
        }
        return isRange;
    }

    private String decodeNbsDateFilter(String dateRange)
    {

        if (dateRange.equals("7DAYS"))
            return "7";
        if (dateRange.equals("14DAYS"))
            return "14";
        if (dateRange.equals("30DAYS"))
            return "29";
        if (dateRange.equals("GT30DAYS"))
            return "30";
        if (dateRange.equals("TODAY"))
            return "1";
        return "";
    }

    public String getSearchCriteria(ArrayList<Object> srchList, String[] srchVal, String SrchField)
    {
        String srchCriteria = null;
        // added by jayasudha to remove the empty values from the list object.
        srchList   = removeEmptyObjectsFromList(srchList);
        // ended by jayasudha
        Map<Object, Object> invMap = convertListtoMap(srchList);
        if (srchVal != null && srchVal.length > 0)
        {
            srchCriteria = SrchField;
            for (int i = 0; i < srchVal.length; i++)
            {
                String value = (String) invMap.get(srchVal[i]);
                srchCriteria = srchCriteria + value + delimiter;
            }
        }
        if (srchList != null && srchList.size() > 0 && srchVal != null && srchVal.length > 0)
        {
           
        	if (srchList.size() == srchVal.length)  
                srchCriteria = null;
        }
        return srchCriteria;
    }
 // added by jayasudha to remove the empty values from the list object.
    private ArrayList<Object> removeEmptyObjectsFromList(ArrayList<Object> srchList) {
		
    	ArrayList<Object> list  = new ArrayList<Object>();
    	if (srchList != null && srchList.size() > 0)
        {
            Iterator<Object> iIter = srchList.iterator();
            while (iIter.hasNext())
            {
                DropDownCodeDT cdDT = (DropDownCodeDT) iIter.next();
                if(cdDT != null && !(cdDT.getValue().equalsIgnoreCase("")))
                	list.add(cdDT);

            }
        }
		return list;
	}
    // ended by jayasudha
	private Map<Object, Object> convertListtoMap(ArrayList<Object> srchList)
    {
        Map<Object, Object> srchMap = new HashMap<Object, Object>();
        if (srchList != null && srchList.size() > 0)
        {
            Iterator<Object> iIter = srchList.iterator();
            while (iIter.hasNext())
            {
                DropDownCodeDT cdDT = (DropDownCodeDT) iIter.next();
                if(cdDT!=null && !(cdDT.getValue().equalsIgnoreCase("")))
                		srchMap.put(cdDT.getKey(), cdDT.getValue());

            }
        }
        return srchMap;
    }

    public Map<Object, Object> getMapFromStringArray(String[] answers)
    {
        Map<Object, Object> map = new HashMap<Object, Object>();
        for (int i = 1; i <= answers.length; i++)
        {
            String answerTxt = answers[i - 1];
            if (answerTxt == null || (answerTxt == ""))
                continue;
            map.put(answerTxt, answerTxt);
        }
        return map;

    }
    
    public boolean contains(String value, String key)
    {
        if( value != null && key != null )
        {
            return ( value.toUpperCase().contains(key.toUpperCase()));
        }
        return false;
    }

}
