import React, { useContext, useState } from 'react';
import { ModalToggleButton, TextInput, Textarea } from '@trussworks/react-uswds';
import { UserContext } from '../../../../providers/UserContext';
import './SavetTemplate.scss';
import { TemplateControllerService } from '../../generated';
import { useAlert } from '../../../../alert';

export const SaveTemplates = ({ modalRef, page }: any) => {
    const init = { name: '', desc: '' };
    const [details, setDetails] = useState(init);
    const { state } = useContext(UserContext);
    const { showAlert } = useAlert();
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
    const authorization = `Bearer ${state.getToken()}`;
    const handleSubmit = () => {
        const { name, desc } = details;
        const request = {
            templateDescription: desc,
            name: name,
            waTemplateUid: page.id
        };
        TemplateControllerService.saveTemplateUsingPost({ request, authorization }).then((resp) => {
            console.log(resp);
            showAlert({ type: 'success', header: 'Add', message: 'Question Added successfully' });
        });
    };
    const validateBtn = !details.name || validateName || !details.desc;
    const renderTemplateForm = (
        <div className="form-container save-template margin-top-1em">
            <div className={validateName ? 'error-border' : ''}>
                <label>
                    Template name <span className="mandatory-indicator">*</span>
                </label>
                {validateName && <label className="error-text">Template name Not Valid</label>}
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
                    Template description <span className="mandatory-indicator">*</span>
                </label>
                <Textarea
                    className="field-space"
                    maxLength={2000}
                    id="tab-description"
                    data-testid="tab-description"
                    name="desc"
                    rows={2}
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
