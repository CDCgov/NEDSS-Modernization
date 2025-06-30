import { displayAgeAsOf, today } from 'date';
import { defaultTo } from 'libs/supplying';

type AgeResolver = (birthday?: string) => string | undefined;

const asOfAgeResolver =
    (asOf?: string): AgeResolver =>
    (birthday?: string) => {
        if (birthday) {
            return displayAgeAsOf(birthday, defaultTo(today, asOf));
        }
    };

export { asOfAgeResolver };
export type { AgeResolver };
