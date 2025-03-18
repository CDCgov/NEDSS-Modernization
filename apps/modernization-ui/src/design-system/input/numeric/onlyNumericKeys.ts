import { KeyboardEvent as ReactKeyboardEvent } from 'react';

const isNumericKey = (key: string) => {
    return key.length == 1 ? /[0-9]/.test(key) : true;
};

const onlyNumericKeys = (event: KeyboardEvent | ReactKeyboardEvent) => {
    if (!event.altKey && !event.ctrlKey && !event.metaKey && !isNumericKey(event.key)) {
        //  prevents spaces and letter characters from being entered but allows keyboard shortcuts like (crtl-v)
        event.preventDefault();
    }
};

export { onlyNumericKeys };
