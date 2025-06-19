import React, { useState } from 'react';
import { ModalToggleButton, TextInput } from '@trussworks/react-uswds';
import './SavetTemplate.scss';

export const SaveTemplates = ({ modalRef }: any) => {
    const init = { name: '', desc: '' };
    const [details, setDetails] = useState(init);

    const [validateName, setValidateName] = useState(false);
    const handleTabInput = ({ target }: any) => {
        setDetails({
            ...details,
            [target.name]: target?.type === 'checkbox' ? target?.checked : target.value
        });
    };
    const handleValidation = ({ target }: React.ChangeEvent<HTMLInputElement>) => {
        const pattern = /^[a-zA-Z0-9\s?!,-_]*$/;
        setValidateName(!pattern.test(target?.value) || target?.value === '');
    };

    const handleSubmit = () => {
        const { name, desc } = details;
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const request = { name, desc };
    };
    const validateBtn = !details.name || validateName || !details.desc;
    const renderTemplateForm = (
        <div className="form-container save-template margin-top-1em">
            <div className={validateName ? 'error-border' : ''}>
                <label>
                    Template Name <span className="mandatory-indicator">*</span>
                </label>
                {validateName && <label className="error-text">Template Name Not Valid</label>}
                <TextInput
                    className="field-space"
                    type="text"
                    id="tab-name"
                    onBlur={handleValidation}
                    data-testid="tab-name"
                    name="name"
                    value={details.name}
                    style={{ border: validateName ? '1px solid #dc3545' : '1px solid black' }}
                    onChange={handleTabInput}
                />
            </div>
            <div>
                <label>
                    Template Description <span className="mandatory-indicator">*</span>
                </label>
                <TextInput
                    className="field-space"
                    type="text"
                    id="tab-description"
                    data-testid="tab-description"
                    name="desc"
                    value={details.desc}
                    onChange={handleTabInput}
                />
            </div>
            <div className="margin-top-1em save-template-footer ds-u-text-align--right ">
                <ModalToggleButton
                    closer
                    modalRef={modalRef}
                    className="submit-btn"
                    onClick={handleSubmit}
                    disabled={validateBtn}
                    type="button">
                    Save
                </ModalToggleButton>
                <ModalToggleButton closer modalRef={modalRef} className="cancel-btn" type="button">
                    Cancel
                </ModalToggleButton>
            </div>
        </div>
    );

    return <div className="add-valueset save-template-block">{renderTemplateForm}</div>;
};
