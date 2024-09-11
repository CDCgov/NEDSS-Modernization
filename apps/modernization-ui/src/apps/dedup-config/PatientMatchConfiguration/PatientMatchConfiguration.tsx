import styles from './PatientMatchConfig.module.scss';
import { Button, Icon } from '@trussworks/react-uswds';
import DataElementsConfiguration from '../DataElementsConfiguration/DataElementsConfiguration';
import { useDedupeContext } from '../context/DedupeContext';
import { useDataElementsContext } from '../context/DataElementsContext';
import NoDataElements from './NoDataElementsContent';
import PassConfiguration from './PatientMatchConfigurationPage/PatientMatchConfigurationPage';

const PatientMatchConfiguration = () => {
    const { mode, setMode } = useDedupeContext();
    const { dataElements } = useDataElementsContext();

    const handleSetDataMode = () => {
        setMode('data');
    };

    return (
        <div>
            {mode === 'data' ? (
                <DataElementsConfiguration />
            ) : (
                <>
                    <div className={styles.header}>
                        <h2>Patient match configuration</h2>
                        <Button unstyled type="button" className={styles.dataConfigButton} onClick={handleSetDataMode}>
                            <Icon.Settings size={3} /> Data elements configuration
                        </Button>
                    </div>
                    <div className={styles.body}>
                        {!dataElements ? <NoDataElements onConfigClick={handleSetDataMode} /> : <PassConfiguration />}
                    </div>
                </>
            )}
        </div>
    );
};

export default PatientMatchConfiguration;
