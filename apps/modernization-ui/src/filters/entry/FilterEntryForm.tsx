import { useMemo } from 'react';

import { Button } from '@trussworks/react-uswds';
import { Controller, FormProvider, useForm, useWatch } from 'react-hook-form';

import { SingleSelect } from 'design-system/select';
import { Property } from 'filters/properties';
import { operators } from 'filters/selectables';

import { DataRangeEntryForm } from './DataRangeEntryForm';
import { ExactValueEntryForm } from './ExactValueEntryForm';
import { FilterEntry } from './FilterEntry';
import { PartialValueEntryForm } from './PartialValueEntryForm';
import styles from './filter-entry-form.module.scss';

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
        formState: { isValid },
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
                            <SingleSelect
                                id="select-column"
                                name={name}
                                label="Select a field"
                                value={properties.find(p => p.value === value)}
                                onBlur={onBlur}
                                onChange={(v) => onChange(v?.value ?? null)}
                                options={properties}
                                error={error?.message}
                            />
                        )}
                    />
                    {selectedProperty && (
                        <Controller
                            control={control}
                            name="operator"
                            shouldUnregister={true}
                            rules={{ required: { value: true, message: 'An operator is required.' } }}
                            render={({ field: { name, value, onBlur, onChange }, fieldState: { error } }) => (
                                <SingleSelect
                                    id="select-operator"
                                    name={name}
                                    label="Operator"
                                    value={operators(property).find(p => p.value === value)}
                                    onBlur={onBlur}
                                    onChange={(v) => onChange(v?.value ?? null)}
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
                <Button type="button" id="cancel-button" onClick={onCancel} outline={true}>
                    Cancel
                </Button>
                <Button type="submit" id="done-button" disabled={!isValid} onClick={handleSubmit(onSubmit)}>
                    Done
                </Button>
            </footer>
        </div>
    );
};

export { FilterEntryForm };
