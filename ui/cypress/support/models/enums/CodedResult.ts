export default class CodedResult {
    constructor(readonly code: string, readonly description: string) {}
    static readonly PRESENT_PRSNT = new CodedResult('PRSNT', 'Present');
    static readonly SHIGA_TOXIN_1_POSITIVE = new CodedResult('C0074448', 'Shiga toxin 1 positive');
    static readonly SHIGA_TOXIN_2_POSITIVE = new CodedResult('C0074449', 'Shiga toxin 2 positive');
    static readonly ABNORMAL = new CodedResult('ABN', 'abnormal');
    static readonly ABNORMAL_PRESENCE_OF = new CodedResult('ABNPRES', 'abnormal presence of');
    static readonly ABNORMAL_RESULT = new CodedResult('ABNRES', 'abnormal result');
    static readonly ABNORMALLY_HIGH = new CodedResult('ABNHIGH', 'abnormally high');
    static readonly ABNORMALLY_LOW = new CodedResult('ABNLOW', 'abnormally low');
    static readonly ABOVE = new CodedResult('ABV', 'above');
    static readonly ABOVE_REFERENCE_RANGE = new CodedResult('ABVREFRAN', 'above reference range');
    static readonly ABOVE_THRESHOLD = new CodedResult('ABOVETHR', 'above threshold');
    static readonly ABSENT = new CodedResult('ABS', 'absent');
    static readonly ADEQUATE = new CodedResult('ADEQ', 'adequate');
    static readonly ANAEROBES_DETECTED = new CodedResult('ANDET', 'anaerobes detected');
    static readonly ANAEROBES_NOT_DETECTED = new CodedResult('ANNOTDET', 'anaerobes not detected');
    static readonly ANALYTE_NOT_DETECTED = new CodedResult('ANALYTABS', 'analyte not detected');
    static readonly BELOW_REFERENCE_RANGE = new CodedResult('BELOWREF', 'below reference range');
    static readonly BELOW_THRESHOLD_LEVEL = new CodedResult('BELOWTHR', 'below threshold level');
    static readonly BORDERLINE = new CodedResult('BORD', 'borderline');
    static readonly BORDERLINE_NORMAL = new CodedResult('BORDNL', 'borderline normal');
    static readonly CONTAMINATED = new CodedResult('C', 'contaminated');
    static readonly DECREASED = new CodedResult('DECR', 'decreased');
    static readonly DEFINITE = new CodedResult('DEF', 'definite');
    static readonly DETECTED = new CodedResult('DET', 'detected');
    static readonly EQUIVOCAL = new CodedResult('EQVOCAL', 'equivocal');
    static readonly EQUIVOCAL_RESULT = new CodedResult('EQVORES', 'equivocal result');
    static readonly FOUND = new CodedResult('FND', 'found');
    static readonly FOUR_PLUS = new CodedResult('++++', 'four plus');
    static readonly HEAVY_GROWTH = new CodedResult('HVYGRO', 'heavy growth');
    static readonly HIGH = new CodedResult('HIGH', 'high');
    static readonly IDENTIFIED = new CodedResult('IDED', 'identified');
    static readonly INCREASED = new CodedResult('INCR', 'increased');
    static readonly INDETERMINATE = new CodedResult('I', 'indeterminate');
    static readonly INDETERMINATE_RESULT = new CodedResult('INDTRES', 'indeterminate result');
    static readonly INTERMEDIATE = new CodedResult('INTMED', 'intermediate');
    static readonly INTERMEDIATELY_SUSCEPTIBLE = new CodedResult('INTSUSC', 'intermediately susceptible');
    static readonly ISOLATED = new CodedResult('ISOL', 'isolated');
    static readonly LIGHT_GROWTH = new CodedResult('LITGRO', 'light growth');
    static readonly LOW = new CodedResult('LOW', 'low');
    static readonly LOWER_LIMIT_OF_REFERENCE_RANGE = new CodedResult('LOWEREF', 'lower limit of reference range');
    static readonly MODERATE_GROWTH = new CodedResult('MODGRO', 'moderate growth');
    static readonly MODERATE_NUMBER = new CodedResult('MODNUM', 'moderate number');
    static readonly MODERATELY_RESISTANT = new CodedResult('MODRES', 'moderately resistant');
    static readonly MODERATELY_SUSCEPTIBLE = new CodedResult('MODSUSC', 'moderately susceptible');
    static readonly NEGATIVE = new CodedResult('N', 'negative');
    static readonly NIL = new CodedResult('NIL', 'nil');
    static readonly NO = new CodedResult('NO', 'no');
    static readonly NO_GROWTH = new CodedResult('NOGRO', 'no growth');
    static readonly NON_REACTIVE = new CodedResult('NR', 'non-reactive');
    static readonly NONE = new CodedResult('NONE', 'none');
    static readonly NORMAL = new CodedResult('NORM', 'normal');
    static readonly NORMAL_LIMITS = new CodedResult('NORMLIM', 'normal limits');
    static readonly NORMAL_RANGE = new CodedResult('NORMRAN', 'normal range');
    static readonly NORMAL_RESULT = new CodedResult('NORMRES', 'normal result');
    static readonly NOT_DETECTED = new CodedResult('NDET', 'not detected');
    static readonly NOT_DONE = new CodedResult('ND', 'not done');
    static readonly NOT_FOUND = new CodedResult('NOTFND', 'not found');
    static readonly NOT_IDENTIFIED = new CodedResult('NOTIDED', 'not identified');
    static readonly NOT_ISOLATED = new CodedResult('NOTISOL', 'not isolated');
    static readonly NOT_SEEN = new CodedResult('NOTSEEN', 'not seen');
    static readonly NOT_SIGNIFICANT = new CodedResult('NOTSIG', 'not significant');
    static readonly NOT_TESTED = new CodedResult('NOTESTED', 'not tested');
    static readonly NOTHING = new CodedResult('NOTHING', 'nothing');
    static readonly NULL = new CodedResult('NULL', 'null');
    static readonly NUMEROUS = new CodedResult('NUM', 'numerous');
    static readonly ONE_PLUS = new CodedResult('+', 'one plus');
    static readonly PEAK = new CodedResult('PEAK', 'peak');
    static readonly PENDING = new CodedResult('PEND', 'pending');
    static readonly POSITIVE = new CodedResult('P', 'positive');
    static readonly PRESENT_PRES = new CodedResult('PRES', 'present');
    static readonly QUANTITY_NOT_SUFFICIENT = new CodedResult('QNS', 'quantity not sufficient');
    static readonly REACTIVE = new CodedResult('REAC', 'reactive');
    static readonly RESISTANT = new CodedResult('RSNT', 'resistant');
    static readonly SCANTY = new CodedResult('SCNT', 'scanty');
    static readonly SCANTY_GROWTH = new CodedResult('SCNTGRO', 'scanty growth');
    static readonly SEEN = new CodedResult('SEEN', 'seen');
    static readonly SENSITIVE = new CodedResult('SENS', 'sensitive');
    static readonly SIGNIFICANT = new CodedResult('SIG', 'significant');
    static readonly SUSCEPTIBILITY_INTERMEDIATE_SUSCEPTIBILITY = new CodedResult(
        'SUSCINT',
        'susceptibility - intermediate susceptibility'
    );
    static readonly SUSCEPTIBILITY_MODERATELY_RESISTANT = new CodedResult(
        'SUSCMODRSNT',
        'susceptibility - moderately resistant'
    );
    static readonly SUSCEPTIBILITY_MODERATELY_SUSCEPTIBLE = new CodedResult(
        'SUSCMOD',
        'susceptibility - moderately susceptible'
    );
    static readonly SUSCEPTIBILITY_RESISTANT = new CodedResult('SUSCRSNT', 'susceptibility - resistant');
    static readonly SUSCEPTIBILITY_SUSCEPTIBLE = new CodedResult('SUSC', 'susceptibility - susceptible');
    static readonly TEST_NOT_DONE = new CodedResult('TESTND', 'test not done');
    static readonly THREE_PLUS = new CodedResult('+++', 'three plus');
    static readonly TOO_MANY_TO_COUNT = new CodedResult('TMTC', 'too many to count');
    static readonly TWO_PLUS = new CodedResult('++', 'two plus');
    static readonly UNDIFFERENTIATED = new CodedResult('G-F509', 'undifferentiated');
    static readonly UNEXPLAINED_LABORATORY_RESULT = new CodedResult('ANOMRES', 'unexplained laboratory result');
    static readonly UNKNOWN = new CodedResult('U', 'unknown');
    static readonly UPPER_LIMIT_OF_REFERENCE_RANGE = new CodedResult('UPPEREF', 'upper limit of reference range');
    static readonly VARIANT = new CodedResult('VARINT', 'variant');
    static readonly VERY_HEAVY_GROWTH = new CodedResult('VHVYGRO', 'very heavy growth');
    static readonly VERY_HIGH = new CodedResult('VHIGH', 'very high');
    static readonly VERY_LIGHT_GROWTH = new CodedResult('VLITGRO', 'very light growth');
    static readonly VERY_LOW = new CodedResult('VLOW', 'very low');
    static readonly WEAK = new CodedResult('WEAK', 'weak');
    static readonly WEAKLY_POSITIVE = new CodedResult('WEAKPOS', 'weakly positive');
    static readonly WITHIN_NORMAL_LIMITS = new CodedResult('WNL', 'within normal limits');
    static readonly WITHIN_NORMAL_RANGE = new CodedResult('WNRAN', 'within normal range');
    static readonly WITHIN_REFERENCE_RANGE = new CodedResult('WITHINREF', 'within reference range');
    static readonly YES = new CodedResult('YES', 'yes');
}
