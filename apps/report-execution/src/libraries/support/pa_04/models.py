from src.libraries.contact_record import ASSOCIATE_BASES, PARTNER_BASES, SOCIAL_BASES

INITIAL_INTERVIEW = 'Initial/Original'
REINTERVIEW = 'Re-Interview'

# PA04's "cluster" grouping (PA04_HIV.sas:241-242, :261-262) is social contacts
# and associates plus 'C1- Cohort', which isn't used by PA03/PA05.
CLUSTER_BASES = SOCIAL_BASES | ASSOCIATE_BASES | {'C1- Cohort'}

BUCKETS: tuple[tuple[str, frozenset[str]], ...] = (
    ('Partners', frozenset(PARTNER_BASES)),
    ('Clusters', frozenset(CLUSTER_BASES)),
)

# Each scope re-runs the same Partner/Cluster block with a different IX_TYPE
# filter (PA04_HIV.sas:291-500 for OI, :681-890 for RI, :989-1198 for Combined).
SCOPES: tuple[tuple[str, frozenset[str]], ...] = (
    (INITIAL_INTERVIEW, frozenset({INITIAL_INTERVIEW})),
    (REINTERVIEW, frozenset({REINTERVIEW})),
    ('Combined', frozenset({INITIAL_INTERVIEW, REINTERVIEW})),
)

# The 7 "examined" disposition codes, in Val_Pa..Val_Pf/Val_P7 order
# (PA04_HIV.sas:306-339).
EXAMINED_DISPOSITION_LABELS: tuple[tuple[str, str], ...] = (
    ('1 - Prev. Pos', 'Dispo. 1 - Prev. Pos'),
    ('2 - Prev. Neg, New Pos', 'Dispo. 2 - Prev. Neg, New Pos'),
    ('3 - Prev. Neg, Still Neg', 'Dispo. 3 - Prev. Neg, Still Neg'),
    ('4 - Prev. Neg, No Test', 'Dispo. 4 - Prev. Neg, No Test'),
    ('5 - No Prev Test, New Pos', 'Dispo. 5 - No Prev Test, New Pos'),
    ('6 - No Prev Test, New Neg', 'Dispo. 6 - No Prev Test, New Neg'),
    ('7 - No Prev Test, No Test', 'Dispo. 7 - No Prev Test, No Test'),
)
EXAMINED_DISPOSITIONS = frozenset(code for code, _ in EXAMINED_DISPOSITION_LABELS)

# The 7 "not examined" disposition codes, in Val_Pg/Val_Ph/Val_Pj/Val_Pk/
# Val_Pl/Val_Pv/Val_Px order (PA04_HIV.sas:344-386).
NOT_EXAMINED_DISPOSITION_LABELS: tuple[tuple[str, str], ...] = (
    (
        'G - Insufficient Info to Begin Investigation',
        'Dispo. G - Insufficient Info to Begin Investigation',
    ),
    ('H - Unable to Locate', 'Dispo. H - Unable to Locate'),
    (
        'J - Located, Not Examined and/or Interviewed',
        'Dispo. J - Located, Not Examined and/or Interviewed',
    ),
    ('K - Sent Out Of Jurisdiction', 'Dispo. K - Sent Out Of Jurisdiction'),
    ('L - Other', 'Dispo. L - Other'),
    ('V - Domestic Violence Risk', 'Dispo. V - Domestic Violence Risk'),
    ('X - Patient Deceased', 'Dispo. X - Patient Deceased'),
)
NOT_EXAMINED_DISPOSITIONS = frozenset(
    code for code, _ in NOT_EXAMINED_DISPOSITION_LABELS
)

# Disposition subset that feeds the Notification/Testing Index numerator
# (PA04_HIV.sas:205-206, 223-224, 263-264, 283-284).
INDEX_DISPOSITIONS = frozenset(
    {
        '2 - Prev. Neg, New Pos',
        '3 - Prev. Neg, Still Neg',
        '5 - No Prev Test, New Pos',
        '6 - No Prev Test, New Neg',
    }
)

# HIV_900_RESULT values counted as seropositive for Val_H (PA04_HIV.sas:96).
HIV_POSITIVE_RESULTS = frozenset(
    {
        '13-Positive/Reactive',
        '21-HIV-1 Pos',
        '22-HIV-1 Pos, Possible Acute',
        '23-HIV-2 Pos',
        '24-HIV-Undifferentiated',
        '12-Prelim Positive',
    }
)

# Wide/pivoted output row: Category 1/2/3, then unscoped Count/Percentage/Index
# (case-level metrics only), then a (Count, Percentage, Index) triplet per
# scope -- From OI (Initial/Original), From RI (Re-Interview), Total
# (Combined) -- for the Partner/Cluster metrics.
Pa04Row = tuple[
    str,
    str | None,
    str | None,
    int | None,
    str | None,
    float | None,
    int | None,
    str | None,
    float | None,
    int | None,
    str | None,
    float | None,
    int | None,
    str | None,
    float | None,
]
