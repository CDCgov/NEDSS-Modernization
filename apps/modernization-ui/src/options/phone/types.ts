import { asSelectable } from 'options/selectable';

const PHONE = asSelectable('PH', 'Phone');
const CELL_PHONE = asSelectable('CP', 'Cellular phone');
const EMAIL = asSelectable('NET', 'Email address');

export { PHONE, CELL_PHONE, EMAIL };
