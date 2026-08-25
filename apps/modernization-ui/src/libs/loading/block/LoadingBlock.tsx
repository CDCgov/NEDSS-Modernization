import { LoadingIndicator } from '../indicator';

import styles from './loading-block.module.scss';

const LoadingBlock = () => {
    return (
        <div className={styles.block}>
            <LoadingIndicator />
        </div>
    );
};

export { LoadingBlock };
