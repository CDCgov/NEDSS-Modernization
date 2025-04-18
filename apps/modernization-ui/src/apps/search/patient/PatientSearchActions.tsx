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
        <Permitted include={[permissions.patient.add, permissions.labReport.add]} mode="any">
            <Permitted include={[permissions.patient.add]}>
                <Button
                    type="button"
                    onClick={add}
                    disabled={disabled}
                    icon={<Icon name="add_circle" />}
                    labelPosition="right">
                    Add new patient
                </Button>
            </Permitted>
        </Permitted>
    );
};

export { PatientSearchActions };
