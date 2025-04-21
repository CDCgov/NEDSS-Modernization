import { Shown } from 'conditional-render';
import { Icon } from 'design-system/icon';
import { ReactNode, useMemo, useState } from 'react';
import styles from './hint.module.scss';

const DEFAULT_TOP = 26;
// width of panel (283px) - width of icon (20px) = 263
const LEFT_OFFSET = 263;

type Props = {
    id: string;
    children: ReactNode | ReactNode[];
    marginTop?: number;
    marginLeft?: number;
    position?: 'left' | 'right';
};
/**
 * Creates an `info_outline` icon that displays the supplied children within a popup on hover.
 *
 * An accompanying `aria-describedby` should be set on the described element.
 *
 * Ex: `<div aria-describedby='my-hint'>Something confusing</div><Hint id='my-hint'>More info</Hint>`
 * @return {Hint}

 */
export const Hint = ({ id, children, marginTop = 0, marginLeft = 0, position = 'right' }: Props) => {
    const [visible, setVisible] = useState(false);
    const [offset, setOffset] = useState<{ top: number; left: number }>({
        top: marginTop + DEFAULT_TOP,
        left: marginLeft + 0
    });

    useMemo(() => {
        if (position === 'left') {
            setOffset({ top: marginTop + DEFAULT_TOP, left: marginLeft - LEFT_OFFSET });
        } else {
            setOffset({ top: marginTop + DEFAULT_TOP, left: marginLeft + 0 });
        }
    }, [marginLeft, marginTop, position]);

    return (
        <div className={styles.infoIcon}>
            <Icon
                onMouseEnter={() => setVisible(true)}
                onMouseLeave={() => setVisible(false)}
                name="info_outline"
                sizing="small"
                className={styles.icon}
            />
            <Shown when={visible}>
                <div id={id} className={styles.panel} style={{ top: offset.top, left: offset.left }}>
                    {children}
                </div>
            </Shown>
        </div>
    );
};
