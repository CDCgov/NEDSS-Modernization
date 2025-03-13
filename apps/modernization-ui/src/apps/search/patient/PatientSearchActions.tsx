import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { Button } from 'components/button';
import { useAddPatientFromSearch } from './add/useAddPatientFromSearch';
import { ADD_PATIENT_PERMISSION, ADD_LAB_REPORT_PERMISSION, Permitted } from 'libs/permission';
import { Icon } from 'design-system/icon';

type Props = {
    disabled: boolean;
};

const PatientSearchActions = ({ disabled }: Props) => {
    const { add } = useAddPatientFromSearch();
    const handleLabReport = () => (window.location.href = '/nbs/MyTaskList1.do?ContextAction=AddLabDataEntry');

    return (
        <Permitted include={[ADD_PATIENT_PERMISSION, ADD_LAB_REPORT_PERMISSION]} mode="any">
            <Permitted include={[ADD_PATIENT_PERMISSION, ADD_LAB_REPORT_PERMISSION]} mode="all">
                <ButtonActionMenu label="Add new" disabled={disabled}>
                    <>
                        <Button type="button" onClick={add}>
                            Add new patient
                        </Button>
                        <Button onClick={handleLabReport}>Add new lab report</Button>
                    </>
                </ButtonActionMenu>
            </Permitted>
            <Permitted include={[ADD_PATIENT_PERMISSION]} exclude={[ADD_LAB_REPORT_PERMISSION]}>
                <Button
                    type="button"
                    onClick={add}
                    disabled={disabled}
                    icon={<Icon name="add_circle" />}
                    labelPosition="right">
                    Add new patient
                </Button>
            </Permitted>
            <Permitted include={[ADD_LAB_REPORT_PERMISSION]} exclude={[ADD_PATIENT_PERMISSION]}>
                <Button
                    type="button"
                    onClick={handleLabReport}
                    disabled={disabled}
                    icon={<Icon name="add_circle" />}
                    labelPosition="right">
                    Add new lab report
                </Button>
            </Permitted>
        </Permitted>
    );
};

export { PatientSearchActions };
