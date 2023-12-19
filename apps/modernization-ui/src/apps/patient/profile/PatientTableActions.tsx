import { Button, Icon } from '@trussworks/react-uswds';
import { Actions } from 'components/Table/Actions';

type ActionType = 'add' | 'update' | 'delete' | 'details';

type ActionProps = {
    index: number;
    handleAction: (type: ActionType) => void;
    setActiveIndex: (action: number | null) => void;
    activeIndex: number | null;
    disabled?: boolean;
    notDeletable?: boolean;
};

export const PatientTableActions = ({
    index,
    handleAction,
    setActiveIndex,
    activeIndex,
    disabled,
    notDeletable
}: ActionProps) => {
    return (
        <div className="table-span">
            <Button
                type="button"
                unstyled
                disabled={disabled}
                onClick={() => setActiveIndex(activeIndex === index ? null : index)}>
                <Icon.MoreHoriz className="font-sans-lg" />
            </Button>
            {activeIndex === index && (
                <Actions
                    handleOutsideClick={() => setActiveIndex(null)}
                    handleAction={handleAction}
                    notDeletable={notDeletable}
                />
            )}
        </div>
    );
};
