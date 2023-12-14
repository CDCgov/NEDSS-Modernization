import { Button, Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';

import styles from './page-information.module.scss';

const PageInformation = () => {
    return (
        <section className={styles.information}>
            <header>
                <Heading level={2}>Page information</Heading>
                <Button type="button" outline className={styles.icon}>
                    <Icon.FileDownload />
                    Metadata
                </Button>
            </header>
            <nav></nav>
            <div className={styles.content}>
                <footer>
                    <Button type="button" outline className={styles.icon}>
                        <Icon.Visibility />
                        View page details
                    </Button>
                </footer>
            </div>
        </section>
    );
};

export { PageInformation };
