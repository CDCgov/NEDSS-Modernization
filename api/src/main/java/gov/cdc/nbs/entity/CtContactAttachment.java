package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CT_contact_attachment")
public class CtContactAttachment {
    @Id
    @Column(name = "ct_contact_attachment_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ct_contact_uid", nullable = false)
    private CtContact ctContactUid;

    @Column(name = "desc_txt", length = 2000)
    private String descTxt;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Lob
    @Column(name = "attachment", columnDefinition = "IMAGE")
    private byte[] attachment;

    @Column(name = "file_nm_txt", length = 250)
    private String fileNmTxt;

}