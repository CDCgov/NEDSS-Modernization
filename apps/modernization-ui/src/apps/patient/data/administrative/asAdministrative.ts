import { orUndefined } from 'utils';
import { Administrative } from '../api';
import { AdministrativeEntry } from '../entry';

const asAdministrative = (entry: AdministrativeEntry): Administrative => {
    const { asOf, comment } = entry;

    return {
        asOf,
        comment: orUndefined(comment)
    };
};

export { asAdministrative };
