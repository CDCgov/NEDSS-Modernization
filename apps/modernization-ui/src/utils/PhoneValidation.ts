export const validatePhoneNumber = (number: string) => {
    const validPhone = /^[0-9]{1,10}$/;
    const validPhoneFormatted = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
    if (number) {
        if (number.match(validPhone) || number.match(validPhoneFormatted)) {
            return true;
        } else {
            return false;
        }
    } else {
        return true;
    }
};
