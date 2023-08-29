import React from 'react';
import { ButtonGroup, Label, ModalFooter, ModalRef, ModalToggleButton, TextInput } from '@trussworks/react-uswds';
import { SectionControllerService } from 'apps/page-builder/generated';
import { RefObject, useContext, useState } from 'react';
import { UserContext } from 'user';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import './AddSectionModal.scss';
import { ToggleButton } from '../ToggleButton';

type AddSectionModalProps = {
    modalRef: RefObject<ModalRef>;
    pageId: string;
    tabId: number;
};

const AddSectionModal = ({ modalRef, pageId, tabId }: AddSectionModalProps) => {
    const [sectionName, setSectionName] = useState('');
    const [sectionDescription, setSectionDescription] = useState('');
    const [visible, setVisible] = useState(true);
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
                request: { name: sectionName, tabId, visible, description: sectionDescription }
            });
        } catch (e) {
            console.error(e);
        }
    };

    return (
        <ModalComponent
            modalRef={modalRef}
            isLarge
            modalHeading="Manage Sections"
            modalBody={
                <>
                    <div style={{ padding: '0 24px' }}>
                        <Label htmlFor="sectionName" aria-required>
                            Section name<span className="required"></span>
                        </Label>
                        <TextInput
                            aria-label="section name"
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
                        <div className="visible-container">
                            <span>Not Visible</span>
                            <ToggleButton
                                aria-label="visible"
                                className="toggle-visible"
                                checked={visible}
                                name="visible"
                                onChange={() => setVisible(!visible)}
                            />
                            <span>Visible</span>
                        </div>
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
                                onClick={handleSubmit}>
                                Add Section
                            </ModalToggleButton>
                        </ButtonGroup>
                    </ModalFooter>
                </>
            }></ModalComponent>
    );
};

export default AddSectionModal;
