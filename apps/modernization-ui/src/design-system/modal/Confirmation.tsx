import { ReactNode } from 'react';
import { Modal } from './Modal';
import { Message } from 'design-system/message';

type Props = {
    title?: string;
    children?: ReactNode;
    confirmText?: string;
    onConfirm: () => void;
    cancelText?: string;
    onCancel: () => void;
};

const Confirmation = ({
    title = 'Confirmation',
    children,
    confirmText = 'Confirm',
    onConfirm,
    cancelText = 'No, go back',
    onCancel
}: Props) => {
    return (
        <Modal
            id={`confirmation-${title}`}
            title={title}
            onClose={onCancel}
            footer={(close) => (
                <>
                    <button type="button" className="usa-button usa-button--outline" onClick={close} data-close-modal>
                        {cancelText}
                    </button>
                    <button type="button" className="usa-button usa-button" onClick={onConfirm}>
                        {confirmText}
                    </button>
                </>
            )}>
            <Message type="warning">{children}</Message>
        </Modal>
    );
};

export { Confirmation };
