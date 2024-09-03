import { TextAutocomplete, TextAutocompleteSingleProps } from 'design-system/autocomplete/single/text';
import { ResultedTestOptionsService } from 'generated';
import { Selectable } from 'options/selectable';

const renderSuggestion = (suggestion: Selectable) => `${suggestion.name} [${suggestion.value}]`;

const resolver = (criteria: string, limit?: number) =>
    ResultedTestOptionsService.resultedtestAutocomplete({
        criteria: criteria,
        limit: limit
    }).then((response) => response as Selectable[]);

const ResultedTestsAutocomplete = ({ id, label, onChange, required, onBlur, value }: TextAutocompleteSingleProps) => (
    <TextAutocomplete
        value={value}
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
