import { Label, Textarea } from '@trussworks/react-uswds';
import { Controller, useFormContext } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { CreateQuestionForm } from '../QuestionForm';
import styles from '../question-form.module.scss';

export const AdministrativeFields = () => {
    const form = useFormContext<CreateQuestionForm>();
    return (
        <>
            <h4>Administrative</h4>
            <Controller
                control={form.control}
                name="adminComments"
                rules={{ ...maxLengthRule(2000) }}
                render={({ field: { onChange, name, value, onBlur } }) => (
                    <>
                        <Label htmlFor={name}>Administrative comments</Label>
                        <Textarea
                            onChange={onChange}
                            onBlur={onBlur}
                            className={styles.textaArea}
                            defaultValue={value}
                            name={name}
                            id={name}
                            rows={1}
                        />
                    </>
                )}
            />
        </>
    );
};
