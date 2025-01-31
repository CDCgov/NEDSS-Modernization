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
    const textarea = useRef<HTMLTextAreaElement>(null);
    const mirroredEle = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const ro = new ResizeObserver(() => {
            console.log(borderWidth);
            if (mirroredEle.current) mirroredEle.current.style.width = `${textarea?.current?.clientWidth ?? 0}px`;
            if (mirroredEle.current) mirroredEle.current.style.height = `${textarea?.current?.clientHeight ?? 0}px`;
        });
        if (textarea.current) ro.observe(textarea.current);

        return () => ro.disconnect();
    }, [textarea.current]);

    const parseValue = (v: string) => (v.endsWith('px') ? parseInt(v.slice(0, -2), 10) : 0);

    const borderWidth = textarea.current
        ? parseValue(window.getComputedStyle(textarea.current).getPropertyValue('border-width'))
        : undefined;

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

    const handleScroll = () => {
        if (mirroredEle.current) mirroredEle.current.scrollTop = textarea.current?.scrollTop ?? 0;
    };

    return (
        <div className={styles.area}>
            <div className={styles.container} id="container">
                <div className={styles.mirror} ref={mirroredEle}>
                    {textarea?.current?.value}
                </div>
                <textarea
                    ref={textarea}
                    autoComplete="off"
                    id={id}
                    name={props.name ?? id}
                    inputMode={inputMode}
                    className={classNames('usa-textarea', styles.textarea, className)}
                    onInput={handleInput}
                    onChange={handleChange}
                    onBlur={onBlur}
                    onScroll={handleScroll}
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
