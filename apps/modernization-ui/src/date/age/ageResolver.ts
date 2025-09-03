import { displayAgeAsOf, today } from 'date';
import { defaultTo } from 'libs/supplying';

const orElseToday = defaultTo(today);

type AgeResolver = (birthday?: string | null) => string | undefined;

const asOfAgeResolver =
    (asOf?: string | null): AgeResolver =>
    (birthday?: string | null) => {
        if (birthday) {
            return displayAgeAsOf(birthday, orElseToday(asOf));
        }
    };

export { asOfAgeResolver };
export type { AgeResolver };
