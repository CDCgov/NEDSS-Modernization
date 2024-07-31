import { ReactNode, RefObject } from 'react';
import { Button, ButtonGroup, Icon, Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import classNames from 'classnames';
import style from './confirmationModal.module.scss';

type Props = {
    id?: string;
    ariaDescribedBy?: string;
    modal: RefObject<ModalRef>;
    title: string;
    message: string | ReactNode;
    detail?: string | ReactNode;
    confirmText?: string;
    onConfirm: () => void;
    cancelText?: string;
    onCancel: () => void;
    confirmBtnClassName?: string;
};

export const ConfirmationModal = ({
    id = 'confirmation',
    ariaDescribedBy = 'confirmation-description',
    modal,
    title,
    message,
    detail,
    confirmText = 'Confirm',
    onConfirm,
    cancelText = 'Cancel',
    onCancel,
    confirmBtnClassName
}: Props) => {
    return (
        <Modal
            forceAction
            ref={modal}
            id={id}
            aria-labelledby="confirmation-heading"
            className="modal"
            aria-describedby={ariaDescribedBy}>
            <ModalHeading id="confirmation-heading">{title}</ModalHeading>
            <div className={classNames(style.content, 'modal-content')}>
                <div className={classNames('warning')}>
                    <Icon.Warning className={classNames(style.warningIcon)} />
                </div>
                <div className={classNames(style.message, 'modal-message')}>
                    <p id={ariaDescribedBy}>{message}</p>
                    {detail && <p id="confirmation-modal-details">{detail}</p>}
                </div>
            </div>
            <ModalFooter id="confirmation-footer">
                <ButtonGroup className={classNames(style.actionButtonGroup)}>
                    <Button type="button" onClick={onCancel} outline data-testid="cancel-btn">
                        {cancelText}
                    </Button>
                    <Button
                        type="button"
                        onClick={onConfirm}
                        data-testid="confirmation-btn"
                        className={`${classNames(style.actionButton)} ${confirmBtnClassName ? confirmBtnClassName : ''}`}>
                        {confirmText}
                    </Button>
                </ButtonGroup>
            </ModalFooter>
        </Modal>
    );
};
