package gov.cdc.nedss.pam.vo;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

public class PamVO implements Serializable, Cloneable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Map<Object, Object> pamAnswerDTMap;
	private Collection<Object>  actEntityDTCollection;
	private Map<Object, Object>  pageRepeatingAnswerDTMap;
	private Map<Object, Object>  answerDTMap;


	public Map<Object, Object> getAnswerDTMap() {
		return answerDTMap;
	}


	public void setAnswerDTMap(Map<Object, Object> answerDTMap) {
		this.answerDTMap = answerDTMap;
	}


	public Map<Object, Object> getPageRepeatingAnswerDTMap() {
		return pageRepeatingAnswerDTMap;
	}


	public void setPageRepeatingAnswerDTMap(Map<Object, Object> pageRepeatingAnswerDTMap) {
		this.pageRepeatingAnswerDTMap = pageRepeatingAnswerDTMap;
	}


	public Map<Object, Object> getPamAnswerDTMap() {
		return pamAnswerDTMap == null ? new HashMap<Object, Object>() : pamAnswerDTMap;
	}


	public void setPamAnswerDTMap(Map<Object, Object> pamAnswerDTMap) {
		this.pamAnswerDTMap = pamAnswerDTMap;
	}

	public Collection<Object>  getActEntityDTCollection() {
		return actEntityDTCollection  == null ? new ArrayList<Object> () : actEntityDTCollection;
	}

	public void setActEntityDTCollection(Collection<Object> actEntityDTCollection) {
		this.actEntityDTCollection  = actEntityDTCollection;
	}
}
