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
@Table(name = "NBS_attachment")
public class NbsAttachment {
    @Id
    @Column(name = "nbs_attachment_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attachment_parent_uid", nullable = false)
    private Act attachmentParentUid;

    @Column(name = "desc_txt", length = 2000)
    private String descTxt;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "attachment", columnDefinition = "IMAGE")
    private byte[] attachment;

    @Column(name = "file_nm_txt", length = 250)
    private String fileNmTxt;

    @Column(name = "type_cd", nullable = false, length = 20)
    private String typeCd;

}
