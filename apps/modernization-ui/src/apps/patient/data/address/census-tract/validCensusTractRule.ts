const validCensusTractRule = (name: string) => ({
    pattern: {
        value: /^(?!0000)(\d{4})(?:\.(?!00|99)\d{2})?$/,
        message: `The ${name} should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.`
    }
});

export { validCensusTractRule };
