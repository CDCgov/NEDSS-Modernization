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
