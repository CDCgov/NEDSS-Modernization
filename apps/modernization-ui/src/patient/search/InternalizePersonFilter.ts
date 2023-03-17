import { internalizeDate } from 'date';
import { PersonFilter } from 'generated/graphql/schema';

export const internalize = (filter: PersonFilter): PersonFilter => ({
    ...filter,
    ...(filter.dateOfBirth && { dateOfBirth: internalizeDate(filter.dateOfBirth) })
});
