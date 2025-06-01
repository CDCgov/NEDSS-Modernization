import { Button } from 'components/button';
import { useAddPatientFromSearch } from './add/useAddPatientFromSearch';
import { permissions, Permitted } from 'libs/permission';

type Props = {
    disabled: boolean;
};

const PatientSearchActions = ({ disabled }: Props) => {
    const { add } = useAddPatientFromSearch();

    return (
        <Permitted permission={permissions.patient.add}>
            <Button type="button" onClick={add} disabled={disabled} icon="add_circle">
                Add new patient
            </Button>
        </Permitted>
    );
};

export { PatientSearchActions };
