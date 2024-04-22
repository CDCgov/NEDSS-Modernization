package gov.cdc.nbs.deduplication.similar;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.deduplication.model.GroupEntry;
import gov.cdc.nbs.deduplication.response.MatchResponse;
import gov.cdc.nbs.deduplication.response.MatchResponse.MatchGroup;

@Component
public class SimilarMatchService {
  private static final String FETCH_GROUPS =
      """
                  DECLARE @v_person_uid  BIGINT,
                  @p_count       BIGINT,
                  @v_asofdate    DATETIME,
                  @v1_person_uid BIGINT,
                  @v1_asofdate   DATETIME,
                  @v_seed        BIGINT,
                  @v_pcount      INT,
                  @group_time    DATETIME;
          DECLARE @result TABLE
            (
               person_uid INT
            );
          DECLARE @groups TABLE
            (
               person_uid INT,
               group_nbr  BIGINT
            );

          SET @group_time = Getdate();

          DECLARE c_personuidprov CURSOR FOR
            SELECT pn.person_uid,
                   pn.as_of_date
            FROM   person_name pn WITH(nolock)
                   INNER JOIN person p WITH(nolock)
                           ON pn.person_uid = p.person_uid
                   INNER JOIN person_name pn2 WITH(nolock)
                           ON pn.person_uid = pn2.person_uid
                              AND pn.as_of_date = pn2.as_of_date
            WHERE ( p.person_uid = p.person_parent_uid )
                  AND ( p.dedup_match_ind IS NULL )
                  AND ( p.group_nbr IS NULL )
                  AND ( p.record_status_cd = 'ACTIVE' )
                  AND p.cd = 'PAT'
                  /* begin added 20090130 HZ */
                  AND ( p.cd_desc_txt IS NULL
                         OR p.cd_desc_txt <> 'Observation Participant' )
                  /* end added 20090130 HZ */
                  AND ( pn.nm_use_cd = 'L' )
                  AND ( pn.record_status_cd = 'ACTIVE' )
                  AND ( pn.last_nm IS NOT NULL )
                  AND ( pn2.nm_use_cd = 'L' )
                  AND ( pn2.record_status_cd = 'ACTIVE' )
                  AND ( pn2.last_nm IS NOT NULL )
                  /*Added 6.0.10*/
                  AND p.birth_time IS NOT NULL
                  AND ( pn.as_of_date = (SELECT Max(pn3.as_of_date)
                                         FROM   person_name pn3
                                         WHERE  pn.person_uid = pn3.person_uid) );

          BEGIN
              SELECT @v_seed = seed_value_nbr
              FROM   local_uid_generator
              WHERE  class_name_cd = 'PERSON_GROUP';

              SET @p_count = 0;

              OPEN c_personuidprov;

              FETCH next FROM c_personuidprov INTO @v_person_uid, @v_asofdate;

              WHILE @@FETCH_STATUS = 0
                BEGIN
                    SET @v_pcount = 0;

                    INSERT INTO @result
                                (person_uid)
                    SELECT p.person_uid
                    FROM   person p WITH(nolock),
                           person p1 WITH(nolock),
                           person_name pn WITH(nolock),
                           person_name pn1 WITH(nolock)
                    WHERE  p.person_uid = p.person_parent_uid
                           AND p1.person_uid = p1.person_parent_uid
                           AND pn.nm_use_cd = 'L'
                           AND pn1.nm_use_cd = 'L'
                           AND pn.record_status_cd = 'ACTIVE'
                           AND pn1.record_status_cd = 'ACTIVE'
                           AND p.record_status_cd = 'ACTIVE'
                           AND p1.record_status_cd = 'ACTIVE'
                           AND p.cd = 'PAT'
                           AND p1.cd = 'PAT'
                           /* begin added 20090130 HZ */
                           AND ( p.cd_desc_txt IS NULL
                                  OR p.cd_desc_txt <> 'Observation Participant' )
                           AND ( p1.cd_desc_txt IS NULL
                                  OR p1.cd_desc_txt <> 'Observation Participant' )
                           /* end added 20090130 HZ */
                           AND p.person_uid = pn.person_uid
                           AND p1.person_uid = pn1.person_uid
                           --and p.curr_sex_cd = p1.curr_sex_cd
                           AND ( ( p.curr_sex_cd = p1.curr_sex_cd )
                                  OR ( p.curr_sex_cd IS NULL
                                        OR p1.curr_sex_cd IS NULL ) )
                           AND pn.last_nm_sndx = pn1.last_nm_sndx
                           AND ( ( pn.first_nm_sndx = pn1.first_nm_sndx )
                                  OR ( pn.first_nm_sndx IS NULL
                                       AND pn1.first_nm_sndx IS NULL ) )
                           AND ( ( pn.last_nm2_sndx = pn1.last_nm2_sndx )
                                  OR ( pn.last_nm2_sndx IS NULL
                                        OR pn1.last_nm2_sndx IS NULL ) )
                           AND ( ( pn.middle_nm = pn1.middle_nm )
                                  OR ( pn.middle_nm IS NULL
                                        OR pn1.middle_nm IS NULL ) )
                           AND ( ( p.birth_gender_cd = p1.birth_gender_cd )
                                  OR ( p.birth_gender_cd IS NULL
                                        OR p1.birth_gender_cd IS NULL ) )
                           /*Updated 6.0.10*/
                           AND ( p.birth_time IS NOT NULL
                                 AND p1.birth_time IS NOT NULL
                                 AND p.birth_time = p1.birth_time )
                           AND ( p.curr_sex_cd != 'U'
                                  OR p.curr_sex_cd IS NULL )
                           AND ( p1.curr_sex_cd != 'U'
                                  OR p1.curr_sex_cd IS NULL )
                           AND ( p.birth_gender_cd != 'U'
                                  OR p.birth_gender_cd IS NULL )
                           AND ( p1.birth_gender_cd != 'U'
                                  OR p1.birth_gender_cd IS NULL )
                           AND p.group_nbr IS NULL
                           AND p1.group_nbr IS NULL
                           AND pn1.person_uid = @v_person_uid
                           AND pn1.as_of_date = @v_asofdate
                           AND ( p.dedup_match_ind != 'R'
                                  OR p.dedup_match_ind IS NULL )
                           -- To skip those records that were removed from merge
                           AND ( p1.dedup_match_ind != 'R'
                                  OR p1.dedup_match_ind IS NULL )
                    -- To skip those records that were removed from merge
                    GROUP  BY p.person_uid
                    UNION
                    SELECT @v_person_uid AS 'person_uid';

                    SET @v_pcount = (SELECT Count(DISTINCT person_uid)
                                     FROM   @result);

                    IF @v_pcount > 1
                      BEGIN
                          SELECT @v_seed = @v_seed + 1;

                          INSERT INTO @groups
                                      (person_uid,
                                       group_nbr)
                          SELECT person_uid,
                                 @v_seed
                          FROM   @result;

                          UPDATE person
                          SET    dedup_match_ind = NULL
                          WHERE  person_uid IN (SELECT person_uid
                                                FROM   @result);

                          SET @p_count = @p_count + 1;
                      END;
                    ELSE
                      BEGIN
                          UPDATE person
                          SET    dedup_match_ind = NULL
                          WHERE  person_uid IN (SELECT person_uid
                                                FROM   @result);
                      END;

                    DELETE FROM @result;

                    FETCH next FROM c_personuidprov INTO @v_person_uid, @v_asofdate;
                END;

              CLOSE c_personuidprov;

              DEALLOCATE c_personuidprov;

              UPDATE local_uid_generator
              SET    seed_value_nbr = 0
              WHERE  class_name_cd = 'PERSON_GROUP';

              SELECT *
              FROM   @groups;
          END;

          RETURN;
                          """;

  private final JdbcTemplate template;

  public SimilarMatchService(final JdbcTemplate template) {
    this.template = template;
  }

  public MatchResponse match() {
    long start = System.currentTimeMillis();
    List<GroupEntry> results = template.query(FETCH_GROUPS, toEntry());
    long end = System.currentTimeMillis();
    var grouped = results.stream()
        .collect(Collectors.groupingBy(GroupEntry::group))
        .entrySet()
        .stream()
        .map(e -> new MatchGroup(
            e.getValue()
                .stream()
                .map(GroupEntry::personUid)
                .collect(Collectors.toList())))
        .collect(Collectors.toList());
    return new MatchResponse(grouped, grouped.size(), end - start);
  }

  private RowMapper<GroupEntry> toEntry() {
    return new RowMapper<GroupEntry>() {
      @Override
      public GroupEntry mapRow(final ResultSet resultSet, final int row) throws SQLException {
        return new GroupEntry(resultSet.getString(1), resultSet.getInt(2));
      }
    };
  }
}
