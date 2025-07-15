import { AlgorithmExport } from 'apps/deduplication/api/model/AlgorithmExport';
import { Shown } from 'conditional-render';
import { useEffect, useState } from 'react';
import { useDataElements } from '../api/useDataElements';
import { useMatchConfiguration } from '../api/useMatchConfiguration';
import { ImportConfigurationModal } from './import/ImportConfigurationModal';
import { ImportPreview } from './import/importPreview/ImportPreview';
import styles from './match-configuration-landing.module.scss';
import { MatchConfiguration } from './match-configuration/MatchConfiguration';
import { useAlert } from 'libs/alert';

export const MatchConfigurationLandingPage = () => {
    const { showError, showAlert } = useAlert();
    const { fetchDataElements, dataElements, error, loading } = useDataElements();
    const { importAlgorithm, error: importError } = useMatchConfiguration(true);
    const [previewedAlgorithm, setPreviewedAlgorithm] = useState<
        { algorithm: AlgorithmExport; fileName: string } | undefined
    >();
    const [showImportModal, setShowImportModal] = useState(false);

    useEffect(() => {
        if (error) {
            showError('Failed to retrieve data elements');
        }
    }, [error]);

    useEffect(() => {
        if (importError) {
            showError('Failed to import algorithm');
        }
    }, [importError]);

    const handleAlgorithmPreview = (fileName: string, algorithm: AlgorithmExport) => {
        setShowImportModal(false);
        setPreviewedAlgorithm({ algorithm, fileName });
    };

    const handleAlgorithmUpload = () => {
        if (previewedAlgorithm) {
            importAlgorithm(previewedAlgorithm.algorithm, () => {
                showAlert({
                    type: 'success',
                    title: 'Success',
                    message: (
                        <span>
                            You have successfully imported the configuration file{' '}
                            <strong>{previewedAlgorithm.fileName}</strong>
                        </span>
                    )
                });
                fetchDataElements();
                setPreviewedAlgorithm(undefined);
            });
        }
    };

    return (
        <div className={styles.matchConfigurationLandingPage}>
            <Shown
                when={previewedAlgorithm === undefined}
                fallback={
                    <ImportPreview
                        previewedAlgorithm={previewedAlgorithm?.algorithm!}
                        onAccept={handleAlgorithmUpload}
                        onCancel={() => setPreviewedAlgorithm(undefined)}
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
                onImport={handleAlgorithmPreview}
                onCancel={() => setShowImportModal(false)}
            />
        </div>
    );
};
