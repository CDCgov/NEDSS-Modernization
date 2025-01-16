import { useFilter } from 'design-system/filter/useFilter';
import { TextInputWithClear } from 'design-system/input/text/TextInputWithClear';
import { Controller } from 'react-hook-form';

type Filter = {
    filter: {
        [key: string]: string;
    };
};

export const TableHeaderFilter = ({ id }: { id: string }) => {
    const { form, applyFilter } = useFilter();

    const handleKeyPress = (event: { key: string }) => {
        if (event.key === 'Enter') {
            applyFilter();
        }
    };

    return (
        <Controller
            control={form?.control}
            name={`filter.${id}` as keyof Filter}
            render={({ field: { onChange, name, value } }) => (
                <TextInputWithClear
                    id={name}
                    value={value as string}
                    onChange={onChange}
                    onKeyPress={handleKeyPress}
                    onClear={() => {
                        onChange();
                        applyFilter();
                    }}
                />
            )}
        />
    );
};
