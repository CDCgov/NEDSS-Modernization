package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "NBS_document")
public class NbsDocument {
    @Id
    @Column(name = "nbs_document_uid", nullable = false)
    private Long id;

    @Lob
    @Column(name = "doc_payload", nullable = false, columnDefinition = "TEXT")
    private String docPayload;

    @Column(name = "doc_type_cd", nullable = false, length = 20)
    private String docTypeCd;

    @Column(name = "local_id", nullable = false, length = 50)
    private String localId;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "prog_area_cd", length = 20)
    private String progAreaCd;

    @Column(name = "jurisdiction_cd", length = 20)
    private String jurisdictionCd;

    @Column(name = "txt", length = 2000)
    private String txt;

    @Column(name = "program_jurisdiction_oid")
    private Long programJurisdictionOid;

    @Column(name = "shared_ind", nullable = false)
    private Character sharedInd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Column(name = "cd", length = 50)
    private String cd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "doc_purpose_cd", length = 50)
    private String docPurposeCd;

    @Column(name = "doc_status_cd", length = 20)
    private String docStatusCd;

    @Column(name = "cd_desc_txt", length = 250)
    private String cdDescTxt;

    @Column(name = "sending_facility_nm", length = 250)
    private String sendingFacilityNm;

    @Column(name = "nbs_interface_uid", nullable = false)
    private Long nbsInterfaceUid;

    @Column(name = "sending_app_event_id", length = 250)
    private String sendingAppEventId;

    @Column(name = "sending_app_patient_id", length = 50)
    private String sendingAppPatientId;

    @Lob
    @Column(name = "phdc_doc_derived", columnDefinition = "TEXT")
    private String phdcDocDerived;

    @Column(name = "payload_view_ind_cd", length = 20)
    private String payloadViewIndCd;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nbs_document_metadata_uid", nullable = false)
    private NbsDocumentMetadatum nbsDocumentMetadataUid;

    @Column(name = "external_version_ctrl_nbr")
    private Short externalVersionCtrlNbr;

    @Column(name = "processing_decision_txt", length = 1000)
    private String processingDecisionTxt;

    @Column(name = "processing_decision_cd", length = 1000)
    private String processingDecisionCd;

}
