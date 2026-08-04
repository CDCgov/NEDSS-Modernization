import { TextAutocomplete, TextAutocompleteSingleProps } from 'design-system/autocomplete/single/text';
import { CodedResultOptionsService } from 'generated';
import { Selectable } from 'options/selectable';

const CodedResultsAutocomplete = ({
    id,
    label,
    onChange,
    required,
    onBlur,
    value,
    sizing,
}: TextAutocompleteSingleProps) => {
    const renderSuggestion = (suggestion: Selectable) => `${suggestion.name} [${suggestion.value}]`;

    const resolver = (criteria: string, limit?: number) =>
        CodedResultOptionsService.codedResultAutocomplete({
            criteria,
            limit,
        }).then((response) => response as Selectable[]);

    return (
        <TextAutocomplete
            sizing={sizing}
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
