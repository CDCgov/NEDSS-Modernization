package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Export_receiving_facility")
public class ExportReceivingFacility {
    @Id
    @Column(name = "export_receiving_facility_uid", nullable = false)
    private Long id;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false, length = 50)
    private String lastChgUserId;

    @Column(name = "receiving_system_nm", length = 100)
    private String receivingSystemNm;

    @Column(name = "receiving_system_oid", length = 100)
    private String receivingSystemOid;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "message_recipient")
    private String messageRecipient;

    @Column(name = "public_key_ldapAddress")
    private String publicKeyLdapaddress;

    @Column(name = "public_key_ldapBaseDN", length = 50)
    private String publicKeyLdapbasedn;

    @Column(name = "public_key_ldapDN")
    private String publicKeyLdapdn;

    @Column(name = "priority_int")
    private Short priorityInt;

    @Column(name = "encrypt", length = 10)
    private String encrypt;

    @Column(name = "signature", length = 10)
    private String signature;

    @Column(name = "receiving_system_short_nm", nullable = false, length = 10)
    private String receivingSystemShortNm;

    @Column(name = "receiving_system_owner", nullable = false, length = 10)
    private String receivingSystemOwner;

    @Column(name = "receiving_system_desc_txt", length = 2000)
    private String receivingSystemDescTxt;

    @Column(name = "sending_ind_cd", nullable = false)
    private Character sendingIndCd;

    @Column(name = "receiving_ind_cd")
    private Character receivingIndCd;

    @Column(name = "allow_transfer_ind_cd")
    private Character allowTransferIndCd;

    @Column(name = "admin_comment", length = 2000)
    private String adminComment;

    @Column(name = "receiving_system_owner_oid", nullable = false, length = 100)
    private String receivingSystemOwnerOid;

    @Column(name = "jur_derive_ind_cd", length = 10)
    private String jurDeriveIndCd;

    @Column(name = "type_cd", length = 20)
    private String typeCd;

}