import { ReactNode, useState } from 'react';
import classNames from 'classnames';

import styles from './collapsible-panel.module.scss';
import { Icon } from 'design-system/icon';

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
                <Icon name={collapsed ? 'expand_more' : 'expand_less'} />
            </button>
        </div>
    );
};

export { CollapsiblePanel };
