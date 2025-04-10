import { AlgorithmExport } from 'apps/deduplication/api/model/AlgorithmExport';
import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import { DataElementsTable } from './data-element-table/DataElementsTable';
import styles from './import-preview.module.scss';
import { PassConfigurationTable } from './pass-configuration-table/PassConfigurationTable';

type Props = {
    previewedAlgorithm: AlgorithmExport;
    onAccept: () => void;
    onCancel: () => void;
};
export const ImportPreview = ({ previewedAlgorithm, onAccept, onCancel }: Props) => {
    return (
        <>
            <header className={styles.importPreviewHeading}>
                <Heading level={1}>Preview configuration</Heading>
                <div className={styles.buttons}>
                    <Button secondary onClick={onCancel}>
                        Cancel
                    </Button>
                    <Button onClick={onAccept}>Add configuration</Button>
                </div>
            </header>
            <main className={styles.importPreviewContent}>
                <DataElementsTable dataElements={previewedAlgorithm.dataElements} />
                <PassConfigurationTable algorithm={previewedAlgorithm.algorithm} />
            </main>
        </>
    );
};
