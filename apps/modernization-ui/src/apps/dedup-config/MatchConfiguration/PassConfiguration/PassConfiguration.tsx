import styles from './PassConfiguration.module.scss';

const PassConfiguration = () => {
    return (
        <div className={styles.configurationDetails}>
            <div className={styles.criteria}>Blocking criteria</div>
            <div className={styles.criteria}>Matching criteria</div>
            <div className={styles.matchingBounds}>Matching bounds</div>
        </div>
    );
};

export default PassConfiguration;
