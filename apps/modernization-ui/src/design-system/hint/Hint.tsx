import { Shown } from 'conditional-render';
import { Icon } from 'design-system/icon';
import { ReactNode, useEffect, useState } from 'react';
import styles from './hint.module.scss';

const DEFAULT_TOP = 26;

type Props = {
    children: ReactNode | ReactNode[];
    marginTop?: number;
    marginLeft?: number;
    position?: 'left' | 'right';
};
export const Hint = ({ children, marginTop = 0, marginLeft = 0, position = 'right' }: Props) => {
    const [visible, setVisible] = useState(false);
    const [offset, setOffset] = useState<{ top: number; left: number }>({
        top: marginTop + DEFAULT_TOP,
        left: marginLeft + 0
    });

    useEffect(() => {
        if (position === 'left') {
            // width of panel (283px) - width of icon (20px) = 263
            setOffset({ top: marginTop + DEFAULT_TOP, left: marginLeft - 263 });
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
                <div className={styles.panel} style={{ top: offset.top, left: offset.left }}>
                    {children}
                </div>
            </Shown>
        </div>
    );
};
