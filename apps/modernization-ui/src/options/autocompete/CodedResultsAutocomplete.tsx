import { Selectable } from 'options/selectable';
import { TextAutocomplete, TextAutocompleteSingleProps } from 'design-system/autocomplete/single/text';
import { CodedResultOptionsService } from 'generated';

const CodedResultsAutocomplete = ({ id, label, onChange, required, onBlur, value }: TextAutocompleteSingleProps) => {
    const renderSuggestion = (suggestion: Selectable) => `${suggestion.name} [${suggestion.value}]`;

    const resolver = (criteria: string, limit?: number) =>
        CodedResultOptionsService.codedResultAutocomplete({
            criteria: criteria,
            limit: limit
        }).then((response) => response as Selectable[]);

    return (
        <TextAutocomplete
            resolver={resolver}
            value={value}
            onChange={onChange}
            required={required}
            onBlur={onBlur}
            id={id}
            label={label}
            asSuggestion={renderSuggestion}
        />
    );
};

export { CodedResultsAutocomplete };
