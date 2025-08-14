package gov.cdc.nbs.questionbank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Auth_user", catalog = "NBS_ODSE")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "auth_user_uid", nullable = false)
  private Long id;

  @Column(name = "user_first_nm", length = 100)
  private String userFirstNm;

  @Column(name = "user_last_nm", length = 100)
  private String userLastNm;

  @Column(name = "nedss_entry_id", nullable = false)
  private Long nedssEntryId;

  public Long id() {
    return id;
  }

  public String userFirstNm() {
    return userFirstNm;
  }

  public String userLastNm() {
    return userLastNm;
  }

  public Long nedssEntryId() {
    return nedssEntryId;
  }

}
