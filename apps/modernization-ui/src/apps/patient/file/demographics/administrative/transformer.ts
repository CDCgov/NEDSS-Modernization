import { Administrative } from 'generated';
import { AdministrativeInformation } from 'libs/patient/demographics/AdministrativeInformation';

const maybeDate = (value: string) => new Date(value);

const transformer = (value: Administrative): AdministrativeInformation => ({
    ...value,
    asOf: maybeDate(value.asOf)
});

export { transformer };
