import { AddReadOnlyComments, UpdateReadOnlyComments } from 'apps/page-builder/generated';
import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { maxLengthRule } from 'validation/entry';

export const CommentsFields = () => {
    const form = useFormContext<AddReadOnlyComments | UpdateReadOnlyComments>();

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
                        defaultValue={value}
                        label="Comments text"
                        required
                        type="text"
                        ariaLabel="commentsText"
                        error={error?.message}
                        multiline
                    />
                )}
            />
        </>
    );
};
