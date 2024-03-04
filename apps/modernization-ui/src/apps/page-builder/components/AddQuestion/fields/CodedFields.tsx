import { Button } from '@trussworks/react-uswds';
import { CreateCodedQuestionRequest, ValueSetControllerService, ValueSetOption } from 'apps/page-builder/generated';
import { useOptions } from 'apps/page-builder/hooks/api/useOptions';
import { authorization } from 'authorization';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { useEffect, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import styles from '../question-form.module.scss';

type Props = {
    onFindValueSet: () => void;
    published?: boolean;
};
export const CodedFields = ({ onFindValueSet, published = true }: Props) => {
    const form = useFormContext<CreateCodedQuestionRequest>();
    const valueSet = useWatch({ control: form.control, name: 'valueSet', exact: true });
    const [valueSets, setValueSets] = useState<ValueSetOption[]>([]);
    const { options, fetch } = useOptions();

    useEffect(() => {
        ValueSetControllerService.findValueSetOptionsUsingGet({ authorization: authorization() }).then((response) => {
            setValueSets(response);
        });
    }, []);

    useEffect(() => {
        const selected = valueSets.find((v) => v.value === valueSet?.toString());
        selected && fetch(selected.codeSetNm);
        form.setValue('defaultValue', undefined);
    }, [valueSet, JSON.stringify(valueSets)]);

    return (
        <>
            <Controller
                control={form.control}
                name="valueSet"
                rules={{
                    required: { value: !published, message: 'Value set is required' }
                }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <SelectInput
                        label="Value set"
                        onChange={(e) => {
                            onChange(e);
                            onBlur();
                        }}
                        onBlur={onBlur}
                        defaultValue={value}
                        options={valueSets}
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                        required={!published}
                        disabled={published}
                    />
                )}
            />
            {!published && (
                <>
                    <Button className={styles.valuesetSearchButton} type="button" outline onClick={onFindValueSet}>
                        Search value set
                    </Button>
                    <Controller
                        control={form.control}
                        name="defaultValue"
                        render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                            <SelectInput
                                label="Default value"
                                onChange={onChange}
                                defaultValue={value}
                                options={valueSet ? options : []}
                                error={error?.message}
                                name={name}
                                id={name}
                                htmlFor={name}
                            />
                        )}
                    />
                </>
            )}
        </>
    );
};
