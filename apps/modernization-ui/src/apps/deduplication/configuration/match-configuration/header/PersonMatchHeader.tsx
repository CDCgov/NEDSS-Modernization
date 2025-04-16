import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import styles from './person-match-header.module.scss';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { useMatchConfiguration } from 'apps/deduplication/api/useMatchConfiguration';

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
                    <Button
                        icon={<Icon name="settings" />}
                        labelPosition="right"
                        secondary
                        onClick={onConfigureDataElementsClick}>
                        Configure data elements
                    </Button>
                    <Button onClick={onImportClick} icon={<Icon name="file_upload" />} sizing="medium" secondary />
                    <Button onClick={exportAlgorithm} icon={<Icon name="file_download" />} sizing="medium" secondary />
                </div>
            </Shown>
        </header>
    );
};
