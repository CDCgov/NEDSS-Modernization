import { ReactNode } from 'react';
import { Modal } from './Modal';
import { Message } from 'design-system/message';
import { Button } from 'design-system/button';

type Props = {
    title?: string;
    children?: ReactNode;
    confirmText?: string;
    destructive?: boolean;
    forceAction?: boolean;
    onConfirm: () => void;
    cancelText?: string;
    onCancel: () => void;
};

const Confirmation = ({
    title = 'Confirmation',
    children,
    confirmText = 'Confirm',
    forceAction,
    destructive = false,
    onConfirm,
    cancelText = 'No, go back',
    onCancel
}: Props) => {
    return (
        <Modal
            id={`confirmation-${title}`}
            size="small"
            title={title}
            forceAction={forceAction}
            onClose={onCancel}
            footer={(close) => (
                <>
                    <Button onClick={close} secondary data-close-modal>
                        {cancelText}
                    </Button>
                    <Button onClick={onConfirm} destructive={destructive}>
                        {confirmText}
                    </Button>
                </>
            )}>
            <Message type="warning">{children}</Message>
        </Modal>
    );
};

export { Confirmation };
