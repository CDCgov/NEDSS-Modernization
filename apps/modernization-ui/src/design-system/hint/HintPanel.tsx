import { ReactNode } from 'react';
import classNames from 'classnames';

import styles from './hint-panel.module.scss';

type HintPanelProps = {
    id: string;
    className?: string;
    children: ReactNode | ReactNode[];
};

const HintPanel = ({ id, className, children }: HintPanelProps) => (
    <div role="tooltip" id={id} className={classNames(styles.panel, className)}>
        {children}
    </div>
);

export { HintPanel };
export type { HintPanelProps };
