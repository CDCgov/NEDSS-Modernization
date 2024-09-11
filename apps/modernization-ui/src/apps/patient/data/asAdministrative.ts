/* eslint-disable no-redeclare */
import { Administrative } from './api';
import { AdministrativeEntry } from './entry';

function asAdministrative(entry: AdministrativeEntry): Administrative;
function asAdministrative(entry: undefined): undefined;
function asAdministrative(entry: AdministrativeEntry | undefined): Administrative | undefined {
    return entry;
}
export { asAdministrative };
