const formattedName = (lastNm: string | undefined | null, firstNm: string | undefined | null) => {
    const format = [];
    lastNm && format.push(lastNm);
    firstNm && format.push(firstNm);
    return (format.length > 1 ? format.join(', ') : format.join('')) || '--';
};

export { formattedName };
