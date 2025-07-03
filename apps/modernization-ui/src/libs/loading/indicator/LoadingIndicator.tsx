import classNames from 'classnames';
import styles from './loading-indicator.module.scss';

type LoadingIndicatorProps = {
    className?: string;
};

const LoadingIndicator = ({ className }: LoadingIndicatorProps) => (
    <span className={classNames(styles.indicator, className)} role="status">
        <span className={styles['screen-reader']}>Loading</span>
    </span>
);

export { LoadingIndicator };
