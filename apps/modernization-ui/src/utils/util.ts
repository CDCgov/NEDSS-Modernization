export const TOTAL_TABLE_DATA = 10;

export const formatInterfaceString = (str: string) => {
    let i;
    const frags = str.split('_');
    for (i = 0; i < frags.length; i++) {
        frags[i] = frags[i].charAt(0).toUpperCase() + frags[i].slice(1).toLowerCase();
    }
    return frags.join(' ');
};

export const convertCamelCase = (str: string) => {
    const result = str.replace(/([A-Z])/g, ' $1');
    const finalResult = result.charAt(0).toUpperCase() + result.slice(1);
    return finalResult || str;
};

export const calculateAge = (birthday: Date) => {
    // birthday is a date
    const ageDifMs = Date.now() - birthday.getTime();
    const ageDate = new Date(ageDifMs); // miliseconds from epoch
    if (Math.abs(ageDate.getMonth()) === 0 && Math.abs(ageDate.getUTCFullYear() - 1970) === 0) {
        return `${Math.abs(ageDate.getDate())} days`;
    }

    if (Math.abs(ageDate.getUTCFullYear() - 1970) === 0) {
        return `${Math.abs(ageDate.getMonth())} months`;
    }

    return `${Math.abs(ageDate.getUTCFullYear() - 1970)} years`;
};

/**
 * Converts mill-seconds to a clock string in the format 'HH:MM:SS' or 'MM:SS' if less than an hour.
 * @param {number} millisecs The number of milliseconds
 * @return {unknown} The clock string.
 */
export const toClockString = (millisecs: number): string => {
    const pad = (num: number) => num.toString().padStart(2, '0');
    const seconds = Math.floor((millisecs / 1000) % 60);
    const minutes = Math.floor((millisecs / (1000 * 60)) % 60);
    const hours = Math.floor(millisecs / (1000 * 60 * 60));
    return hours > 0 ? `${hours}:${pad(minutes)}:${pad(seconds)}` : `${minutes}:${pad(seconds)}`;
};

/**
 * Given an object, selects the value at the given path, using dot notation.
 * Example: selectField({ a: { b: { c: 42 } } }, 'a.b.c') returns 42
 * @param {object} obj The object for which to select the field
 * @param {string} path The path to the field, using dot notation ('foo.bar.baz')
 * @return {unknown} The original object if path is empty, or the field located at the specified path.
 */
export const selectField = (obj: { [key: string]: any }, path: string) => {
    if (!path) return obj;
    const keys = path.split('.');
    return keys.reduce((acc, part) => acc && acc[part], obj);
};
