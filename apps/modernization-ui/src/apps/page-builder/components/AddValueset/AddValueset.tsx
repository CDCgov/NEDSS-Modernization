import React, { useContext, useState } from 'react';
import { GetQuestionResponse, QuestionControllerService } from 'apps/page-builder/generated';
import { Button, Icon, ModalToggleButton } from '@trussworks/react-uswds';
import './AddValueset.scss';
import { ValueSetControllerService } from '../../generated';
import '../../pages/ValuesetLibrary/ValuesetTabs.scss';
import { UserContext } from '../../../../providers/UserContext';
import { useAlert } from 'alert';
import { Concept } from '../Concept/Concept';

export const AddValueset = ({ hideHeader, modalRef }: any) => {
    const { state } = useContext(UserContext);
    const { showAlert } = useAlert();
    // Fields
    const [isLocalOrPhin, setIsLocalOrPhin] = useState('LOCAL');
    const [name, setName] = useState('');
    const [desc, setDesc] = useState('');
    const [code, setCode] = useState('');
    const [activeTab, setActiveTab] = useState('details');
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
            // @ts-ignore
            const id = parseInt(localStorage.getItem('selectedQuestion'));
            updateQuestion(id);
            return response;
        });
    };

    const updateQuestion = async (id: number) => {
        // TODO :  we have to add logic for get question ID here
        const { question }: GetQuestionResponse = await QuestionControllerService.getQuestionUsingGet({
            authorization,
            id
        }).then((response: GetQuestionResponse) => {
            return response;
        });

        const {
            valueSet,
            unitValue,
            description,
            messagingInfo,
            label,
            tooltip,
            displayControl,
            mask,
            dataMartInfo,
            uniqueName,
            fieldLength,
            fieldSize
        }: any = question;

        const request = {
            description,
            labelInMessage: messagingInfo.labelInMessage,
            messageVariableId: messagingInfo.messageVariableId,
            hl7DataType: messagingInfo.hl7DataType,
            label,
            displayControl,
            mask,
            fieldLength,
            defaultLabelInReport: dataMartInfo.defaultLabelInReport,
            uniqueName,
            valueSet,
            unitValue,
            // eslint-disable-next-line no-dupe-keys
            tooltip: tooltip || 'demo tooltip',
            size: 50,
            fieldSize: fieldSize || 50
        };

        QuestionControllerService.updateQuestionUsingPut({
            authorization,
            id,
            request
        }).then((response: any) => {
            showAlert({ type: 'success', header: 'Add', message: 'Question Added successfully' });
            return response;
        });
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

    const handleType = (e: any) => {
        setTimeout(() => {
            setIsLocalOrPhin(e.target.value);
        }, 10);
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
    const renderTabs = (
        <ul className="tabs">
            <li className={activeTab == 'details' ? 'active' : ''} onClick={() => setActiveTab('details')}>
                Value set details
            </li>
            <li className={activeTab == 'concepts' ? 'active' : ''} onClick={() => setActiveTab('concepts')}>
                Value set concepts
            </li>
        </ul>
    );

    return (
        <div className="add-valueset">
            {!hideHeader && (
                <Button className="usa-button--unstyled close-btn" type={'button'} onClick={handleClose}>
                    <Icon.Close />
                </Button>
            )}
            {!hideHeader && (
                <h3 className="main-header-title" data-testid="header-title">
                    <Button className="usa-button--unstyled back-btn" type={'button'} onClick={handleBack}>
                        <Icon.ArrowBack />
                    </Button>
                    Add value set
                </h3>
            )}
            <div className="add-valueset__container">
                <h3 className="header-title">Add value set</h3>
                <p className="fields-info">
                    All fields with <span className="mandatory-indicator">*</span> are required
                </p>
                {renderTabs}
                {activeTab === 'details' ? (
                    <>
                        <p className="description">
                            These fields will not be displayed to your users, it only makes it easier for others to
                            search for search for this question in the Value set library
                        </p>
                        <br></br>
                        <input
                            type="radio"
                            name="isLocalOrPhin"
                            value="LOCAL"
                            id="rdLOCAL"
                            className="field-space"
                            checked={isLocalOrPhin === 'LOCAL'}
                            onChange={handleType}
                        />
                        <label htmlFor="rdLOCAL" className="radio-label">
                            LOCAL
                        </label>
                        <input
                            type="radio"
                            id="rdPHIN"
                            name="isLocalOrPhin"
                            value="PHIN"
                            className="right-radio"
                            checked={isLocalOrPhin === 'PHIN'}
                            onChange={handleType}
                        />
                        <label htmlFor="rdPHIN" className="radio-label">
                            PHIN
                        </label>
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
                                Value set code <span className="mandatory-indicator">*</span>
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
                                onBlur={(e: React.ChangeEvent<HTMLInputElement>) =>
                                    validateValuesetCode(e.target.value)
                                }
                                value={code}
                                onInput={(e: React.ChangeEvent<HTMLInputElement>) => setCode(e.target.value)}
                            />
                        </div>
                    </>
                ) : (
                    <Concept />
                )}
            </div>
            <ModalToggleButton
                className="submit-btn"
                type="button"
                modalRef={modalRef}
                onClick={handleSubmit}
                disabled={disableBtn}>
                Create and Add to question
            </ModalToggleButton>
            <ModalToggleButton className="cancel-btn" modalRef={modalRef} type="button">
                Cancel
            </ModalToggleButton>
        </div>
    );
};
