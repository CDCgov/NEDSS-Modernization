const isInteger = (value: string) => !Number.isNaN(parseInt(value, 10));

const masked = (placeholder: string) => (value: string) => {
    const stripped = value.replace(/\D/g, '');
    let newValue = '';

    for (let i = 0, charIndex = 0; i < placeholder.length; i += 1) {
        const isInt = isInteger(stripped[charIndex]);
        const matchesNumber = placeholder[i] === '_';

        if (matchesNumber && isInt) {
            newValue += stripped[charIndex];
            charIndex += 1;
        } else if ((!isInt && matchesNumber) || (matchesNumber && !isInt)) {
            return newValue;
        } else {
            //  take the value from the placeholder
            newValue += placeholder[i];
        }
        // break if no characters left and the pattern is non-special character
        if (stripped[charIndex] === undefined) {
            break;
        }
    }

    return newValue;
};

const maskedAsDate = masked('__/__/____');

export { maskedAsDate };
