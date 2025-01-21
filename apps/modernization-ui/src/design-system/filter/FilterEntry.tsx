import React, { useEffect } from 'react';
import { TextInput } from 'design-system/input/text/TextInput';
import { Controller, useForm } from 'react-hook-form';
import { useFilter } from './useFilter';

export type Filter = {
    [key: string]: string;
};

export const FilterEntry = ({ id, property }: { id: string; property: string }) => {
    const { onApply, onReset, filterEntry } = useFilter();
    const methods = useForm<Filter, Partial<Filter>>({ mode: 'onBlur' });

    const handleKeyPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            event.stopPropagation();
            onApply(methods.getValues());
        }
    };

    useEffect(() => {
        if (filterEntry) {
            methods.setValue(id, filterEntry[id]);
        }
    }, [filterEntry]);

    return property === 'text' ? (
        <Controller
            control={methods.control}
            name={id}
            render={({ field: { onChange, name, value } }) => (
                <TextInput
                    id={name}
                    value={value as string}
                    onChange={onChange}
                    clearable
                    onKeyPress={handleKeyPress}
                    onClear={() => {
                        onChange();
                        onReset();
                    }}
                />
            )}
        />
    ) : (
        <></>
    );
};
