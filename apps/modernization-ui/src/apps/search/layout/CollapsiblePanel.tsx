import { ReactNode, useState } from 'react';
import { Icon } from '@trussworks/react-uswds';
import classNames from 'classnames';

import styles from './collapsible-panel.module.scss';

type Props = {
    id?: string;
    children: ReactNode;
    className?: string;
    ariaLabel?: string;
};

const CollapsiblePanel = ({ children, id, className, ariaLabel }: Props) => {
    const [collapsed, setCollapsed] = useState<boolean>(false);

    return (
        <div id={id} className={classNames(className, styles.collapsible, { [styles.collapsed]: collapsed })}>
            <span className={styles.wrapper}>
                <span className={styles.content} aria-hidden={collapsed}>
                    {children}
                </span>
            </span>
            <button
                type="button"
                className={classNames(styles.control)}
                aria-label={collapsed ? `Show ${ariaLabel}` : `Hide ${ariaLabel}`}
                onClick={() => setCollapsed((current) => !current)}>
                {!collapsed && <Icon.ExpandLess size={3} aria-hidden />}
                {collapsed && <Icon.ExpandMore size={3} aria-hidden />}
            </button>
        </div>
    );
};

export { CollapsiblePanel };
