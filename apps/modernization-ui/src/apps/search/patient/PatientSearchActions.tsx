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
                type="button"
                onClick={add}
                disabled={disabled}
                icon={<Icon name="add_circle" />}
                aria-keyshortcuts="Alt+A"
                accessKey="a">
                Add new patient
            </Button>
        </Permitted>
    );
};

export { PatientSearchActions };
