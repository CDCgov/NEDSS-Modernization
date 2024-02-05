export const validSubsectionNameRule = (value: string): string | undefined => {
    const regex1 = /^[\w*()+\-=;:/.,\s]+$/gm;
    const regex2 = /.*[^ ].*/gm;

    if (!value.match(regex1)) {
        return 'Valid characters are A-Z, a-z, 0-9, or * ( ) _ + - = ; : / . ,';
    }

    if (!value.match(regex2)) {
        return 'Cannot be empty';
    }
};
