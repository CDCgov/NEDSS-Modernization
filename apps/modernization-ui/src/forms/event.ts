import { ChangeEvent } from 'react';
import { UseFormReturn } from 'react-hook-form';

export const handleChangeToDefaultValue = (
    form: UseFormReturn,
    name: string,
    value: any,
    e: ChangeEvent<HTMLSelectElement>,
    onChange: (event: ChangeEvent<HTMLSelectElement>) => void
): void => {
    if (!e.target.value) {
        form.setValue(name as any, value, {
            shouldDirty: true,
            shouldValidate: true,
        });
        return;
    }
    onChange(e);
};
