package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ClinicalDocumentHistId implements Serializable {
    private static final long serialVersionUID = -6309785901704601139L;
    @Column(name = "clinical_document_uid", nullable = false)
    private Long clinicalDocumentUid;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ClinicalDocumentHistId entity = (ClinicalDocumentHistId) o;
        return Objects.equals(this.clinicalDocumentUid, entity.clinicalDocumentUid) &&
                Objects.equals(this.versionCtrlNbr, entity.versionCtrlNbr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clinicalDocumentUid, versionCtrlNbr);
    }

}