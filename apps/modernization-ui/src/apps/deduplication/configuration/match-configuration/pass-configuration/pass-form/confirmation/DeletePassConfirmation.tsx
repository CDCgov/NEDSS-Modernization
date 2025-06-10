import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Message } from 'design-system/message';
import { Modal } from 'design-system/modal';

type Props = {
    passName: string;
    visible: boolean;
    isLastPass: boolean;
    onAccept: () => void;
    onCancel: () => void;
};
export const DeletePassConfirmation = ({ passName, visible, isLastPass, onAccept, onCancel }: Props) => {
    const footer = () => (
        <>
            <Button secondary onClick={onCancel} data-close-modal>
                No, back to configuration
            </Button>
            <Button destructive onClick={onAccept} data-close-modal>
                Yes, delete
            </Button>
        </>
    );
    return (
        <Shown when={visible}>
            <Modal
                id={`confirmation-delete-pass`}
                size="small"
                title="Delete pass configuration"
                onClose={onCancel}
                footer={footer}>
                <Message type="warning">
                    Are you sure you want to delete the configuration for the <strong>{passName}</strong> pass?{' '}
                    {isLastPass && (
                        <>
                            If you delete the only remaining pass configuration, the algorithm will not find any
                            matches.
                        </>
                    )}
                </Message>
            </Modal>
        </Shown>
    );
};
