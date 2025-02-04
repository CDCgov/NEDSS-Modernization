import { ReactNode } from 'react';
import { Loading } from 'components/Spinner';

import styles from './loading-panel.module.scss';
import { Shown } from 'conditional-render';
import classNames from 'classnames';

type LoadingPanelProps = {
    className?: string;
    loading: boolean;
    children: ReactNode | ReactNode[];
};

const LoadingPanel = ({ loading = false, children, className }: LoadingPanelProps) => {
    return (
        <>
            <Shown when={loading}>
                <span className={styles.panel}>
                    <span className={classNames(styles.loader, className)}>
                        <Loading center />
                    </span>
                </span>
            </Shown>
            {children}
        </>
    );
};

export { LoadingPanel };
