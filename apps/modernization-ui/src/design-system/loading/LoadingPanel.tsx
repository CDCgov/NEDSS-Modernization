import { ReactNode } from 'react';
import { Loading } from 'components/Spinner';

import styles from './loading-panel.module.scss';
import { Shown } from 'conditional-render';

type LoadingPanelProps = {
    loading: boolean;
    children: ReactNode | ReactNode[];
};

const LoadingPanel = ({ loading = false, children }: LoadingPanelProps) => {
    return (
        <>
            <Shown when={loading}>
                <span className={styles.panel}>
                    <span className={styles.loader}>
                        <Loading center />
                    </span>
                </span>
            </Shown>
            {children}
        </>
    );
};

export { LoadingPanel };
