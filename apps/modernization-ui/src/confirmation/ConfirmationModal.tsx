import { ReactNode, RefObject } from 'react';
import {
    Button,
    ButtonGroup,
    Icon,
    Modal,
    ModalFooter,
    ModalHeading,
    ModalRef,
    ModalToggleButton,
} from '@trussworks/react-uswds';
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
    confirmBtnClassName?: string;
    disabled?: boolean;
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
    confirmBtnClassName,
    disabled = false,
}: Props) => {
    return (
        <Modal
            // allow escape to cancel unless interaction is disabled
            forceAction={disabled}
            ref={modal}
            id={id}
            aria-labelledby="confirmation-heading"
            className={classNames(style.content, 'modal')}
            aria-describedby={ariaDescribedBy}
        >
            <ModalHeading id="confirmation-heading" className={style.heading}>
                {title}
            </ModalHeading>
            <div className="modal-content">
                <div className={classNames('warning')}>
                    <Icon.Warning className={classNames(style.warningIcon)} aria-label="Warning" />
                </div>
                <div className={classNames(style.message, 'modal-message')}>
                    <p id={ariaDescribedBy}>{message}</p>
                    {detail && <p id="confirmation-modal-details">{detail}</p>}
                </div>
            </div>
            <ModalFooter id="confirmation-footer">
                <ButtonGroup className={classNames(style.actionButtonGroup)}>
                    <ModalToggleButton modalRef={modal} outline={true} data-testid="cancel-btn" disabled={disabled}>
                        {cancelText}
                    </ModalToggleButton>
                    <Button
                        type="button"
                        onClick={onConfirm}
                        data-testid="confirmation-btn"
                        className={classNames(style.actionButton, confirmBtnClassName)}
                        disabled={disabled}
                    >
                        {confirmText}
                    </Button>
                </ButtonGroup>
            </ModalFooter>
        </Modal>
    );
};
