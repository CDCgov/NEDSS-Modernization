package gov.cdc.nedss.report.javaRepot.vo;

import gov.cdc.nedss.util.AbstractVO;
/**
 * Util class for Pa1 report
 * @author Pradeep Kumar Sharma
 *
 */

public class Pa1VO  extends AbstractVO implements java.io.Serializable{
private static final long serialVersionUID = -2043762561317525426L;

private String reportType; //STD or HIV

//Investigator or Summary
private String workerLabel;
private String worker;

//Case Assignments and Outcomes Section - Col1
private String caseAssignmentsAndOutcomesSectionName;

private String numCasesAssignedLabel;
private String numCasesAssigned;

private String casesClosedLabel;
private String casesClosedCount;
private String casesClosedPercent;

private String casesIxdLabel;
private String casesIxdCount;
private String casesIxdPercent;

private String casesIxdWI3dLabel;
private String casesIxdWI3dCount;
private String casesIxdWI3dPercent;

private String casesIxdWI5dLabel;
private String casesIxdWI5dCount;
private String casesIxdWI5dPercent;

private String casesIxdWI7dLabel;
private String casesIxdWI7dCount;
private String casesIxdWI7dPercent;

private String casesIxdWI14dLabel;
private String casesIxdWI14dCount;
private String casesIxdWI14dPercent;

private String casesReinterviewedLabel;
private String casesReinterviewedCount;
private String casesReinterviewedPercent;

//Case Assignments and Outcomes Section - Col2
private String hivPrevPositiveLabel;
private String hivPrevPositiveCount;
private String hivPrevPositivePercent;

private String hivTestedLabel;
private String hivTestedCount;
private String hivTestedPercent;

private String hivNewPositiveLabel;
private String hivNewPositiveCount;
private String hivNewPositivePercent;

private String hivPostTestCounselLabel;
private String hivPostTestCounselCount;
private String hivPostTestCounselPercent;
//STD Only..
private String diseaseInterventionIndexLabel;
private String diseaseInterventionIndex;

private String treatmentIndexLabel;
private String treatmentIndex;

private String casesWithSourceIdentifiedLabel;
private String casesWithSourceIdentifiedCount;
private String casesWithSourceIdentifiedPercent;
//Hiv Only..
private String partnerNotificationIndexLabel;
private String partnerNotificationIndex;

private String testingIndexLabel;
private String testingIndex;

//Partners and Clusters Initiated - Col1
private String partnersAndClustersInitiatedSectionName;

private String totalPeriodPartnersLabel;
private String totalPeriodPartnersCount;
private String totalPeriodPartnersPercent;

private String totalPartnersInitiatedLabel;
private String totalPartnersInitiatedCount;
private String totalPartnersInitiatedPercent;

private String partnersInitiatedFromOILabel;
private String partnersInitiatedFromOICount;

private String partnersInitiatedFromRILabel;
private String partnersInitiatedFromRICount;

private String contactIndexLabel;
private String contactIndex;

private String casesWithNoPartnersLabel;
private String casesWithNoPartnersCount;
private String casesWithNoPartnersPercent;

//Partners and Clusters Initiated - Col2
private String totalClusterInitiatedLabel;
private String totalClusterInitiatedCount;

private String clusterIndexLabel;
private String clusterIndex;

private String casesWithNoClustersLabel;
private String casesWithNoClustersCount;
private String casesWithNoClustersPercent;

// STD Dispositions - Partners and Clusters - Col1
private String dispositionsPartnersAndClustersSectionName;

private String newPartnersExaminedLabel;
private String newPartnersExaminedCount;
private String newPartnersExaminedPercent;

private String newPartnersPreventativeRxLabel;
private String newPartnersPreventativeRxCount;
private String newPartnersPreventativeRxPercent;

private String newPartnersRefusedPrevRxLabel;
private String newPartnersRefusedPrevRxCount;
private String newPartnersRefusedPrevRxPercent;

private String newPartnersInfectedRxLabel;
private String newPartnersInfectedRxCount;
private String newPartnersInfectedRxPercent;

private String newPartnersInfectedNoRxLabel;
private String newPartnersInfectedNoRxCount;
private String newPartnersInfectedNoRxPercent;

private String newPartnersNotInfectedLabel;
private String newPartnersNotInfectedCount;
private String newPartnersNotInfectedPercent;

private String newPartnersNoExamLabel;
private String newPartnersNoExamCount;
private String newPartnersNoExamPercent;

private String newPartnersInsufficientInfoLabel;
private String newPartnersInsufficientInfoCount;
private String newPartnersInsufficientInfoPercent;

private String newPartnersUnableToLocateLabel;
private String newPartnersUnableToLocateCount;
private String newPartnersUnableToLocatePercent;

private String newPartnersRefusedExamLabel;
private String newPartnersRefusedExamCount;
private String newPartnersRefusedExamPercent;

private String newPartnersOOJLabel;
private String newPartnersOOJCount;
private String newPartnersOOJPercent;

private String newPartnersOtherLabel;
private String newPartnersOtherCount;
private String newPartnersOtherPercent;

private String newPartnersPreviousRxLabel;
private String newPartnersPreviousRxCount;
private String newPartnersPreviousRxPercent;

private String newPartnersStdOpenLabel;
private String newPartnersStdOpenCount;
private String newPartnersStdOpenPercent;

//STD Dispositions - Partners and Clusters - Col2

private String newClustersExaminedLabel;
private String newClustersExaminedCount;
private String newClustersExaminedPercent;

private String newClustersPreventativeRxLabel;
private String newClustersPreventativeRxCount;
private String newClustersPreventativeRxPercent;

private String newClustersRefusedPrevRxLabel;
private String newClustersRefusedPrevRxCount;
private String newClustersRefusedPrevRxPercent;

private String newClustersInfectedRxLabel;
private String newClustersInfectedRxCount;
private String newClustersInfectedRxPercent;

private String newClustersInfectedNoRxLabel;
private String newClustersInfectedNoRxCount;
private String newClustersInfectedNoRxPercent;

private String newClustersNotInfectedLabel;
private String newClustersNotInfectedCount;
private String newClustersNotInfectedPercent;

private String newClustersNoExamLabel;
private String newClustersNoExamCount;
private String newClustersNoExamPercent;

private String newClustersInsufficientInfoLabel;
private String newClustersInsufficientInfoCount;
private String newClustersInsufficientInfoPercent;

private String newClustersUnableToLocateLabel;
private String newClustersUnableToLocateCount;
private String newClustersUnableToLocatePercent;

private String newClustersRefusedExamLabel;
private String newClustersRefusedExamCount;
private String newClustersRefusedExamPercent;

private String newClustersOOJLabel;
private String newClustersOOJCount;
private String newClustersOOJPercent;

private String newClustersOtherLabel;
private String newClustersOtherCount;
private String newClustersOtherPercent;

private String newClustersPreviousRxLabel;
private String newClustersPreviousRxCount;
private String newClustersPreviousRxPercent;

private String newClustersStdOpenLabel;
private String newClustersStdOpenCount;
private String newClustersStdOpenPercent;


//HIV Dispositions - Partners and Clusters - Col1
private String newPartnersNotifiedLabel;
private String newPartnersNotifiedCount;
private String newPartnersNotifiedPercent;

private String newPartnersPrevNegNewPosLabel;
private String newPartnersPrevNegNewPosCount;
private String newPartnersPrevNegNewPosPercent;

private String newPartnersPrevNegStillNegLabel;
private String newPartnersPrevNegStillNegCount;
private String newPartnersPrevNegStillNegPercent;

private String newPartnersPrevNegNoTestLabel;
private String newPartnersPrevNegNoTestCount;
private String newPartnersPrevNegNoTestPercent;
//no
private String newPartnersNoPrevNewPosLabel;
private String newPartnersNoPrevNewPosCount;
private String newPartnersNoPrevNewPosPercent;

private String newPartnersNoPrevNewNegLabel;
private String newPartnersNoPrevNewNegCount;
private String newPartnersNoPrevNewNegPercent;

private String newPartnersNoPrevNoTestLabel;
private String newPartnersNoPrevNoTestCount;
private String newPartnersNoPrevNoTestPercent;

private String newPartnersNotNotifiedLabel;
private String newPartnersNotNotifiedCount;
private String newPartnersNotNotifiedPercent;

private String newPartnersNotNotifiedInsufficientInfoLabel;
private String newPartnersNotNotifiedInsufficientInfoCount;
private String newPartnersNotNotifiedInsufficientInfoPercent;

private String newPartnersNotNotifiedUnableToLocateLabel;
private String newPartnersNotNotifiedUnableToLocateCount;
private String newPartnersNotNotifiedUnableToLocatePercent;

private String newPartnersNotNotifiedRefusedExamLabel;
private String newPartnersNotNotifiedRefusedExamCount;
private String newPartnersNotNotifiedRefusedExamPercent;

private String newPartnersNotNotifiedOOJLabel;
private String newPartnersNotNotifiedOOJCount;
private String newPartnersNotNotifiedOOJPercent;

private String newPartnersNotNotifiedOtherLabel;
private String newPartnersNotNotifiedOtherCount;
private String newPartnersNotNotifiedOtherPercent;

private String newPartnersPrevPosLabel;
private String newPartnersPrevPosCount;
private String newPartnersPrevPosPercent;

private String newPartnersHivOpenLabel;
private String newPartnersHivOpenCount;
private String newPartnersHivOpenPercent;
//HIV Dispositions - Partners and Clusters - Col2

private String newClustersNotifiedLabel;
private String newClustersNotifiedCount;
private String newClustersNotifiedPercent;

private String newClustersPrevNegNewPosLabel;
private String newClustersPrevNegNewPosCount;
private String newClustersPrevNegNewPosPercent;

private String newClustersPrevNegStillNegLabel;
private String newClustersPrevNegStillNegCount;
private String newClustersPrevNegStillNegPercent;

private String newClustersPrevNegNoTestLabel;
private String newClustersPrevNegNoTestCount;
private String newClustersPrevNegNoTestPercent;
//no
private String newClustersNoPrevNewPosLabel;
private String newClustersNoPrevNewPosCount;
private String newClustersNoPrevNewPosPercent;

private String newClustersNoPrevNewNegLabel;
private String newClustersNoPrevNewNegCount;
private String newClustersNoPrevNewNegPercent;

private String newClustersNoPrevNoTestLabel;
private String newClustersNoPrevNoTestCount;
private String newClustersNoPrevNoTestPercent;

private String newClustersNotNotifiedLabel;
private String newClustersNotNotifiedCount;
private String newClustersNotNotifiedPercent;

private String newClustersNotNotifiedInsufficientInfoLabel;
private String newClustersNotNotifiedInsufficientInfoCount;
private String newClustersNotNotifiedInsufficientInfoPercent;

private String newClustersNotNotifiedUnableToLocateLabel;
private String newClustersNotNotifiedUnableToLocateCount;
private String newClustersNotNotifiedUnableToLocatePercent;

private String newClustersNotNotifiedRefusedExamLabel;
private String newClustersNotNotifiedRefusedExamCount;
private String newClustersNotNotifiedRefusedExamPercent;

private String newClustersNotNotifiedOOJLabel;
private String newClustersNotNotifiedOOJCount;
private String newClustersNotNotifiedOOJPercent;

private String newClustersNotNotifiedOtherLabel;
private String newClustersNotNotifiedOtherCount;
private String newClustersNotNotifiedOtherPercent;

private String newClustersHivPrevPosLabel;
private String newClustersHivPrevPosCount;
private String newClustersHivPrevPosPercent;

private String newClustersHivOpenLabel;
private String newClustersHivOpenCount;
private String newClustersHivOpenPercent;

//STD Speed of Exam - Partners and Clusters Col1
private String speedOfExamSectionName;
private String seNewPartnersExaminedLabel;
private String seNewPartnersExaminedCount;

private String seNewPartnersExaminedWI3dLabel;
private String seNewPartnersExaminedWI3dCount;
private String seNewPartnersExaminedWI3dPercent;

private String seNewPartnersExaminedWI5dLabel;
private String seNewPartnersExaminedWI5dCount;
private String seNewPartnersExaminedWI5dPercent;

private String seNewPartnersExaminedWI7dLabel;
private String seNewPartnersExaminedWI7dCount;
private String seNewPartnersExaminedWI7dPercent;

private String seNewPartnersExaminedWI14dLabel;
private String seNewPartnersExaminedWI14dCount;
private String seNewPartnersExaminedWI14dPercent;

//STD Speed of Exam - Partners and Clusters Col2
private String seNewClustersExaminedLabel;
private String seNewClustersExaminedCount;

private String seNewClustersExaminedWI3dLabel;
private String seNewClustersExaminedWI3dCount;
private String seNewClustersExaminedWI3dPercent;

private String seNewClustersExaminedWI5dLabel;
private String seNewClustersExaminedWI5dCount;
private String seNewClustersExaminedWI5dPercent;

private String seNewClustersExaminedWI7dLabel;
private String seNewClustersExaminedWI7dCount;
private String seNewClustersExaminedWI7dPercent;

private String seNewClustersExaminedWI14dLabel;
private String seNewClustersExaminedWI14dCount;
private String seNewClustersExaminedWI14dPercent;

//HIV Speed of Exam - Partners and Clusters Col1
private String speedOfNotificationSectionName;
private String seNewPartnersNotifiedLabel;
private String seNewPartnersNotifiedCount;

private String seNewPartnersNotifiedWI3dLabel;
private String seNewPartnersNotifiedWI3dCount;
private String seNewPartnersNotifiedWI3dPercent;

private String seNewPartnersNotifiedWI5dLabel;
private String seNewPartnersNotifiedWI5dCount;
private String seNewPartnersNotifiedWI5dPercent;

private String seNewPartnersNotifiedWI7dLabel;
private String seNewPartnersNotifiedWI7dCount;
private String seNewPartnersNotifiedWI7dPercent;

private String seNewPartnersNotifiedWI14dLabel;
private String seNewPartnersNotifiedWI14dCount;
private String seNewPartnersNotifiedWI14dPercent;

//HIVSpeed of Exam - Partners and Clusters Col2
private String seNewClustersNotifiedLabel;
private String seNewClustersNotifiedCount;

private String seNewClustersNotifiedWI3dLabel;
private String seNewClustersNotifiedWI3dCount;
private String seNewClustersNotifiedWI3dPercent;

private String seNewClustersNotifiedWI5dLabel;
private String seNewClustersNotifiedWI5dCount;
private String seNewClustersNotifiedWI5dPercent;

private String seNewClustersNotifiedWI7dLabel;
private String seNewClustersNotifiedWI7dCount;
private String seNewClustersNotifiedWI7dPercent;

private String seNewClustersNotifiedWI14dLabel;
private String seNewClustersNotifiedWI14dCount;
private String seNewClustersNotifiedWI14dPercent;

public String getReportType() {
	return reportType;
}
public void setReportType(String reportType) {
	this.reportType = reportType;
}
public String getWorkerLabel() {
	return workerLabel;
}
public void setWorkerLabel(String workerLabel) {
	this.workerLabel = workerLabel;
}
public String getWorker() {
	return worker;
}
public void setWorker(String worker) {
	this.worker = worker;
}
public String getNumCasesAssignedLabel() {
	return numCasesAssignedLabel;
}
public void setNumCasesAssignedLabel(String numCasesAssignedLabel) {
	this.numCasesAssignedLabel = numCasesAssignedLabel;
}
public String getNumCasesAssigned() {
	return numCasesAssigned;
}
public void setNumCasesAssigned(String numCasesAssigned) {
	this.numCasesAssigned = numCasesAssigned;
}
public String getCasesClosedLabel() {
	return casesClosedLabel;
}
public void setCasesClosedLabel(String casesClosedLabel) {
	this.casesClosedLabel = casesClosedLabel;
}
public String getCasesClosedCount() {
	return casesClosedCount;
}
public void setCasesClosedCount(String casesClosedCount) {
	this.casesClosedCount = casesClosedCount;
}
public String getCasesClosedPercent() {
	return casesClosedPercent;
}
public void setCasesClosedPercent(String casesClosedPercent) {
	this.casesClosedPercent = casesClosedPercent;
}
public String getCasesIxdLabel() {
	return casesIxdLabel;
}
public void setCasesIxdLabel(String casesIxdLabel) {
	this.casesIxdLabel = casesIxdLabel;
}
public String getCasesIxdCount() {
	return casesIxdCount;
}
public void setCasesIxdCount(String casesIxdCount) {
	this.casesIxdCount = casesIxdCount;
}
public String getCasesIxdPercent() {
	return casesIxdPercent;
}
public void setCasesIxdPercent(String casesIxdPercent) {
	this.casesIxdPercent = casesIxdPercent;
}
public String getCasesIxdWI3dLabel() {
	return casesIxdWI3dLabel;
}
public void setCasesIxdWI3dLabel(String casesIxdWI3dLabel) {
	this.casesIxdWI3dLabel = casesIxdWI3dLabel;
}
public String getCasesIxdWI3dCount() {
	return casesIxdWI3dCount;
}
public void setCasesIxdWI3dCount(String casesIxdWI3dCount) {
	this.casesIxdWI3dCount = casesIxdWI3dCount;
}
public String getCasesIxdWI3dPercent() {
	return casesIxdWI3dPercent;
}
public void setCasesIxdWI3dPercent(String casesIxdWI3dPercent) {
	this.casesIxdWI3dPercent = casesIxdWI3dPercent;
}
public String getCasesIxdWI5dLabel() {
	return casesIxdWI5dLabel;
}
public void setCasesIxdWI5dLabel(String casesIxdWI5dLabel) {
	this.casesIxdWI5dLabel = casesIxdWI5dLabel;
}
public String getCasesIxdWI5dCount() {
	return casesIxdWI5dCount;
}
public void setCasesIxdWI5dCount(String casesIxdWI5dCount) {
	this.casesIxdWI5dCount = casesIxdWI5dCount;
}
public String getCasesIxdWI5dPercent() {
	return casesIxdWI5dPercent;
}
public void setCasesIxdWI5dPercent(String casesIxdWI5dPercent) {
	this.casesIxdWI5dPercent = casesIxdWI5dPercent;
}
public String getCasesIxdWI7dLabel() {
	return casesIxdWI7dLabel;
}
public void setCasesIxdWI7dLabel(String casesIxdWI7dLabel) {
	this.casesIxdWI7dLabel = casesIxdWI7dLabel;
}
public String getCasesIxdWI7dCount() {
	return casesIxdWI7dCount;
}
public void setCasesIxdWI7dCount(String casesIxdWI7dCount) {
	this.casesIxdWI7dCount = casesIxdWI7dCount;
}
public String getCasesIxdWI7dPercent() {
	return casesIxdWI7dPercent;
}
public void setCasesIxdWI7dPercent(String casesIxdWI7dPercent) {
	this.casesIxdWI7dPercent = casesIxdWI7dPercent;
}
public String getCasesIxdWI14dLabel() {
	return casesIxdWI14dLabel;
}
public void setCasesIxdWI14dLabel(String casesIxdWI4dLabel) {
	this.casesIxdWI14dLabel = casesIxdWI4dLabel;
}
public String getCasesIxdWI14dCount() {
	return casesIxdWI14dCount;
}
public void setCasesIxdWI14dCount(String casesIxdWI14dCount) {
	this.casesIxdWI14dCount = casesIxdWI14dCount;
}
public String getCasesIxdWI14dPercent() {
	return casesIxdWI14dPercent;
}
public void setCasesIxdWI14dPercent(String casesIxdWI14dPercent) {
	this.casesIxdWI14dPercent = casesIxdWI14dPercent;
}
public String getCasesReinterviewedLabel() {
	return casesReinterviewedLabel;
}
public void setCasesReinterviewedLabel(String casesReinterviewedLabel) {
	this.casesReinterviewedLabel = casesReinterviewedLabel;
}
public String getCasesReinterviewedCount() {
	return casesReinterviewedCount;
}
public void setCasesReinterviewedCount(String casesReinterviewedCount) {
	this.casesReinterviewedCount = casesReinterviewedCount;
}
public String getCasesReinterviewedPercent() {
	return casesReinterviewedPercent;
}
public void setCasesReinterviewedPercent(String casesReinterviewedPercent) {
	this.casesReinterviewedPercent = casesReinterviewedPercent;
}
public String getHivPrevPositiveLabel() {
	return hivPrevPositiveLabel;
}
public void setHivPrevPositiveLabel(String hivPrevPositiveLabel) {
	this.hivPrevPositiveLabel = hivPrevPositiveLabel;
}
public String getHivPrevPositiveCount() {
	return hivPrevPositiveCount;
}
public void setHivPrevPositiveCount(String hivPrevPositiveCount) {
	this.hivPrevPositiveCount = hivPrevPositiveCount;
}
public String getHivPrevPositivePercent() {
	return hivPrevPositivePercent;
}
public void setHivPrevPositivePercent(String hivPrevPositivePercent) {
	this.hivPrevPositivePercent = hivPrevPositivePercent;
}
public String getHivTestedLabel() {
	return hivTestedLabel;
}
public void setHivTestedLabel(String hivTestedLabel) {
	this.hivTestedLabel = hivTestedLabel;
}
public String getHivTestedCount() {
	return hivTestedCount;
}
public void setHivTestedCount(String hivTestedCount) {
	this.hivTestedCount = hivTestedCount;
}
public String getHivTestedPercent() {
	return hivTestedPercent;
}
public void setHivTestedPercent(String hivTestedPercent) {
	this.hivTestedPercent = hivTestedPercent;
}
public String getHivNewPositiveLabel() {
	return hivNewPositiveLabel;
}
public void setHivNewPositiveLabel(String hivNewPositiveLabel) {
	this.hivNewPositiveLabel = hivNewPositiveLabel;
}
public String getHivNewPositiveCount() {
	return hivNewPositiveCount;
}
public void setHivNewPositiveCount(String hivNewPositiveCount) {
	this.hivNewPositiveCount = hivNewPositiveCount;
}
public String getHivNewPositivePercent() {
	return hivNewPositivePercent;
}
public void setHivNewPositivePercent(String hivNewPositivePercent) {
	this.hivNewPositivePercent = hivNewPositivePercent;
}
public String getHivPostTestCounselLabel() {
	return hivPostTestCounselLabel;
}
public void setHivPostTestCounselLabel(String hivPostTestCounselLabel) {
	this.hivPostTestCounselLabel = hivPostTestCounselLabel;
}
public String getHivPostTestCounselCount() {
	return hivPostTestCounselCount;
}
public void setHivPostTestCounselCount(String hivPostTestCounselCount) {
	this.hivPostTestCounselCount = hivPostTestCounselCount;
}
public String getHivPostTestCounselPercent() {
	return hivPostTestCounselPercent;
}
public void setHivPostTestCounselPercent(String hivPostTestCounselPercent) {
	this.hivPostTestCounselPercent = hivPostTestCounselPercent;
}
public String getDiseaseInterventionIndexLabel() {
	return diseaseInterventionIndexLabel;
}
public void setDiseaseInterventionIndexLabel(
		String diseaseInterventionIndexLabel) {
	this.diseaseInterventionIndexLabel = diseaseInterventionIndexLabel;
}
public String getDiseaseInterventionIndex() {
	return diseaseInterventionIndex;
}
public void setDiseaseInterventionIndex(String diseaseInterventionIndex) {
	this.diseaseInterventionIndex = diseaseInterventionIndex;
}
public String getTreatmentIndexLabel() {
	return treatmentIndexLabel;
}
public void setTreatmentIndexLabel(String treatmentIndexLabel) {
	this.treatmentIndexLabel = treatmentIndexLabel;
}
public String getTreatmentIndex() {
	return treatmentIndex;
}
public void setTreatmentIndex(String treatmentIndex) {
	this.treatmentIndex = treatmentIndex;
}
public String getCasesWithSourceIdentifiedLabel() {
	return casesWithSourceIdentifiedLabel;
}
public void setCasesWithSourceIdentifiedLabel(
		String casesWithSourceIdentifiedLabel) {
	this.casesWithSourceIdentifiedLabel = casesWithSourceIdentifiedLabel;
}
public String getCasesWithSourceIdentifiedCount() {
	return casesWithSourceIdentifiedCount;
}
public void setCasesWithSourceIdentifiedCount(
		String casesWithSourceIdentifiedCount) {
	this.casesWithSourceIdentifiedCount = casesWithSourceIdentifiedCount;
}
public String getCasesWithSourceIdentifiedPercent() {
	return casesWithSourceIdentifiedPercent;
}
public void setCasesWithSourceIdentifiedPercent(
		String casesWithSourceIdentifiedPercent) {
	this.casesWithSourceIdentifiedPercent = casesWithSourceIdentifiedPercent;
}
public String getPartnerNotificationIndexLabel() {
	return partnerNotificationIndexLabel;
}
public void setPartnerNotificationIndexLabel(
		String partnerNotificationIndexLabel) {
	this.partnerNotificationIndexLabel = partnerNotificationIndexLabel;
}
public String getPartnerNotificationIndex() {
	return partnerNotificationIndex;
}
public void setPartnerNotificationIndex(String partnerNotificationIndex) {
	this.partnerNotificationIndex = partnerNotificationIndex;
}
public String getTestingIndexLabel() {
	return testingIndexLabel;
}
public void setTestingIndexLabel(String testingIndexLabel) {
	this.testingIndexLabel = testingIndexLabel;
}
public String getTestingIndex() {
	return testingIndex;
}
public void setTestingIndex(String testingIndex) {
	this.testingIndex = testingIndex;
}
public String getTotalPeriodPartnersLabel() {
	return totalPeriodPartnersLabel;
}
public void setTotalPeriodPartnersLabel(String totalPeriodPartnersLabel) {
	this.totalPeriodPartnersLabel = totalPeriodPartnersLabel;
}
public String getTotalPeriodPartnersCount() {
	return totalPeriodPartnersCount;
}
public void setTotalPeriodPartnersCount(String totalPeriodPartnersCount) {
	this.totalPeriodPartnersCount = totalPeriodPartnersCount;
}
public String getTotalPeriodPartnersPercent() {
	return totalPeriodPartnersPercent;
}
public void setTotalPeriodPartnersPercent(String totalPeriodPartnersPercent) {
	this.totalPeriodPartnersPercent = totalPeriodPartnersPercent;
}
public String getTotalPartnersInitiatedLabel() {
	return totalPartnersInitiatedLabel;
}
public void setTotalPartnersInitiatedLabel(String totalPartnersInitiatedLabel) {
	this.totalPartnersInitiatedLabel = totalPartnersInitiatedLabel;
}
public String getTotalPartnersInitiatedCount() {
	return totalPartnersInitiatedCount;
}
public void setTotalPartnersInitiatedCount(String totalPartnersInitiatedCount) {
	this.totalPartnersInitiatedCount = totalPartnersInitiatedCount;
}
public String getTotalPartnersInitiatedPercent() {
	return totalPartnersInitiatedPercent;
}
public void setTotalPartnersInitiatedPercent(
		String totalPartnersInitiatedPercent) {
	this.totalPartnersInitiatedPercent = totalPartnersInitiatedPercent;
}
public String getPartnersInitiatedFromOILabel() {
	return partnersInitiatedFromOILabel;
}
public void setPartnersInitiatedFromOILabel(String partnersInitiatedFromOILabel) {
	this.partnersInitiatedFromOILabel = partnersInitiatedFromOILabel;
}
public String getPartnersInitiatedFromOICount() {
	return partnersInitiatedFromOICount;
}
public void setPartnersInitiatedFromOICount(String partnersInitiatedFromOICount) {
	this.partnersInitiatedFromOICount = partnersInitiatedFromOICount;
}
public String getPartnersInitiatedFromRILabel() {
	return partnersInitiatedFromRILabel;
}
public void setPartnersInitiatedFromRILabel(String partnersInitiatedFromRILabel) {
	this.partnersInitiatedFromRILabel = partnersInitiatedFromRILabel;
}
public String getPartnersInitiatedFromRICount() {
	return partnersInitiatedFromRICount;
}
public void setPartnersInitiatedFromRICount(String partnersInitiatedFromRICount) {
	this.partnersInitiatedFromRICount = partnersInitiatedFromRICount;
}
public String getContactIndexLabel() {
	return contactIndexLabel;
}
public void setContactIndexLabel(String contactIndexLabel) {
	this.contactIndexLabel = contactIndexLabel;
}
public String getContactIndex() {
	return contactIndex;
}
public void setContactIndex(String contactIndex) {
	this.contactIndex = contactIndex;
}
public String getCasesWithNoPartnersLabel() {
	return casesWithNoPartnersLabel;
}
public void setCasesWithNoPartnersLabel(String casesWithNoPartnersLabel) {
	this.casesWithNoPartnersLabel = casesWithNoPartnersLabel;
}
public String getCasesWithNoPartnersPercent() {
	return casesWithNoPartnersPercent;
}
public void setCasesWithNoPartnersPercent(String casesWithNoPartnersPercent) {
	this.casesWithNoPartnersPercent = casesWithNoPartnersPercent;
}
public String getCasesWithNoPartnersCount() {
	return casesWithNoPartnersCount;
}
public void setCasesWithNoPartnersCount(String casesWithNoPartnersCount) {
	this.casesWithNoPartnersCount = casesWithNoPartnersCount;
}
public String getTotalClusterInitiatedLabel() {
	return totalClusterInitiatedLabel;
}
public void setTotalClusterInitiatedLabel(String totalClusterInitiatedLabel) {
	this.totalClusterInitiatedLabel = totalClusterInitiatedLabel;
}
public String getTotalClusterInitiatedCount() {
	return totalClusterInitiatedCount;
}
public void setTotalClusterInitiatedCount(String totalClusterInitiatedCount) {
	this.totalClusterInitiatedCount = totalClusterInitiatedCount;
}
public String getClusterIndexLabel() {
	return clusterIndexLabel;
}
public void setClusterIndexLabel(String clusterIndexLabel) {
	this.clusterIndexLabel = clusterIndexLabel;
}
public String getClusterIndex() {
	return clusterIndex;
}
public void setClusterIndex(String clusterIndex) {
	this.clusterIndex = clusterIndex;
}
public String getCasesWithNoClustersLabel() {
	return casesWithNoClustersLabel;
}
public void setCasesWithNoClustersLabel(String casesWithNoClustersLabel) {
	this.casesWithNoClustersLabel = casesWithNoClustersLabel;
}
public String getCasesWithNoClustersCount() {
	return casesWithNoClustersCount;
}
public void setCasesWithNoClustersCount(String casesWithNoClustersCount) {
	this.casesWithNoClustersCount = casesWithNoClustersCount;
}
public String getCasesWithNoClustersPercent() {
	return casesWithNoClustersPercent;
}
public void setCasesWithNoClustersPercent(String casesWithNoClustersPercent) {
	this.casesWithNoClustersPercent = casesWithNoClustersPercent;
}
public String getNewPartnersExaminedLabel() {
	return newPartnersExaminedLabel;
}
public void setNewPartnersExaminedLabel(String newPartnersExaminedLabel) {
	this.newPartnersExaminedLabel = newPartnersExaminedLabel;
}
public String getNewPartnersExaminedCount() {
	return newPartnersExaminedCount;
}
public void setNewPartnersExaminedCount(String newPartnersExaminedCount) {
	this.newPartnersExaminedCount = newPartnersExaminedCount;
}
public String getNewPartnersExaminedPercent() {
	return newPartnersExaminedPercent;
}
public void setNewPartnersExaminedPercent(String newPartnersExaminedPercent) {
	this.newPartnersExaminedPercent = newPartnersExaminedPercent;
}
public String getNewPartnersPreventativeRxLabel() {
	return newPartnersPreventativeRxLabel;
}
public void setNewPartnersPreventativeRxLabel(
		String newPartnersPreventativeRxLabel) {
	this.newPartnersPreventativeRxLabel = newPartnersPreventativeRxLabel;
}
public String getNewPartnersPreventativeRxCount() {
	return newPartnersPreventativeRxCount;
}
public void setNewPartnersPreventativeRxCount(
		String newPartnersPreventativeRxCount) {
	this.newPartnersPreventativeRxCount = newPartnersPreventativeRxCount;
}

public String getNewPartnersPreventativeRxPercent() {
	return newPartnersPreventativeRxPercent;
}
public void setNewPartnersPreventativeRxPercent(
		String newPartnersPreventativeRxPercent) {
	this.newPartnersPreventativeRxPercent = newPartnersPreventativeRxPercent;
}
public String getNewPartnersRefusedPrevRxLabel() {
	return newPartnersRefusedPrevRxLabel;
}
public void setNewPartnersRefusedPrevRxLabel(
		String newPartnersRefusedPrevRxLabel) {
	this.newPartnersRefusedPrevRxLabel = newPartnersRefusedPrevRxLabel;
}
public String getNewPartnersRefusedPrevRxCount() {
	return newPartnersRefusedPrevRxCount;
}
public void setNewPartnersRefusedPrevRxCount(
		String newPartnersRefusedPrevRxCount) {
	this.newPartnersRefusedPrevRxCount = newPartnersRefusedPrevRxCount;
}
public String getNewPartnersRefusedPrevRxPercent() {
	return newPartnersRefusedPrevRxPercent;
}
public void setNewPartnersRefusedPrevRxPercent(
		String newPartnersRefusedPrevRxPercent) {
	this.newPartnersRefusedPrevRxPercent = newPartnersRefusedPrevRxPercent;
}
public String getNewPartnersInfectedRxLabel() {
	return newPartnersInfectedRxLabel;
}
public void setNewPartnersInfectedRxLabel(String newPartnersInfectedRxLabel) {
	this.newPartnersInfectedRxLabel = newPartnersInfectedRxLabel;
}
public String getNewPartnersInfectedRxCount() {
	return newPartnersInfectedRxCount;
}
public void setNewPartnersInfectedRxCount(String newPartnersInfectedRxCount) {
	this.newPartnersInfectedRxCount = newPartnersInfectedRxCount;
}
public String getNewPartnersInfectedRxPercent() {
	return newPartnersInfectedRxPercent;
}
public void setNewPartnersInfectedRxPercent(String newPartnersInfectedRxPercent) {
	this.newPartnersInfectedRxPercent = newPartnersInfectedRxPercent;
}
public String getNewPartnersInfectedNoRxLabel() {
	return newPartnersInfectedNoRxLabel;
}
public void setNewPartnersInfectedNoRxLabel(String newPartnersInfectedNoRxLabel) {
	this.newPartnersInfectedNoRxLabel = newPartnersInfectedNoRxLabel;
}
public String getNewPartnersInfectedNoRxCount() {
	return newPartnersInfectedNoRxCount;
}
public void setNewPartnersInfectedNoRxCount(String newPartnersInfectedNoRxCount) {
	this.newPartnersInfectedNoRxCount = newPartnersInfectedNoRxCount;
}
public String getNewPartnersInfectedNoRxPercent() {
	return newPartnersInfectedNoRxPercent;
}
public void setNewPartnersInfectedNoRxPercent(
		String newPartnersInfectedNoRxPercent) {
	this.newPartnersInfectedNoRxPercent = newPartnersInfectedNoRxPercent;
}
public String getNewPartnersNotInfectedLabel() {
	return newPartnersNotInfectedLabel;
}
public void setNewPartnersNotInfectedLabel(String newPartnersNotInfectedLabel) {
	this.newPartnersNotInfectedLabel = newPartnersNotInfectedLabel;
}
public String getNewPartnersNotInfectedCount() {
	return newPartnersNotInfectedCount;
}
public void setNewPartnersNotInfectedCount(String newPartnersNotInfectedCount) {
	this.newPartnersNotInfectedCount = newPartnersNotInfectedCount;
}
public String getNewPartnersNotInfectedPercent() {
	return newPartnersNotInfectedPercent;
}
public void setNewPartnersNotInfectedPercent(
		String newPartnersNotInfectedPercent) {
	this.newPartnersNotInfectedPercent = newPartnersNotInfectedPercent;
}
public String getNewPartnersNoExamLabel() {
	return newPartnersNoExamLabel;
}
public void setNewPartnersNoExamLabel(String newPartnersNoExamLabel) {
	this.newPartnersNoExamLabel = newPartnersNoExamLabel;
}
public String getNewPartnersNoExamCount() {
	return newPartnersNoExamCount;
}
public void setNewPartnersNoExamCount(String newPartnersNoExamCount) {
	this.newPartnersNoExamCount = newPartnersNoExamCount;
}
public String getNewPartnersNoExamPercent() {
	return newPartnersNoExamPercent;
}
public void setNewPartnersNoExamPercent(String newPartnersNoExamPercent) {
	this.newPartnersNoExamPercent = newPartnersNoExamPercent;
}
public String getNewPartnersInsufficientInfoLabel() {
	return newPartnersInsufficientInfoLabel;
}
public void setNewPartnersInsufficientInfoLabel(
		String newPartnersInsufficientInfoLabel) {
	this.newPartnersInsufficientInfoLabel = newPartnersInsufficientInfoLabel;
}
public String getNewPartnersInsufficientInfoCount() {
	return newPartnersInsufficientInfoCount;
}
public void setNewPartnersInsufficientInfoCount(
		String newPartnersInsufficientInfoCount) {
	this.newPartnersInsufficientInfoCount = newPartnersInsufficientInfoCount;
}
public String getNewPartnersInsufficientInfoPercent() {
	return newPartnersInsufficientInfoPercent;
}
public void setNewPartnersInsufficientInfoPercent(
		String newPartnersInsufficientInfoPercent) {
	this.newPartnersInsufficientInfoPercent = newPartnersInsufficientInfoPercent;
}
public String getNewPartnersUnableToLocateLabel() {
	return newPartnersUnableToLocateLabel;
}
public void setNewPartnersUnableToLocateLabel(
		String newPartnersUnableToLocateLabel) {
	this.newPartnersUnableToLocateLabel = newPartnersUnableToLocateLabel;
}
public String getNewPartnersUnableToLocateCount() {
	return newPartnersUnableToLocateCount;
}
public void setNewPartnersUnableToLocateCount(
		String newPartnersUnableToLocateCount) {
	this.newPartnersUnableToLocateCount = newPartnersUnableToLocateCount;
}
public String getNewPartnersUnableToLocatePercent() {
	return newPartnersUnableToLocatePercent;
}
public void setNewPartnersUnableToLocatePercent(
		String newPartnersUnableToLocatePercent) {
	this.newPartnersUnableToLocatePercent = newPartnersUnableToLocatePercent;
}
public String getNewPartnersRefusedExamLabel() {
	return newPartnersRefusedExamLabel;
}
public void setNewPartnersRefusedExamLabel(String newPartnersRefusedExamLabel) {
	this.newPartnersRefusedExamLabel = newPartnersRefusedExamLabel;
}
public String getNewPartnersRefusedExamCount() {
	return newPartnersRefusedExamCount;
}
public void setNewPartnersRefusedExamCount(String newPartnersRefusedExamCount) {
	this.newPartnersRefusedExamCount = newPartnersRefusedExamCount;
}
public String getNewPartnersRefusedExamPercent() {
	return newPartnersRefusedExamPercent;
}
public void setNewPartnersRefusedExamPercent(
		String newPartnersRefusedExamPercent) {
	this.newPartnersRefusedExamPercent = newPartnersRefusedExamPercent;
}
public String getNewPartnersOOJLabel() {
	return newPartnersOOJLabel;
}
public void setNewPartnersOOJLabel(String newPartnersOOJLabel) {
	this.newPartnersOOJLabel = newPartnersOOJLabel;
}
public String getNewPartnersOOJCount() {
	return newPartnersOOJCount;
}
public void setNewPartnersOOJCount(String newPartnersOOJCount) {
	this.newPartnersOOJCount = newPartnersOOJCount;
}
public String getNewPartnersOOJPercent() {
	return newPartnersOOJPercent;
}
public void setNewPartnersOOJPercent(String newPartnersOOJPercent) {
	this.newPartnersOOJPercent = newPartnersOOJPercent;
}
public String getNewPartnersOtherLabel() {
	return newPartnersOtherLabel;
}
public void setNewPartnersOtherLabel(String newPartnersOtherLabel) {
	this.newPartnersOtherLabel = newPartnersOtherLabel;
}
public String getNewPartnersOtherCount() {
	return newPartnersOtherCount;
}
public void setNewPartnersOtherCount(String newPartnersOtherCount) {
	this.newPartnersOtherCount = newPartnersOtherCount;
}
public String getNewPartnersOtherPercent() {
	return newPartnersOtherPercent;
}
public void setNewPartnersOtherPercent(String newPartnersOtherPercent) {
	this.newPartnersOtherPercent = newPartnersOtherPercent;
}
public String getNewPartnersPreviousRxLabel() {
	return newPartnersPreviousRxLabel;
}
public void setNewPartnersPreviousRxLabel(String newPartnersPreviousRxLabel) {
	this.newPartnersPreviousRxLabel = newPartnersPreviousRxLabel;
}
public String getNewPartnersPreviousRxCount() {
	return newPartnersPreviousRxCount;
}
public void setNewPartnersPreviousRxCount(String newPartnersPreviousRxCount) {
	this.newPartnersPreviousRxCount = newPartnersPreviousRxCount;
}
public String getNewPartnersPreviousRxPercent() {
	return newPartnersPreviousRxPercent;
}

public void setNewPartnersPreviousRxPercent(String newPartnersPreviousRxPercent) {
	this.newPartnersPreviousRxPercent = newPartnersPreviousRxPercent;
}
public String getNewPartnersStdOpenLabel() {
	return newPartnersStdOpenLabel;
}
public void setNewPartnersStdOpenLabel(String newPartnersStdOpenLabel) {
	this.newPartnersStdOpenLabel = newPartnersStdOpenLabel;
}
public String getNewPartnersStdOpenCount() {
	return newPartnersStdOpenCount;
}
public void setNewPartnersStdOpenCount(String newPartnersStdOpenCount) {
	this.newPartnersStdOpenCount = newPartnersStdOpenCount;
}
public String getNewPartnersStdOpenPercent() {
	return newPartnersStdOpenPercent;
}
public void setNewPartnersStdOpenPercent(String newPartnersStdOpenPercent) {
	this.newPartnersStdOpenPercent = newPartnersStdOpenPercent;
}
public String getNewClustersExaminedLabel() {
	return newClustersExaminedLabel;
}
public void setNewClustersExaminedLabel(String newClustersExaminedLabel) {
	this.newClustersExaminedLabel = newClustersExaminedLabel;
}
public String getNewClustersExaminedCount() {
	return newClustersExaminedCount;
}
public void setNewClustersExaminedCount(String newClustersExaminedCount) {
	this.newClustersExaminedCount = newClustersExaminedCount;
}
public String getNewClustersExaminedPercent() {
	return newClustersExaminedPercent;
}
public void setNewClustersExaminedPercent(String newClustersExaminedPercent) {
	this.newClustersExaminedPercent = newClustersExaminedPercent;
}
public String getNewClustersPreventativeRxLabel() {
	return newClustersPreventativeRxLabel;
}
public void setNewClustersPreventativeRxLabel(
		String newClustersPreventativeRxLabel) {
	this.newClustersPreventativeRxLabel = newClustersPreventativeRxLabel;
}
public String getNewClustersPreventativeRxCount() {
	return newClustersPreventativeRxCount;
}
public void setNewClustersPreventativeRxCount(
		String newClustersPreventativeRxCount) {
	this.newClustersPreventativeRxCount = newClustersPreventativeRxCount;
}
public String getNewClustersPreventativeRxPercent() {
	return newClustersPreventativeRxPercent;
}
public void setNewClustersPreventativeRxPercent(
		String newClustersPreventativeRxPercent) {
	this.newClustersPreventativeRxPercent = newClustersPreventativeRxPercent;
}
public String getNewClustersRefusedPrevRxLabel() {
	return newClustersRefusedPrevRxLabel;
}
public void setNewClustersRefusedPrevRxLabel(
		String newClustersRefusedPrevRxLabel) {
	this.newClustersRefusedPrevRxLabel = newClustersRefusedPrevRxLabel;
}
public String getNewClustersRefusedPrevRxCount() {
	return newClustersRefusedPrevRxCount;
}
public void setNewClustersRefusedPrevRxCount(
		String newClustersRefusedPrevRxCount) {
	this.newClustersRefusedPrevRxCount = newClustersRefusedPrevRxCount;
}
public String getNewClustersRefusedPrevRxPercent() {
	return newClustersRefusedPrevRxPercent;
}
public void setNewClustersRefusedPrevRxPercent(
		String newClustersRefusedPrevRxPercent) {
	this.newClustersRefusedPrevRxPercent = newClustersRefusedPrevRxPercent;
}
public String getNewClustersInfectedRxLabel() {
	return newClustersInfectedRxLabel;
}
public void setNewClustersInfectedRxLabel(String newClustersInfectedRxLabel) {
	this.newClustersInfectedRxLabel = newClustersInfectedRxLabel;
}
public String getNewClustersInfectedRxCount() {
	return newClustersInfectedRxCount;
}
public void setNewClustersInfectedRxCount(String newClustersInfectedRxCount) {
	this.newClustersInfectedRxCount = newClustersInfectedRxCount;
}
public String getNewClustersInfectedRxPercent() {
	return newClustersInfectedRxPercent;
}
public void setNewClustersInfectedRxPercent(String newClustersInfectedRxPercent) {
	this.newClustersInfectedRxPercent = newClustersInfectedRxPercent;
}
public String getNewClustersInfectedNoRxLabel() {
	return newClustersInfectedNoRxLabel;
}
public void setNewClustersInfectedNoRxLabel(String newClustersInfectedNoRxLabel) {
	this.newClustersInfectedNoRxLabel = newClustersInfectedNoRxLabel;
}
public String getNewClustersInfectedNoRxCount() {
	return newClustersInfectedNoRxCount;
}
public void setNewClustersInfectedNoRxCount(String newClustersInfectedNoRxCount) {
	this.newClustersInfectedNoRxCount = newClustersInfectedNoRxCount;
}
public String getNewClustersInfectedNoRxPercent() {
	return newClustersInfectedNoRxPercent;
}
public void setNewClustersInfectedNoRxPercent(
		String newClustersInfectedNoRxPercent) {
	this.newClustersInfectedNoRxPercent = newClustersInfectedNoRxPercent;
}
public String getNewClustersNotInfectedLabel() {
	return newClustersNotInfectedLabel;
}
public void setNewClustersNotInfectedLabel(String newClustersNotInfectedLabel) {
	this.newClustersNotInfectedLabel = newClustersNotInfectedLabel;
}
public String getNewClustersNotInfectedCount() {
	return newClustersNotInfectedCount;
}
public void setNewClustersNotInfectedCount(String newClustersNotInfectedCount) {
	this.newClustersNotInfectedCount = newClustersNotInfectedCount;
}
public String getNewClustersNotInfectedPercent() {
	return newClustersNotInfectedPercent;
}
public void setNewClustersNotInfectedPercent(
		String newClustersNotInfectedPercent) {
	this.newClustersNotInfectedPercent = newClustersNotInfectedPercent;
}
public String getNewClustersNoExamLabel() {
	return newClustersNoExamLabel;
}
public void setNewClustersNoExamLabel(
		String newClustersNoExamLabel) {
	this.newClustersNoExamLabel = newClustersNoExamLabel;
}
public String getNewClustersNoExamCount() {
	return newClustersNoExamCount;
}
public void setNewClustersNewClustersNoExamCount(
		String newClustersNoExamCount) {
	this.newClustersNoExamCount = newClustersNoExamCount;
}
public String getNewClustersNewClustersNoExamPercent() {
	return newClustersNoExamPercent;
}
public void setNewClustersNoExamPercent(
		String newClusterssNoExamPercent) {
	this.newClustersNoExamPercent = newClustersNoExamPercent;
}
public String getNewClustersInsufficientInfoLabel() {
	return newClustersInsufficientInfoLabel;
}
public void setNewClustersInsufficientInfoLabel(
		String newClustersInsufficientInfoLabel) {
	this.newClustersInsufficientInfoLabel = newClustersInsufficientInfoLabel;
}
public String getNewClustersInsufficientInfoCount() {
	return newClustersInsufficientInfoCount;
}
public void setNewClustersInsufficientInfoCount(
		String newClustersInsufficientInfoCount) {
	this.newClustersInsufficientInfoCount = newClustersInsufficientInfoCount;
}
public String getNewClustersInsufficientInfoPercent() {
	return newClustersInsufficientInfoPercent;
}
public void setNewClustersInsufficientInfoPercent(
		String newClustersInsufficientInfoPercent) {
	this.newClustersInsufficientInfoPercent = newClustersInsufficientInfoPercent;
}
public String getNewClustersUnableToLocateLabel() {
	return newClustersUnableToLocateLabel;
}
public void setNewClustersUnableToLocateLabel(
		String newClustersUnableToLocateLabel) {
	this.newClustersUnableToLocateLabel = newClustersUnableToLocateLabel;
}
public String getNewClustersUnableToLocateCount() {
	return newClustersUnableToLocateCount;
}
public void setNewClustersUnableToLocateCount(
		String newClustersUnableToLocateCount) {
	this.newClustersUnableToLocateCount = newClustersUnableToLocateCount;
}
public String getNewClustersUnableToLocatePercent() {
	return newClustersUnableToLocatePercent;
}
public void setNewClustersUnableToLocatePercent(
		String newClustersUnableToLocatePercent) {
	this.newClustersUnableToLocatePercent = newClustersUnableToLocatePercent;
}
public String getNewClustersRefusedExamLabel() {
	return newClustersRefusedExamLabel;
}
public void setNewClustersRefusedExamLabel(String newClustersRefusedExamLabel) {
	this.newClustersRefusedExamLabel = newClustersRefusedExamLabel;
}
public String getNewClustersRefusedExamCount() {
	return newClustersRefusedExamCount;
}
public void setNewClustersRefusedExamCount(String newClustersRefusedExamCount) {
	this.newClustersRefusedExamCount = newClustersRefusedExamCount;
}
public String getNewClustersRefusedExamPercent() {
	return newClustersRefusedExamPercent;
}
public void setNewClustersRefusedExamPercent(
		String newClustersRefusedExamPercent) {
	this.newClustersRefusedExamPercent = newClustersRefusedExamPercent;
}
public String getNewClustersOOJLabel() {
	return newClustersOOJLabel;
}
public void setNewClustersOOJLabel(String newClustersOOJLabel) {
	this.newClustersOOJLabel = newClustersOOJLabel;
}
public String getNewClustersOOJCount() {
	return newClustersOOJCount;
}
public void setNewClustersOOJCount(String newClustersOOJCount) {
	this.newClustersOOJCount = newClustersOOJCount;
}
public String getNewClustersOOJPercent() {
	return newClustersOOJPercent;
}
public void setNewClustersOOJPercent(String newClustersOOJPercent) {
	this.newClustersOOJPercent = newClustersOOJPercent;
}
public String getNewClustersOtherLabel() {
	return newClustersOtherLabel;
}
public void setNewClustersOtherLabel(String newClustersOtherLabel) {
	this.newClustersOtherLabel = newClustersOtherLabel;
}
public String getNewClustersOtherCount() {
	return newClustersOtherCount;
}
public void setNewClustersOtherCount(String newClustersOtherCount) {
	this.newClustersOtherCount = newClustersOtherCount;
}
public String getNewClustersOtherPercent() {
	return newClustersOtherPercent;
}
public void setNewClustersOtherPercent(String newClustersOtherPercent) {
	this.newClustersOtherPercent = newClustersOtherPercent;
}
public String getNewClustersPreviousRxLabel() {
	return newClustersPreviousRxLabel;
}
public void setNewClustersPreviousRxLabel(String newClustersPreviousRxLabel) {
	this.newClustersPreviousRxLabel = newClustersPreviousRxLabel;
}
public String getNewClustersPreviousRxCount() {
	return newClustersPreviousRxCount;
}
public void setNewClustersPreviousRxCount(String newClustersPreviousRxCount) {
	this.newClustersPreviousRxCount = newClustersPreviousRxCount;
}
public String getNewClustersPreviousRxPercent() {
	return newClustersPreviousRxPercent;
}
public void setNewClustersPreviousRxPercent(String newClustersPreviousRxPercent) {
	this.newClustersPreviousRxPercent = newClustersPreviousRxPercent;
}
public String getNewClustersStdOpenLabel() {
	return newClustersStdOpenLabel;
}
public void setNewClustersStdOpenLabel(String newClustersStdOpenLabel) {
	this.newClustersStdOpenLabel = newClustersStdOpenLabel;
}
public String getNewClustersStdOpenCount() {
	return newClustersStdOpenCount;
}
public void setNewClustersStdOpenCount(String newClustersStdOpenCount) {
	this.newClustersStdOpenCount = newClustersStdOpenCount;
}
public String getNewClustersStdOpenPercent() {
	return newClustersStdOpenPercent;
}
public void setNewClustersStdOpenPercent(String newClustersStdOpenPercent) {
	this.newClustersStdOpenPercent = newClustersStdOpenPercent;
}
public String getNewPartnersNotifiedLabel() {
	return newPartnersNotifiedLabel;
}
public void setNewPartnersNotifiedLabel(String newPartnersNotifiedLabel) {
	this.newPartnersNotifiedLabel = newPartnersNotifiedLabel;
}
public String getNewPartnersNotifiedCount() {
	return newPartnersNotifiedCount;
}
public void setNewPartnersNotifiedCount(String newPartnersNotifiedCount) {
	this.newPartnersNotifiedCount = newPartnersNotifiedCount;
}
public String getNewPartnersNotifiedPercent() {
	return newPartnersNotifiedPercent;
}
public void setNewPartnersNotifiedPercent(String newPartnersNotifiedPercent) {
	this.newPartnersNotifiedPercent = newPartnersNotifiedPercent;
}
public String getNewPartnersPrevNegNewPosLabel() {
	return newPartnersPrevNegNewPosLabel;
}
public void setNewPartnersPrevNegNewPosLabel(
		String newPartnersPrevNegNewPosLabel) {
	this.newPartnersPrevNegNewPosLabel = newPartnersPrevNegNewPosLabel;
}
public String getNewPartnersPrevNegNewPosCount() {
	return newPartnersPrevNegNewPosCount;
}
public void setNewPartnersPrevNegNewPosCount(
		String newPartnersPrevNegNewPosCount) {
	this.newPartnersPrevNegNewPosCount = newPartnersPrevNegNewPosCount;
}
public String getNewPartnersPrevNegNewPosPercent() {
	return newPartnersPrevNegNewPosPercent;
}
public void setNewPartnersPrevNegNewPosPercent(
		String newPartnersPrevNegNewPosPercent) {
	this.newPartnersPrevNegNewPosPercent = newPartnersPrevNegNewPosPercent;
}
public String getNewPartnersPrevNegStillNegLabel() {
	return newPartnersPrevNegStillNegLabel;
}
public void setNewPartnersPrevNegStillNegLabel(
		String newPartnersPrevNegStillNegLabel) {
	this.newPartnersPrevNegStillNegLabel = newPartnersPrevNegStillNegLabel;
}
public String getNewPartnersPrevNegStillNegCount() {
	return newPartnersPrevNegStillNegCount;
}
public void setNewPartnersPrevNegStillNegCount(
		String newPartnersPrevNegStillNegCount) {
	this.newPartnersPrevNegStillNegCount = newPartnersPrevNegStillNegCount;
}
public String getNewPartnersPrevNegStillNegPercent() {
	return newPartnersPrevNegStillNegPercent;
}
public void setNewPartnersPrevNegStillNegPercent(
		String newPartnersPrevNegStillNegPercent) {
	this.newPartnersPrevNegStillNegPercent = newPartnersPrevNegStillNegPercent;
}
public String getNewPartnersPrevNegNoTestLabel() {
	return newPartnersPrevNegNoTestLabel;
}
public void setNewPartnersPrevNegNoTestLabel(
		String newPartnersPrevNegNoTestLabel) {
	this.newPartnersPrevNegNoTestLabel = newPartnersPrevNegNoTestLabel;
}
public String getNewPartnersPrevNegNoTestCount() {
	return newPartnersPrevNegNoTestCount;
}
public void setNewPartnersPrevNegNoTestCount(
		String newPartnersPrevNegNoTestCount) {
	this.newPartnersPrevNegNoTestCount = newPartnersPrevNegNoTestCount;
}
public String getNewPartnersPrevNegNoTestPercent() {
	return newPartnersPrevNegNoTestPercent;
}
public void setNewPartnersPrevNegNoTestPercent(
		String newPartnersPrevNegNoTestPercent) {
	this.newPartnersPrevNegNoTestPercent = newPartnersPrevNegNoTestPercent;
}
public String getNewPartnersNoPrevNewPosLabel() {
	return newPartnersNoPrevNewPosLabel;
}
public void setNewPartnersNoPrevNewPosLabel(String newPartnersNoPrevNewPosLabel) {
	this.newPartnersNoPrevNewPosLabel = newPartnersNoPrevNewPosLabel;
}
public String getNewPartnersNoPrevNewPosCount() {
	return newPartnersNoPrevNewPosCount;
}
public void setNewPartnersNoPrevNewPosCount(String newPartnersNoPrevNewPosCount) {
	this.newPartnersNoPrevNewPosCount = newPartnersNoPrevNewPosCount;
}
public String getNewPartnersNoPrevNewPosPercent() {
	return newPartnersNoPrevNewPosPercent;
}
public void setNewPartnersNoPrevNewPosPercent(
		String newPartnersNoPrevNewPosPercent) {
	this.newPartnersNoPrevNewPosPercent = newPartnersNoPrevNewPosPercent;
}
public String getNewPartnersNoPrevNewNegLabel() {
	return newPartnersNoPrevNewNegLabel;
}
public void setNewPartnersNoPrevNewNegLabel(String newPartnersNoPrevNewNegLabel) {
	this.newPartnersNoPrevNewNegLabel = newPartnersNoPrevNewNegLabel;
}
public String getNewPartnersNoPrevNewNegCount() {
	return newPartnersNoPrevNewNegCount;
}
public void setNewPartnersNoPrevNewNegCount(String newPartnersNoPrevNewNegCount) {
	this.newPartnersNoPrevNewNegCount = newPartnersNoPrevNewNegCount;
}
public String getNewPartnersNoPrevNewNegPercent() {
	return newPartnersNoPrevNewNegPercent;
}
public void setNewPartnersNoPrevNewNegPercent(
		String newPartnersNoPrevNewNegPercent) {
	this.newPartnersNoPrevNewNegPercent = newPartnersNoPrevNewNegPercent;
}
public String getNewPartnersNoPrevNoTestLabel() {
	return newPartnersNoPrevNoTestLabel;
}
public void setNewPartnersNoPrevNoTestLabel(String newPartnersNoPrevNoTestLabel) {
	this.newPartnersNoPrevNoTestLabel = newPartnersNoPrevNoTestLabel;
}
public String getNewPartnersNoPrevNoTestCount() {
	return newPartnersNoPrevNoTestCount;
}
public void setNewPartnersNoPrevNoTestCount(String newPartnersNoPrevNoTestCount) {
	this.newPartnersNoPrevNoTestCount = newPartnersNoPrevNoTestCount;
}
public String getNewPartnersNoPrevNoTestPercent() {
	return newPartnersNoPrevNoTestPercent;
}
public void setNewPartnersNoPrevNoTestPercent(
		String newPartnersNoPrevNoTestPercent) {
	this.newPartnersNoPrevNoTestPercent = newPartnersNoPrevNoTestPercent;
}
public String getNewPartnersNotNotifiedLabel() {
	return newPartnersNotNotifiedLabel;
}
public void setNewPartnersNotNotifiedLabel(String newPartnersNotNotifiedLabel) {
	this.newPartnersNotNotifiedLabel = newPartnersNotNotifiedLabel;
}
public String getNewPartnersNotNotifiedCount() {
	return newPartnersNotNotifiedCount;
}
public void setNewPartnersNotNotifiedCount(String newPartnersNotNotifiedCount) {
	this.newPartnersNotNotifiedCount = newPartnersNotNotifiedCount;
}
public String getNewPartnersNotNotifiedPercent() {
	return newPartnersNotNotifiedPercent;
}
public void setNewPartnersNotNotifiedPercent(
		String newPartnersNotNotifiedPercent) {
	this.newPartnersNotNotifiedPercent = newPartnersNotNotifiedPercent;
}
public String getNewPartnersNotNotifiedInsufficientInfoLabel() {
	return newPartnersNotNotifiedInsufficientInfoLabel;
}
public void setNewPartnersNotNotifiedInsufficientInfoLabel(
		String newPartnersNotNotifiedInsufficientInfoLabel) {
	this.newPartnersNotNotifiedInsufficientInfoLabel = newPartnersNotNotifiedInsufficientInfoLabel;
}
public String getNewPartnersNotNotifiedInsufficientInfoCount() {
	return newPartnersNotNotifiedInsufficientInfoCount;
}
public void setNewPartnersNotNotifiedInsufficientInfoCount(
		String newPartnersNotNotifiedInsufficientInfoCount) {
	this.newPartnersNotNotifiedInsufficientInfoCount = newPartnersNotNotifiedInsufficientInfoCount;
}
public String getNewPartnersNotNotifiedInsufficientInfoPercent() {
	return newPartnersNotNotifiedInsufficientInfoPercent;
}
public void setNewPartnersNotNotifiedInsufficientInfoPercent(
		String newPartnersNotNotifiedInsufficientInfoPercent) {
	this.newPartnersNotNotifiedInsufficientInfoPercent = newPartnersNotNotifiedInsufficientInfoPercent;
}
public String getNewPartnersNotNotifiedUnableToLocateLabel() {
	return newPartnersNotNotifiedUnableToLocateLabel;
}
public void setNewPartnersNotNotifiedUnableToLocateLabel(
		String newPartnersNotNotifiedUnableToLocateLabel) {
	this.newPartnersNotNotifiedUnableToLocateLabel = newPartnersNotNotifiedUnableToLocateLabel;
}
public String getNewPartnersNotNotifiedUnableToLocateCount() {
	return newPartnersNotNotifiedUnableToLocateCount;
}
public void setNewPartnersNotNotifiedUnableToLocateCount(
		String newPartnersNotNotifiedUnableToLocateCount) {
	this.newPartnersNotNotifiedUnableToLocateCount = newPartnersNotNotifiedUnableToLocateCount;
}
public String getNewPartnersNotNotifiedUnableToLocatePercent() {
	return newPartnersNotNotifiedUnableToLocatePercent;
}
public void setNewPartnersNotNotifiedUnableToLocatePercent(
		String newPartnersNotNotifiedUnableToLocatePercent) {
	this.newPartnersNotNotifiedUnableToLocatePercent = newPartnersNotNotifiedUnableToLocatePercent;
}
public String getNewPartnersNotNotifiedRefusedExamLabel() {
	return newPartnersNotNotifiedRefusedExamLabel;
}
public void setNewPartnersNotNotifiedRefusedExamLabel(
		String newPartnersNotNotifiedRefusedExamLabel) {
	this.newPartnersNotNotifiedRefusedExamLabel = newPartnersNotNotifiedRefusedExamLabel;
}
public String getNewPartnersNotNotifiedRefusedExamCount() {
	return newPartnersNotNotifiedRefusedExamCount;
}
public void setNewPartnersNotNotifiedRefusedExamCount(
		String newPartnersNotNotifiedRefusedExamCount) {
	this.newPartnersNotNotifiedRefusedExamCount = newPartnersNotNotifiedRefusedExamCount;
}
public String getNewPartnersNotNotifiedRefusedExamPercent() {
	return newPartnersNotNotifiedRefusedExamPercent;
}
public void setNewPartnersNotNotifiedRefusedExamPercent(
		String newPartnersNotNotifiedRefusedExamPercent) {
	this.newPartnersNotNotifiedRefusedExamPercent = newPartnersNotNotifiedRefusedExamPercent;
}
public String getNewPartnersNotNotifiedOOJLabel() {
	return newPartnersNotNotifiedOOJLabel;
}
public void setNewPartnersNotNotifiedOOJLabel(
		String newPartnersNotNotifiedOOJLabel) {
	this.newPartnersNotNotifiedOOJLabel = newPartnersNotNotifiedOOJLabel;
}
public String getNewPartnersNotNotifiedOOJCount() {
	return newPartnersNotNotifiedOOJCount;
}
public void setNewPartnersNotNotifiedOOJCount(
		String newPartnersNotNotifiedOOJCount) {
	this.newPartnersNotNotifiedOOJCount = newPartnersNotNotifiedOOJCount;
}
public String getNewPartnersNotNotifiedOOJPercent() {
	return newPartnersNotNotifiedOOJPercent;
}
public void setNewPartnersNotNotifiedOOJPercent(
		String newPartnersNotNotifiedOOJPercent) {
	this.newPartnersNotNotifiedOOJPercent = newPartnersNotNotifiedOOJPercent;
}
public String getNewPartnersNotNotifiedOtherLabel() {
	return newPartnersNotNotifiedOtherLabel;
}
public void setNewPartnersNotNotifiedOtherLabel(
		String newPartnersNotNotifiedOtherLabel) {
	this.newPartnersNotNotifiedOtherLabel = newPartnersNotNotifiedOtherLabel;
}
public String getNewPartnersNotNotifiedOtherCount() {
	return newPartnersNotNotifiedOtherCount;
}
public void setNewPartnersNotNotifiedOtherCount(
		String newPartnersNotNotifiedOtherCount) {
	this.newPartnersNotNotifiedOtherCount = newPartnersNotNotifiedOtherCount;
}
public String getNewPartnersNotNotifiedOtherPercent() {
	return newPartnersNotNotifiedOtherPercent;
}
public void setNewPartnersNotNotifiedOtherPercent(
		String newPartnersNotNotifiedOtherPercent) {
	this.newPartnersNotNotifiedOtherPercent = newPartnersNotNotifiedOtherPercent;
}
public String getNewPartnersPrevPosLabel() {
	return newPartnersPrevPosLabel;
}
public void setNewPartnersPrevPosLabel(String newPartnersPrevPosLabel) {
	this.newPartnersPrevPosLabel = newPartnersPrevPosLabel;
}
public String getNewPartnersPrevPosCount() {
	return newPartnersPrevPosCount;
}
public void setNewPartnersPrevPosCount(String newPartnersPrevPosCount) {
	this.newPartnersPrevPosCount = newPartnersPrevPosCount;
}
public String getNewPartnersPrevPosPercent() {
	return newPartnersPrevPosPercent;
}
public void setNewPartnersPrevPosPercent(String newPartnersPrevPosPercent) {
	this.newPartnersPrevPosPercent = newPartnersPrevPosPercent;
}
public String getNewPartnersHivOpenLabel() {
	return newPartnersHivOpenLabel;
}
public void setNewPartnersHivOpenLabel(String newPartnersHivOpenLabel) {
	this.newPartnersHivOpenLabel = newPartnersHivOpenLabel;
}
public String getNewPartnersHivOpenCount() {
	return newPartnersHivOpenCount;
}
public void setNewPartnersHivOpenCount(String newPartnersHivOpenCount) {
	this.newPartnersHivOpenCount = newPartnersHivOpenCount;
}
public String getNewPartnersHivOpenPercent() {
	return newPartnersHivOpenPercent;
}
public void setNewPartnersHivOpenPercent(String newPartnersHivOpenPercent) {
	this.newPartnersHivOpenPercent = newPartnersHivOpenPercent;
}
public String getNewClustersNotifiedLabel() {
	return newClustersNotifiedLabel;
}
public void setNewClustersNotifiedLabel(String newClustersNotifiedLabel) {
	this.newClustersNotifiedLabel = newClustersNotifiedLabel;
}
public String getNewClustersNotifiedCount() {
	return newClustersNotifiedCount;
}
public void setNewClustersNotifiedCount(String newClustersNotifiedCount) {
	this.newClustersNotifiedCount = newClustersNotifiedCount;
}
public String getNewClustersNotifiedPercent() {
	return newClustersNotifiedPercent;
}
public void setNewClustersNotifiedPercent(String newClustersNotifiedPercent) {
	this.newClustersNotifiedPercent = newClustersNotifiedPercent;
}
public String getNewClustersPrevNegNewPosLabel() {
	return newClustersPrevNegNewPosLabel;
}
public void setNewClustersPrevNegNewPosLabel(
		String newClustersPrevNegNewPosLabel) {
	this.newClustersPrevNegNewPosLabel = newClustersPrevNegNewPosLabel;
}
public String getNewClustersPrevNegNewPosCount() {
	return newClustersPrevNegNewPosCount;
}
public void setNewClustersPrevNegNewPosCount(
		String newClustersPrevNegNewPosCount) {
	this.newClustersPrevNegNewPosCount = newClustersPrevNegNewPosCount;
}
public String getNewClustersPrevNegNewPosPercent() {
	return newClustersPrevNegNewPosPercent;
}
public void setNewClustersPrevNegNewPosPercent(
		String newClustersPrevNegNewPosPercent) {
	this.newClustersPrevNegNewPosPercent = newClustersPrevNegNewPosPercent;
}
public String getNewClustersPrevNegStillNegLabel() {
	return newClustersPrevNegStillNegLabel;
}
public void setNewClustersPrevNegStillNegLabel(
		String newClustersPrevNegStillNegLabel) {
	this.newClustersPrevNegStillNegLabel = newClustersPrevNegStillNegLabel;
}
public String getNewClustersPrevNegStillNegCount() {
	return newClustersPrevNegStillNegCount;
}
public void setNewClustersPrevNegStillNegCount(
		String newClustersPrevNegStillNegCount) {
	this.newClustersPrevNegStillNegCount = newClustersPrevNegStillNegCount;
}
public String getNewClustersPrevNegStillNegPercent() {
	return newClustersPrevNegStillNegPercent;
}
public void setNewClustersPrevNegStillNegPercent(
		String newClustersPrevNegStillNegPercent) {
	this.newClustersPrevNegStillNegPercent = newClustersPrevNegStillNegPercent;
}
public String getNewClustersPrevNegNoTestLabel() {
	return newClustersPrevNegNoTestLabel;
}
public void setNewClustersPrevNegNoTestLabel(
		String newClustersPrevNegNoTestLabel) {
	this.newClustersPrevNegNoTestLabel = newClustersPrevNegNoTestLabel;
}
public String getNewClustersPrevNegNoTestCount() {
	return newClustersPrevNegNoTestCount;
}
public void setNewClustersPrevNegNoTestCount(
		String newClustersPrevNegNoTestCount) {
	this.newClustersPrevNegNoTestCount = newClustersPrevNegNoTestCount;
}
public String getNewClustersPrevNegNoTestPercent() {
	return newClustersPrevNegNoTestPercent;
}
public void setNewClustersPrevNegNoTestPercent(
		String newClustersPrevNegNoTestPercent) {
	this.newClustersPrevNegNoTestPercent = newClustersPrevNegNoTestPercent;
}
public String getNewClustersNoPrevNewPosLabel() {
	return newClustersNoPrevNewPosLabel;
}
public void setNewClustersNoPrevNewPosLabel(String newClustersNoPrevNewPosLabel) {
	this.newClustersNoPrevNewPosLabel = newClustersNoPrevNewPosLabel;
}
public String getNewClustersNoPrevNewPosCount() {
	return newClustersNoPrevNewPosCount;
}
public void setNewClustersNoPrevNewPosCount(String newClustersNoPrevNewPosCount) {
	this.newClustersNoPrevNewPosCount = newClustersNoPrevNewPosCount;
}
public String getNewClustersNoPrevNewPosPercent() {
	return newClustersNoPrevNewPosPercent;
}
public void setNewClustersNoPrevNewPosPercent(
		String newClustersNoPrevNewPosPercent) {
	this.newClustersNoPrevNewPosPercent = newClustersNoPrevNewPosPercent;
}
public String getNewClustersNoPrevNewNegLabel() {
	return newClustersNoPrevNewNegLabel;
}
public void setNewClustersNoPrevNewNegLabel(String newClustersNoPrevNewNegLabel) {
	this.newClustersNoPrevNewNegLabel = newClustersNoPrevNewNegLabel;
}
public String getNewClustersNoPrevNewNegCount() {
	return newClustersNoPrevNewNegCount;
}
public void setNewClustersNoPrevNewNegCount(String newClustersNoPrevNewNegCount) {
	this.newClustersNoPrevNewNegCount = newClustersNoPrevNewNegCount;
}
public String getNewClustersNoPrevNewNegPercent() {
	return newClustersNoPrevNewNegPercent;
}
public void setNewClustersNoPrevNewNegPercent(
		String newClustersNoPrevNewNegPercent) {
	this.newClustersNoPrevNewNegPercent = newClustersNoPrevNewNegPercent;
}
public String getNewClustersNoPrevNoTestLabel() {
	return newClustersNoPrevNoTestLabel;
}
public void setNewClustersNoPrevNoTestLabel(String newClustersNoPrevNoTestLabel) {
	this.newClustersNoPrevNoTestLabel = newClustersNoPrevNoTestLabel;
}
public String getNewClustersNoPrevNoTestCount() {
	return newClustersNoPrevNoTestCount;
}
public void setNewClustersNoPrevNoTestCount(String newClustersNoPrevNoTestCount) {
	this.newClustersNoPrevNoTestCount = newClustersNoPrevNoTestCount;
}
public String getNewClustersNoPrevNoTestPercent() {
	return newClustersNoPrevNoTestPercent;
}
public void setNewClustersNoPrevNoTestPercent(
		String newClustersNoPrevNoTestPercent) {
	this.newClustersNoPrevNoTestPercent = newClustersNoPrevNoTestPercent;
}
public String getNewClustersNotNotifiedLabel() {
	return newClustersNotNotifiedLabel;
}
public void setNewClustersNotNotifiedLabel(String newClustersNotNotifiedLabel) {
	this.newClustersNotNotifiedLabel = newClustersNotNotifiedLabel;
}
public String getNewClustersNotNotifiedCount() {
	return newClustersNotNotifiedCount;
}
public void setNewClustersNotNotifiedCount(String newClustersNotNotifiedCount) {
	this.newClustersNotNotifiedCount = newClustersNotNotifiedCount;
}
public String getNewClustersNotNotifiedPercent() {
	return newClustersNotNotifiedPercent;
}
public void setNewClustersNotNotifiedPercent(
		String newClustersNotNotifiedPercent) {
	this.newClustersNotNotifiedPercent = newClustersNotNotifiedPercent;
}
public String getNewClustersNotNotifiedInsufficientInfoLabel() {
	return newClustersNotNotifiedInsufficientInfoLabel;
}
public void setNewClustersNotNotifiedInsufficientInfoLabel(
		String newClustersNotNotifiedInsufficientInfoLabel) {
	this.newClustersNotNotifiedInsufficientInfoLabel = newClustersNotNotifiedInsufficientInfoLabel;
}
public String getNewClustersNotNotifiedInsufficientInfoCount() {
	return newClustersNotNotifiedInsufficientInfoCount;
}
public void setNewClustersNotNotifiedInsufficientInfoCount(
		String newClustersNotNotifiedInsufficientInfoCount) {
	this.newClustersNotNotifiedInsufficientInfoCount = newClustersNotNotifiedInsufficientInfoCount;
}
public String getNewClustersNotNotifiedInsufficientInfoPercent() {
	return newClustersNotNotifiedInsufficientInfoPercent;
}
public void setNewClustersNotNotifiedInsufficientInfoPercent(
		String newClustersNotNotifiedInsufficientInfoPercent) {
	this.newClustersNotNotifiedInsufficientInfoPercent = newClustersNotNotifiedInsufficientInfoPercent;
}
public String getNewClustersNotNotifiedUnableToLocateLabel() {
	return newClustersNotNotifiedUnableToLocateLabel;
}
public void setNewClustersNotNotifiedUnableToLocateLabel(
		String newClustersNotNotifiedUnableToLocateLabel) {
	this.newClustersNotNotifiedUnableToLocateLabel = newClustersNotNotifiedUnableToLocateLabel;
}
public String getNewClustersNotNotifiedUnableToLocateCount() {
	return newClustersNotNotifiedUnableToLocateCount;
}
public void setNewClustersNotNotifiedUnableToLocateCount(
		String newClustersNotNotifiedUnableToLocateCount) {
	this.newClustersNotNotifiedUnableToLocateCount = newClustersNotNotifiedUnableToLocateCount;
}
public String getNewClustersNotNotifiedUnableToLocatePercent() {
	return newClustersNotNotifiedUnableToLocatePercent;
}
public void setNewClustersNotNotifiedUnableToLocatePercent(
		String newClustersNotNotifiedUnableToLocatePercent) {
	this.newClustersNotNotifiedUnableToLocatePercent = newClustersNotNotifiedUnableToLocatePercent;
}
public String getNewClustersNotNotifiedRefusedExamLabel() {
	return newClustersNotNotifiedRefusedExamLabel;
}
public void setNewClustersNotNotifiedRefusedExamLabel(
		String newClustersNotNotifiedRefusedExamLabel) {
	this.newClustersNotNotifiedRefusedExamLabel = newClustersNotNotifiedRefusedExamLabel;
}
public String getNewClustersNotNotifiedRefusedExamCount() {
	return newClustersNotNotifiedRefusedExamCount;
}
public void setNewClustersNotNotifiedRefusedExamCount(
		String newClustersNotNotifiedRefusedExamCount) {
	this.newClustersNotNotifiedRefusedExamCount = newClustersNotNotifiedRefusedExamCount;
}
public String getNewClustersNotNotifiedRefusedExamPercent() {
	return newClustersNotNotifiedRefusedExamPercent;
}
public void setNewClustersNotNotifiedRefusedExamPercent(
		String newClustersNotNotifiedRefusedExamPercent) {
	this.newClustersNotNotifiedRefusedExamPercent = newClustersNotNotifiedRefusedExamPercent;
}
public String getNewClustersNotNotifiedOOJLabel() {
	return newClustersNotNotifiedOOJLabel;
}
public void setNewClustersNotNotifiedOOJLabel(
		String newClustersNotNotifiedOOJLabel) {
	this.newClustersNotNotifiedOOJLabel = newClustersNotNotifiedOOJLabel;
}
public String getNewClustersNotNotifiedOOJCount() {
	return newClustersNotNotifiedOOJCount;
}
public void setNewClustersNotNotifiedOOJCount(
		String newClustersNotNotifiedOOJCount) {
	this.newClustersNotNotifiedOOJCount = newClustersNotNotifiedOOJCount;
}
public String getNewClustersNotNotifiedOOJPercent() {
	return newClustersNotNotifiedOOJPercent;
}
public void setNewClustersNotNotifiedOOJPercent(
		String newClustersNotNotifiedOOJPercent) {
	this.newClustersNotNotifiedOOJPercent = newClustersNotNotifiedOOJPercent;
}
public String getNewClustersNotNotifiedOtherLabel() {
	return newClustersNotNotifiedOtherLabel;
}
public void setNewClustersNotNotifiedOtherLabel(
		String newClustersNotNotifiedOtherLabel) {
	this.newClustersNotNotifiedOtherLabel = newClustersNotNotifiedOtherLabel;
}
public String getNewClustersNotNotifiedOtherCount() {
	return newClustersNotNotifiedOtherCount;
}
public void setNewClustersNotNotifiedOtherCount(
		String newClustersNotNotifiedOtherCount) {
	this.newClustersNotNotifiedOtherCount = newClustersNotNotifiedOtherCount;
}
public String getNewClustersNotNotifiedOtherPercent() {
	return newClustersNotNotifiedOtherPercent;
}
public void setNewClustersNotNotifiedOtherPercent(
		String newClustersNotNotifiedOtherPercent) {
	this.newClustersNotNotifiedOtherPercent = newClustersNotNotifiedOtherPercent;
}
public String getNewClustersHivPrevPosLabel() {
	return newClustersHivPrevPosLabel;
}
public void setNewClustersHivPrevPosLabel(String newClustersHivPrevPosLabel) {
	this.newClustersHivPrevPosLabel = newClustersHivPrevPosLabel;
}
public String getNewClustersHivPrevPosCount() {
	return newClustersHivPrevPosCount;
}
public void setNewClustersHivPrevPosCount(
		String newClustersHivPrevPosCount) {
	this.newClustersHivPrevPosCount = newClustersHivPrevPosCount;
}
public String getNewClustersHivPrevPosPercent() {
	return newClustersHivPrevPosPercent;
}
public void setNewClustersHivPrevPosPercent(
		String newClustersHivPrevPosPercent) {
	this.newClustersHivPrevPosPercent = newClustersHivPrevPosPercent;
}
public String getNewClustersHivOpenLabel() {
	return newClustersHivOpenLabel;
}
public void setNewClustersHivOpenLabel(String newClustersHivOpenLabel) {
	this.newClustersHivOpenLabel = newClustersHivOpenLabel;
}
public String getNewClustersHivOpenCount() {
	return newClustersHivOpenCount;
}
public void setNewClustersHivOpenCount(String newClustersHivOpenCount) {
	this.newClustersHivOpenCount = newClustersHivOpenCount;
}
public String getNewClustersHivOpenPercent() {
	return newClustersHivOpenPercent;
}
public void setNewClustersHivOpenPercent(String newClustersHivOpenPercent) {
	this.newClustersHivOpenPercent = newClustersHivOpenPercent;
}
public String getSeNewPartnersExaminedLabel() {
	return seNewPartnersExaminedLabel;
}
public void setSeNewPartnersExaminedLabel(String seNewPartnersExaminedLabel) {
	this.seNewPartnersExaminedLabel = seNewPartnersExaminedLabel;
}
public String getSeNewPartnersExaminedCount() {
	return seNewPartnersExaminedCount;
}
public void setSeNewPartnersExaminedCount(String seNewPartnersExaminedCount) {
	this.seNewPartnersExaminedCount = seNewPartnersExaminedCount;
}
public String getSeNewPartnersExaminedWI3dLabel() {
	return seNewPartnersExaminedWI3dLabel;
}
public void setSeNewPartnersExaminedWI3dLabel(
		String seNewPartnersExaminedWI3dLabel) {
	this.seNewPartnersExaminedWI3dLabel = seNewPartnersExaminedWI3dLabel;
}
public String getSeNewPartnersExaminedWI3dCount() {
	return seNewPartnersExaminedWI3dCount;
}
public void setSeNewPartnersExaminedWI3dCount(
		String seNewPartnersExaminedWI3dCount) {
	this.seNewPartnersExaminedWI3dCount = seNewPartnersExaminedWI3dCount;
}
public String getSeNewPartnersExaminedWI3dPercent() {
	return seNewPartnersExaminedWI3dPercent;
}
public void setSeNewPartnersExaminedWI3dPercent(
		String seNewPartnersExaminedWI3dPercent) {
	this.seNewPartnersExaminedWI3dPercent = seNewPartnersExaminedWI3dPercent;
}
public String getSeNewPartnersExaminedWI5dLabel() {
	return seNewPartnersExaminedWI5dLabel;
}
public void setSeNewPartnersExaminedWI5dLabel(
		String seNewPartnersExaminedWI5dLabel) {
	this.seNewPartnersExaminedWI5dLabel = seNewPartnersExaminedWI5dLabel;
}
public String getSeNewPartnersExaminedWI5dCount() {
	return seNewPartnersExaminedWI5dCount;
}
public void setSeNewPartnersExaminedWI5dCount(
		String seNewPartnersExaminedWI5dCount) {
	this.seNewPartnersExaminedWI5dCount = seNewPartnersExaminedWI5dCount;
}
public String getSeNewPartnersExaminedWI5dPercent() {
	return seNewPartnersExaminedWI5dPercent;
}
public void setSeNewPartnersExaminedWI5dPercent(
		String seNewPartnersExaminedWI5dPercent) {
	this.seNewPartnersExaminedWI5dPercent = seNewPartnersExaminedWI5dPercent;
}
public String getSeNewPartnersExaminedWI7dLabel() {
	return seNewPartnersExaminedWI7dLabel;
}
public void setSeNewPartnersExaminedWI7dLabel(
		String seNewPartnersExaminedWI7dLabel) {
	this.seNewPartnersExaminedWI7dLabel = seNewPartnersExaminedWI7dLabel;
}
public String getSeNewPartnersExaminedWI7dCount() {
	return seNewPartnersExaminedWI7dCount;
}
public void setSeNewPartnersExaminedWI7dCount(
		String seNewPartnersExaminedWI7dCount) {
	this.seNewPartnersExaminedWI7dCount = seNewPartnersExaminedWI7dCount;
}
public String getSeNewPartnersExaminedWI7dPercent() {
	return seNewPartnersExaminedWI7dPercent;
}
public void setSeNewPartnersExaminedWI7dPercent(
		String seNewPartnersExaminedWI7dPercent) {
	this.seNewPartnersExaminedWI7dPercent = seNewPartnersExaminedWI7dPercent;
}
public String getSeNewPartnersExaminedWI14dLabel() {
	return seNewPartnersExaminedWI14dLabel;
}
public void setSeNewPartnersExaminedWI14dLabel(
		String seNewPartnersExaminedWI14dLabel) {
	this.seNewPartnersExaminedWI14dLabel = seNewPartnersExaminedWI14dLabel;
}
public String getSeNewPartnersExaminedWI14dCount() {
	return seNewPartnersExaminedWI14dCount;
}
public void setSeNewPartnersExaminedWI14dCount(
		String seNewPartnersExaminedWI14dCount) {
	this.seNewPartnersExaminedWI14dCount = seNewPartnersExaminedWI14dCount;
}
public String getSeNewPartnersExaminedWI14dPercent() {
	return seNewPartnersExaminedWI14dPercent;
}
public void setSeNewPartnersExaminedWI14dPercent(
		String seNewPartnersExaminedWI14dPercent) {
	this.seNewPartnersExaminedWI14dPercent = seNewPartnersExaminedWI14dPercent;
}
public String getSeNewClustersExaminedLabel() {
	return seNewClustersExaminedLabel;
}
public void setSeNewClustersExaminedLabel(String seNewClustersExaminedLabel) {
	this.seNewClustersExaminedLabel = seNewClustersExaminedLabel;
}
public String getSeNewClustersExaminedCount() {
	return seNewClustersExaminedCount;
}
public void setSeNewClustersExaminedCount(String seNewClustersExaminedCount) {
	this.seNewClustersExaminedCount = seNewClustersExaminedCount;
}
public String getSeNewClustersExaminedWI3dLabel() {
	return seNewClustersExaminedWI3dLabel;
}
public void setSeNewClustersExaminedWI3dLabel(
		String seNewClustersExaminedWI3dLabel) {
	this.seNewClustersExaminedWI3dLabel = seNewClustersExaminedWI3dLabel;
}
public String getSeNewClustersExaminedWI3dCount() {
	return seNewClustersExaminedWI3dCount;
}
public void setSeNewClustersExaminedWI3dCount(
		String seNewClustersExaminedWI3dCount) {
	this.seNewClustersExaminedWI3dCount = seNewClustersExaminedWI3dCount;
}
public String getSeNewClustersExaminedWI3dPercent() {
	return seNewClustersExaminedWI3dPercent;
}
public void setSeNewClustersExaminedWI3dPercent(
		String seNewClustersExaminedWI3dPercent) {
	this.seNewClustersExaminedWI3dPercent = seNewClustersExaminedWI3dPercent;
}
public String getSeNewClustersExaminedWI5dLabel() {
	return seNewClustersExaminedWI5dLabel;
}
public void setSeNewClustersExaminedWI5dLabel(
		String seNewClustersExaminedWI5dLabel) {
	this.seNewClustersExaminedWI5dLabel = seNewClustersExaminedWI5dLabel;
}
public String getSeNewClustersExaminedWI5dCount() {
	return seNewClustersExaminedWI5dCount;
}
public void setSeNewClustersExaminedWI5dCount(
		String seNewClustersExaminedWI5dCount) {
	this.seNewClustersExaminedWI5dCount = seNewClustersExaminedWI5dCount;
}
public String getSeNewClustersExaminedWI5dPercent() {
	return seNewClustersExaminedWI5dPercent;
}
public void setSeNewClustersExaminedWI5dPercent(
		String seNewClustersExaminedWI5dPercent) {
	this.seNewClustersExaminedWI5dPercent = seNewClustersExaminedWI5dPercent;
}
public String getSeNewClustersExaminedWI7dLabel() {
	return seNewClustersExaminedWI7dLabel;
}
public void setSeNewClustersExaminedWI7dLabel(
		String seNewClustersExaminedWI7dLabel) {
	this.seNewClustersExaminedWI7dLabel = seNewClustersExaminedWI7dLabel;
}
public String getSeNewClustersExaminedWI7dCount() {
	return seNewClustersExaminedWI7dCount;
}
public void setSeNewClustersExaminedWI7dCount(
		String seNewClustersExaminedWI7dCount) {
	this.seNewClustersExaminedWI7dCount = seNewClustersExaminedWI7dCount;
}
public String getSeNewClustersExaminedWI7dPercent() {
	return seNewClustersExaminedWI7dPercent;
}
public void setSeNewClustersExaminedWI7dPercent(
		String seNewClustersExaminedWI7dPercent) {
	this.seNewClustersExaminedWI7dPercent = seNewClustersExaminedWI7dPercent;
}
public String getSeNewClustersExaminedWI14dLabel() {
	return seNewClustersExaminedWI14dLabel;
}
public void setSeNewClustersExaminedWI14dLabel(
		String seNewClustersExaminedWI14dLabel) {
	this.seNewClustersExaminedWI14dLabel = seNewClustersExaminedWI14dLabel;
}
public String getSeNewClustersExaminedWI14dCount() {
	return seNewClustersExaminedWI14dCount;
}
public void setSeNewClustersExaminedWI14dCount(
		String seNewClustersExaminedWI14dCount) {
	this.seNewClustersExaminedWI14dCount = seNewClustersExaminedWI14dCount;
}
public String getSeNewClustersExaminedWI14dPercent() {
	return seNewClustersExaminedWI14dPercent;
}
public void setSeNewClustersExaminedWI14dPercent(
		String seNewClustersExaminedWI14dPercent) {
	this.seNewClustersExaminedWI14dPercent = seNewClustersExaminedWI14dPercent;
}
public String getSeNewPartnersNotifiedLabel() {
	return seNewPartnersNotifiedLabel;
}
public void setSeNewPartnersNotifiedLabel(String seNewPartnersNotifiedLabel) {
	this.seNewPartnersNotifiedLabel = seNewPartnersNotifiedLabel;
}
public String getSeNewPartnersNotifiedCount() {
	return seNewPartnersNotifiedCount;
}
public void setSeNewPartnersNotifiedCount(String seNewPartnersNotifiedCount) {
	this.seNewPartnersNotifiedCount = seNewPartnersNotifiedCount;
}
public String getSeNewPartnersNotifiedWI3dLabel() {
	return seNewPartnersNotifiedWI3dLabel;
}
public void setSeNewPartnersNotifiedWI3dLabel(
		String seNewPartnersNotifiedWI3dLabel) {
	this.seNewPartnersNotifiedWI3dLabel = seNewPartnersNotifiedWI3dLabel;
}
public String getSeNewPartnersNotifiedWI3dCount() {
	return seNewPartnersNotifiedWI3dCount;
}
public void setSeNewPartnersNotifiedWI3dCount(
		String seNewPartnersNotifiedWI3dCount) {
	this.seNewPartnersNotifiedWI3dCount = seNewPartnersNotifiedWI3dCount;
}
public String getSeNewPartnersNotifiedWI3dPercent() {
	return seNewPartnersNotifiedWI3dPercent;
}
public void setSeNewPartnersNotifiedWI3dPercent(
		String seNewPartnersNotifiedWI3dPercent) {
	this.seNewPartnersNotifiedWI3dPercent = seNewPartnersNotifiedWI3dPercent;
}
public String getSeNewPartnersNotifiedWI5dLabel() {
	return seNewPartnersNotifiedWI5dLabel;
}
public void setSeNewPartnersNotifiedWI5dLabel(
		String seNewPartnersNotifiedWI5dLabel) {
	this.seNewPartnersNotifiedWI5dLabel = seNewPartnersNotifiedWI5dLabel;
}
public String getSeNewPartnersNotifiedWI5dCount() {
	return seNewPartnersNotifiedWI5dCount;
}
public void setSeNewPartnersNotifiedWI5dCount(
		String seNewPartnersNotifiedWI5dCount) {
	this.seNewPartnersNotifiedWI5dCount = seNewPartnersNotifiedWI5dCount;
}
public String getSeNewPartnersNotifiedWI5dPercent() {
	return seNewPartnersNotifiedWI5dPercent;
}
public void setSeNewPartnersNotifiedWI5dPercent(
		String seNewPartnersNotifiedWI5dPercent) {
	this.seNewPartnersNotifiedWI5dPercent = seNewPartnersNotifiedWI5dPercent;
}
public String getSeNewPartnersNotifiedWI7dLabel() {
	return seNewPartnersNotifiedWI7dLabel;
}
public void setSeNewPartnersNotifiedWI7dLabel(
		String seNewPartnersNotifiedWI7dLabel) {
	this.seNewPartnersNotifiedWI7dLabel = seNewPartnersNotifiedWI7dLabel;
}
public String getSeNewPartnersNotifiedWI7dCount() {
	return seNewPartnersNotifiedWI7dCount;
}
public void setSeNewPartnersNotifiedWI7dCount(
		String seNewPartnersNotifiedWI7dCount) {
	this.seNewPartnersNotifiedWI7dCount = seNewPartnersNotifiedWI7dCount;
}
public String getSeNewPartnersNotifiedWI7dPercent() {
	return seNewPartnersNotifiedWI7dPercent;
}
public void setSeNewPartnersNotifiedWI7dPercent(
		String seNewPartnersNotifiedWI7dPercent) {
	this.seNewPartnersNotifiedWI7dPercent = seNewPartnersNotifiedWI7dPercent;
}
public String getSeNewPartnersNotifiedWI14dLabel() {
	return seNewPartnersNotifiedWI14dLabel;
}
public void setSeNewPartnersNotifiedWI14dLabel(
		String seNewPartnersNotifiedWI14dLabel) {
	this.seNewPartnersNotifiedWI14dLabel = seNewPartnersNotifiedWI14dLabel;
}
public String getSeNewPartnersNotifiedWI14dCount() {
	return seNewPartnersNotifiedWI14dCount;
}
public void setSeNewPartnersNotifiedWI14dCount(
		String seNewPartnersNotifiedWI14dCount) {
	this.seNewPartnersNotifiedWI14dCount = seNewPartnersNotifiedWI14dCount;
}
public String getSeNewPartnersNotifiedWI14dPercent() {
	return seNewPartnersNotifiedWI14dPercent;
}
public void setSeNewPartnersNotifiedWI14dPercent(
		String seNewPartnersNotifiedWI14dPercent) {
	this.seNewPartnersNotifiedWI14dPercent = seNewPartnersNotifiedWI14dPercent;
}
public String getSeNewClustersNotifiedLabel() {
	return seNewClustersNotifiedLabel;
}
public void setSeNewClustersNotifiedLabel(String seNewClustersNotifiedLabel) {
	this.seNewClustersNotifiedLabel = seNewClustersNotifiedLabel;
}
public String getSeNewClustersNotifiedCount() {
	return seNewClustersNotifiedCount;
}
public void setSeNewClustersNotifiedCount(String seNewClustersNotifiedCount) {
	this.seNewClustersNotifiedCount = seNewClustersNotifiedCount;
}
public String getSeNewClustersNotifiedWI3dLabel() {
	return seNewClustersNotifiedWI3dLabel;
}
public void setSeNewClustersNotifiedWI3dLabel(
		String seNewClustersNotifiedWI3dLabel) {
	this.seNewClustersNotifiedWI3dLabel = seNewClustersNotifiedWI3dLabel;
}
public String getSeNewClustersNotifiedWI3dCount() {
	return seNewClustersNotifiedWI3dCount;
}
public void setSeNewClustersNotifiedWI3dCount(
		String seNewClustersNotifiedWI3dCount) {
	this.seNewClustersNotifiedWI3dCount = seNewClustersNotifiedWI3dCount;
}
public String getSeNewClustersNotifiedWI3dPercent() {
	return seNewClustersNotifiedWI3dPercent;
}
public void setSeNewClustersNotifiedWI3dPercent(
		String seNewClustersNotifiedWI3dPercent) {
	this.seNewClustersNotifiedWI3dPercent = seNewClustersNotifiedWI3dPercent;
}
public String getSeNewClustersNotifiedWI5dLabel() {
	return seNewClustersNotifiedWI5dLabel;
}
public void setSeNewClustersNotifiedWI5dLabel(
		String seNewClustersNotifiedWI5dLabel) {
	this.seNewClustersNotifiedWI5dLabel = seNewClustersNotifiedWI5dLabel;
}
public String getSeNewClustersNotifiedWI5dCount() {
	return seNewClustersNotifiedWI5dCount;
}
public void setSeNewClustersNotifiedWI5dCount(
		String seNewClustersNotifiedWI5dCount) {
	this.seNewClustersNotifiedWI5dCount = seNewClustersNotifiedWI5dCount;
}
public String getSeNewClustersNotifiedWI5dPercent() {
	return seNewClustersNotifiedWI5dPercent;
}
public void setSeNewClustersNotifiedWI5dPercent(
		String seNewClustersNotifiedWI5dPercent) {
	this.seNewClustersNotifiedWI5dPercent = seNewClustersNotifiedWI5dPercent;
}
public String getSeNewClustersNotifiedWI7dLabel() {
	return seNewClustersNotifiedWI7dLabel;
}
public void setSeNewClustersNotifiedWI7dLabel(
		String seNewClustersNotifiedWI7dLabel) {
	this.seNewClustersNotifiedWI7dLabel = seNewClustersNotifiedWI7dLabel;
}
public String getSeNewClustersNotifiedWI7dCount() {
	return seNewClustersNotifiedWI7dCount;
}
public void setSeNewClustersNotifiedWI7dCount(
		String seNewClustersNotifiedWI7dCount) {
	this.seNewClustersNotifiedWI7dCount = seNewClustersNotifiedWI7dCount;
}
public String getSeNewClustersNotifiedWI7dPercent() {
	return seNewClustersNotifiedWI7dPercent;
}
public void setSeNewClustersNotifiedWI7dPercent(
		String seNewClustersNotifiedWI7dPercent) {
	this.seNewClustersNotifiedWI7dPercent = seNewClustersNotifiedWI7dPercent;
}
public String getSeNewClustersNotifiedWI14dLabel() {
	return seNewClustersNotifiedWI14dLabel;
}
public void setSeNewClustersNotifiedWI14dLabel(
		String seNewClustersNotifiedWI14dLabel) {
	this.seNewClustersNotifiedWI14dLabel = seNewClustersNotifiedWI14dLabel;
}
public String getSeNewClustersNotifiedWI14dCount() {
	return seNewClustersNotifiedWI14dCount;
}
public void setSeNewClustersNotifiedWI14dCount(
		String seNewClustersNotifiedWI14dCount) {
	this.seNewClustersNotifiedWI14dCount = seNewClustersNotifiedWI14dCount;
}
public String getSeNewClustersNotifiedWI14dPercent() {
	return seNewClustersNotifiedWI14dPercent;
}
public void setSeNewClustersNotifiedWI14dPercent(
		String seNewClustersNotifiedWI14dPercent) {
	this.seNewClustersNotifiedWI14dPercent = seNewClustersNotifiedWI14dPercent;
}

public String getCaseAssignmentsAndOutcomesSectionName() {
	return caseAssignmentsAndOutcomesSectionName;
}
public void setCaseAssignmentsAndOutcomesSectionName(
		String caseAssignmentsAndOutcomesSectionName) {
	this.caseAssignmentsAndOutcomesSectionName = caseAssignmentsAndOutcomesSectionName;
}
public String getPartnersAndClustersInitiatedSectionName() {
	return partnersAndClustersInitiatedSectionName;
}
public void setPartnersAndClustersInitiatedSectionName(
		String partnersAndClustersInitiatedSectionName) {
	this.partnersAndClustersInitiatedSectionName = partnersAndClustersInitiatedSectionName;
}
public String getDispositionsPartnersAndClustersSectionName() {
	return dispositionsPartnersAndClustersSectionName;
}
public void setDispositionsPartnersAndClustersSectionName(
		String dispositionsPartnersAndClustersSectionName) {
	this.dispositionsPartnersAndClustersSectionName = dispositionsPartnersAndClustersSectionName;
}
public String getSpeedOfExamSectionName() {
	return speedOfExamSectionName;
}
public void setSpeedOfExamSectionName(
		String speedOfExamSectionName) {
	this.speedOfExamSectionName = speedOfExamSectionName;
}
public String getSpeedOfNotificationSectionName() {
	return speedOfNotificationSectionName;
}
public void setSpeedOfNotificationSectionName(
		String speedOfrNotificationSectionName) {
	this.speedOfNotificationSectionName = speedOfNotificationSectionName;   
}
public String getNewClustersNoExamPercent() {
	return newClustersNoExamPercent;
}
public void setNewClustersNoExamCount(String newClustersNoExamCount) {
	this.newClustersNoExamCount = newClustersNoExamCount;
}
@Override
public void setItDirty(boolean itDirty) {
}
@Override
public boolean isItDirty() {
	return false;
}
@Override
public void setItNew(boolean itNew) {	
}
@Override
public boolean isItNew() {
	return false;
}
@Override
public void setItDelete(boolean itDelete) {
}
@Override
public boolean isItDelete() {
	return false;
}
@Override
public boolean isEqual(Object objectname1, Object objectname2, Class<?> voClass) {
	return false;
}



}