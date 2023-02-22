export const validateDate = (date: string) => {
    const validDate = /^[0-9]{1,8}$/;
    const validDateFormatted = /^\(?([0-9]{2})\)?[-/]?([0-9]{2})[-/]?([0-9]{4})$/;
    if (date.match(validDate) || date.match(validDateFormatted)) {
        return true;
    } else {
        return false;
    }
};
