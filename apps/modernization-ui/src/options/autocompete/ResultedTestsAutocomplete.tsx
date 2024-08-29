import { Autocomplete, AutocompleteSingleProps } from 'design-system/autocomplete';
import { Option, ResultedTestOptionsService } from 'generated';
import { Selectable } from 'options/selectable';

const onSelectableResultedTest = (response: Array<Option>) => {
    return response.map(
        (data): Selectable => ({
            name: `${data.name} [${data.value}]`,
            value: data.value,
            label: `${data.name} [${data.value}]`
        })
    );
};

const resolver = (criteria: string, limit?: number) =>
    ResultedTestOptionsService.resultedtestAutocomplete({
        criteria: criteria,
        limit: limit
    }).then((response) => {
        return onSelectableResultedTest(response);
    });

const ResultedTestsAutocomplete = ({ id, label, onChange, required, onBlur, ...rest }: AutocompleteSingleProps) => (
    <Autocomplete
        resolver={resolver}
        onChange={onChange}
        required={required}
        onBlur={onBlur}
        id={id}
        label={label}
        {...rest}
    />
);

export { ResultedTestsAutocomplete };
