package gov.cdc.nbs.entity.srte;

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
public class StateModelId implements Serializable {
    private static final long serialVersionUID = -2312303987380804376L;
    @Column(name = "business_trigger_code_set_nm", nullable = false, length = 256)
    private String businessTriggerCodeSetNm;

    @Column(name = "business_trigger_set_seq_num", nullable = false)
    private Short businessTriggerSetSeqNum;

    @Column(name = "business_trigger_code", nullable = false, length = 20)
    private String businessTriggerCode;

    @Column(name = "module_cd", nullable = false, length = 20)
    private String moduleCd;

    @Column(name = "record_status_from_code", nullable = false, length = 20)
    private String recordStatusFromCode;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        StateModelId entity = (StateModelId) o;
        return Objects.equals(this.businessTriggerCodeSetNm, entity.businessTriggerCodeSetNm) &&
                Objects.equals(this.businessTriggerCode, entity.businessTriggerCode) &&
                Objects.equals(this.recordStatusFromCode, entity.recordStatusFromCode) &&
                Objects.equals(this.moduleCd, entity.moduleCd) &&
                Objects.equals(this.businessTriggerSetSeqNum, entity.businessTriggerSetSeqNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessTriggerCodeSetNm, businessTriggerCode, recordStatusFromCode, moduleCd,
                businessTriggerSetSeqNum);
    }

}