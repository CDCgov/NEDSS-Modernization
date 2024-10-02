import { Selectable, asSelectable, findByValue } from 'options';

const UNKNOWN = asSelectable('U', 'Unknown');

const genders: Selectable[] = [asSelectable('F', 'Female'), asSelectable('M', 'Male'), UNKNOWN];

const asSelectableGender = (value: string | null | undefined) =>
    (value && findByValue(genders, UNKNOWN)(value)) || UNKNOWN;

export { genders, asSelectableGender };
