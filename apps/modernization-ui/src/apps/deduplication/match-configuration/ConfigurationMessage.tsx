import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import { Heading } from 'components/heading';
import styles from './configuration-message.module.scss';

export const ConfigurationMessage = () => {
    return (
        <section className={styles.configurationMessage}>
            <Heading level={2}>Data elements not configured</Heading>
            <p>
                To get started configuring the algorithm, the data elements the algorithm will use must first be
                configured.
            </p>
            <NavLinkButton to={'/deduplication/data-elements'}>Configure data elements</NavLinkButton>
        </section>
    );
};
