import { Button, ButtonGroup, Modal, ModalFooter, ModalHeading, ModalRef } from '@trussworks/react-uswds';
import { AlertBanner } from 'alert';
import { RefObject } from 'react';
import type { Blocker } from 'react-router-dom';

type Props = {
    modal: RefObject<ModalRef>;
    blocker: Blocker;
    onConfirm: () => void;
    onCancel: () => void;
};

const NavigationWarning = ({ modal, onConfirm, onCancel, blocker }: Props) => {
    if (blocker.state === 'blocked') {
        console.log('blocker', blocker);
        return (
            <Modal
                id="navigationWarning"
                forceAction
                ref={modal}
                aria-labelledby="confirmation-heading"
                className="modal">
                <ModalHeading id="confirmation-heading">Are you sure you want to leave this page</ModalHeading>
                <AlertBanner type="warning">You're gonna navigate away</AlertBanner>
                <ModalFooter id="confirmation-footer">
                    <ButtonGroup>
                        <Button type="button" onClick={onCancel} outline>
                            cancel
                        </Button>
                        <Button type="button" onClick={onConfirm}>
                            confirm
                        </Button>
                    </ButtonGroup>
                </ModalFooter>
            </Modal>
        );
    } else {
        return null;
    }
};

export default NavigationWarning;
