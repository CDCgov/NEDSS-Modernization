import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import React, { useRef } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import './EditBusinessRules.scss';
import { PageRuleControllerService } from '../../../generated';
import { useAlert } from 'alert';
import { authorization } from 'authorization';
import { ConfirmationModal } from 'confirmation';

const EditBusinessRules = () => {
    const navigate = useNavigate();
    const { pageId, ruleId } = useParams();
    const deleteWarningModalModal = useRef<ModalRef>(null);
    const { showAlert } = useAlert();

    const handleCancel = () => {
        navigate(-1);
    };

    const handleDeleteRule = async () => {
        if (pageId) {
            try {
                await PageRuleControllerService.deletePageRuleUsingDelete({
                    authorization: authorization(),
                    pageId: pageId ?? '',
                    ruleId: Number(ruleId)
                });
                showAlert({ type: 'success', header: 'Success', message: 'Rule deleted.' });
                navigate(-1);
            } catch (error: any) {
                showAlert({ type: 'error', header: 'error', message: error });
                console.error('Error', error);
            }
        }
    };

    return (
        <>
            <ConfirmationModal
                modal={deleteWarningModalModal}
                title="Warning"
                message="Are you sure you want to delete this business rule?"
                detail="Once deleted, this business rule will be permanently removed from the system and will nolonger be associated with the page."
                confirmText="Yes, delete"
                onConfirm={handleDeleteRule}
                onCancel={handleCancel}
            />
            <div className="edit-rules">
                <div className="edit-rules__buttons">
                    {ruleId && (
                        <ModalToggleButton
                            opener
                            modalRef={deleteWarningModalModal}
                            type="button"
                            className="delete-btn"
                            unstyled>
                            <Icon.Delete size={3} className="margin-right-2px" />
                            <span>Delete</span>
                        </ModalToggleButton>
                    )}
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

export default EditBusinessRules;
