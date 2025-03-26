import { KeyboardEvent as ReactKeyboardEvent } from 'react';

const isNumericKey = (key: string) => {
    return key.length == 1 ? /\d/.test(key) : true;
};

const onlyNumericKeys = (event: KeyboardEvent | ReactKeyboardEvent) => {
    if (!event.altKey && !event.ctrlKey && !event.metaKey && !isNumericKey(event.key)) {
        //  prevents spaces and letter characters from being entered but allows keyboard shortcuts like (crtl-v)
        event.preventDefault();
    }
};

const onlyNumericOrDecimal = (event: KeyboardEvent | ReactKeyboardEvent) => {
    if (event.key !== '.' && !isNumericKey(event.key)) {
        event.preventDefault();
    }
};

export { onlyNumericKeys, onlyNumericOrDecimal };
