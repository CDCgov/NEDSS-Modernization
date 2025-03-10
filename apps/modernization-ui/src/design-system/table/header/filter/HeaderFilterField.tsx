import { KeyboardEvent as ReactKeyboardEvent, useEffect, useState } from 'react';
import { TextInputField } from 'design-system/input/text';
import { Shown } from 'conditional-render';
import { FilterDescriptor, FilterInteraction } from 'design-system/filter';

import styles from './header-filter-field.module.scss';
import { Sizing } from 'design-system/field';

type HeaderFilterFieldProps = {
    descriptor: FilterDescriptor;
    sizing: Sizing | undefined;
    label: string;
    filtering: FilterInteraction;
    sorted?: boolean;
};

const HeaderFilterField = ({ descriptor, label, filtering, sizing, sorted }: HeaderFilterFieldProps) => {
    const { valueOf, apply, clear, onFilterChange, pendingFilter } = filtering;

    const initialValue = valueOf(descriptor.id);
    const [value, setValue] = useState<string | undefined>(initialValue);

    useEffect(() => {
        setValue(valueOf(descriptor.id));
    }, [valueOf]);

    const handleKey = (event: ReactKeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            event.stopPropagation();
            const next = (event.target as HTMLInputElement).value;
            if (next) {
                apply(pendingFilter);
            } else {
                clear(descriptor.id);
            }
        }
    };

    const handleClear = () => clear(descriptor.id);

    const handleChange = (next?: string) => {
        next && onFilterChange(descriptor.id, next);
        setValue(next);
    };

    return (
        <Shown when={descriptor.type === 'text'}>
            <TextInputField
                clearable={Boolean(initialValue)}
                className={styles.filter}
                id={`text-filter-${descriptor.id}`}
                name={label}
                aria-label={`filter by ${label}`}
                value={value}
                onChange={handleChange}
                onClear={handleClear}
                onKeyDown={handleKey}
                sizing={sizing}
                sorted={sorted}
            />
        </Shown>
    );
};

export { HeaderFilterField };
