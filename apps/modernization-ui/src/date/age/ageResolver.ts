import { displayAgeAsOf, today } from 'date';
import { defaultTo } from 'libs/supplying';

type AgeResolver = (birthday?: string) => string | undefined;

const ageResolver =
    (asOf?: string): AgeResolver =>
    (birthday?: string) => {
        if (birthday) {
            return displayAgeAsOf(birthday, defaultTo(today, asOf));
        }
    };

export { ageResolver };
export type { AgeResolver };
