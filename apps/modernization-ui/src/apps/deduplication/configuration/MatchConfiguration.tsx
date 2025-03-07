import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { useDataElements } from '../api/useDataElements';
import { AlgorithmNotConfigured } from './notification-cards/AlgorithmNotConfigured';
import { PassConfiguration } from './pass-configuration/PassConfiguration';
import styles from './match-configuration.module.scss';

export const MatchConfiguration = () => {
    const { dataElements } = useDataElements();

    return (
        <div className={styles.configurationSetup}>
            <header>
                <Heading level={1}>Person match configuration</Heading>
            </header>
            <main>
                <Shown when={dataElements !== undefined} fallback={<AlgorithmNotConfigured />}>
                    <PassConfiguration />
                </Shown>
            </main>
        </div>
    );
};
