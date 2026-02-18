package gov.cdc.nbs.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Auth_prog_area_admin", catalog = "NBS_ODSE")
public class AuthProgAreaAdmin {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "auth_prog_area_admin_uid", nullable = false)
  private Long id;

  @Column(name = "prog_area_cd", length = 100)
  private String progAreaCd;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "auth_user_uid", nullable = false)
  private AuthUser authUserUid;

  @Column(name = "Auth_user_ind", nullable = false)
  private Character authUserInd;

  @Embedded private AuthAudit audit;
}
