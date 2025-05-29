const defaultComparator = <V>(value: V, comparing: V) => {
    if (value instanceof Date && comparing instanceof Date) {
        return value.getTime() - comparing.getTime();
    } else if (typeof value === 'string' && typeof comparing === 'string') {
        return value.localeCompare(comparing, undefined, { numeric: true });
    } else if (value > comparing) {
        return 1;
    } else if (value < comparing) {
        return -1;
    } else {
        return 0;
    }
};

export { defaultComparator };
