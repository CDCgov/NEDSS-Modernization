import { Icon } from '@trussworks/react-uswds';
import { AlertProvider, useAlert } from 'alert';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { useEffect, useState } from 'react';
import { useForm, useWatch } from 'react-hook-form';
import NavBar from 'shared/header/NavBar';
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
    const form = useForm<{ passes?: any }>({ mode: 'onBlur' });
    const watch = useWatch({ control: form.control });

    if (!form || !form.control) {
        console.error('useForm() did not return a valid form object.');
        return null; // Prevents rendering the component if form is invalid
    }

    useEffect(() => {
        if (error) {
            showError({ message: error });
        }
    }, [error]);

    return (
        <div className={styles.configurationSetup}>
            <NavBar title="Person Match Configuration" />
            <header>
                <Heading level={1}>Person match configuration</Heading>
            </header>
            <main>
                <Shown when={!watch?.passes}>
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
