import { orUndefined } from 'utils';
import { AdministrativeInformation } from '../administrative';
import { AdministrativeInformationRequest } from './administrativeRequest';

const asAdministrative = (value: Partial<AdministrativeInformation>): AdministrativeInformationRequest | undefined => {
    const { asOf, comment } = value;

    if (asOf) {
        return {
            asOf,
            comment: orUndefined(comment)
        };
    }
};

export { asAdministrative };
