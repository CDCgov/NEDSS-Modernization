const validZipCodeRule = (name: string) => ({
    pattern: {
        value: /^\d{5}(?:[-\s]\d{4})?$/,
        message: `Please enter a valid ${name} (XXXXX or XXXXX-XXXX ) using only numeric characters (0-9).`
    }
});

export { validZipCodeRule };
