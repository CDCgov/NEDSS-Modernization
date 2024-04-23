import { ReactNode, RefObject } from 'react';
import { Button, ButtonGroup, Icon, Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import classNames from 'classnames';
import style from './successModal.module.scss';

type Props = {
    id?: string;
    ariaDescribedBy?: string;
    modal: RefObject<ModalRef>;
    title: string;
    message: string | ReactNode;
    detail?: string | ReactNode;
    action: string;
    onAction: () => void;
};

export const SuccessModal = ({
    id = 'success',
    ariaDescribedBy = 'success-description',
    modal,
    title,
    message,
    detail,
    action,
    onAction
}: Props) => {
    return (
        <Modal
            forceAction
            ref={modal}
            id={id}
            aria-labelledby="success-heading"
            className="modal"
            aria-describedby={ariaDescribedBy}>
            <ModalHeading id="success-heading">{title}</ModalHeading>
            <div className={classNames(style.content, 'modal-content')}>
                <div className={classNames(style.success)}>
                    <Icon.CheckCircle className={classNames(style.successIcon)} />
                </div>
                <div className={classNames(style.message, 'modal-message')}>
                    <p id={ariaDescribedBy}>{message}</p>
                    {detail && <p id="success-modal-details">{detail}</p>}
                </div>
            </div>
            <ModalFooter id="success-footer">
                <ButtonGroup className={classNames(style.actionButtonGroup)}>
                    <Button type="button" onClick={onAction} className={classNames(style.actionButton)}>
                        {action}
                    </Button>
                </ButtonGroup>
            </ModalFooter>
        </Modal>
    );
};
