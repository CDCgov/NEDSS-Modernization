import { Autocomplete, AutocompleteSingleProps } from 'design-system/autocomplete';
import { UserOptionsService } from 'generated';

const resolver = (criteria: string, limit?: number) =>
    UserOptionsService.userAutocomplete({
        criteria: criteria,
        limit: limit
    }).then((response) => response);

const UserAutocomplete = ({ id, label, onChange, ...rest }: AutocompleteSingleProps) => (
    <Autocomplete resolver={resolver} onChange={onChange} id={id} label={label} {...rest} />
);

export { UserAutocomplete };
