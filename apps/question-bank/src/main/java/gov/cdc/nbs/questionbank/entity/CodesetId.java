package gov.cdc.nbs.questionbank.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class CodesetId implements Serializable {

    @Column(name = "class_cd", nullable = false, length = 30)
    private String classCd;

    @Column(name = "code_set_nm", nullable = false, length = 256)
    private String codeSetNm;

}
