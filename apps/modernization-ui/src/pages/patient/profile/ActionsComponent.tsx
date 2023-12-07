import { Button, Icon } from '@trussworks/react-uswds';
import { Actions as ActionState } from 'components/Table/Actions';
import { Patient } from './Patient';

type ActionProps = {
    patient: Patient | undefined;
    index: number;
    handleAction: (type: string) => void;
    setIsActions: (action: number | null) => void;
    isActions: number | null;
};

export const ActionsComponent = ({ patient, index, handleAction, setIsActions, isActions }: ActionProps) => {
    return (
        <div className="table-span">
            <Button
                type="button"
                unstyled
                disabled={patient?.status !== 'ACTIVE'}
                onClick={() => setIsActions(isActions === index ? null : index)}>
                <Icon.MoreHoriz className="font-sans-lg" />
            </Button>
            {isActions === index && (
                <ActionState handleOutsideClick={() => setIsActions(null)} handleAction={handleAction} />
            )}
        </div>
    );
};
