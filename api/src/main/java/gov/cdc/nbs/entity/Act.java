package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Act {
    @Id
    @Column(name = "act_uid", nullable = false)
    private Long id;

    @Column(name = "class_cd", nullable = false, length = 10)
    private String classCd;

    @Column(name = "mood_cd", nullable = false, length = 10)
    private String moodCd;

}