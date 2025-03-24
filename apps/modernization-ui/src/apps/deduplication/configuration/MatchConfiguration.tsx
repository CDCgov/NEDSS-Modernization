import { useAlert } from 'alert';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { useEffect } from 'react';
import { useNavigate } from 'react-router';
import { useDataElements } from '../api/useDataElements';
import styles from './match-configuration.module.scss';
import { AlgorithmNotConfigured } from './notification-cards/AlgorithmNotConfigured';
import { PassConfiguration } from './pass-configuration/PassConfiguration';

export const MatchConfiguration = () => {
    const { showError } = useAlert();
    const nav = useNavigate();
    const { dataElements, error } = useDataElements();

    useEffect(() => {
        if (error) {
            showError({ message: 'Failed to retrieve data elements' });
        }
    }, [error]);

    return (
        <div className={styles.configurationSetup}>
            <header>
                <Heading level={1}>Person match configuration</Heading>
                <div className={styles.buttons}>
                    <Button
                        icon={<Icon name="settings" />}
                        labelPosition="right"
                        secondary
                        onClick={() => nav('/deduplication/data_elements')}>
                        Configure data elements
                    </Button>
                    <Button icon={<Icon name="file_upload" />} sizing="medium" secondary />
                    <Button icon={<Icon name="file_download" />} sizing="medium" secondary />
                </div>
            </header>
            <main>
                <Shown when={dataElements !== undefined} fallback={<AlgorithmNotConfigured />}>
                    {dataElements && <PassConfiguration dataElements={dataElements} />}
                </Shown>
            </main>
        </div>
    );
};
