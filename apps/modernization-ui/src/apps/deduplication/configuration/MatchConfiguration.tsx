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
import { useMatchConfiguration } from '../api/useMatchConfiguration';
import { Loading } from 'components/Spinner';

export const MatchConfiguration = () => {
    const { showError } = useAlert();
    const nav = useNavigate();
    const { dataElements, error, loading } = useDataElements();
    const { exportAlgorithm } = useMatchConfiguration(true);

    useEffect(() => {
        if (error) {
            showError({ message: 'Failed to retrieve data elements' });
        }
    }, [error]);

    const dataElementsConfigured = (): boolean => {
        // check if dataElements are undefined
        if (dataElements === undefined) {
            return false;
        }
        // attempt to find an entry that is active
        return Object.values(dataElements).find((d) => d.active) !== undefined;
    };

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
                    <Button onClick={exportAlgorithm} icon={<Icon name="file_download" />} sizing="medium" secondary />
                </div>
            </header>
            <main>
                <Shown when={!loading} fallback={<Loading />}>
                    <Shown when={dataElementsConfigured()} fallback={<AlgorithmNotConfigured />}>
                        {dataElements && <PassConfiguration dataElements={dataElements} />}
                    </Shown>
                </Shown>
            </main>
        </div>
    );
};
