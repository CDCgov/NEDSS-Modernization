import { ModalRef } from '@trussworks/react-uswds';
import { RefObject, useEffect } from 'react';
import { VerifiableAdddress, VerifiableAddressConsumer } from './VerifiableAddress';
import { useAddressVerification } from './useAddressVerification';

import { StateCodedValues } from 'location';
import { AddressVerificationActionModal } from './AddressVerificationActionModal';
import { NoActionRequired } from './NoActionRequired';

type Props = {
    modal: RefObject<ModalRef>;
    states: StateCodedValues;
    input: VerifiableAdddress;
    onConfirm: VerifiableAddressConsumer;
    onCancel: () => void;
};

export const AddressVerificationModal = ({ modal, states, input, onConfirm, onCancel }: Props) => {
    const { state, verify } = useAddressVerification({ states });

    useEffect(() => {
        verify(input);
    }, []);

    switch (state.status) {
        case 'idle':
            return <></>;
        case 'not-verifiable':
        case 'verified':
            return <NoActionRequired modal={modal} onClose={() => onConfirm(input)} />;
        case 'verifying':
        case 'unverified':
        case 'verified-found':
            return (
                <AddressVerificationActionModal modal={modal} state={state} onConfirm={onConfirm} onCancel={onCancel} />
            );
    }
};
