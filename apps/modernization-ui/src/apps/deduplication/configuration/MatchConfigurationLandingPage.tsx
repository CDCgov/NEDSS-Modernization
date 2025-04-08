import { useAlert } from 'alert';
import { Shown } from 'conditional-render';
import { useEffect, useState } from 'react';
import { useDataElements } from '../api/useDataElements';
import { AlgorithmExport } from 'apps/deduplication/api/model/AlgorithmExport';
import { ImportConfigurationModal } from './import/ImportConfigurationModal';
import { ImportPreview } from './import/importPreview/ImportPreview';
import { MatchConfiguration } from './match-configuration/MatchConfiguration';
import styles from './match-configuration-landing.module.scss';
import { useMatchConfiguration } from '../api/useMatchConfiguration';

export const MatchConfigurationLandingPage = () => {
    const { showError, showSuccess } = useAlert();
    const { dataElements, error, loading } = useDataElements();
    const { importAlgorithm, error: importError } = useMatchConfiguration(true);
    const [importedAlgorithm, setImportedAlgorithm] = useState<AlgorithmExport | undefined>();
    const [showImportModal, setShowImportModal] = useState(false);

    useEffect(() => {
        if (error) {
            showError({ message: 'Failed to retrieve data elements' });
        }
    }, [error]);

    useEffect(() => {
        if (importError) {
            showError({ message: 'Failed to import algorithm' });
        }
    }, [importError]);

    const handleAlgorithmImport = (algorithm: AlgorithmExport) => {
        setShowImportModal(false);
        setImportedAlgorithm(algorithm);
    };

    const handleAlgorithmUpload = () => {
        if (importedAlgorithm) {
            importAlgorithm(importedAlgorithm, () => {
                setImportedAlgorithm(undefined);
                showSuccess({ message: 'Successfully imported algorithm' });
            });
        }
    };

    return (
        <div className={styles.matchConfigurationLandingPage}>
            <Shown
                when={importedAlgorithm === undefined}
                fallback={
                    <ImportPreview
                        importedAlgorithm={importedAlgorithm!}
                        onAccept={handleAlgorithmUpload}
                        onCancel={() => setImportedAlgorithm(undefined)}
                    />
                }>
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
