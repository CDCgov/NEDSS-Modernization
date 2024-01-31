import { Label, Textarea } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { Controller, useFormContext } from 'react-hook-form';
import { maxLengthRule } from 'validation/entry';
import { CreateQuestionForm } from '../QuestionForm';
import styles from '../question-form.module.scss';

export const AdministrativeFields = () => {
    const form = useFormContext<CreateQuestionForm>();
    return (
        <>
            <Heading className={styles.heading} level={4}>
                Administrative
            </Heading>
            <div className={styles.fieldInfo}>These fields will not be displayed to your users</div>
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
                            defaultValue={value}
                            name={name}
                            id={name}
                            rows={1}
                            className={styles.textaArea}
                        />
                    </>
                )}
            />
        </>
    );
};
