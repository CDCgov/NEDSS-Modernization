import { Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { NavLink } from 'react-router-dom';
import styles from './match-configuration.module.scss';

export const MatchConfiguration = () => {
    return (
        <div className={styles.matchConfiguration}>
            <header>
                <Heading level={1}>Patient match configuration</Heading>
                <NavLink to={'/deduplication/data-elements'}>
                    <Icon.Settings size={3} />
                    Data elements configuration
                </NavLink>
            </header>
        </div>
    );
};
