from src.libraries.contact_record import ASSOCIATE_BASES, SOCIAL_BASES

WorkerKey = tuple[str | None, int | None]
Pa05Row = tuple[str, str, str | None, str | None, int, str | None, float | None]

INITIAL_INTERVIEW = 'Initial/Original'
REINTERVIEW = 'Re-Interview'
INTERVIEWED_STATUS = 'I - Interviewed'
PENDING_STATUS = 'A - Awaiting'
REFUSED_STATUS = 'R - Refused Interview'
UNABLE_TO_LOCATE_STATUS = 'U - Unable to Locate'
OTHER_STATUS = 'O - Other'
# PA05's "cluster" grouping (PA05_CI, PA05.sas:509-516) is social contacts and
# associates combined -- unlike PA03, which reports them separately.
CLUSTER_BASES = SOCIAL_BASES | ASSOCIATE_BASES

# Each entry: 
# (category_1, category_2, category_3, metric_key, denominator_key, rate_type)
METRICS: tuple[tuple[str, str | None, str | None, str, str | None, str | None], ...] = (
    ('Num. Cases Assigned', None, None, 'A', None, None),  # Var_A, PA05.sas:304-309
    (
        'Num. Cases Assigned',
        'Num. Cases Open',
        None,
        'B',
        'A',
        'percent',
    ),  # Var_B, PA05.sas:312-318
    (
        'Num. Cases Assigned',
        'Num. Cases Closed',
        None,
        'C',
        'A',
        'percent',
    ),  # Var_C, PA05.sas:320-326
    (
        'Num. Cases Assigned',
        'Num. Cases Pending',
        None,
        'D',
        'A',
        'percent',
    ),  # Var_D, PA05.sas:329-335
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        None,
        'E',
        'A',
        'percent',
    ),  # Var_E, PA05.sas:338-345
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "Clinic IX'S",
        'F',
        'E',
        'percent',
    ),  # Var_F, PA05.sas:348-357
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "Field IX'S",
        'G',
        'E',
        'percent',
    ),  # Var_G, PA05.sas:361-370
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "IX'D w/in 3 Days",
        'H',
        'E',
        'percent',
    ),  # Var_H, PA05.sas:375-381
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "IX'D w/in 5 Days",
        'I',
        'E',
        'percent',
    ),  # Var_I, PA05.sas:383-389
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "IX'D w/in 7 Days",
        'J',
        'E',
        'percent',
    ),  # Var_J, PA05.sas:391-397
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "IX'D w/in 14 Days",
        'K',
        'E',
        'percent',
    ),  # Var_K, PA05.sas:399-406
    (
        "Num. Cases Not IX'D",
        None,
        None,
        'L',
        'A',
        'percent',
    ),  # Var_L, PA05.sas:408-415
    (
        "Num. Cases Not IX'D",
        'Refused',
        None,
        'M',
        'L',
        'percent',
    ),  # Var_M, PA05.sas:418-425
    (
        "Num. Cases Not IX'D",
        'No Locate',
        None,
        'N',
        'L',
        'percent',
    ),  # Var_N, PA05.sas:428-435
    (
        "Num. Cases Not IX'D",
        'Other',
        None,
        'O',
        'L',
        'percent',
    ),  # Var_O, PA05.sas:438-445
    ("Num. of OI'S", None, None, 'P', None, None),  # Var_P, PA05.sas:447-453
    (
        "Num. of OI'S",
        "OI'S that were NCI",
        None,
        'Q',
        'P',
        'percent',
    ),  # Var_Q, PA05.sas:455-461
    (
        "Num. of OI'S",
        'Period Partners',
        None,
        'R',
        'P',
        'ratio',
    ),  # Var_R, PA05.sas:269-289 (pp) + 464-474 (ppnodup/r)
    (
        "Num. of OI'S",
        'Partners Initiated',
        None,
        'S',
        'P',
        'ratio',
    ),  # Var_S, PA05.sas:476-482
    ("Num. of RI'S", None, None, 'T', None, None),  # Var_T, PA05.sas:485-491
    (
        "Num. of RI'S",
        "RI's that were NCI",
        None,
        'U',
        'T',
        'percent',
    ),  # Var_U, PA05.sas:493-499
    (
        "Num. of RI'S",
        'Partners Initiated',
        None,
        'V',
        'T',
        'ratio',
    ),  # Var_V, PA05.sas:501-507
    (
        'Clusters Init. (OI & RI)',
        None,
        None,
        'W',
        'P',
        'ratio',
    ),  # Var_W, PA05.sas:509-516
)
