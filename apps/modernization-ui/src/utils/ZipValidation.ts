export const validateZipCode = (code: string) => {
    const validZip = /^\d{5}(?:[-\s]\d{4})?$/;
    if (code.match(validZip)) {
        return true;
    } else {
        return false;
    }
};
