import React from 'react';
import {
    ButtonGroup,
    Label,
    Modal,
    ModalFooter,
    ModalHeading,
    ModalRef,
    ModalToggleButton,
    TextInput
} from '@trussworks/react-uswds';
import { SectionControllerService } from 'apps/page-builder/generated';
import { RefObject, useContext, useState } from 'react';
import { UserContext } from 'user';
// import './AddSectionModal.scss';
// import { Button } from '@trussworks/react-uswds';

type AddSectionModalProps = {
    modalRef: RefObject<ModalRef>;
    pageId: string;
    tabId: number;
};

const AddSectionModal = ({ modalRef, pageId, tabId }: AddSectionModalProps) => {
    const [sectionName, setSectionName] = useState('');
    const [sectionDescription, setSectionDescription] = useState('');
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;

    const handleSectionNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSectionName(e.target.value);
    };

    const handleSectionDescriptionChange = (e: any) => {
        setSectionDescription(e.target.value);
    };

    const handleSubmit = async () => {
        try {
            await SectionControllerService.createSectionUsingPost({
                authorization: token,
                pageId: pageId,
                request: { name: sectionName, tabId, visible: true }
            });
        } catch (e) {
            console.error(e);
        }
    };

    return (
        <Modal id="add-section-modal" className="add-section-modal" ref={modalRef}>
            <ModalHeading>Add Section</ModalHeading>
            <div>
                <Label htmlFor="sectionName" aria-required>
                    Section name
                </Label>
                <TextInput
                    type="text"
                    name="sectionName"
                    value={sectionName}
                    id={'add-section-name'}
                    onChange={handleSectionNameChange}
                />
                <Label htmlFor="sectionDescription">Section description</Label>
                <TextInput
                    aria-label="section description"
                    type="text"
                    name="sectionDescription"
                    value={sectionDescription}
                    id={'add-section-description'}
                    onChange={handleSectionDescriptionChange}
                />
            </div>
            <ModalFooter className="padding-2 margin-left-auto footer">
                <ButtonGroup className="flex-justify-end">
                    <ModalToggleButton modalRef={modalRef} closer outline data-testid="condition-cancel-btn">
                        Cancel
                    </ModalToggleButton>
                    <ModalToggleButton modalRef={modalRef} closer data-testid="section-add-btn" onClick={handleSubmit}>
                        Add Section
                    </ModalToggleButton>
                </ButtonGroup>
            </ModalFooter>
        </Modal>
    );
};

export default AddSectionModal;
