import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { useDataElements } from '../api/useDataElements';
import { AlgorithmNotConfigured } from './notification-cards/AlgorithmNotConfigured';
import { PassConfiguration } from './pass-configuration/PassConfiguration';
import styles from './match-configuration.module.scss';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';

export const MatchConfiguration = () => {
    const { configuration } = useDataElements();

    return (
        <div className={styles.configurationSetup}>
            <header>
                <Heading level={1}>Person match configuration</Heading>
                <div className={styles.buttons}>
                    <Button icon={<Icon name="settings" />} labelPosition="right" secondary>
                        Configure data elements
                    </Button>
                    <Button icon={<Icon name="file_upload" />} sizing="medium" secondary />
                    <Button icon={<Icon name="file_download" />} sizing="medium" secondary />
                </div>
            </header>
            <main>
                <Shown when={configuration !== undefined} fallback={<AlgorithmNotConfigured />}>
                    <PassConfiguration />
                </Shown>
            </main>
        </div>
    );
};
