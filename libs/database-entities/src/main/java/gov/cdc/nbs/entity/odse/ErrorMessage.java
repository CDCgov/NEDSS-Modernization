package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Error_message")
public class ErrorMessage {
    @Id
    @Column(name = "error_message_uid", nullable = false)
    private Long id;

    @Column(name = "error_cd", nullable = false, length = 20)
    private String errorCd;

    @Column(name = "error_desc_txt", length = 150)
    private String errorDescTxt;

}
