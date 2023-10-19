import React from 'react';
import { ButtonGroup, ModalFooter, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { RefObject } from 'react';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import './GroupSectionModal.scss';
import { AlertModal } from '../AlertModal/AlertModal';

type CommonProps = {
    modalRef: RefObject<ModalRef>;
    onGroupSection?: () => void;
    isConfirm: boolean;
    type: string;
};

type GroupSectionModalProps = CommonProps;

const GroupSectionModal = ({ modalRef, isConfirm, type }: GroupSectionModalProps) => {
    return (
        <ModalComponent
            modalRef={modalRef}
            isLarge
            modalHeading={isConfirm ? `'Warning` : 'Confirmation'}
            modalBody={
                <>
                    <div style={{ padding: '0 24px' }}>
                        <AlertModal
                            type={type}
                            message="You have indicated that you would like to ungroup the repeating block questions in the Tribal Affiliation Repeating Block questions. Select Ungroup or Cancel to return to Edit Page. "
                        />
                    </div>
                    <ModalFooter className="padding-2 margin-left-auto footer">
                        <ButtonGroup className="flex-justify-end">
                            <ModalToggleButton modalRef={modalRef} closer outline data-testid="condition-cancel-btn">
                                Cancel
                            </ModalToggleButton>
                            <ModalToggleButton
                                modalRef={modalRef}
                                closer
                                data-testid="section-add-btn"
                                onClick={() => {}}>
                                Ungroup
                            </ModalToggleButton>
                        </ButtonGroup>
                    </ModalFooter>
                </>
            }></ModalComponent>
    );
};

export default GroupSectionModal;
