import { Selectable } from 'options';
import { fromSelectable } from './fromSelectable';
import { Term } from './terms';

const fromSelectables =
    (source: string, title: string) =>
    (selectables?: Selectable[]): Term[] =>
        selectables ? selectables.map(fromSelectable(source, title)) : [];

export { fromSelectables };
