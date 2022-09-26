package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "NBS_page")
public class NbsPage {
    @Id
    @Column(name = "nbs_page_uid", nullable = false)
    private Long id;

    @Column(name = "wa_template_uid", nullable = false)
    private Long waTemplateUid;

    @Column(name = "form_cd", length = 50)
    private String formCd;

    @Column(name = "desc_txt", length = 2000)
    private String descTxt;

    @Column(name = "jsp_payload")
    private byte[] jspPayload;

    @Column(name = "datamart_nm", length = 21)
    private String datamartNm;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "bus_obj_type", nullable = false, length = 50)
    private String busObjType;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

}