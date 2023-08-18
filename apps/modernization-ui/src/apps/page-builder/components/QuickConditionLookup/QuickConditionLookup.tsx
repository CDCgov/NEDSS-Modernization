import { ButtonGroup, Modal, ModalFooter, ModalRef, ModalHeading, ModalToggleButton } from '@trussworks/react-uswds';
import { RefObject } from 'react';

type Props = {
    modal: RefObject<ModalRef>;
};

export const QuickConditionLookup = ({ modal }: Props) => {
    return (
        <Modal
            ref={modal}
            forceAction
            id="quick-condition-lookup"
            isInitiallyOpen={false}
            isLarge
            aria-labelledby="incomplete-form-confirmation-modal-heading"
            className="padding-0"
            aria-describedby="incomplete-form-confirmation-modal-description">
            <ModalHeading
                id="incomplete-form-confirmation-modal-heading"
                className="border-bottom border-base-lighter font-sans-lg padding-2">
                Unverified Address
            </ModalHeading>
            <h2>Quick condition lookup</h2>
            <p>Body</p>
            <p>Body</p>
            <p>Body</p>
            <ModalFooter className="padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <ModalToggleButton modalRef={modal} closer>
                        Go back
                    </ModalToggleButton>
                </ButtonGroup>
            </ModalFooter>
        </Modal>
    );
};
