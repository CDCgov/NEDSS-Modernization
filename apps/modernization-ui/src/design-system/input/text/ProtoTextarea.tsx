import { useRef, useEffect, useState } from 'react';
import styles from './proto-textarea.module.scss';
import classNames from 'classnames';

type TextOnChange = (value?: string) => void;

type ProtoTextareaProps = {
    id: string;
    inputMode?: 'none' | 'text' | 'tel' | 'url' | 'email' | 'search';
    value?: string | null;
    onChange?: TextOnChange;
    onBlur?: () => void;
} & Omit<
    JSX.IntrinsicElements['textarea'],
    'defaultValue' | 'onChange' | 'onBlur' | 'value' | 'type' | 'inputMode' | 'autoComplete'
>;

const ProtoTextarea = ({
    id,
    inputMode = 'text',
    placeholder,
    value,
    maxLength = 2000,
    onChange,
    onBlur,
    className,
    ...props
}: ProtoTextareaProps) => {
    const view = useRef<HTMLDivElement>(null);
    const textarea = useRef<HTMLTextAreaElement>(null);

    const [current, setCurrent] = useState<string>(value ?? '');
    // const [display, setDisplay] = useState<string>();

    useEffect(() => {
        setCurrent(value ?? '');
    }, [value]);

    const handleInput = () => {
        const next = textarea.current?.value;

        if (next) {
            setCurrent(next);
        } else {
            setCurrent('');
        }
    };

    const handleChange = () => {
        if (current) {
            onChange?.(current);
        } else {
            onChange?.();
        }
    };

    return (
        <div className={styles.area}>
            <div className={styles.content}>
                <div className={styles.view} ref={view} aria-hidden dangerouslySetInnerHTML={{ __html: current }}></div>
                <textarea
                    ref={textarea}
                    autoComplete="off"
                    id={id}
                    name={props.name ?? id}
                    inputMode={inputMode}
                    className={classNames('usa-textarea', styles.editing, className)}
                    onInput={handleInput}
                    onChange={handleChange}
                    onBlur={onBlur}
                    placeholder={placeholder}
                    value={current}
                    {...props}
                />
            </div>
            <div className={styles.counter}>
                <span className={current.length > maxLength ? styles.limit : ''}>{current.length}</span>/{maxLength}
            </div>
        </div>
    );
};

export { ProtoTextarea };
export type { ProtoTextareaProps };
