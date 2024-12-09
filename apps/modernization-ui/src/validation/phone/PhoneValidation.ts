const valid = /^([0-9]{7,10}|(([0-9]{3}|\([0-9]{3}\))[-. ]?)[0-9]{3}[-. ][0-9]{4})$/;

/**
 * Validates the given phone number matches the allowed formats.
 *
 * @param {string} number   The phone number to validate
 * @param {string} name     The name of the field being validated, defaults to "phone number"
 * @return {boolean | string} true when valid otherwise a string containing the error message.
 */
export const validatePhoneNumber = (number: string, name = 'phone number'): boolean | string => {
    return (
        !number ||
        valid.test(number) ||
        `Please enter a valid ${name} (XXX-XXX-XXXX) using only numeric characters (0-9).`
    );
};
