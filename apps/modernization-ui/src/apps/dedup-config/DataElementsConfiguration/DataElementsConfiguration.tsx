import { Button, Icon } from '@trussworks/react-uswds';
import { useDataElementsContext } from '../context/DataElementsContext';
import { useDedupeContext } from '../context/DedupeContext';

const DataElementsConfiguration = () => {
    const { dataElements } = useDataElementsContext();
    const { setMode } = useDedupeContext();
    console.log(dataElements);
    return (
        <div>
            <div>
                <h2>
                    <Icon.ArrowBack onClick={() => setMode('patient')} /> Data elements configuration
                </h2>
                <Button unstyled type="button" onClick={() => setMode('patient')}>
                    <Icon.Settings /> Patient match configuration
                </Button>
            </div>
        </div>
    );
};

export default DataElementsConfiguration;
