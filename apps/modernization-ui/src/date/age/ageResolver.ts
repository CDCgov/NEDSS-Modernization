import { displayAgeAsOf, today } from 'date';
import { defaultTo } from 'libs/supplying';

const orElseToday = defaultTo(today);

type AgeResolver = (birthday?: string) => string | undefined;

const asOfAgeResolver =
    (asOf?: string): AgeResolver =>
    (birthday?: string) => {
        if (birthday) {
            return displayAgeAsOf(birthday, orElseToday(asOf));
        }
    };

export { asOfAgeResolver };
export type { AgeResolver };
