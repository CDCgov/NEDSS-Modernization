import { useEffect, useState } from 'react';

import { Button } from '@trussworks/react-uswds';
import { Controller, useFormContext, useWatch } from 'react-hook-form';

import { CreateCodedQuestionRequest, ValueSetControllerService, ValueSetOption } from 'apps/page-builder/generated';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { SingleSelect } from 'design-system/select';

import styles from '../question-form.module.scss';

type Props = {
    onFindValueSet?: () => void;
    published?: boolean;
    editing?: boolean;
};
export const CodedFields = ({ onFindValueSet, editing = false, published = false }: Props) => {
    const form = useFormContext<CreateCodedQuestionRequest>();
    const valueSet = useWatch({ control: form.control, name: 'valueSet', exact: true });
    const [valueSets, setValueSets] = useState<ValueSetOption[]>([]);
    const { options, fetch } = useOptions();

    useEffect(() => {
        ValueSetControllerService.findValueSetOptions().then((response) => {
            setValueSets(response);
        });
    }, []);

    useEffect(() => {
        const selected = valueSets.find((v) => v.value === valueSet?.toString());
        if (selected) fetch(selected.codeSetNm);
        form.setValue('defaultValue', undefined);
    }, [valueSet, JSON.stringify(valueSets)]);

    return (
        <>
            <Controller
                control={form.control}
                name="valueSet"
                rules={{
                    required: { value: !published, message: 'Value set is required' },
                }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Value set"
                        onChange={(val) => {
                            onChange(valueSets.find((v) => v.value === val?.value)?.id ?? null);
                            onBlur();
                        }}
                        onBlur={onBlur}
                        value={valueSets.find((v) => v.id === value)}
                        options={valueSets}
                        error={error?.message}
                        name={name}
                        id={name}
                        required={!published}
                        disabled={published}
                    />
                )}
            />
            {!published && !editing && (
                <>
                    <Button
                        className={styles.valuesetSearchButton}
                        type="button"
                        outline={true}
                        onClick={onFindValueSet}
                    >
                        Search value set
                    </Button>
                    <Controller
                        control={form.control}
                        name="defaultValue"
                        render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                            <SingleSelect
                                label="Default value"
                                onChange={(v) => onChange(v?.value ?? null)}
                                value={options.find((o) => o.value === value)}
                                options={valueSet ? options : []}
                                error={error?.message}
                                name={name}
                                id={name}
                            />
                        )}
                    />
                </>
            )}
        </>
    );
};
