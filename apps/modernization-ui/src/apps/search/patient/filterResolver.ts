import { Filter } from 'design-system/filter';
import { isEmpty } from 'utils';
import { PatientSearchFilter } from './patientSearchFilter';

const filterResolver = (filter: Filter): PatientSearchFilter | undefined => {
    const { id, address, ageOrDateOfBirth, email, name, sex, phone, identification } = filter;

    const resolved = { id, address, ageOrDateOfBirth, email, name, sex, phone, identification };

    return isEmpty(resolved) ? undefined : resolved;
};

export { filterResolver };
