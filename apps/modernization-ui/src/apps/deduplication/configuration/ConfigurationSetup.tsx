import { Icon } from '@trussworks/react-uswds';
import { AlertProvider, useAlert } from 'alert';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { NavLink } from 'react-router-dom';
import { useEffect, useState } from 'react';
import styles from './configurationSetup.module.scss';

export const ConfigurationSetup = () => {
    return (
        <AlertProvider>
            <ConfigurationSetupContent />
        </AlertProvider>
    );
};

const ConfigurationSetupContent = () => {
    const { showError } = useAlert();
    const [error] = useState<string | null>(null);

    useEffect(() => {
        if (error) {
            showError({ message: error });
        }
    }, [error, showError]);

    return (
        <div className={styles.headers}>
            {/* Top White Box for the Header */}
            <header className={styles.headerBox}>
                <Heading level={1}>Person match configuration</Heading>
            </header>

            {/* Main Content in a Separate White Box */}
            <main className={styles.contentBox}>
                <h2 className={styles.title}>Algorithm not configured</h2>
                <p className={styles.description}>
                    Before configuring the algorithm, the data elements available to the algorithm must first be set.
                    <br />
                    To get started, you may manually update the data elements or upload a configuration file.
                </p>
                <div className={styles.buttonContainer}>
                    <NavLink to={'/deduplication/data-elements'}>
                        <Button>
                            <Icon.Settings size={3} />
                            Configure data elements
                        </Button>
                    </NavLink>

                    <Button>
                        <Icon.UploadFile size={3} /> {/* placeholder icon */}
                        Import configuration file
                    </Button>
                </div>
            </main>
        </div>
    );
};
