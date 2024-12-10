import classNames from 'classnames';
import { ChangeEvent, useEffect, useState } from 'react';
import styles from './textarea.module.scss';

type TextOnChange = (value?: string) => void;

type TextAreaProps = {
    id: string;
    inputMode?: 'none' | 'text' | 'tel' | 'url' | 'email' | 'search';
    value?: string | null;
    onChange?: TextOnChange;
    onBlur?: () => void;
} & Omit<
    JSX.IntrinsicElements['textarea'],
    'defaultValue' | 'onChange' | 'onBlur' | 'value' | 'type' | 'inputMode' | 'autoComplete'
>;

const TextArea = ({
    id,
    inputMode = 'text',
    placeholder,
    value,
    onChange,
    onBlur,
    className,
    ...props
}: TextAreaProps) => {
    const [current, setCurrent] = useState<string>(value ?? '');

    useEffect(() => {
        setCurrent(value ?? '');
    }, [value]);

    const handleChange = (event: ChangeEvent<HTMLTextAreaElement>) => {
        const next = event.target.value;
        if (next) {
            setCurrent(next);
            onChange?.(next);
        } else {
            setCurrent('');
            onChange?.();
        }
    };

    return (
        <div className={styles.textArea}>
            <textarea
                autoComplete="off"
                id={id}
                name={props.name ?? id}
                inputMode={inputMode}
                className={classNames('usa-input', className)}
                onChange={handleChange}
                onBlur={onBlur}
                placeholder={placeholder}
                value={current}
                {...props}
            />
            <div className={styles.counter}>
                <span className={current.length > 2000 ? styles.limit : ''}>{current.length}</span>/2000
            </div>
        </div>
    );
};

export { TextArea };
export type { TextAreaProps };
