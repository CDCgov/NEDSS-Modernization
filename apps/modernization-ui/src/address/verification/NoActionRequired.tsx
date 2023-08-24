import { RefObject, useEffect } from 'react';
import { ModalRef } from '@trussworks/react-uswds';

type NoActionRequiredProps = {
    modal: RefObject<ModalRef>;
    onClose: () => void;
};

const NoActionRequired = ({ modal, onClose }: NoActionRequiredProps) => {
    useEffect(() => {
        modal.current?.toggleModal(undefined, false);
        onClose();
    }, []);
    return <></>;
};

export { NoActionRequired };
