import { Icon } from '@trussworks/react-uswds';
import { Button } from 'design-system/button';
import { NotificationCard } from './NotificationCard';

export const AlgorithmNotConfigured = () => {
    return (
        <NotificationCard
            heading="Algorithm not configured"
            body={
                <span>
                    To configure the algorithm, first configure the data elements used in the algorithm.
                    <br />
                    Get started by manually configuring the data elements or importing a configuration file.
                </span>
            }
            buttons={
                <>
                    <Button sizing="medium">
                        <Icon.Settings size={3} />
                        Configure data elements
                    </Button>

                    <Button sizing="medium">
                        <Icon.UploadFile size={3} />
                        Import configuration file
                    </Button>
                </>
            }
        />
    );
};
