export const formattedName = (lastNm = '', firstNm = '') => {
    const format = [];
    lastNm && format.push(lastNm);
    firstNm && format.push(firstNm);
    return (format.length > 1 ? format.join(', ') : format.join('')) || '--';
};
