import { KeyboardEvent as ReactKeyboardEvent } from 'react';
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
    const { valueOf, apply, clear, add } = filtering;

    const handleKey = (event: ReactKeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            event.stopPropagation();
            const next = (event.target as HTMLInputElement).value;
            if (next) {
                apply();
            } else {
                clear(descriptor.id);
            }
        }
    };

    const handleClear = () => clear(descriptor.id);

    const handleChange = (next?: string) => add(descriptor.id, next);

    return (
        <Shown when={descriptor.type === 'text'}>
            <TextInputField
                clearable={Boolean(valueOf(descriptor.id))}
                className={styles.filter}
                id={`text-filter-${descriptor.id}`}
                name={label}
                aria-label={`filter by ${label}`}
                value={valueOf(descriptor.id)}
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
