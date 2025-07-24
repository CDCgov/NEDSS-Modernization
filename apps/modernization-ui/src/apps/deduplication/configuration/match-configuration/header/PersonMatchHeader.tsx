import { Button } from 'design-system/button';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { useMatchConfiguration } from 'apps/deduplication/api/useMatchConfiguration';

import styles from './person-match-header.module.scss';

type Props = {
    showButtons?: boolean;
    onImportClick?: () => void;
    onConfigureDataElementsClick?: () => void;
};
export const PersonMatchHeader = ({ showButtons = false, onImportClick, onConfigureDataElementsClick }: Props) => {
    const { exportAlgorithm } = useMatchConfiguration(true);

    return (
        <header className={styles.personMatchHeader}>
            <Heading level={1}>Person match configuration</Heading>
            <Shown when={showButtons}>
                <div className={styles.buttons}>
                    <Button icon="settings" labelPosition="right" secondary onClick={onConfigureDataElementsClick}>
                        Configure data elements
                    </Button>
                    <Button
                        onClick={onImportClick}
                        icon="file_upload"
                        sizing="medium"
                        data-tooltip-position="top"
                        aria-label="Import configuration"
                        secondary
                    />
                    <Button
                        onClick={exportAlgorithm}
                        icon="file_download"
                        sizing="medium"
                        data-tooltip-position="top"
                        data-tooltip-offset="left"
                        aria-label="Export configuration"
                        secondary
                    />
                </div>
            </Shown>
        </header>
    );
};
