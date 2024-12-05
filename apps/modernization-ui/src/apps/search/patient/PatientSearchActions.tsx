import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { Button } from 'components/button';
import { useAddPatientFromSearch } from './add/useAddPatientFromSearch';

type Props = {
    disabled: boolean;
};

const PatientSearchActions = ({ disabled }: Props) => {
    const { add } = useAddPatientFromSearch();

    return (
        <ButtonActionMenu label="Add new" disabled={disabled}>
            <>
                <Button type="button" onClick={add}>
                    Add new patient
                </Button>
                <Button onClick={() => (window.location.href = '/nbs/MyTaskList1.do?ContextAction=AddLabDataEntry')}>
                    Add new lab report
                </Button>
            </>
        </ButtonActionMenu>
    );
};

export { PatientSearchActions };
