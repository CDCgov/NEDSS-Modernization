import { ChangeEvent } from 'react';
import { UseFormReturn } from 'react-hook-form/dist/types/form';

export const handleChangeToDefaultValue = (
    form: UseFormReturn,
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
