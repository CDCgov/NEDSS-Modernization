import { Pass } from 'apps/deduplication/api/model/Pass';
import { DataElements } from 'apps/deduplication/data-elements/DataElement';
import { useState } from 'react';
import { ImportModal } from './importModal/ImportModal';

export type AlgorithmExport = {
    dataElements: DataElements;
    algorithm: { passes: Pass[] };
};

type Props = {
    visible: boolean;
    onImport: (algorithm: AlgorithmExport) => void;
    onCancel: () => void;
};
export const ImportConfigurationModal = ({ visible, onImport, onCancel }: Props) => {
    const [importError, setImportError] = useState<string | undefined>();

    const parseFile = (file: File) => {
        setImportError(undefined);
        file.text().then((content) => {
            try {
                const algorithm: AlgorithmExport = JSON.parse(content);
                onImport(algorithm);
            } catch (error) {
                setImportError(
                    'The imported JSON file was invalid. Please review the file and ensure the file is the appropriate format and all values are valid.'
                );
            }
        });
    };

    const handleCancel = () => {
        setImportError(undefined);
        onCancel();
    };

    const dropSectionContent = (
        <>
            Drag configuration file here or <span>choose from folder</span>
        </>
    );

    return (
        <ImportModal
            id="import-configuration-modal"
            title="Import configuration file"
            infoText="Input accepts only JSON file type"
            dropSectionContent={dropSectionContent}
            accept=".json"
            visible={visible}
            error={importError}
            onImport={parseFile}
            onCancel={handleCancel}
        />
    );
};
