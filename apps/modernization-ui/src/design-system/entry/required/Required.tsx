import styles from './required.module.scss';

type RequiredProps = {
    message?: string;
};

const Required = ({ message = 'Required' }: RequiredProps) => <span className={styles.required}>{message}</span>;

export { Required };
