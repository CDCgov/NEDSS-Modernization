const valid = /^([0-9]{7,10}|(([0-9]{3}|\([0-9]{3}\))[-. ]?)[0-9]{3}[-. ][0-9]{4})$/;

/**
 * Validates the given phone number matches the allowed formats.
 *
 * @param {string} number  The phone number to validate
 * @return {boolean | string} true when valid otherwise a string containing the error message.
 */
export const validatePhoneNumber = (number: string): boolean | string => {
    return (
        !number ||
        valid.test(number) ||
        'Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9).'
    );
};
