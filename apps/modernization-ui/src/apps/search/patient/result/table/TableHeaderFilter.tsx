import { useFilter } from 'design-system/filter/useFilter';
import { TextInput } from 'design-system/input/text/TextInput';
import { Controller } from 'react-hook-form';

type Filter = {
    filter: {
        [key: string]: string;
    };
};

export const TableHeaderFilter = ({ id }: { id: string }) => {
    const { form, applyFilter } = useFilter();

    const handleKeyDown = (event: { key: string }) => {
        if (event.key === 'Enter') {
            applyFilter();
        }
    };

    return (
        <Controller
            control={form?.control}
            name={`filter.${id}` as keyof Filter}
            render={({ field: { onChange, name, value } }) => (
                <TextInput
                    id={name}
                    value={value as string}
                    onChange={onChange}
                    clearable
                    onKeyDown={handleKeyDown}
                    onClear={() => {
                        onChange();
                        applyFilter();
                    }}
                />
            )}
        />
    );
};
