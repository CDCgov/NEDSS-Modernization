package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Geocoding_Result")
public class GeocodingResult {
    @Id
    @Column(name = "geocoding_result_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "postal_locator_uid", nullable = false)
    private PostalLocator postalLocatorUid;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "firm_name", length = 50)
    private String firmName;

    @Column(name = "street_addr1", length = 100)
    private String streetAddr1;

    @Column(name = "street_addr2", length = 100)
    private String streetAddr2;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "state", length = 2)
    private String state;

    @Column(name = "zip_cd", length = 10)
    private String zipCd;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "cnty_cd", length = 10)
    private String cntyCd;

    @Column(name = "house_number", length = 20)
    private String houseNumber;

    @Column(name = "prefix_direction", length = 10)
    private String prefixDirection;

    @Column(name = "street_name", length = 50)
    private String streetName;

    @Column(name = "street_type", length = 10)
    private String streetType;

    @Column(name = "postfix_direction", length = 10)
    private String postfixDirection;

    @Column(name = "unit_number", length = 20)
    private String unitNumber;

    @Column(name = "unit_type", length = 10)
    private String unitType;

    @Column(name = "zip5_cd", length = 10)
    private String zip5Cd;

    @Column(name = "zip4_cd", length = 10)
    private String zip4Cd;

    @Column(name = "longitude")
    private Integer longitude;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "census_block_id", length = 20)
    private String censusBlockId;

    @Column(name = "segment_id", length = 20)
    private String segmentId;

    @Column(name = "data_type", length = 1)
    private String dataType;

    @Column(name = "location_quality_cd", length = 10)
    private String locationQualityCd;

    @Column(name = "match_cd", length = 10)
    private String matchCd;

    @Column(name = "score", precision = 11, scale = 2)
    private BigDecimal score;

    @Column(name = "result_type", nullable = false, length = 1)
    private String resultType;

    @Column(name = "num_matches", columnDefinition = "tinyint not null")
    private Short numMatches;

    @Column(name = "next_score_diff", precision = 11, scale = 2)
    private BigDecimal nextScoreDiff;

    @Column(name = "next_score_count", columnDefinition = "tinyint")
    private Short nextScoreCount;

    @Column(name = "entity_uid")
    private Long entityUid;

    @Column(name = "entity_class_cd", length = 10)
    private String entityClassCd;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

}