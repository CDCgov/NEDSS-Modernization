import { KeyboardEvent as ReactKeyboardEvent, useState } from 'react';
import { TextInput } from 'design-system/input/text';
import { Shown } from 'conditional-render';
import { FilterDescriptor, FilterInteraction } from 'design-system/filter';

import styles from './header-filter-field.module.scss';

type HeaderFilterFieldProps = { descriptor: FilterDescriptor; label: string; filtering: FilterInteraction };

const HeaderFilterField = ({ descriptor, label, filtering }: HeaderFilterFieldProps) => {
    const { apply, clear, filter } = filtering;
    const [value, setValue] = useState<string | undefined>(() => (filter ? filter[descriptor.id] : undefined));

    const handleKey = (event: ReactKeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            event.stopPropagation();
            if (value) {
                apply(descriptor.id, value);
            } else {
                clear(descriptor.id);
            }
        }
    };

    return (
        <Shown when={descriptor.type === 'text'}>
            <TextInput
                clearable
                className={styles.filter}
                id={`text-filter-${descriptor.id}`}
                name={label}
                aria-label={`filter by ${label}`}
                value={value}
                onChange={setValue}
                onKeyDown={handleKey}
            />
        </Shown>
    );
};

export { HeaderFilterField };
