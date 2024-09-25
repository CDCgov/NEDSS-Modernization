import { Administrative } from '../api';
import { AdministrativeEntry } from '../entry';

const asAdministrative = (entry: AdministrativeEntry): Administrative => entry;
export { asAdministrative };
