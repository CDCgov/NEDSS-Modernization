import { Selectable } from 'options';
import { Term } from './terms';

const fromSelectable =
    (source: string, title: string) =>
    ({ name, value }: Selectable): Term => ({
        source,
        title,
        name,
        value
    });

export { fromSelectable };
