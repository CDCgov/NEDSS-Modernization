import { ReactNode } from 'react';

import classNames from 'classnames';

import { Loading } from 'components/Spinner';
import { Shown } from 'conditional-render';

import styles from './loading-panel.module.scss';

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
                        <Loading center={true} />
                    </span>
                </span>
            </Shown>
            {children}
        </>
    );
};

export { LoadingPanel };
