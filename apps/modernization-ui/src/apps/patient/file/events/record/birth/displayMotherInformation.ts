import { maybeMap } from 'utils/mapping';
import { exists } from 'utils/exists';
import { maybeDisplayName } from 'name';
import { displayAddressText } from 'address/display';
import { MotherInformation } from './birth-record';

const maybeDisplayAddress = maybeMap(displayAddressText);

const displayMotherInformation = (mother: MotherInformation) =>
    [maybeDisplayName(mother.name), maybeDisplayAddress(mother.address), mother.address?.county]
        .filter(exists)
        .join('\n');

const maybeDisplayMotherInformation = maybeMap(displayMotherInformation);

export { displayMotherInformation, maybeDisplayMotherInformation };
