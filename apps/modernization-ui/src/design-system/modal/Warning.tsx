import { ReactNode } from 'react';
import { Modal } from './Modal';
import { Message } from './message/Message';

type Props = {
    summary: string;
    children?: ReactNode;
    onClose: () => void;
};

const Warning = ({ summary, children, onClose }: Props) => {
    return (
        <Modal
            id={`warning-${summary}`}
            title="Warning"
            onClose={onClose}
            footer={(close) => (
                <button type="button" className="usa-button usa-button--outline" onClick={close}>
                    Go back
                </button>
            )}>
            <Message type="warning" summary={summary}>
                {children}
            </Message>
        </Modal>
    );
};

export { Warning };
