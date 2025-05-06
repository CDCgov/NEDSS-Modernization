import { Button } from 'components/button';
import { useAddPatientFromSearch } from './add/useAddPatientFromSearch';
import { permissions, Permitted } from 'libs/permission';
import { Icon } from 'design-system/icon';
import { useFocusAddNewPatientButton } from './useFocusAddNewPatientButton';

type Props = {
    disabled: boolean;
};

const PatientSearchActions = ({ disabled }: Props) => {
    const { add } = useAddPatientFromSearch();
    useFocusAddNewPatientButton();

    return (
        <Permitted permission={permissions.patient.add}>
            <Button
                id="add-new-patient-button"
                type="button"
                onClick={add}
                disabled={disabled}
                icon={<Icon name="add_circle" />}
                labelPosition="right"
                tabIndex={0}>
                Add new patient
            </Button>
        </Permitted>
    );
};

export { PatientSearchActions };
