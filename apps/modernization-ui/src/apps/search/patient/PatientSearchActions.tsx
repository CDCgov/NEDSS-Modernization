import { Button } from 'components/button';
import { useAddPatientFromSearch } from './add/useAddPatientFromSearch';
import { permissions, Permitted } from 'libs/permission';
import { Icon } from 'design-system/icon';

type Props = {
    disabled: boolean;
};

const PatientSearchActions = ({ disabled }: Props) => {
    const { add } = useAddPatientFromSearch();

    return (
        <Permitted permission={permissions.patient.add}>
            <Button 
                accessKey="a" 
                aria-keyshortcuts="Alt+A"
                type="button" 
                onClick={add} 
                disabled={disabled} 
                icon={<Icon name="add_circle" />}>
                Add new patient
            </Button>
        </Permitted>
    );
};

export { PatientSearchActions };
