package gov.cdc.nedss.webapp.nbs.form.supervisor;

import gov.cdc.nedss.proxy.ejb.queue.vo.SupervisorReviewVO;
import gov.cdc.nedss.webapp.nbs.action.util.QueueUtil;
import gov.cdc.nedss.webapp.nbs.form.util.BaseForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupervisorReviewForm extends BaseForm
{
    private Map                searchCriteriaArrayMap = new HashMap();
    private Map                searchMap              = new HashMap();
    private ArrayList          dateFilterList         = new ArrayList();
    private List               supervisorVos;
    private SupervisorReviewVO supervisorVO           = new SupervisorReviewVO();
    private QueueUtil          queueUtil              = new QueueUtil();

    public void initializeDropDowns()
    {
        dateFilterList = queueUtil.getStartDateDropDownValues();
    }

    public SupervisorReviewVO getSupervisorVO()
    {
        return supervisorVO;
    }

    public void setSupervisorVO(SupervisorReviewVO supervisorVO)
    {
        this.supervisorVO = supervisorVO;
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

    public ArrayList getConditionList()
    {
        Map m = new HashMap();
        for (Object o : supervisorVos)
        {
            SupervisorReviewVO s = (SupervisorReviewVO) o;
            if (s.getCondition() != null)
            {
                m.put(s.getCondition(), s.getCondition());
            }
        }
        return queueUtil.getUniqueValueFromMap(m);
    }

    public void clearAll()
    {
        getAttributeMap().clear();
        searchCriteriaArrayMap = new HashMap<Object, Object>();
    }

    public List getSupervisorVos()
    {
        return supervisorVos;
    }

    public void setSupervisorVos(List supervisorVos)
    {
        this.supervisorVos = supervisorVos;
    }

    public ArrayList getReferralBasisList()
    {
        Map m = new HashMap();
        for (Object o : supervisorVos)
        {
            SupervisorReviewVO s = (SupervisorReviewVO) o;
            if (s.getReferralBasisCd() != null)
            {
                m.put(s.getReferralBasisCd(), s.getReferralBasisCd());
            }
        }
        return queueUtil.getUniqueValueFromMap(m);
    }

    public ArrayList getTypeList()
    {
        Map m = new HashMap();
        for (Object o : supervisorVos)
        {
            SupervisorReviewVO s = (SupervisorReviewVO) o;
            if (s.getActivityType() != null)
            {
                m.put(s.getActivityType(), s.getActivityType());
            }
        }
        return queueUtil.getUniqueValueFromMap(m);
    }

    public ArrayList getInvestigatorNames()
    {
        Map m = new HashMap();
        for (Object o : supervisorVos)
        {
            SupervisorReviewVO s = (SupervisorReviewVO) o;
            if (s.getInvestigatorFullName().length() > 0)
            {
                m.put(s.getInvestigatorFullName(), s.getInvestigatorFullName());
            }
        }
        return queueUtil.getUniqueValueFromMap(m);
    }

    public ArrayList getSupervisorNames()
    {
        Map m = new HashMap();
        for (Object o : supervisorVos)
        {
            SupervisorReviewVO s = (SupervisorReviewVO) o;
            if (s.getSupervisorFullName().length() > 0)
            {
                m.put(s.getSupervisorFullName(), s.getSupervisorFullName());
            }
        }
        return queueUtil.getUniqueValueFromMap(m);
    }
}
