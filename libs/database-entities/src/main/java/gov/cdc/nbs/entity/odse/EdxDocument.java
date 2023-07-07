package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "EDX_Document")
public class EdxDocument {
    @Id
    @Column(name = "EDX_Document_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "act_uid")
    private Act actUid;

    @Lob
    @Column(name = "payload", nullable = false, columnDefinition = "XML")
    private String payload;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "doc_type_cd", nullable = false, length = 20)
    private String docTypeCd;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nbs_document_metadata_uid", nullable = false)
    private NbsDocumentMetadatum nbsDocumentMetadataUid;

    @Column(name = "original_payload")
    private String originalPayload;

    @Column(name = "original_doc_type_cd", length = 20)
    private String originalDocTypeCd;

    @Column(name = "edx_document_parent_uid")
    private Long edxDocumentParentUid;

}
