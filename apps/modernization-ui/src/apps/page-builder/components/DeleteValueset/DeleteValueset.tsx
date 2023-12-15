import React, { useContext, useRef } from 'react';
import { ModalRef, Icon, ModalToggleButton } from '@trussworks/react-uswds';
import './DeleteValueset.scss';
import { UserContext } from '../../../../providers/UserContext';
import { useAlert } from '../../../../alert';
import { ValueSetControllerService } from '../../generated';
import { ConfirmationModal } from '../../../../confirmation';

type CommonProps = {
    codeSetNm: string;
};

const DeleteValueset = ({ codeSetNm }: CommonProps) => {
    const removeModalRef = useRef<ModalRef>(null);
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const { showAlert } = useAlert();

    const handleRemoveValueset = () => {
        ValueSetControllerService.deleteValueSetUsingPut({
            authorization: token,
            codeSetNm: codeSetNm
        }).then((resp: any) => {
            showAlert({ type: 'success', header: 'Removed', message: resp.message });
        });
    };
    return (
        <>
            <ModalToggleButton modalRef={removeModalRef} className="line-btn display-none" type="submit" unstyled>
                <Icon.Delete className="primary-color" />
                <span> Remove valueset</span>
            </ModalToggleButton>
            <ConfirmationModal
                modal={removeModalRef}
                title={`Remove value set ${codeSetNm}?`}
                message="you are removing the value set's association to questions. Related business rules logic will also be rmeoved"
                confirmText="Yes, delete"
                onConfirm={handleRemoveValueset}
                onCancel={() => {}}
            />
        </>
    );
};

export default DeleteValueset;
