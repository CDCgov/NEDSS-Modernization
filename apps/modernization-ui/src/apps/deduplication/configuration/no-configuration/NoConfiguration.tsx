import { Icon } from '@trussworks/react-uswds';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import styles from './no-configuration.module.scss';

export const NoConfiguration = () => {
    return (
        <div className={styles.noConfiguration}>
            <div className={styles.card}>
                <div className={styles.cardHeader}>
                    <Heading level={2}>Algorithm not configured</Heading>
                </div>

                <div className={styles.cardBody}>
                    <p className={styles.description}>
                        To configure the algorithm, first configure the data elements used in the algorithm.
                        <br /> Get started by manually configuring the data elements or importing a configuration file.
                    </p>

                    <div className={styles.buttonContainer}>
                        <Button sizing="medium">
                            <Icon.Settings size={3} />
                            Configure data elements
                        </Button>

                        <Button sizing="medium">
                            <Icon.UploadFile size={3} />
                            Import configuration file
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    );
};
