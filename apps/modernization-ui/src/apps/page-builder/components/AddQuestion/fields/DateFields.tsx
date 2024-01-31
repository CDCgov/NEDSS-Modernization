import { SelectInput } from 'components/FormInputs/SelectInput';
import { Option } from 'generated';
import { useEffect, useState } from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import { CreateQuestionForm } from '../QuestionForm';
import styles from '../question-form.module.scss';
import { Label, Radio } from '@trussworks/react-uswds';

type Props = {
    maskOptions: Option[];
};
export const DateFields = ({ maskOptions }: Props) => {
    const form = useFormContext<CreateQuestionForm>();
    const [dateMaskOptions, setDateMaskOptions] = useState<Option[]>([]);

    useEffect(() => {
        form.setValue('allowFutureDates', false);
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
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        options={dateMaskOptions}
                        error={error?.message}
                        name={name}
                        id={name}
                        htmlFor={name}
                        required
                    />
                )}
            />
            <Controller
                control={form.control}
                name="allowFutureDates"
                render={({ field: { onChange, name, value } }) => (
                    <>
                        <Label htmlFor={name} className="required">
                            Allow for future dates
                        </Label>
                        <div className={styles.futureDateButtons}>
                            <Radio id={name} name={name} label="Yes" onChange={() => onChange(true)} checked={value} />
                            <Radio
                                id="codeSet_PHIN"
                                name="codeSet"
                                label="No"
                                onChange={() => onChange(false)}
                                checked={!value}
                            />
                        </div>
                    </>
                )}
            />
        </>
    );
};
