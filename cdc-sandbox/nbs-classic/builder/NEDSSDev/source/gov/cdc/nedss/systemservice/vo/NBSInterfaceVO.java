package gov.cdc.nedss.systemservice.vo;

import java.util.Collection;
import java.util.Map;

import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.act.publichealthcase.vo.PublicHealthCaseVO;
import gov.cdc.nedss.pam.act.NbsCaseAnswerDT;
import gov.cdc.nedss.systemservice.dt.NbsInterfaceDT;
import gov.cdc.nedss.systemservice.ejb.nbsdocumentejb.vo.NBSDocumentVO;
import gov.cdc.nedss.util.AbstractVO;
/**
 * 
 * @author pradeep Sharma
 *@since Release 4.1
 */
public class NBSInterfaceVO  extends AbstractVO{
	private NbsInterfaceDT nbsInterfaceDT;
	private NBSDocumentVO nbsDocumentVO;
	private PublicHealthCaseVO publicHealthCaseVO;
	private Map<Object, Object> nbsCaseAnswerDTMap;
	private Collection<ObservationVO> observationDTColl;
	
	public Map<Object, Object> getNbsCaseAnswerDTMap() {
		return nbsCaseAnswerDTMap;
	}
	public void setNbsCaseAnswerDTMap(Map<Object, Object> nbsCaseAnswerDTMap) {
		this.nbsCaseAnswerDTMap = nbsCaseAnswerDTMap;
	}

	
	
	public Collection<ObservationVO> getObservationDTColl() {
		return observationDTColl;
	}
	public void setObservationDTColl(Collection<ObservationVO> observationDTColl) {
		this.observationDTColl = observationDTColl;
	}
	public PublicHealthCaseVO getPublicHealthCaseVO() {
		return publicHealthCaseVO;
	}
	public void setPublicHealthCaseVO(PublicHealthCaseVO publicHealthCaseVO) {
		this.publicHealthCaseVO = publicHealthCaseVO;
	}
	public NbsInterfaceDT getNbsInterfaceDT() {
		return nbsInterfaceDT;
	}
	public void setNbsInterfaceDT(NbsInterfaceDT nbsInterfaceDT) {
		this.nbsInterfaceDT = nbsInterfaceDT;
	}
	public NBSDocumentVO getNbsDocumentVO() {
		return nbsDocumentVO;
	}
	public void setNbsDocumentVO(NBSDocumentVO nbsDocumentVO) {
		this.nbsDocumentVO = nbsDocumentVO;
	}
	@Override
	public boolean isEqual(Object objectname1, Object objectname2,
			Class<?> voClass) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItDelete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItDirty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isItNew() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setItDelete(boolean itDelete) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setItDirty(boolean itDirty) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setItNew(boolean itNew) {
		// TODO Auto-generated method stub
		
	}

}
