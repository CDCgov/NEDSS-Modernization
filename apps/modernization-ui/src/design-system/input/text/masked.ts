const isInteger = (value: string) => !Number.isNaN(parseInt(value, 10));

const masked = (placeholder: string) => (value: string) => {
    const stripped = value.replace(/\D/g, '');
    let newValue = '';

    for (let i = 0, charIndex = 0; i < placeholder.length; i += 1) {
        const next = stripped[charIndex];

        if (next === undefined) {
            break;
        }

        const isInt = isInteger(next);
        const matchesNumber = placeholder[i] === '_';

        if (matchesNumber && isInt) {
            newValue += next;
            charIndex += 1;
        } else if ((!isInt && matchesNumber) || (matchesNumber && !isInt)) {
            return newValue;
        } else {
            //  use the placeholder
            newValue += placeholder[i];
        }
    }

    return newValue;
};

export { masked };
