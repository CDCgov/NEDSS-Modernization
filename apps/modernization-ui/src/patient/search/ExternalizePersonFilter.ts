import { externalizeDate } from 'date';
import { PersonFilter } from 'generated/graphql/schema';

export const externalize = (filter: PersonFilter): PersonFilter => ({
    ...filter,
    ...(filter.dateOfBirth && { dateOfBirth: externalizeDate(filter.dateOfBirth) })
});
