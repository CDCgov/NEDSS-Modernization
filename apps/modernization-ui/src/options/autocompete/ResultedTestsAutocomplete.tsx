import { Autocomplete, AutocompleteSingleProps } from 'design-system/autocomplete';
import { Option, ResultedTestOptionsService } from 'generated';
import { Selectable } from 'options/selectable';

const onSelectableResultedTest = (response: Array<Option>) => {
    return response.map(
        (data): Selectable => ({
            name: data.name,
            value: data.value,
            label: data.label
        })
    );
};

const renderSuggestion = (suggestion: { label: string; value: string }) => {
    return <>{`${suggestion.label} [${suggestion.value}]`}</>;
};

const resolver = (criteria: string, limit?: number) =>
    ResultedTestOptionsService.resultedtestAutocomplete({
        criteria: criteria,
        limit: limit
    }).then((response) => {
        return onSelectableResultedTest(response);
    });

const ResultedTestsAutocomplete = ({ id, label, onChange, required, onBlur }: AutocompleteSingleProps) => (
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

export { ResultedTestsAutocomplete };
