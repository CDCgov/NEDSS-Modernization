import { getYear, isValid, parse } from 'date-fns';

const formats = ['MM/dd/yyyy', 'MM/yyyy', 'yyyy'];

export const validateDateRange = (value: string[], field: string) => {
    if (rangeValuesMissing(value)) return getRangeValErrorMsg(field, false);

    const fromDate = new Date(value[0]!); // can't be undefined because of above checks
    const toDate = new Date(value[1]!); // can't be undefined because of above checks

    for (const { date, val, str } of [
        { date: fromDate, val: value[0], str: 'From' },
        { date: toDate, val: value[1], str: 'To' },
    ]) {
        if (date.toString() === 'Invalid Date' || !isDateFormat(val)) {
            return `${str} date of "${val}" is not valid mm/dd/yyyy formatted date for ${field}.`;
        }
    }

    if (fromDate > toDate) return getBeforeErrorMsg(field, true);
    return;
};

export const validateNumericRange = (value: string[], field: string) => {
    if (rangeValuesMissing(value)) return getRangeValErrorMsg(field, false);

    const fromNum = parseFloat(value[0]); // can't be undefined because of above checks
    const toNum = parseFloat(value[1]); // can't be undefined because of above checks
    for (const { number, val, str } of [
        { number: fromNum, val: value[0], str: 'From' },
        { number: toNum, val: value[1], str: 'To' },
    ]) {
        if (Number.isNaN(number) || !isNumberFormat(val)) {
            return `${str} value of "${val}" is not a valid number for ${field}.`;
        }
    }

    if (fromNum > toNum) return getBeforeErrorMsg(field, false);
};

export const getRangeValErrorMsg = (field: string, isInvalidVal: boolean) => {
    const invalidTypeMsg = isInvalidVal ? 'valid ' : '';
    return `Enter ${invalidTypeMsg}From and To values for ${field}.`;
};

export const getBeforeErrorMsg = (field: string, isDate: boolean) => {
    const typeMsg = isDate ? 'date' : 'value';
    return `From ${typeMsg} must be before To ${typeMsg} for ${field}.`;
};

export const rangeValuesMissing = (value: string[]) => {
    return (!!value[0] && !value[1]) || (!value[0] && !!value[1]) || value[0] === '' || value[1] === '';
};

const isNumberFormat = (val: unknown) => {
    if (typeof val === 'string') {
        return !!val.match(/^-?\d+(\.\d+)?$/);
    }

    return typeof val === 'number';
};

export const isDateFormat = (val: string): boolean => {
    const allowedLengths = formats.map((f) => f.length);
    if (!allowedLengths.includes(val.length)) {
        return false;
    }

    for (const format of formats) {
        if (val.length !== format.length) {
            continue;
        }

        if (format.includes('/') !== val.includes('/')) {
            continue;
        }

        const parsedDate = parse(val, format, new Date());

        if (isValid(parsedDate)) {
            const year = getYear(parsedDate as Date);

            if (year >= 1000 && year <= 9999) {
                return true;
            }
        }
    }
    return false;
};
