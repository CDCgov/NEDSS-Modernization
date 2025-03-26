import { KeyboardEvent as ReactKeyboardEvent } from 'react';
import { onlyPatientIdKeys } from './onlyPatientIdKeys';

describe('onlyPatientIdKeys', () => {
    const createKeyboardEvent = (
        key: string,
        altKey: boolean = false,
        ctrlKey: boolean = false,
        metaKey: boolean = false
    ): ReactKeyboardEvent => {
        return {
            key,
            altKey,
            ctrlKey,
            metaKey,
            preventDefault: jest.fn()
        } as unknown as ReactKeyboardEvent;
    };

    it('should allow numbers, comma, semicolon, and space', () => {
        const allowedKeys = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ',', ';', ' '];
        allowedKeys.forEach((key) => {
            const event = createKeyboardEvent(key);
            onlyPatientIdKeys(event);
            expect(event.preventDefault).not.toHaveBeenCalled();
        });
    });

    it('should prevent letters and other special characters', () => {
        const disallowedKeys = ['a', 'b', 'c', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')'];
        disallowedKeys.forEach((key) => {
            const event = createKeyboardEvent(key);
            onlyPatientIdKeys(event);
            expect(event.preventDefault).toHaveBeenCalled();
        });
    });

    it('should allow any key when alt, ctrl, or meta is pressed', () => {
        const keys = ['a', 'b', '1', '!', '@'];
        keys.forEach((key) => {
            const eventAlt = createKeyboardEvent(key, true);
            onlyPatientIdKeys(eventAlt);
            expect(eventAlt.preventDefault).not.toHaveBeenCalled();

            const eventCtrl = createKeyboardEvent(key, false, true);
            onlyPatientIdKeys(eventCtrl);
            expect(eventCtrl.preventDefault).not.toHaveBeenCalled();

            const eventMeta = createKeyboardEvent(key, false, false, true);
            onlyPatientIdKeys(eventMeta);
            expect(eventMeta.preventDefault).not.toHaveBeenCalled();
        });
    });
});