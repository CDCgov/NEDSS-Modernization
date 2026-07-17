REFERRAL_GROUPS = {
    'partners': [
        'P1 - Partner, Sex',
        'P2 - Partner, Needle-Sharing',
        'P3 - Partner, Both',
    ],
    'clus': [
        'A1 - Associate 1',
        'A2 - Associate 2',
        'A3 - Associate 3',
        'S1 - Social Contact 1',
        'S2 - Social Contact 2',
        'S3 - Social Contact 3',
    ],
    'reac': ['T1 - Positive Test', 'T2 - Morbidity Report'],
    'cohort': ['C1- Cohort'],
}

NOT_EXAMINED_VAR_MAP = {
    'var_u_p': 'G - Insufficient Info to Begin Investigation',
    'var_v_p': 'H - Unable to Locate',
    'var_w_p': 'J - Located, Not Examined and/or Interviewed',
    'var_x_p': 'K - Sent Out Of Jurisdiction',
    'var_y_p': 'L - Other',
    'var_z_p': 'V - Domestic Violence Risk',
    'var_aa_p': 'X - Patient Deceased',
    'var_ab_p': 'Z - Previous Preventative Treatment',
}
NOT_EXAMINED_VAR_ORDER = [
    'var_u_p',
    'var_v_p',
    'var_w_p',
    'var_x_p',
    'var_y_p',
    'var_z_p',
    'var_aa_p',
    'var_ab_p',
]

OUTPUT_COLUMNS = [
    'Worker',
    'Category 1',
    'Category 2',
    'Part.',
    'Clus.',
    'Reac.',
    'Other',
    'Total',
]

# STD Constants
STD_EXAMINED_DISPOS = [
    'A - Preventative Treatment',
    'B - Refused Preventative Treatment',
    'C - Infected, Brought to Treatment',
    'D - Infected, Not Treated',
    'F - Not Infected',
]
STD_NOT_EXAMINED_DISPOS = [
    'G - Insufficient Info to Begin Investigation',
    'H - Unable to Locate',
    'J - Located, Not Examined and/or Interviewed',
    'K - Sent Out Of Jurisdiction',
    'L - Other',
    'V - Domestic Violence Risk',
    'X - Patient Deceased',
    'Z - Previous Preventative Treatment',
]
STD_SPECIFIC_VAR_MAP = {
    'var_n_p': 'A - Preventative Treatment',
    'var_o_p': 'B - Refused Preventative Treatment',
    'var_p_p': 'C - Infected, Brought to Treatment',
    'var_q_p': 'D - Infected, Not Treated',
    'var_r_p': 'F - Not Infected',
    'var_ac_p': 'E - Previously Treated for This Infection',
}
STD_SPECIFIC_VAR_ORDER = [
    'var_n_p',
    'var_o_p',
    'var_p_p',
    'var_q_p',
    'var_r_p',
    'var_ac_p',
]
# HIV Constants
HIV_EXAMINED_DISPOS = [
    '2 - Prev. Neg, New Pos',
    '3 - Prev. Neg, Still Neg',
    '4 - Prev. Neg, No Test',
    '5 - No Prev Test, New Pos',
    '6 - No Prev Test, New Neg',
    '7 - No Prev Test, No Test',
]
HIV_NOT_EXAMINED_DISPOS = [
    'G - Insufficient Info to Begin Investigation',
    'H - Unable to Locate',
    'J - Located, Not Examined and/or Interviewed',
    'K - Sent Out Of Jurisdiction',
    'L - Other',
    'V - Domestic Violence Risk',
    'X - Patient Deceased',
    'Z - Previous Preventative Treatment',
]
HIV_SPECIFIC_VAR_MAP = {
    'var_n_p': '2 - Prev. Neg, New Pos',
    'var_o_p': '3 - Prev. Neg, Still Neg',
    'var_p_p': '4 - Prev. Neg, No Test',
    'var_q_p': '5 - No Prev Test, New Pos',
    'var_r_p': '6 - No Prev Test, New Neg',
    'var_s_p': '7 - No Prev Test, No Test',
}
HIV_SPECIFIC_VAR_ORDER = [
    'var_n_p',
    'var_o_p',
    'var_p_p',
    'var_q_p',
    'var_r_p',
    'var_s_p',
]

HIV_NOT_EXAMINED_VAR_MAP = {
    'var_u_p': 'G - Insufficient Info to Begin Investigation',
    'var_v_p': 'H - Unable to Locate',
    'var_w_p': 'J - Located, Not Examined and/or Interviewed',
    'var_x_p': 'K - Sent Out Of Jurisdiction',
    'var_y_p': 'L - Other',
    'var_z_p': 'V - Domestic Violence Risk',
    'var_aa_p': 'X - Patient Deceased',
    'var_ab_p': 'Z - Previous Preventative Treatment',
}
