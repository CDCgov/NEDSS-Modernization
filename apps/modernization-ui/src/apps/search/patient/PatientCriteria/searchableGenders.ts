import { asSelectable } from 'options';
import { genders } from 'options/gender';

const NO_VALUE = asSelectable('NO_VALUE', 'No value');

const searchableGenders = [...genders, NO_VALUE];

export { searchableGenders, NO_VALUE };
