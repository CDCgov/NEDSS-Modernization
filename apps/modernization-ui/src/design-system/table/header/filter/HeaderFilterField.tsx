import { KeyboardEvent as ReactKeyboardEvent } from 'react';
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
    const { valueOf, apply, clear, add } = filtering;

    const handleKey = (event: ReactKeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            event.stopPropagation();
            apply();
        }
    };

    const handleClear = () => clear(descriptor.id);

    const handleChange = (next?: string) => {
        if (next) {
            add(descriptor.id, next);
        } else {
            add(descriptor.id, next);
            clear(descriptor.id);
        }
    };

    return (
        <Shown when={descriptor.type === 'text'}>
            <TextInput
                clearable={Boolean(valueOf(descriptor.id))}
                className={classNames(styles.filter, {
                    [styles.small]: sizing === 'small',
                    [styles.medium]: sizing === 'medium',
                    [styles.large]: sizing === 'large'
                })}
                id={`text-filter-${descriptor.id}`}
                name={label}
                aria-label={`filter by ${label}`}
                value={valueOf(descriptor.id)}
                onChange={handleChange}
                onClear={handleClear}
                onKeyDown={handleKey}
            />
        </Shown>
    );
};

export { HeaderFilterField };
