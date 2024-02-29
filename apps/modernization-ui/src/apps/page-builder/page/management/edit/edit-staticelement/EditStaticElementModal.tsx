import { ModalRef } from '@trussworks/react-uswds';
import { PagesQuestion } from 'apps/page-builder/generated';
import { RefObject } from 'react';
import { EditStaticElement } from '../staticelement/EditStaticElement';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';

type Props = {
    modal: RefObject<ModalRef>;
    question?: PagesQuestion;
    onClosed: () => void;
};
export const EditStaticElementModal = ({ modal, question, onClosed }: Props) => {
    const handleClose = () => {
        modal.current?.toggleModal();
        onClosed();
    };
    return (
        <ModalComponent
            modalRef={modal}
            modalHeading={'Edit static elements'}
            modalBody={question && <EditStaticElement question={question} onCloseModal={handleClose} />}
        />
    );
};
