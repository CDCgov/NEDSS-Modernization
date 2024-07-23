import { ButtonActionMenu } from 'components/ButtonActionMenu/ButtonActionMenu';
import { Button } from 'components/button';
import { useAddPatientFromSearch } from './add';
import { useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';

const PatientSearchActions = () => {
    const { status } = useSearchResultDisplay();
    const { add } = useAddPatientFromSearch();

    return (
        <ButtonActionMenu label="Add new" disabled={status != 'completed'}>
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
