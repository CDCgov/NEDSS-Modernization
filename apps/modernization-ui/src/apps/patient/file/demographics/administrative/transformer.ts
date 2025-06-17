import { Administrative } from 'generated';
import { AdministrativeInformation } from 'libs/patient/demographics/administrative/AdministrativeInformation';
import { maybeMap } from 'utils/mapping';

const maybeDate = maybeMap((value: string) => new Date(value));

const transformer = (value: Administrative): AdministrativeInformation => ({
    ...value,
    asOf: maybeDate(value.asOf)
});

export { transformer };
