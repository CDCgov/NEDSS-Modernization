import { Autocomplete, AutocompleteSingleProps } from 'design-system/autocomplete';
import { CodedResultOptionsService, Option } from 'generated';
import { Selectable } from 'options/selectable';

const renderSuggestion = (suggestion: { label: string; value: string }) => {
    return <>{`${suggestion.label} [${suggestion.value}]`}</>;
};

const onSelectableCodedResults = (response: Array<Option>) => {
    console.log({ response });
    return response.map(
        (data): Selectable => ({
            name: data.name,
            value: data.value,
            label: data.label
        })
    );
};

const resolver = (criteria: string, limit?: number) =>
    CodedResultOptionsService.codedResultAutocomplete({
        criteria: criteria,
        limit: limit
    }).then((response) => {
        return onSelectableCodedResults(response);
    });

const CodedResultsAutocomplete = ({ id, label, onChange, required, onBlur }: AutocompleteSingleProps) => (
    <Autocomplete
        resolver={resolver}
        onChange={onChange}
        required={required}
        onBlur={onBlur}
        id={id}
        label={label}
        asSuggestion={renderSuggestion}
    />
);

export { CodedResultsAutocomplete };
