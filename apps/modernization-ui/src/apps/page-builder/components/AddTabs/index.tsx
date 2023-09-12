import React, { useState } from 'react';
import { ModalToggleButton, TextInput } from '@trussworks/react-uswds';
import { ToggleButton } from '../ToggleButton';

export const AddTab = ({ modalRef }: any) => {
    const [tabDetails, setTabDetails] = useState({ name: '', desc: '', visible: true });
    const handleTabInput = ({ target }: any) => {
        setTabDetails({
            ...tabDetails,
            [target.name]: target?.type === 'checkbox' ? target?.checked : target.value
        });
    };
    const renderTabForm = (
        <div className="form-container margin-top-1em">
            <div>
                <label>Tab Name</label>
                <TextInput
                    className="field-space"
                    type="text"
                    id="tab-name"
                    data-testid="tab-name"
                    name="name"
                    value={tabDetails.name}
                    onChange={handleTabInput}
                />
            </div>
            <div>
                <label>Tab Description</label>
                <TextInput
                    className="field-space"
                    type="text"
                    id="tab-description"
                    data-testid="tab-description"
                    name="desc"
                    value={tabDetails.desc}
                    onChange={handleTabInput}
                />
            </div>
            <div>
                <label> Visible</label>
                <ToggleButton checked={tabDetails.visible} name="visible" onChange={handleTabInput} />
            </div>
            <div className=" margin-bottom-1em add-tab-modal ds-u-text-align--right ">
                <ModalToggleButton
                    closer
                    modalRef={modalRef}
                    className="submit-btn"
                    disabled={!tabDetails.name}
                    type="button">
                    Add
                </ModalToggleButton>
                <ModalToggleButton closer modalRef={modalRef} className="cancel-btn" type="button">
                    Cancel
                </ModalToggleButton>
            </div>
        </div>
    );

    return <div className="add-valueset">{renderTabForm}</div>;
};
