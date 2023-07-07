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
@Table(name = "NBS_Conversion_Page_Mgmt")
public class NbsConversionPageMgmt {
    @Id
    @Column(name = "NBS_Conversion_Page_Mgmt_uid", nullable = false)
    private Long id;

    @Column(name = "map_name", nullable = false, length = 100)
    private String mapName;

    @Column(name = "from_page_form_cd", nullable = false, length = 50)
    private String fromPageFormCd;

    @Column(name = "to_page_form_cd", nullable = false, length = 50)
    private String toPageFormCd;

    @Column(name = "mapping_status_cd", nullable = false, length = 20)
    private String mappingStatusCd;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Lob
    @Column(name = "xml_Payload", columnDefinition = "TEXT")
    private String xmlPayload;

}
