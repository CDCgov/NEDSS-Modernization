import classNames from 'classnames';
import { ChangeEvent, useEffect, useState } from 'react';
import styles from './textarea.module.scss';

type TextOnChange = (value?: string) => void;

type TextAreaProps = {
    id: string;
    inputMode?: 'none' | 'text' | 'tel' | 'url' | 'email' | 'search';
    value?: string | null;
    maxLength?: number;
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
    maxLength = 2000,
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
                className={classNames('usa-textarea', className)}
                onChange={handleChange}
                onBlur={onBlur}
                placeholder={placeholder}
                value={current}
                required={props.required}
                aria-required={props.required}
                {...props}
            />
            <div className={styles.counter}>
                <span className={current.length > maxLength ? styles.limit : ''}>{current.length}</span>/{maxLength}
            </div>
        </div>
    );
};

export { TextArea };
export type { TextAreaProps };
