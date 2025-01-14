import {
    SelectableAutocomplete,
    SelectableAutocompleteSingleProps
} from 'design-system/autocomplete/single/selectable';
import { UserOptionsService } from 'generated';

const resolver = (criteria: string, limit?: number) =>
    UserOptionsService.userAutocomplete({
        criteria: criteria,
        limit: limit
    }).then((response) => response);

const UserAutocomplete = (props: SelectableAutocompleteSingleProps) => (
    <SelectableAutocomplete resolver={resolver} {...props} />
);

export { UserAutocomplete };
