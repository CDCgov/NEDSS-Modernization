import { KeyboardEvent as ReactKeyboardEvent } from 'react';

const isWithinMinMaxRange = (valueStr: string, minStr?: string | number, maxStr?: string | number) => {
    const min = minStr ? parseInt(minStr.toString()) : undefined;
    const max = maxStr ? parseInt(maxStr.toString()) : undefined;
    const value = parseInt(valueStr);

    // if min and max are not defined then skip the range check and return true
    if (typeof min === 'undefined' && typeof max === 'undefined') {
        return true;
    }

    // min is defined but max is not defined, we need to return true here to allow the user to enter in a large value.
    // example: 1980 is greater than 1875 but 1, 9, 8, 0 are all less than 1875.
    if (typeof min === 'number' && typeof max === 'undefined') {
        return true;
    }

    // max is defined but min is not defined
    if (typeof min === 'undefined' && typeof max === 'number') {
        if (value <= max) {
            return true;
        }
        return false;
    }

    // both min and max are defined
    if (typeof min === 'number' && typeof max === 'number') {
        if (value >= min && value <= max) {
            return true;
        }
        return false;
    }
    return false;
};

const isNumericKey = (key: string) => {
    return key.length == 1 ? /[0-9]/.test(key) : true;
};

const onlyNumericKeys = (event: KeyboardEvent | ReactKeyboardEvent) => {
    if (!event.altKey && !event.ctrlKey && !event.metaKey && !isNumericKey(event.key)) {
        //  prevents spaces and letter characters from being entered but allows keyboard shortcuts like (crtl-v)
        event.preventDefault();
    }
};

export { onlyNumericKeys, isWithinMinMaxRange };
