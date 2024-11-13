import { Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { NavLink } from 'react-router-dom';
import styles from './match-configuration.module.scss';
import { useDataElements } from '../api/useDataElements';
import { ConfigurationMessage } from './ConfigurationMessage';
import { Shown } from 'conditional-render';

export const MatchConfiguration = () => {
    const { configuration } = useDataElements();
    return (
        <div className={styles.matchConfiguration}>
            <header>
                <Heading level={1}>Patient match configuration</Heading>
                <NavLink to={'/deduplication/data-elements'}>
                    <Icon.Settings size={3} />
                    Data elements configuration
                </NavLink>
            </header>
            <main>
                <Shown when={configuration == null}>
                    <ConfigurationMessage />
                </Shown>

                <Shown when={configuration != null}>Real content!</Shown>
            </main>
        </div>
    );
};
