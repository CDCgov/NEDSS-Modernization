import { ReactNode, RefObject, useEffect } from 'react';
import { Button, ButtonGroup, Icon, Modal, ModalHeading, ModalRef } from '@trussworks/react-uswds';

import { AddressVerificationState } from './useAddressVerification';
import { VerifiableAdddress, VerifiableAddressConsumer } from './VerifiableAddress';

import { VerifyingAddressDisplay } from './verifying/VerifyingAddressDisplay';
import { UnverifiedAddressDisplay } from './unverified/UnverifiedAddressDisplay';
import { VerrifiedAddressDisplay } from './verified/VerifiedAddressDisplay';

type VerifiableAddressAction = {
    name: string;
    address: VerifiableAdddress;
};

type Actionable = {
    title: string;
    large?: boolean;
    content: ReactNode;
    actions: VerifiableAddressAction[];
};

const resolveActionable = (state: AddressVerificationState): Actionable | undefined => {
    switch (state.status) {
        case 'verifying':
            return {
                title: 'Verifying address',
                content: <VerifyingAddressDisplay />,
                actions: []
            };
        case 'unverified':
            return {
                title: 'Unverified address',
                content: <UnverifiedAddressDisplay />,
                actions: [{ name: 'Contine anyways', address: state.input }]
            };
        case 'verified-found':
            return {
                title: 'Verified address found',
                large: true,
                content: <VerrifiedAddressDisplay input={state.input} alternative={state.alternative} />,
                actions: [
                    { name: 'Continue without update', address: state.input },
                    { name: 'Update address and continue', address: state.alternative }
                ]
            };
    }
};

type AddressVerificationModalProps = {
    modal: RefObject<ModalRef>;
    state: AddressVerificationState;
    onConfirm: VerifiableAddressConsumer;
    onCancel: () => void;
};

const AddressVerificationActionModal = ({ modal, state, onConfirm, onCancel }: AddressVerificationModalProps) => {
    const actionable = resolveActionable(state);

    useEffect(() => {
        modal.current?.toggleModal(undefined, true);
    }, []);

    return (
        (actionable && (
            <Modal
                forceAction
                isLarge={actionable.large}
                ref={modal}
                id="address-verification-modal"
                aria-labelledby="address-verification-heading"
                className="modal"
                aria-describedby="address-verification-description">
                <ModalHeading
                    id="address-verification-heading"
                    className="border-bottom border-base-lighter font-sans-lg padding-2">
                    {actionable.title}
                </ModalHeading>
                <div className="modal-content">
                    <div className="warning">
                        <Icon.Warning className="font-sans-2xl margin-x-2" />
                    </div>
                    <div className="modal-message">{actionable.content}</div>
                </div>
                <div className="modal-actions">
                    <ButtonGroup>
                        <Button type="button" onClick={onCancel} outline>
                            Go back
                        </Button>
                        {actionable.actions.map((action, index) => (
                            <Button
                                key={index}
                                type="button"
                                className="usa-button"
                                onClick={() => onConfirm(action.address)}>
                                {action.name}
                            </Button>
                        ))}
                    </ButtonGroup>
                </div>
            </Modal>
        )) || <></>
    );
};

export { AddressVerificationActionModal };
