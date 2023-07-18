import React, { useContext, useState } from 'react';
import { Button, Icon } from '@trussworks/react-uswds';
import { ClassicButton } from '../../../../classic';
import './AddValueset.scss';
import { ValueSetControllerService } from '../../generated';
import { UserContext } from '../../../../providers/UserContext';

export const AddValueset = () => {
    const { state } = useContext(UserContext);
    // Fields
    const [isLocalOrPhin, setIsLocalOrPhin] = useState(true);
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
            valueSetTypeCd: 'LOCAL'
        };

        ValueSetControllerService.createValueSetUsingPost({
            authorization,
            request
        }).then((response: any) => {
            setDesc('');
            setCode('');
            setName('');
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
        const pattern = /^[a-zA-Z0-9_]*$/;
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
                    All fields with <span className="mandatory-indicator">*</span> are require
                </p>
                <p className="description">
                    These fields will not be displayed to your users, it only makes it easier for others to search for
                    this question in the Value set library
                </p>
                <br></br>
                <input
                    type="radio"
                    name="isLocalOrPhin"
                    value="local"
                    className="field-space"
                    checked={isLocalOrPhin}
                    onChange={(e: any) => setIsLocalOrPhin(e.target.checked)}
                />
                <span className="radio-label">LOCAL</span>
                <input
                    type="radio"
                    name="isLocalOrPhin"
                    value="phin"
                    className="right-radio"
                    checked={!isLocalOrPhin}
                    onChange={(e: any) => setIsLocalOrPhin(!e.target.checked)}
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
                    <p className="error-text">Value set code Not Valid</p>
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
            <ClassicButton className="submit-btn" url="" onClick={handleSubmit} disabled={isValidationFailure}>
                Add to question
            </ClassicButton>
            <ClassicButton className="cancel-btn" url="" onClick={handleSubmit}>
                Cancel
            </ClassicButton>
        </div>
    );
};
