import styles from './PatientMatchConfig.module.scss';
import { Button, Icon } from '@trussworks/react-uswds';
import DataElementsConfiguration from './DataElementsConfiguration/DataElementsConfiguration';
import { useDedupeContext } from './context/DedupeContext';

const PatientMatchConfiguration = () => {
    const { mode, setMode } = useDedupeContext();

    return (
        <div>
            {mode === 'data' ? (
                <DataElementsConfiguration />
            ) : (
                <>
                    <div className={styles.header}>
                        <h2>Patient match configuration</h2>
                        <Button
                            unstyled
                            type="button"
                            className={styles.dataConfigButton}
                            onClick={() => setMode('data')}>
                            <Icon.Settings size={3} /> Data elements configuration
                        </Button>
                    </div>
                    <div className={styles.content}>CONTENT</div>
                </>
            )}
        </div>
    );
};

export default PatientMatchConfiguration;
