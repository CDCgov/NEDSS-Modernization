package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ReportSectionId implements Serializable {
    private static final long serialVersionUID = -6528307349351877438L;
    @Column(name = "report_section_uid", nullable = false)
    private Long reportSectionUid;

    @Column(name = "section_cd", nullable = false, length = 50)
    private String sectionCd;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ReportSectionId entity = (ReportSectionId) o;
        return Objects.equals(this.reportSectionUid, entity.reportSectionUid) &&
                Objects.equals(this.sectionCd, entity.sectionCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportSectionUid, sectionCd);
    }

}
