import { Shown } from 'conditional-render';
import { Icon } from 'design-system/icon';
import { ReactNode, useCallback, useRef, useState } from 'react';
import styles from './hint.module.scss';

const DEFAULT_TOP = 26;
// width of panel (283px) - width of icon (20px) = 263
const DEFAULT_LEFT = 20;
const LEFT_OFFSET = 283;

type Props = {
    id: string;
    children: ReactNode | ReactNode[];
    /** When specified, uses this as the hover-over element, otherwise uses default info icon. */
    target?: ReactNode;
    marginTop?: number;
    marginLeft?: number;
    position?: 'left' | 'right';
};
/**
 * Encapsulates a hint that can be displayed on hover.
 * By default, creates an `info_outline` icon that displays the supplied children within a popup on hover.
 * You can override this by supplying your own target component.
 *
 * An accompanying `aria-describedby` should be set on the described element.
 * Ex: `<div aria-describedby='my-hint'>Something confusing</div><Hint id='my-hint'>More info</Hint>`
 * @return {Hint}
 */
export const Hint = ({ id, children, target, marginTop = 0, marginLeft = 0, position = 'right' }: Props) => {
    const [visible, setVisible] = useState(false);
    const targetRef = useRef<HTMLDivElement>(null);

    const calcOffset = useCallback(() => {
        const targetHeight = targetRef.current?.clientHeight || DEFAULT_TOP;
        const targetWidth = targetRef.current?.clientWidth || DEFAULT_LEFT;
        const top = marginTop + targetHeight;
        const left = position === 'left' ? marginLeft - LEFT_OFFSET + targetWidth : marginLeft;
        return { top, left };
    }, [targetRef, marginTop, marginLeft, position]);

    const offset = calcOffset();

    return (
        <div className={styles.hint}>
            <div
                ref={targetRef}
                className={styles.target}
                onMouseEnter={() => setVisible(true)}
                onMouseLeave={() => setVisible(false)}>
                {target || <Icon name="info_outline" sizing="small" className={styles.icon} />}
            </div>
            <Shown when={visible}>
                <div id={id} className={styles.panel} style={{ top: offset.top, left: offset.left }}>
                    {children}
                </div>
            </Shown>
        </div>
    );
};



