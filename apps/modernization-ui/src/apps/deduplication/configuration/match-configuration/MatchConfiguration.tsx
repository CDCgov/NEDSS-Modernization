import { Heading } from 'components/heading';
import { Loading } from 'components/Spinner';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { useMemo } from 'react';
import { useNavigate } from 'react-router';
import { useMatchConfiguration } from '../../api/useMatchConfiguration';
import { DataElements } from '../../api/model/DataElement';
import styles from './match-configuration.module.scss';
import { AlgorithmNotConfigured } from './notification-cards/AlgorithmNotConfigured';
import { PassConfiguration } from './pass-configuration/PassConfiguration';

type Props = {
    loading: boolean;
    dataElements?: DataElements;
    onImportClick: () => void;
};
export const MatchConfiguration = ({ loading, dataElements, onImportClick }: Props) => {
    const nav = useNavigate();
    const { exportAlgorithm } = useMatchConfiguration(true);

    const dataElementsConfigured = useMemo(() => {
        if (dataElements === undefined) {
            return false;
        }
        // attempt to find an entry that is active
        return Object.values(dataElements).find((d) => d.active) !== undefined;
    }, [dataElements]);

    return (
        <>
            <header className={styles.headerSection}>
                <Heading level={1}>Person match configuration</Heading>
                <Shown when={dataElementsConfigured}>
                    <div className={styles.buttons}>
                        <Button
                            icon={<Icon name="settings" />}
                            labelPosition="right"
                            secondary
                            onClick={() => nav('/deduplication/data_elements')}>
                            Configure data elements
                        </Button>
                        <Button onClick={onImportClick} icon={<Icon name="file_upload" />} sizing="medium" secondary />
                        <Button
                            onClick={exportAlgorithm}
                            icon={<Icon name="file_download" />}
                            sizing="medium"
                            secondary
                        />
                    </div>
                </Shown>
            </header>
            <main className={styles.contentSection}>
                <Shown when={!loading} fallback={<Loading />}>
                    <Shown
                        when={dataElementsConfigured}
                        fallback={<AlgorithmNotConfigured onImportClick={onImportClick} />}>
                        {dataElements && <PassConfiguration dataElements={dataElements} />}
                    </Shown>
                </Shown>
            </main>
        </>
    );
};
