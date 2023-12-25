import { useMemo } from 'react';
import { Controller, FormProvider, useForm, useWatch } from 'react-hook-form';
import { Button } from '@trussworks/react-uswds';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Property } from 'filters/properties';
import { FilterEntry } from './FilterEntry';
import { DataRangeEntryForm } from './DataRangeEntryForm';
import { PartialValueEntryForm } from './PartialValueEntryForm';
import { ExactValueEntryForm } from './ExactValueEntryForm';
import styles from './filter-entry-form.module.scss';
import { operators } from 'filters/selectables';

type FilterEditViewProps = {
    properties: Property[];
    filter?: FilterEntry;
    onSave: (entry: FilterEntry) => void;
    onCancel: () => void;
};

const FilterEntryForm = ({ properties, onSave, onCancel }: FilterEditViewProps) => {
    const methods = useForm<FilterEntry, Partial<FilterEntry>>({ mode: 'onBlur' });

    const {
        control,
        handleSubmit,
        formState: { isValid }
    } = methods;

    const selectedProperty = useWatch({ control, name: 'property' });

    const property = useMemo(
        () => (selectedProperty && properties.find((property) => property.value === selectedProperty)) || undefined,
        [properties, selectedProperty]
    );

    const selectedOperator = useWatch({ control, name: 'operator' });

    const exactEntry =
        property && property.type === 'value' && (selectedOperator === 'EQUALS' || selectedOperator === 'NOT_EQUAL_TO');

    const partialEntry =
        property &&
        property.type === 'value' &&
        (selectedOperator === 'CONTAINS' || selectedOperator === 'STARTS_WITH');

    const onSubmit = (submitted: FilterEntry) => {
        onSave(submitted);
    };

    return (
        <div className={styles.entry}>
            <section>
                <FormProvider {...methods}>
                    <Controller
                        control={control}
                        name="property"
                        rules={{ required: { value: true, message: 'A field is required.' } }}
                        render={({ field: { name, value, onBlur, onChange }, fieldState: { error } }) => (
                            <SelectInput
                                name={name}
                                htmlFor={name}
                                label="Select a field"
                                defaultValue={value}
                                onBlur={onBlur}
                                onChange={onChange}
                                options={properties}
                                error={error?.message}
                            />
                        )}
                    />
                    {selectedProperty && (
                        <Controller
                            control={control}
                            name="operator"
                            shouldUnregister
                            rules={{ required: { value: true, message: 'An operator is required.' } }}
                            render={({ field: { name, value, onBlur, onChange }, fieldState: { error } }) => (
                                <SelectInput
                                    name={name}
                                    htmlFor={name}
                                    label="Operator"
                                    defaultValue={value}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    options={operators(property)}
                                    error={error?.message}
                                />
                            )}
                        />
                    )}
                    {selectedOperator === 'BETWEEN' && <DataRangeEntryForm />}
                    {partialEntry && <PartialValueEntryForm />}
                    {exactEntry && <ExactValueEntryForm property={property} />}
                </FormProvider>
            </section>
            <footer>
                <Button type="button" onClick={onCancel} outline>
                    Cancel
                </Button>
                <Button type="submit" disabled={!isValid} onClick={handleSubmit(onSubmit)}>
                    Done
                </Button>
            </footer>
        </div>
    );
};

export { FilterEntryForm };
