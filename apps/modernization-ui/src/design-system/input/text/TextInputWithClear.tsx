import { Icon } from 'design-system/icon';
import { TextInput, TextOnChange } from './TextInput';
import styles from './text-input-with-icon.module.scss';
import { useEffect, useState } from 'react';

type TextInputIconProps = {
    value: string;
    onChange: TextOnChange;
    id: string;
    onClear: () => void;
    onKeyPress: (event: { key: string }) => void;
};

export const TextInputWithClear = ({ id, value, onChange, onClear, onKeyPress }: TextInputIconProps) => {
    const [current, setCurrent] = useState<string>(value ?? '');

    const onClearInput = () => {
        setCurrent('');
        onClear();
    };

    useEffect(() => {
        setCurrent(value);
    }, [value]);

    return (
        <div className={styles['input-with-icon']}>
            <TextInput
                inputMode="text"
                type="text"
                value={current}
                onChange={onChange}
                id={id}
                onKeyDown={onKeyPress}
            />
            {current && (
                <Icon
                    data-testid="clear-icon"
                    name="close"
                    size="small"
                    className={styles.icon}
                    onClick={onClearInput}
                />
            )}
        </div>
    );
};
