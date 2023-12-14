import { Controller } from "react-hook-form";
import Input from "react-select/dist/declarations/src/components/Input";
import { maxLengthRule } from "validation/entry";

type HyperlinkFieldsProps = {
    form : any;
}

export const HyperlinkFields = ( form ) : HyperlinkFieldsProps => {


    return (
        <>
            <Controller
                control={form.control}
                name="type.label"
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
            <Controller
                control={form.control}
                name="type.linkUrl"
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
        </>
    );
};
