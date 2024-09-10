import { ReactNode, useState } from 'react';
import classNames from 'classnames';

import styles from './collapsible-panel.module.scss';
import { Icon } from 'design-system/icon';

type Props = {
    id?: string;
    children: ReactNode;
    className?: string;
    contentClassName?: string;
    ariaLabel?: string;
};

const CollapsiblePanel = ({ children, id, className, contentClassName, ariaLabel }: Props) => {
    const [collapsed, setCollapsed] = useState<boolean>(false);

    return (
        <div id={id} className={classNames(styles.collapsible, className, { [styles.collapsed]: collapsed })}>
            <div className={styles.boundary}>
                <div className={classNames(styles.content, contentClassName)} aria-hidden={collapsed}>
                    {children}
                </div>
            </div>
            <button
                type="button"
                className={classNames(styles.control, { [styles.collapsed]: collapsed })}
                aria-label={collapsed ? `Show ${ariaLabel}` : `Hide ${ariaLabel}`}
                onClick={() => setCollapsed((current) => !current)}>
                <Icon name={collapsed ? 'expand_more' : 'expand_less'} />
            </button>
        </div>
    );
};

export { CollapsiblePanel };
