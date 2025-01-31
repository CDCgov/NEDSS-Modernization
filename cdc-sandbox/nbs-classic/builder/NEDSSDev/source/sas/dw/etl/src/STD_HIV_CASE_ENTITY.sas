PROC SQL;
DROP TABLE NBS_RDB.F_S_STD_HIV_CASE;
CREATE TABLE 
	STD_HIV_CASE_ENTITY_INIT 
AS SELECT 
	* 
FROM 
	NBS_ODS.NBS_ACT_ENTITY 
WHERE 
	ACT_UID 
	IN 
		(
			SELECT 
				PUBLIC_HEALTH_CASE_UID 
			FROM 
				NBS_ODS.PUBLIC_HEALTH_CASE 
			WHERE 
				PUBLIC_HEALTH_CASE_UID 
			IN 
				(
					SELECT 
						PAGE_CASE_UID 
					FROM 
						STD_HIV_CASE_UIDS
				)
		);  
QUIT;
PROC SORT DATA=STD_HIV_CASE_ENTITY_INIT; BY ACT_UID; RUN;

PROC TRANSPOSE DATA=STD_HIV_CASE_ENTITY_INIT OUT=STD_HIV_CASE_ENTITY;
    BY ACT_UID;
	COPY LAST_CHG_TIME ADD_TIME;
	ID TYPE_CD;
	VAR ENTITY_UID;
RUN;
/*PROC SORT DATA=STD_HIV_CASE_ENTITY; BY ACT_UID; RUN;*/
DATA F_S_STD_HIV_CASE;
SET STD_HIV_CASE_ENTITY;
if InvestgrOfPHC ~=. then INVESTIGATOR_uid=InvestgrOfPHC;
if PerAsReporterOfPHC ~=. then PERSON_AS_REPORTER_UID=PerAsReporterOfPHC;
if SubjOfPHC ~=. then patient_uid=SubjOfPHC;
if PhysicianOfPHC~=. then PHYSICIAN_UID=PhysicianOfPHC;
if ACT_UID ~=. then page_case_uid=ACT_UID;

if CASupervisorOfPHC~=. then CA_SupervisorOfPHC=CASupervisorOfPHC;
if ClosureInvestgrOfPHC~=. then Closure_Investgr_Of_PHC=ClosureInvestgrOfPHC;
if DispoFldFupInvestgrOfPHC~=. then Dispo_Fld_FupInvestgr_Of_PHC=DispoFldFupInvestgrOfPHC;
if FldFupInvestgrOfPHC~=. then Fld_Fup_Investgr_Of_PHC=FldFupInvestgrOfPHC;
if FldFupProvOfPHC~=. then Fld_Fup_Prov_Of_PHC=FldFupProvOfPHC;
if FldFupSupervisorOfPHC~=. then Fld_Fup_Supervisor_Of_PHC=FldFupSupervisorOfPHC;
if InitFldFupInvestgrOfPHC~=. then Init_Fld_Fup_Investgr_Of_PHC=InitFldFupInvestgrOfPHC;
if InitFupInvestgrOfPHC~=. then Init_Fup_Investgr_Of_PHC=InitFupInvestgrOfPHC;
if InitInterviewerOfPHC~=. then Init_Interviewer_Of_PHC=InitInterviewerOfPHC;
if InterviewerOfPHC~=. then Interviewer_Of_PHC=InterviewerOfPHC;
if SurvInvestgrOfPHC~=. then Surv_Investgr_Of_PHC=SurvInvestgrOfPHC;

if FldFupFacilityOfPHC~=. then Fld_Fup_Facility_Of_PHC=FldFupFacilityOfPHC;
if HospOfADT ~=. then HOSPITAL_UID=HospOfADT;
if OrgAsHospitalOfDelivery ~=. then Org_As_Hospital_Of_Delivery=OrgAsHospitalOfDelivery;
if PerAsProviderOfDelivery ~=. then Per_As_Provider_Of_Delivery=PerAsProviderOfDelivery;
if PerAsProviderOfOBGYN ~=. then Per_As_Provider_Of_OBGYN=PerAsProviderOfOBGYN;
if PerAsProvideroOfPediatrics ~=. then Per_As_Provider_Of_Pediatrics=PerAsProvideroOfPediatrics;

if OrgAsReporterOfPHC ~=. then ORG_AS_REPORTER_UID=OrgAsReporterOfPHC;
if OrgAsClinicOfPHC ~=. then ORDERING_FACILTY_UID=OrgAsClinicOfPHC;



if SubjOfPHC>0 then 
 do; 
  output F_S_STD_HIV_CASE;
 end;
drop CASupervisorOfPHC;
drop ClosureInvestgrOfPHC;
drop DispoFldFupInvestgrOfPHC;
drop FldFupFacilityOfPHC;
drop FldFupInvestgrOfPHC;
drop FldFupProvOfPHC;
drop FldFupSupervisorOfPHC;
drop InitFldFupInvestgrOfPHC;
drop InitFupInvestgrOfPHC;
drop InitInterviewerOfPHC;
drop InterviewerOfPHC;
drop SurvInvestgrOfPHC;
drop InvestgrOfPHC;
drop OrgAsReporterOfPHC;
drop HospOfADT;
drop OrgAsHospitalOfDelivery;
drop PerAsProviderOfDelivery;
drop PerAsProviderOfOBGYN;
drop PerAsProvideroOfPediatrics;

drop PerAsReporterOfPHC;
drop SubjOfPHC;
drop PhysicianOfPHC;
drop OrgAsClinicOfPHC;
run;
DATA F_S_STD_HIV_CASE;
SET F_S_STD_HIV_CASE;
DROP _NAME_;
DROP _LABEL_;
RUN;


%DBLOAD (F_S_STD_HIV_CASE, F_S_STD_HIV_CASE);

