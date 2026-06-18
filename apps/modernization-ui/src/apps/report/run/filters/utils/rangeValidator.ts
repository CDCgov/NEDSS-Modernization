export const validateDateRange = (value, field) => {
    if (rangeValuesMissing(value)) return getRangeValErrorMsg(field, false);

    const fromDate = new Date(value[0]!); // can't be undefined because of above checks
    const toDate = new Date(value[1]!); // can't be undefined because of above checks
    for (const { date, ind, str } of [
        { date: fromDate, ind: 0, str: 'From' },
        { date: toDate, ind: 1, str: 'To' },
    ]) {
        if (date.toString() === 'Invalid Date' || !isDateFormat(value[ind])) {
            return `${str} date of "${value[ind]}" is not valid mm/dd/yyyy formatted date for ${field}.`;
        }
    }

    if (fromDate > toDate) return getBeforeErrorMsg(field, true);
    return;
};

export const validateNumericRange = (value, field) => {
    if (rangeValuesMissing(value)) return getRangeValErrorMsg(field, false);

    const fromNum = parseInt(value[0]); // can't be undefined because of above checks
    const toNum = parseInt(value[1]); // can't be undefined because of above checks
    for (const { number, ind, str } of [
        { number: fromNum, ind: 0, str: 'From' },
        { number: toNum, ind: 1, str: 'To' },
    ]) {
        if (number.toString() === 'NaN' || !isNumberFormat(value[ind])) {
            return `${str} value of "${value[ind]}" is not a valid number for ${field}.`;
        }
    }

    if (fromNum > toNum) return getBeforeErrorMsg(field, false);
};

export const getRangeValErrorMsg = (field: string, isInvalidVal: boolean) => {
    const invalidTypeMsg = isInvalidVal ? 'valid ' : '';
    return `Enter ${invalidTypeMsg}from and to values for ${field}.`;
};

export const getBeforeErrorMsg = (field: string, isDate: boolean) => {
    const typeMsg = isDate ? 'date' : 'value';
    return `From ${typeMsg} must be before to ${typeMsg} for ${field}.`;
};

export const rangeValuesMissing = (value) => {
    return (!!value[0] && !value[1]) || (!value[0] && !!value[1]) || value[0] === '' || value[1] === '';
};

const isNumberFormat = (val) => {
    if (typeof val === 'string') {
        return !!val.match(/^-?\d+(\.\d+)?$/);
    }

    return typeof val === 'number';
};

export const isDateFormat = (val) => {
    const month = '(?:0?[1-9]|1[0-2])';
    const day = '(?:0?[1-9]|[12]\\d|3[01])';
    const year = '\\d{4}';
    const pattern = `^(?:${month}\\/(?:${day}\\/)?)?${year}$`
    // matches MM/YYYY, MM/DD/YYYY, YYYY
    return typeof val === 'string' ? !!val.match(pattern) : false;
};
