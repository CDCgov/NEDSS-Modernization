import React, { useContext, useState } from 'react';
import { Button, Icon } from '@trussworks/react-uswds';
import './AddValueset.scss';
import { ValueSetControllerService } from '../../generated';
import { UserContext } from '../../../../providers/UserContext';
import { useAlert } from 'alert';

export const AddValueset = () => {
    const { state } = useContext(UserContext);
    const { showAlert } = useAlert();
    // Fields
    const [isLocalOrPhin, setIsLocalOrPhin] = useState('LOCAL');
    const [name, setName] = useState('');
    const [desc, setDesc] = useState('');
    const [code, setCode] = useState('');

    const [isValuesetNameNotValid, setIsValuesetNameNotValid] = useState(false);
    const [isValuesetCodeNotValid, setIsValuesetCodeNotValid] = useState(false);
    const [isValidationFailure, setIsValidationFailure] = useState(false);

    const authorization = `Bearer ${state.getToken()}`;

    const handleSubmit = () => {
        const request = {
            valueSetNm: name,
            valueSetCode: code,
            valueSetTypeCd: isLocalOrPhin
        };

        ValueSetControllerService.createValueSetUsingPost({
            authorization,
            request
        }).then((response: any) => {
            setDesc('');
            setCode('');
            setName('');
            showAlert({ type: 'success', header: 'Created', message: 'Question Added successfully' });
            return response;
        });
        // xox- POST API call here
        // POST /page-builder/api/v1/valueset/
    };
    const handleClose = () => {
        window.close();
    };

    const handleBack = () => {
        history.go(-1);
    };
    const validateValuesetName = (name: string) => {
        const pattern = /^[a-zA-Z0-9]*$/;
        if (name.match(pattern)) {
            setIsValuesetNameNotValid(false);
            setIsValidationFailure(false);
        } else {
            setIsValuesetNameNotValid(true);
            setIsValidationFailure(true);
        }
    };

    const validateValuesetCode = (name: string) => {
        const pattern = /^[a-zA-Z0-9_]*$/;
        if (name.match(pattern)) {
            setIsValuesetCodeNotValid(false);
            setIsValidationFailure(false);
        } else {
            setIsValuesetCodeNotValid(true);
            setIsValidationFailure(true);
        }
    };
    const disableBtn = isValidationFailure || !name || !code;

    return (
        <div className="add-valueset">
            <Button className="usa-button--unstyled close-btn" type={'button'} onClick={handleClose}>
                <Icon.Close />
            </Button>
            <h3 className="main-header-title" data-testid="header-title">
                <Button className="usa-button--unstyled back-btn" type={'button'} onClick={handleBack}>
                    <Icon.ArrowBack />
                </Button>
                Add value set
            </h3>
            <div className="add-valueset__container">
                <h3 className="header-title">Add value set</h3>
                <p className="fields-info">
                    All fields with <span className="mandatory-indicator">*</span> are required
                </p>
                <p className="description">
                    These fields will not be displayed to your users, it only makes it easier for others to search for
                    this question in the Value set library
                </p>
                <br></br>
                <input
                    type="radio"
                    name="isLocalOrPhin"
                    value="LOCAL"
                    className="field-space"
                    checked={isLocalOrPhin === 'LOCAL'}
                    onChange={(e: any) => setIsLocalOrPhin(e.target.value)}
                />
                <span className="radio-label">LOCAL</span>
                <input
                    type="radio"
                    name="isLocalOrPhin"
                    value="PHIN"
                    className="right-radio"
                    checked={isLocalOrPhin === 'PHIN'}
                    onChange={(e: any) => setIsLocalOrPhin(e.target.value)}
                />
                <span className="radio-label">PHIN</span>
                <br></br>
                <div className={isValuesetNameNotValid ? 'error-border' : ''}>
                    <label>
                        Value set name<span className="mandatory-indicator">*</span>
                    </label>
                    <p className="error-text">Value set Name Not Valid</p>
                    <input
                        className="field-space"
                        type="text"
                        name="valuesetName"
                        style={{ border: isValuesetNameNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: any) => validateValuesetName(e.target.value)}
                        value={name}
                        onInput={(e: any) => setName(e.target.value)}
                    />
                </div>
                <div>
                    <label>Value set description</label>
                    <input
                        className="field-space"
                        type="text"
                        name="valuesetDesc"
                        style={{ border: '1px solid black' }}
                        value={desc}
                        onInput={(e: React.ChangeEvent<HTMLInputElement>) => setDesc(e.target.value)}
                    />
                </div>
                <div className={isValuesetCodeNotValid ? 'error-border' : ''}>
                    <label>
                        Value set code<span className="mandatory-indicator">*</span>
                    </label>
                    <br></br>
                    <p data-testid="error-text" className="error-text">
                        Value set code Not Valid
                    </p>
                    <input
                        className="field-space"
                        type="text"
                        name="valuesetCode"
                        style={{ border: isValuesetCodeNotValid ? '1px solid #dc3545' : '1px solid black' }}
                        onBlur={(e: React.ChangeEvent<HTMLInputElement>) => validateValuesetCode(e.target.value)}
                        value={code}
                        onInput={(e: React.ChangeEvent<HTMLInputElement>) => setCode(e.target.value)}
                    />
                </div>
            </div>
            <Button className="submit-btn" type="button" onClick={handleSubmit} disabled={disableBtn}>
                Add to question
            </Button>
            <Button className="cancel-btn" type="button" onClick={handleSubmit}>
                Cancel
            </Button>
        </div>
    );
};
