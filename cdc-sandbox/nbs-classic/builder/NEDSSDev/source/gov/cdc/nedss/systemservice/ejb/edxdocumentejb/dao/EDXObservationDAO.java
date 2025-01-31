package gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dao;

import java.sql.Timestamp;

import gov.cdc.nedss.act.observation.dt.ObservationDT;
import gov.cdc.nedss.act.observation.ejb.dao.ObservationDAOImpl;
import gov.cdc.nedss.act.observation.vo.ObservationVO;
import gov.cdc.nedss.exception.NEDSSAppException;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.dt.EdxLabInformationDT;
import gov.cdc.nedss.systemservice.ejb.edxdocumentejb.labutil.EdxELRConstants;
import gov.cdc.nedss.util.LogUtils;
/**
 * 
 * @author Pradeep Kumar Sharma
 *This is a utility class to match the information for checking existing observation into the system.
 */
public class EDXObservationDAO {
	static final LogUtils logger = new LogUtils(EDXObservationDAO.class.getName());
	
	public static ObservationDT matchingObservationInODS(EdxLabInformationDT edxLabInformationDT) throws NEDSSAppException {
		
		String fillerNumber ="";
		try {
			ObservationVO observationVO = edxLabInformationDT.getRootObservationVO();
			fillerNumber = edxLabInformationDT.getFillerNumber();
			
			ObservationDAOImpl odsObsDAO = new ObservationDAOImpl();
			ObservationDT obsDT = null;

			if (edxLabInformationDT.getRootObservationVO()!= null) {
				obsDT = odsObsDAO.matchingObservation(edxLabInformationDT);
			} else {
				logger.error("Error!! masterObsVO not available for fillerNbr:" + edxLabInformationDT.getFillerNumber());
				return null;
			}

			if (obsDT != null) // find a match is it a correction?
			{
				String msgStatus = observationVO.getTheObservationDT().getStatusCd();
				String odsStatus = obsDT.getStatusCd();
				if (msgStatus == null || odsStatus == null) {
					logger.error("Error!! null status cd: msgInObs status=" + msgStatus + " odsObs status=" + odsStatus);
					return null;
				}
				if ((odsStatus.equals(EdxELRConstants.ELR_OBS_STATUS_CD_NEW) && (msgStatus
						.equals(EdxELRConstants.ELR_OBS_STATUS_CD_NEW) || msgStatus
						.equals(EdxELRConstants.ELR_OBS_STATUS_CD_COMPLETED) || msgStatus
						.equals(EdxELRConstants.ELR_OBS_STATUS_CD_SUPERCEDED)
						))
						|| (odsStatus.equals(EdxELRConstants.ELR_OBS_STATUS_CD_COMPLETED) && (msgStatus
						.equals(EdxELRConstants.ELR_OBS_STATUS_CD_COMPLETED) || msgStatus
						.equals(EdxELRConstants.ELR_OBS_STATUS_CD_SUPERCEDED)))
						|| (odsStatus
								.equals(EdxELRConstants.ELR_OBS_STATUS_CD_SUPERCEDED) && msgStatus
								.equals(EdxELRConstants.ELR_OBS_STATUS_CD_SUPERCEDED))
						) {

					// OK, we have a Correction
					
					if(obsDT.getActivityToTime()!=null && obsDT.getActivityToTime().after(edxLabInformationDT.getRootObservationVO().getTheObservationDT().getActivityToTime())){
						edxLabInformationDT.setActivityTimeOutOfSequence(true);
						edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_14);
						edxLabInformationDT.setLocalId(obsDT.getLocalId());
						throw new NEDSSAppException("An Observation Lab test match was found for Accession # "+fillerNumber+ ", but the activity time is out of sequence.");
					}
					return obsDT;
				} else if (odsStatus
						.equals(EdxELRConstants.ELR_OBS_STATUS_CD_SUPERCEDED)
						&& msgStatus
								.equals(EdxELRConstants.ELR_OBS_STATUS_CD_COMPLETED)) {
					edxLabInformationDT.setFinalPostCorrected(true);
					edxLabInformationDT.setLocalId(obsDT.getLocalId());
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_14);
					throw new NEDSSAppException("Lab report "+obsDT.getLocalId()+" was not updated. Final report with Accession # "+fillerNumber+ " was sent after a corrected report was received.");
				}else if (odsStatus
						.equals(EdxELRConstants.ELR_OBS_STATUS_CD_COMPLETED)
						&& msgStatus
								.equals(EdxELRConstants.ELR_OBS_STATUS_CD_NEW)) {
					edxLabInformationDT.setPreliminaryPostFinal(true);
					edxLabInformationDT.setLocalId(obsDT.getLocalId());
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_14);
					throw new NEDSSAppException("Lab report "+obsDT.getLocalId()+" was not updated. Preliminary report with Accession # "+fillerNumber+ " was sent after a final report was received.");
				}else if (odsStatus
						.equals(EdxELRConstants.ELR_OBS_STATUS_CD_SUPERCEDED)
						&& msgStatus
								.equals(EdxELRConstants.ELR_OBS_STATUS_CD_NEW)) {
					edxLabInformationDT.setPreliminaryPostCorrected(true);
					edxLabInformationDT.setLocalId(obsDT.getLocalId());
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_14);
					throw new NEDSSAppException("Lab report "+obsDT.getLocalId()+" was not updated. Preliminary report with Accession # "+fillerNumber+ " was sent after a corrected report was received.");
				}else {
					edxLabInformationDT.setFinalPostCorrected(true);
					edxLabInformationDT.setLocalId(obsDT.getLocalId());
					logger.error(" Error!! Invalid status combination: msgInObs status="
							+ msgStatus + " odsObs status=" + odsStatus);
					edxLabInformationDT.setErrorText(EdxELRConstants.ELR_MASTER_LOG_ID_14);
					throw new NEDSSAppException("Lab report "+obsDT.getLocalId()+" was not updated. Final report with Accession # "+fillerNumber+ " was sent after a corrected report was received.");
				}
			}
		} catch (Exception e) {
			logger.fatal("Error ocurred in matchingObservation for fillerNbr="+ fillerNumber,e);
			throw new NEDSSAppException("Lab report was not updated. Exception thrown ",e);
		}

		return null;
	}

	

}
