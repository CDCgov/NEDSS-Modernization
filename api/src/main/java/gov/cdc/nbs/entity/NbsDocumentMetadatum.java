package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "NBS_document_metadata")
public class NbsDocumentMetadatum {
    @Id
    @Column(name = "nbs_document_metadata_uid", nullable = false)
    private Long id;

    @Column(name = "xml_schema_location", nullable = false, length = 250)
    private String xmlSchemaLocation;

    @Lob
    @Column(name = "document_view_xsl", nullable = false, columnDefinition = "TEXT")
    private String documentViewXsl;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "doc_type_cd", length = 20)
    private String docTypeCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "xmlbean_factory_class_nm", length = 100)
    private String xmlbeanFactoryClassNm;

    @Column(name = "parser_class_nm", length = 100)
    private String parserClassNm;

    @Lob
    @Column(name = "document_view_cda_xsl", columnDefinition = "TEXT")
    private String documentViewCdaXsl;

    @Column(name = "DOC_TYPE_VERSION_TXT", length = 250)
    private String docTypeVersionTxt;

}