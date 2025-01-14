import classNames from 'classnames';
import styles from './investigation-status.module.scss';

type Props = {
    status: 'open' | 'closed';
};

const InvestigationStatus = ({ status }: Props) => (
    <span
        className={classNames(styles.status, {
            [styles.open]: status === 'open',
            [styles.closed]: status === 'closed'
        })}>
        {status}
    </span>
);

export { InvestigationStatus };
