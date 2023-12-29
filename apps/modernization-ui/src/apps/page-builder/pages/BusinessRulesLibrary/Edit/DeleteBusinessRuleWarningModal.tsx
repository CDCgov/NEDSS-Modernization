import { Modal, ModalHeading, Button, Icon, ModalToggleButton, ModalFooter } from '@trussworks/react-uswds';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import './DeleteBusinessRulesWarningModal.scss';

interface DeleteBusinessRuleWarningModalProps {
    deleteWarningModalModalRef: any;
    onDeleteRule: () => void;
}

const DeleteBusinessRuleWarningModal = ({
    deleteWarningModalModalRef,
    onDeleteRule
}: DeleteBusinessRuleWarningModalProps) => {
    return (
        <Modal
            id="delete-rule-warning-modal"
            style={{ minWidth: '33%' }}
            ref={deleteWarningModalModalRef}
            forceAction
            isInitiallyOpen={false}
            className="padding-0">
            <ModalHeading className="border-bottom border-base-lighter font-sans-lg padding-2">
                <div className="header">
                    <div>Warning</div>
                    <Button
                        type="button"
                        unstyled
                        onClick={() => deleteWarningModalModalRef.current?.toggleModal(undefined, false)}
                        className="close-btn">
                        <Icon.Close />
                    </Button>
                </div>
            </ModalHeading>
            <div className="delete-rule-modal-body padding-2">
                <AlertBanner type="warning">
                    <div className="alert-content">
                        <h3>Are you sure you want to delete this business rule?</h3>
                        <p>
                            Once deleted, this business rule will be permanently removed from the system and will no
                            longer be associated with the page.
                        </p>
                    </div>
                </AlertBanner>
            </div>

            <ModalFooter>
                <div></div>
                <div className="delete-rule-modal-footer">
                    <ModalToggleButton
                        modalRef={deleteWarningModalModalRef}
                        closer
                        outline
                        data-testid="condition-cancel-btn">
                        Cancel
                    </ModalToggleButton>
                    <ModalToggleButton
                        modalRef={deleteWarningModalModalRef}
                        closer
                        onClick={onDeleteRule}
                        data-testid="modal-condition-add-btn">
                        Yes, Delete
                    </ModalToggleButton>
                </div>
            </ModalFooter>
        </Modal>
    );
};

export default DeleteBusinessRuleWarningModal;
