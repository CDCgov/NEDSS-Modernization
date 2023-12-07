import { Button, Icon } from '@trussworks/react-uswds';
import { Actions } from 'components/Table/Actions';
import { Patient } from './Patient';

type ActionType = 'add' | 'update' | 'delete' | 'details';

type ActionProps = {
    patient: Patient | undefined;
    index: number;
    handleAction: (type: ActionType) => void;
    setActionIndex: (action: number | null) => void;
    actionIndex: number | null;
};

export const TableActions = ({ patient, index, handleAction, setActionIndex, actionIndex }: ActionProps) => {
    return (
        <div className="table-span">
            <Button
                type="button"
                unstyled
                disabled={patient?.status !== 'ACTIVE'}
                onClick={() => setActionIndex(actionIndex === index ? null : index)}>
                <Icon.MoreHoriz className="font-sans-lg" />
            </Button>
            {actionIndex === index && (
                <Actions handleOutsideClick={() => setActionIndex(null)} handleAction={handleAction} />
            )}
        </div>
    );
};
