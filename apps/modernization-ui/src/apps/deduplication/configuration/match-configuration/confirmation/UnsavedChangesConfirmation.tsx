import { Shown } from 'conditional-render';
import { Confirmation } from 'design-system/modal';

type Props = {
    passName: string;
    visible: boolean;
    onAccept: () => void;
    onCancel: () => void;
};
export const UnsavedChangesConfirmation = ({ passName, visible, onAccept, onCancel }: Props) => {
    return (
        <Shown when={visible}>
            <Confirmation
                onConfirm={onAccept}
                title="Unsaved changes"
                confirmText="Yes, leave"
                cancelText="No, back to configuration"
                forceAction
                onCancel={onCancel}>
                Leaving this screen before saving will result in loss of data for <strong>{passName}</strong>. Are you
                sure you want to proceed?
            </Confirmation>
        </Shown>
    );
};
