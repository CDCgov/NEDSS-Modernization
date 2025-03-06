import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { useDataElements } from '../api/useDataElements';
import { NoConfiguration } from './no-configuration/NoConfiguration';
import styles from './match-configuration.module.scss';
import { PassConfiguration } from './pass-configuration/PassConfiguration';

export const MatchConfiguration = () => {
    const { dataElements } = useDataElements();

    return (
        <div className={styles.configurationSetup}>
            <header>
                <Heading level={1}>Person match configuration</Heading>
            </header>
            <main>
                <Shown when={dataElements !== undefined} fallback={<NoConfiguration />}>
                    <PassConfiguration />
                </Shown>
            </main>
        </div>
    );
};
