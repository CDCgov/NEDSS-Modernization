import { Autocomplete, AutocompleteSingleProps } from 'design-system/autocomplete';
import { UserOptionsService } from 'generated';

const resolver = (criteria: string, limit?: number) =>
    UserOptionsService.userAutocomplete({
        criteria: criteria,
        limit: limit
    }).then((response) => response);

const UserAutocomplete = (props: Omit<AutocompleteSingleProps, 'resolver'>) => (
    <Autocomplete resolver={resolver} {...props} />
);

export { UserAutocomplete };
