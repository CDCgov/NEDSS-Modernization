import { KeyboardEvent as ReactKeyboardEvent } from 'react';

const isPatientIdKey = (key: string) => {
    return key.length === 1 ? /[0-9,; ]/.test(key) : true;
};

const onlyPatientIdKeys = (event: KeyboardEvent | ReactKeyboardEvent) => {
    if (!event.altKey && !event.ctrlKey && !event.metaKey && !isPatientIdKey(event.key)) {
        // prevents characters other than space, numbers, comma, and semicolon from being entered
        event.preventDefault();
    }
};

export { onlyPatientIdKeys };
