// ============================================================================
// DISPLAY TEXT TO VALUE MAPPING FUNCTIONS
// ============================================================================

export const maps = {
    /**
     * Map address use display text to value
     * @param {string} displayText - Display text (e.g., 'Primary Work Place')
     * @returns {string} - Value code (e.g., 'WP')
     */
    addressUseToValue(displayText: any) {
        const map: Record<string, string> = {
            'Primary Work Place': 'WP',
            'Alternate Work Place': 'SB',
            'Organizational Contact': 'OC',
        };
        return map[displayText] || displayText;
    },

    /**
     * Map address type display text to value
     * @param {string} displayText - Display text (e.g., 'Office')
     * @returns {string} - Value code (e.g., 'O')
     */
    addressTypeToValue(displayText: any) {
        const map: Record<string, string> = {
            Office: 'O',
            'Postal/Mailing': 'M',
        };
        return map[displayText] || displayText;
    },

    /**
     * Map telephone use display text to value
     * @param {string} displayText - Display text (e.g., 'Primary Work Place')
     * @returns {string} - Value code (e.g., 'WP')
     */
    telephoneUseToValue(displayText: any) {
        const map: Record<string, string> = {
            'Primary Work Place': 'WP',
            'Alternate Work Place': 'SB',
            'Organizational Contact': 'OC',
        };
        return map[displayText] || displayText;
    },

    /**
     * Map telephone type display text to value
     * @param {string} displayText - Display text (e.g., 'Phone')
     * @returns {string} - Value code (e.g., 'PH')
     */
    telephoneTypeToValue(displayText: any) {
        const map: Record<string, string> = {
            Phone: 'PH',
            FAX: 'FAX',
            'Answering service': 'AN',
        };
        return map[displayText] || displayText;
    },

    /**
     * Map ID type display text to value
     * @param {string} displayText - Display text (e.g., 'ABCs Hospital ID')
     * @returns {string} - Value code (e.g., 'ABC')
     */
    idTypeToValue(displayText: any) {
        const map: Record<string, string> = {
            'ABCs Hospital ID': 'ABC',
            'Clinical Laboratory Improvement Amendments': 'CLIA',
            'Facility ID': 'FI',
            'Manufacturer Identifier': 'MID',
            'National employer identifier': 'NE',
            'National health plan identifier': 'NH',
            'Organization identifier': 'XX',
            'Partner Services Site ID': 'PSID',
            Other: 'OTH',
        };
        return map[displayText] || displayText;
    },

    /**
     * Map ID authority display text to value
     * @param {string} displayText - Display text (e.g., 'CMS Provider')
     * @returns {string} - Value code (e.g., 'CMS')
     */
    idAuthorityToValue(displayText: any) {
        const map: Record<string, string> = {
            AHA: 'AHA',
            'CLIA (CMS)': 'CLIA',
            'CMS Provider': 'CMS',
            Other: 'OTH',
        };
        return map[displayText] || displayText;
    },

    /**
     * Map edit reason display text to value
     * @param {string} displayText - Display text
     * @returns {string} - Value code ('c' or 'n')
     */
    editReasonToValue(displayText: any) {
        const map: Record<string, string> = {
            'Typographical error correction or additional information': 'c',
            'A change to existing information for non typographical reasons': 'n',
        };
        return map[displayText] || displayText;
    },
};
