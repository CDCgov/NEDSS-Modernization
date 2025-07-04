import { ReactNode } from 'react';
import { LoadingIndicator } from '../indicator';

import styles from './loading-overlay.module.scss';

type LoadingOverlayProps = {
    className?: string;
    children: ReactNode | ReactNode[];
};

const LoadingOverlay = ({ children }: LoadingOverlayProps) => {
    return (
        <div className={styles.container}>
            <LoadingIndicator />
            <span className={styles.glass}></span>
            <span>{children}</span>
        </div>
    );
};

export { LoadingOverlay };
