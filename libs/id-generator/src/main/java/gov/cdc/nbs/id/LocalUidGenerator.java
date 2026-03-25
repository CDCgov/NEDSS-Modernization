package gov.cdc.nbs.id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "Local_UID_generator", catalog = "NBS_ODSE")
public class LocalUidGenerator {
  @Id
  @Column(name = "class_name_cd", nullable = false, length = 50)
  private String id;

  @Column(name = "type_cd", nullable = false, length = 10)
  private String typeCd;

  @Column(name = "UID_prefix_cd", length = 10)
  private String uidPrefixCd;

  @Column(name = "UID_suffix_CD", length = 10)
  private String uidSuffixCd;

  @Column(name = "seed_value_nbr", nullable = false)
  private Long seedValueNbr;
}
