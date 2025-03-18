import { KeyboardEvent as ReactKeyboardEvent } from 'react';

const isAlphaKey = (key: string) => {
    return key.length === 1 ? /^[a-zA-Z\s]$/.test(key) : true;
};

const onlyAlphaKeys = (event: KeyboardEvent | ReactKeyboardEvent) => {
    if (!event.altKey && !event.ctrlKey && !event.metaKey && !isAlphaKey(event.key)) {
        // prevents numbers and special characters from being entered, but allows spaces
        event.preventDefault();
    }
};

export { onlyAlphaKeys };
