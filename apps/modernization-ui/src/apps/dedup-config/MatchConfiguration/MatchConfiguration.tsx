import { Button, Icon } from '@trussworks/react-uswds';
import styles from './MatchConfiguration.module.scss';
import NoPassConfigurations from './PassConfiguration/NoPassConfigurations';

const MatchConfiguration = () => {
    return (
        <div className={styles.wrapper}>
            <div className={styles.configurationList}>
                <h3>Pass configurations</h3>
                <p className={styles.noConfigurationText}>No pass configurations have been created.</p>
                <Button unstyled type={'button'}>
                    <Icon.Add />
                    Add new pass configuration
                </Button>
            </div>
            <div className={styles.configurationDetails}>
                <NoPassConfigurations />
            </div>
        </div>
    );
};

export default MatchConfiguration;
