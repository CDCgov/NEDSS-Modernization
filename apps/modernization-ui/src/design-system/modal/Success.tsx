import { ReactNode } from 'react';

import { Message } from 'design-system/message';

import { Modal } from './Modal';

type Props = {
    title?: string;
    children: ReactNode;
    onClose: () => void;
};

const Success = ({ title = 'Success', children, onClose }: Props) => {
    return (
        <Modal
            id={`success-${title}`}
            size="small"
            title={title}
            onClose={onClose}
            footer={(close) => (
                <button
                    type="button"
                    className="usa-button usa-button--outline"
                    onClick={close}
                    data-close-modal={true}
                >
                    Go back
                </button>
            )}
        >
            <Message type="success">{children}</Message>
        </Modal>
    );
};

export { Success };
