package gov.cdc.nedss.pagemanagement.wa.dto;

/**
 * PageElementDTO is used to transfer the relevant values for display purposes on the 
 * page builder UI. Properties here can be matched with a similar property 
 * in the PageElementVO object. Note: property name may not be the same. The 
 * "translator" responsible for creating an object of this class from a PageElementVO 
 * object will handle this name matching as well. 
 */
public class PageElementDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * Will be used to identify the element uniquely during
	 * a "page builder" session. i.e., Given a collection of 
	 * page elements in the current user session, this ID can be 
	 * used to uniquely identify a single element.     
	 */
	private Long pageElementUid;
	
	/**
	 * Equivalent to a name. 
	 * for example:
	 *  "Patient" for a tab element 
	 * 	"Demographics" for a Section element
	 *  "Billing Address" for a Subsection element
	 *  "Date of Birth" for a question element
	 */
	private String elementLabel; 
	
	/**
	 * Short description that can be used to describe the element. 
	 * This can be used as a tool tip when user hovers over the 
	 * element/element label.
	 */
	private String elementDescription;
	
	/**
	 * Type of the element. For example, "tab", "section", "subsection", 
	 * "question", "static element" etc...
	 */
	private String elementType;
	
	/**
	 * Applicable only for a question element to
	 * display the unique ID for a question.
	 */
	private String questionIdentifier;
	
	/**
	 * Set to 'Y' if the page element is standardized or 'N' otherwise.
	 * A Standardized page element has special business requirements
	 * attached to it. For example, a standardized
     * page builder question like first_name cannot be removed
     * from a page.
	 */
	private String isStandardized;
	
	/**
	 * Similar to the isStandardized property. 
	 * Set to 'Y' if the page element is published or 'N' otherwise.
	 * A Published page element has special business requirements
	 * attached to it. 
	 */
	private String isPublished;
	
	//TODO: for Demo of Batch Entry, remove it during development
	/**
	 * Will be used to identify the element uniquely during
	 * batch entry.    
	 */
	private Integer questionGroupSeqNbr;
	
	/**
	 * Will be used to identify the data location during
	 * batch entry.    
	 */
	private String dataLocation;
	/**
	 * Will be used to identify rolling notes
	 * during batch entry.    
	 */	
	private String elementComponentType;
	
	
	private String isCoInfection;
	
	/**
	 * Applicable only to repeating subsections
	 */
	private String blockName;
	private Integer dataMartRepeatNumber;
	private String dataMartColumnNm;
	
	
    public String getElementComponentType() {
		return elementComponentType;
	}

	public void setElementComponentType(String elementComponentType) {
		this.elementComponentType = elementComponentType;
	}
	
    public String getDataLocation() {
		return dataLocation;
	}

	public void setDataLocation(String dataLocation) {
		this.dataLocation = dataLocation;
	}

	public Integer getQuestionGroupSeqNbr() {
		return questionGroupSeqNbr;
	}

	public void setQuestionGroupSeqNbr(Integer questionGroupSeqNbr) {
		this.questionGroupSeqNbr = questionGroupSeqNbr;
	}

    public Long getPageElementUid() {
        return pageElementUid;
    }

    public void setPageElementUid(Long pageElementUid) {
        this.pageElementUid = pageElementUid;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getElementLabel() {
		return elementLabel;
	}

	public void setElementLabel(String elementLabel) {
		this.elementLabel = elementLabel;
	}

	public String getElementDescription() {
		return elementDescription;
	}

	public void setElementDescription(String elementDescription) {
		this.elementDescription = elementDescription;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

    public String getQuestionIdentifier() {
        return questionIdentifier;
    }

    public void setQuestionIdentifier(String questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
    }

    public String getIsStandardized() {
        return isStandardized;
    }

    public void setIsStandardized(String isStandardized) {
        this.isStandardized = isStandardized;
    }

    public String getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(String isPublished) {
        this.isPublished = isPublished;
    }

	public String getIsCoInfection() {
		return isCoInfection;
	}

	public void setIsCoInfection(String isCoInfection) {
		this.isCoInfection = isCoInfection;
	}

	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public Integer getDataMartRepeatNumber() {
		return dataMartRepeatNumber;
	}

	public void setDataMartRepeatNumber(Integer dataMartRepeatNumber) {
		this.dataMartRepeatNumber = dataMartRepeatNumber;
	}

	public String getDataMartColumnNm() {
		return dataMartColumnNm;
	}

	public void setDataMartColumnNm(String dataMartColumnNm) {
		this.dataMartColumnNm = dataMartColumnNm;
	}
    
}