package gov.cdc.nedss.webapp.nbs.form.message;

import gov.cdc.nedss.proxy.ejb.queue.vo.MessageLogVO;
import gov.cdc.nedss.systemservice.util.DropDownCodeDT;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;
import gov.cdc.nedss.webapp.nbs.form.util.CommonForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageLogForm extends BaseForm
{
    Map searchCriteriaArrayMap = new HashMap();
    Map searchMap              = new HashMap();
    
    private ArrayList dateFilterList = new ArrayList (); 
    private ArrayList statusList = new ArrayList();
    private ArrayList conditionList = new ArrayList();
    
    private MessageLogVO messageLogVO = new MessageLogVO();
    

    public MessageLogVO getMessageLogVO()
    {
        return messageLogVO;
    }

    public void setMessageLogVO(MessageLogVO messageLogVO)
    {
        this.messageLogVO = messageLogVO;
    }

    public void initializeDropDowns()
    {
        QueueUtil queueUtil = new QueueUtil();
        dateFilterList  = queueUtil.getStartDateDropDownValues();
        DropDownCodeDT cdDT = new DropDownCodeDT();
        statusList.clear();
        cdDT.setKey("N");
        cdDT.setValue("New");
        statusList.add(cdDT);
        cdDT=new DropDownCodeDT();
        cdDT.setKey("R");
        cdDT.setValue("Read");
        statusList.add(cdDT); 
    }
    
    public Map getSearchCriteriaArrayMap()
    {
        return searchCriteriaArrayMap;
    }

    public void setSearchCriteriaArrayMap(Map searchCriteriaArrayMap)
    {
        this.searchCriteriaArrayMap = searchCriteriaArrayMap;
    }

    public Map getSearchMap()
    {
        return searchMap;
    }

    public void setSearchMap(Map searchMap)
    {
        this.searchMap = searchMap;
    }

    public ArrayList getDateFilterList()
    {
        return dateFilterList;
    }

    public void setDateFilterList(ArrayList dateFilterList)
    {
        this.dateFilterList = dateFilterList;
    }

    /**
     * @param key
     * @param answer
     */
    public void setAnswerArray(String key, String[] answer)
    {
        if (answer.length > 0)
        {
            String[] answerList = new String[answer.length];
            boolean selected = false;
            for (int i = 1; i <= answer.length; i++)
            {
                String answerTxt = answer[i - 1];
                if (!answerTxt.equals(""))
                {
                    selected = true;
                    answerList[i - 1] = answerTxt;
                }
            }
            if (selected)
                searchCriteriaArrayMap.put(key, answerList);
        }
    }

    public String[] getAnswerArray(String key)
    {
        return (String[]) searchCriteriaArrayMap.get(key);
    }

    public ArrayList getStatusList()
    {
        return statusList;
    }

    public void setStatusList(ArrayList messageStatusList)
    {
        this.statusList = messageStatusList;
    }

    public ArrayList getConditionList()
    {
        return conditionList;
    }

    public void setConditionList(ArrayList conditionList)
    {
        this.conditionList = conditionList;
    }

    public void clearAll()
    {
        getAttributeMap().clear();
        searchCriteriaArrayMap = new HashMap<Object,Object>(); 
    }
}
