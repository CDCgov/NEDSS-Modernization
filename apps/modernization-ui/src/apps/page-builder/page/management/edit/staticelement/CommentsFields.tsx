import { AddReadOnlyComments, PagesQuestion } from 'apps/page-builder/generated';
import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { maxLengthRule } from 'validation/entry';

type CommentsProps = {
    question?: PagesQuestion;
};

export const CommentsFields = ({ question }: CommentsProps) => {
    const form = useFormContext<AddReadOnlyComments>();

    return (
        <>
            <Controller
                control={form.control}
                name="commentsText"
                rules={{
                    required: { value: true, message: 'Comments is required' },
                    ...maxLengthRule(2000)
                }}
                render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                    <Input
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={question ? question.defaultValue?.toString() : value}
                        label="Comments text"
                        required
                        type="text"
                        error={error?.message}
                        multiline
                    />
                )}
            />
        </>
    );
};
