import { exists } from 'utils/exists';

const withoutProperty =
    <T>(id: keyof T) =>
    (current?: T) => {
        if (current) {
            const next = { ...current };
            delete next[id];

            // if the last property is removed, return undefined
            return exists(next) ? next : undefined;
        }
    };

export { withoutProperty };
