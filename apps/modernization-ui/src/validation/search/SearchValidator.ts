const valid = /^([0-9]{1,10}|(([0-9]{1,3}|\([0-9]{1,3}\))[-. ]?)([0-9]{1,3})?([-. ][0-9]{1,4})?)$/;

export const validate = (number: string): boolean => {
    return !number || valid.test(number);
};
