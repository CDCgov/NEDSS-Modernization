import { KeyboardEvent as ReactKeyboardEvent, useEffect, useState } from 'react';
import { TextInput } from 'design-system/input/text';
import { Shown } from 'conditional-render';
import { FilterDescriptor, FilterInteraction } from 'design-system/filter';

import styles from './header-filter-field.module.scss';
import { Sizing } from 'design-system/field';
import classNames from 'classnames';

type HeaderFilterFieldProps = {
    descriptor: FilterDescriptor;
    sizing: Sizing | undefined;
    label: string;
    filtering: FilterInteraction;
};

const HeaderFilterField = ({ descriptor, label, filtering, sizing }: HeaderFilterFieldProps) => {
    const { valueOf, apply, clear } = filtering;

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
                apply(descriptor.id, next);
            } else {
                clear(descriptor.id);
            }
        }
    };

    const handleClear = () => clear(descriptor.id);

    const handleChange = (next?: string) => setValue(next);

    return (
        <Shown when={descriptor.type === 'text'}>
            <TextInput
                clearable={Boolean(initialValue)}
                className={classNames(styles.filter, {
                    [styles.small]: sizing === 'small',
                    [styles.medium]: sizing === 'medium',
                    [styles.large]: sizing === 'large'
                })}
                id={`text-filter-${descriptor.id}`}
                name={label}
                aria-label={`filter by ${label}`}
                value={value}
                onChange={handleChange}
                onClear={handleClear}
                onKeyDown={handleKey}
            />
        </Shown>
    );
};

export { HeaderFilterField };
