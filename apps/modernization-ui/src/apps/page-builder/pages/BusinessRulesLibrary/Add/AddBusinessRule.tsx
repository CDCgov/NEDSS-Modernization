import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import React, { useRef } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import './EditBusinessRules.scss';
// import { PageRuleControllerService } from '../../../generated';
import { useAlert } from 'alert';
// import { authorization } from 'authorization';

const AddBusinessRule = () => {
    const navigate = useNavigate();
    // const { pageId } = useParams();
    const deleteWarningModalModal = useRef<ModalRef>(null);
    // const { showAlert } = useAlert();

    const handleCancel = () => {
        navigate(-1);
    };

    return (
        <>
            <div className="edit-rules">
                <div className="edit-rules__buttons">
                    <ModalToggleButton
                        opener
                        modalRef={deleteWarningModalModal}
                        type="button"
                        className="delete-btn"
                        unstyled>
                        <Icon.Delete size={3} className="margin-right-2px" />
                        <span>Delete</span>
                    </ModalToggleButton>
                    <div>
                        <Button type="button" outline onClick={handleCancel}>
                            Cancel
                        </Button>
                        <Button type="submit" className="lbr" disabled>
                            Add to Library
                        </Button>
                    </div>
                </div>
            </div>
        </>
    );
};

export default AddBusinessRule;
