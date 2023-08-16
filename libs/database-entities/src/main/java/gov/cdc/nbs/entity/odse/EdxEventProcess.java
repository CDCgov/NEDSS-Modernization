package gov.cdc.nbs.entity.odse;

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
@Table(name = "EDX_event_process")
public class EdxEventProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edx_event_process_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nbs_document_uid")
    private NbsDocument nbsDocumentUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nbs_event_uid", nullable = false)
    private Act nbsEventUid;

    @Column(name = "source_event_id", length = 250)
    private String sourceEventId;

    @Column(name = "doc_event_type_cd", nullable = false, length = 50)
    private String docEventTypeCd;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "parsed_ind")
    private Character parsedInd;

    @Column(name = "edx_document_uid")
    private Long edxDocumentUid;

}
