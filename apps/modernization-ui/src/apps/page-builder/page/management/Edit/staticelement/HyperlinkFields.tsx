import { AddHyperlink } from 'apps/page-builder/generated';
import { Controller, useFormContext } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { maxLengthRule } from 'validation/entry';

export const HyperlinkFields = () => {
    const form = useFormContext<AddHyperlink>();

    return (
        <>
            <Controller
                control={form.control}
                name="label"
                rules={{
                    required: { value: true, message: 'Label is required' },
                    ...maxLengthRule(50)
                }}
                render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                    <Input
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        label="Label"
                        type="text"
                        error={error?.message}
                        required
                    />
                )}
            />
            Example: Click here to go to CDC News
            <Controller
                control={form.control}
                name="linkUrl"
                rules={{
                    required: { value: true, message: 'Link is required' },
                    ...maxLengthRule(50)
                }}
                render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                    <Input
                        onChange={onChange}
                        onBlur={onBlur}
                        defaultValue={value}
                        label="Link URL"
                        type="text"
                        error={error?.message}
                        required
                    />
                )}
            />
            Example: http://www.cdc.gov/news
        </>
    );
};
