package gov.cdc.nbs.entity.odse;

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
public class ReportId implements Serializable {
    private static final long serialVersionUID = 9151365581914919398L;
    @Column(name = "data_source_uid", nullable = false)
    private Long dataSourceUid;

    @Column(name = "report_uid", nullable = false)
    private Long reportUid;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        ReportId entity = (ReportId) o;
        return Objects.equals(this.dataSourceUid, entity.dataSourceUid) &&
                Objects.equals(this.reportUid, entity.reportUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSourceUid, reportUid);
    }

}