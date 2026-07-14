"""Shared D_CONTACT_RECORD classification constants.

CTT_REFERRAL_BASIS and CTT_PROCESSING_DECISION code sets used by both PA03
(Internet Partner Services Report) and PA05 (Worker Interview Activity
Report) when deciding which contact records count toward partner, social
contact, and associate totals.
"""

PARTNER_BASES = {
    'P1 - Partner, Sex',
    'P2 - Partner, Needle-Sharing',
    'P3 - Partner, Both',
}
SOCIAL_BASES = {
    'S1 - Social Contact 1',
    'S2 - Social Contact 2',
    'S3 - Social Contact 3',
}
ASSOCIATE_BASES = {
    'A1 - Associate 1',
    'A2 - Associate 2',
    'A3 - Associate 3',
}
VALID_PROCESSING_DECISIONS = {
    'Field Follow-up',
    'Record Search Closure',
    'Secondary Referral',
}
