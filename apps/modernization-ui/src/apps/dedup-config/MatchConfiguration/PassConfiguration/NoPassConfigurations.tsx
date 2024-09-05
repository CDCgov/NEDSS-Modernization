import styles from './NoPassConfigurations.module.scss';

const NoPassConfigurations = () => {
    return (
        <div className={styles.content}>
            <h3 className={styles.heading}>No pass configurations have been created</h3>
            <p className={styles.text}>
                To get started, select <span>"Add pass configuration"</span> from the left panel.
            </p>
        </div>
    );
};

export default NoPassConfigurations;
