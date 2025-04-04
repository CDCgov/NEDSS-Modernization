import { useAlert } from 'alert';
import { Shown } from 'conditional-render';
import { useEffect, useState } from 'react';
import { useDataElements } from '../api/useDataElements';
import { AlgorithmExport, ImportConfigurationModal } from './import/ImportConfigurationModal';
import { ImportPreview } from './import/ImportPreview';
import styles from './match-configuration-landing.module.scss';
import { MatchConfiguration } from './match-configuration/MatchConfiguration';

export const MatchConfigurationLandingPage = () => {
    const { showError } = useAlert();
    const { dataElements, error, loading } = useDataElements();
    const [importedDataElements] = useState<AlgorithmExport | undefined>();
    const [showImportModal, setShowImportModal] = useState(false);

    useEffect(() => {
        if (error) {
            showError({ message: 'Failed to retrieve data elements' });
        }
    }, [error]);

    const handleAlgorithmImport = (algorithm: AlgorithmExport) => {
        console.log('import algorithm: ', algorithm);
        setShowImportModal(false);
    };

    return (
        <div className={styles.matchConfigurationLandingPage}>
            <Shown when={importedDataElements !== undefined}>
                <ImportPreview />
            </Shown>
            <Shown when={importedDataElements === undefined}>
                <MatchConfiguration
                    dataElements={dataElements}
                    loading={loading}
                    onImportClick={() => setShowImportModal(true)}
                />
            </Shown>
            <ImportConfigurationModal
                visible={showImportModal}
                onImport={handleAlgorithmImport}
                onCancel={() => setShowImportModal(false)}
            />
        </div>
    );
};
