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
    const text = useRef<HTMLTextAreaElement>(null);

    const containerEle = document.getElementById('container');
    const textarea = document.querySelector('textarea');

    const overlayEle = document.createElement('div');
    overlayEle.classList.add(styles.overlay);
    containerEle?.prepend(overlayEle);

    const highlightEle = document.createElement('div');
    highlightEle.classList.add(styles.highlight);
    overlayEle.appendChild(highlightEle);

    const mirroredEle = document.createElement('div');
    mirroredEle.textContent = textarea?.textContent ?? '';
    mirroredEle.classList.add(styles.mirror);
    overlayEle.appendChild(mirroredEle);

    const cursorPos = textarea?.selectionStart;
    const textBeforeCursor = textarea?.textContent?.substring(0, cursorPos);
    const textAfterCursor = textarea?.textContent?.substring(cursorPos ?? 0);

    const pre = document.createTextNode(textBeforeCursor ?? '');
    const post = document.createTextNode(textAfterCursor ?? '');
    const caretEle = document.createElement('span');
    caretEle.innerHTML = '&nbsp;';

    mirroredEle.innerHTML = '';
    mirroredEle.append(pre, caretEle, post);

    const rect = caretEle.getBoundingClientRect();
    highlightEle.style.height = `${rect.height}px`;
    highlightEle.style.top = `${rect.top + (textarea?.scrollTop ?? 0)}px`;

    const [current, setCurrent] = useState<string>(value ?? '');
    // const [display, setDisplay] = useState<string>();

    useEffect(() => {
        setCurrent(value ?? '');
    }, [value]);

    const handleInput = () => {
        const next = text.current?.value;

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
            <div className={styles.overlay}>
                <div
                    className={styles.highlight}
                    ref={view}
                    aria-hidden
                    dangerouslySetInnerHTML={{ __html: current }}></div>
                <div className={styles.mirror}></div>
                <textarea
                    ref={text}
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
