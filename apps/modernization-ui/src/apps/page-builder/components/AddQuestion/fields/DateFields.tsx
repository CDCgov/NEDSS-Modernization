import { Label, Radio } from '@trussworks/react-uswds';
import { CreateDateQuestionRequest } from 'apps/page-builder/generated';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Option } from 'generated';
import { useEffect, useState } from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import styles from '../question-form.module.scss';

type Props = {
    maskOptions: Option[];
};
export const DateFields = ({ maskOptions }: Props) => {
    const form = useFormContext<CreateDateQuestionRequest>();
    const [allowFuture, setAllowFuture] = useState<boolean>(false);
    const [dateMaskOptions, setDateMaskOptions] = useState<Option[]>([]);

    useEffect(() => {
        form.setValue('allowFutureDates', false);
        form.setValue('mask', CreateDateQuestionRequest.mask.DATE);
    }, []);

    useEffect(() => {
        setDateMaskOptions(maskOptions.filter((m) => m.value === 'DATE'));
    }, [maskOptions]);

    return (
        <>
            <Controller
                control={form.control}
                name="mask"
                rules={{ required: { value: true, message: 'Date format required' } }}
                render={({ field: { onChange, onBlur, name, value }, fieldState: { error } }) => (
                    <SelectInput
                        label="Date format"
                        onChange={(e) => {
                            onChange(e);
                            onBlur();
                        }}
                        onBlur={onBlur}
                        defaultValue={value}
                        options={dateMaskOptions}
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                        disabled
                    />
                )}
            />

            <Label htmlFor="allowFutureDates" className="required">
                Allow for future dates
            </Label>
            <div className={styles.yesNoRadioButtons}>
                <Radio
                    id="allowFutureDates yes"
                    name="allowFutureDates yes"
                    value="yes"
                    label="Yes"
                    onChange={() => {
                        setAllowFuture(true);
                        form.setValue('allowFutureDates', true);
                    }}
                    checked={allowFuture}
                />
                <Radio
                    id="allowFutureDates no"
                    name="allowFutureDates no"
                    value="no"
                    label="No"
                    onChange={() => {
                        setAllowFuture(false);
                        form.setValue('allowFutureDates', false);
                    }}
                    checked={!allowFuture}
                />
            </div>
        </>
    );
};
