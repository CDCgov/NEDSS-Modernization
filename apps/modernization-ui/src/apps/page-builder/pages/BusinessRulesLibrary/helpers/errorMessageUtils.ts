export const removeNumericAndSymbols = (text: string | undefined) => {
    const firstChar = text?.charAt(0);
    if (firstChar && firstChar <= '9' && firstChar >= '0') {
        return text?.replace(/\d+/, '').replace('. ', '');
    }
    return text;
};

export const checkForSemicolon = (text: string | undefined) => {
    const labelLength = text?.length;
    if (labelLength) {
        const lastChar = text.charAt(labelLength - 1);
        if (lastChar === ':') {
            return text.replace(':', '');
        }
        return text;
    }
    return text;
};
