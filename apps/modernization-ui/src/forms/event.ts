import { ChangeEvent } from 'react';

export const handleChangeToDefaultValue = (
    form: any,
    name: String,
    value: any,
    e: ChangeEvent<HTMLSelectElement>,
    onChange: (event: ChangeEvent<HTMLSelectElement>) => void
): void => {
    if (!e.target.value) {
        form.setValue(name as any, value, {
            shouldDirty: true,
            shouldValidate: true
        });
        return;
    }
    onChange(e);
};
