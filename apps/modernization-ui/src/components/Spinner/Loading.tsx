import classNames from 'classnames';
import styles from './loading.module.scss';

type Props = {
    className?: string;
    background?: 'clear' | 'white' | 'colored';
};

const Loading = ({ className, background = 'clear' }: Props) => (
    <span
        className={classNames(styles.loading, className, {
            [styles.white]: background === 'white',
            [styles.colored]: background === 'colored'
        })}>
        <span className={styles.icon} role="status">
            <span className={styles['screen-reader']}>Loading</span>
        </span>
    </span>
);

export { Loading };
