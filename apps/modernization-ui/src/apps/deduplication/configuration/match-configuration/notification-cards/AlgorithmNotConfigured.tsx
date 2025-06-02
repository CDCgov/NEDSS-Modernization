import { Button } from 'design-system/button';
import { useNavigate } from 'react-router';
import { NotificationCard } from './NotificationCard';

type Props = {
    onImportClick: () => void;
};
export const AlgorithmNotConfigured = ({ onImportClick }: Props) => {
    const nav = useNavigate();
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
                    <Button icon="settings" labelPosition="right" onClick={() => nav('/deduplication/data_elements')}>
                        Configure data elements
                    </Button>

                    <Button icon="file_upload" labelPosition="right" onClick={onImportClick}>
                        Import configuration file
                    </Button>
                </>
            }
        />
    );
};
