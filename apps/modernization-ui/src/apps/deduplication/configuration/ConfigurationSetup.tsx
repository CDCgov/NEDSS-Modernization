import { Icon } from '@trussworks/react-uswds';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import styles from './configurationSetup.module.scss';

export const ConfigurationSetup = () => {
    return <ConfigurationSetupContent />;
};

const ConfigurationSetupContent = () => {
    return (
        <div className={styles.configurationSetup}>
            <header>
                <Heading level={1}>Person match configuration</Heading>
            </header>
            <main>
                <Shown when={true /* This should be replaced with a valid condition */}>
                    <div className={styles.card}>
                        {/* Card */}
                        <div className={styles.cardHeader}>
                            {/* Header */}
                            <Heading level={2} className={styles.title}>
                                Algorithm not configured
                            </Heading>
                        </div>

                        <div className={styles.cardBody}>
                            {/* Body */}
                            <p className={styles.description}>
                                Before configuring the algorithm, the data elements available to the algorithm must
                                first be set. To get started, you may manually update the data elements or upload a
                                configuration file.
                            </p>

                            <div className={styles.buttonContainer}>
                                <Button sizing="medium">
                                    <Icon.Settings size={3} />
                                    {/* Todo: This button should nav to deduplication/data-elements */}
                                    Configure data elements
                                </Button>

                                <Button sizing="medium">
                                    <Icon.UploadFile size={3} />
                                    {/* Todo: This button should open a pop-up for import */}
                                    Import configuration file
                                </Button>
                            </div>
                        </div>
                    </div>
                </Shown>
            </main>
        </div>
    );
};
