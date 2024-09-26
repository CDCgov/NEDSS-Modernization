import { ReactNode } from 'react';
import { Modal } from './Modal';
import { Message } from '../message/Message';

type Props = {
    title?: string;
    children: ReactNode;
    onClose: () => void;
};

const Warning = ({ title = 'Warning', children, onClose }: Props) => {
    return (
        <Modal
            id={`warning-${title}`}
            size="small"
            title={title}
            onClose={onClose}
            footer={(close) => (
                <button type="button" className="usa-button usa-button--outline" onClick={close} data-close-modal>
                    Go back
                </button>
            )}>
            <Message type="warning">{children}</Message>
        </Modal>
    );
};

export { Warning };
