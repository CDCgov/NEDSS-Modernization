import React, { useContext, useState } from 'react';
import { GetQuestionResponse, QuestionControllerService } from 'apps/page-builder/generated';
import { Button, DatePicker, Grid, FormGroup, Icon, ModalToggleButton } from '@trussworks/react-uswds';
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
    const [isShowFrom, setShowForm] = useState(false);
    const [isValuesetNameNotValid, setIsValuesetNameNotValid] = useState(false);
    const [isValuesetCodeNotValid, setIsValuesetCodeNotValid] = useState(false);
    const [isValidationFailure, setIsValidationFailure] = useState(false);

    const authorization = `Bearer ${state.getToken()}`;
    const [concept, setConcept] = useState({ effective: 'always', uIDisplayName: '', localCode: '', conceptCode: '' });

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
        // xox- POST API call here
        // POST /page-builder/api/v1/valueset/
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
            // modalRef.current.closeModal();
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
    const handleAddConceptFrom = () => {
        setShowForm(!isShowFrom);
    };
    const handleConcept = ({ target }: React.ChangeEvent<HTMLInputElement>) => {
        setConcept({ ...concept, [target.name]: target.value });
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
    const renderConceptForm = (
        <div className="form-container">
            <div>
                <label>UI Display name</label>
                <input
                    className="field-space"
                    type="text"
                    name="uIDisplayName"
                    style={{ border: '1px solid black' }}
                    value={concept.uIDisplayName}
                    onInput={handleConcept}
                />
            </div>
            <Grid row className="inline-field">
                <Grid tablet={{ col: true }}>
                    <div className="margin-right-2">
                        <label>Local code</label>
                        <input
                            className="field-space"
                            type="text"
                            name="localCode"
                            style={{ border: '1px solid black' }}
                            value={concept.localCode}
                            onInput={handleConcept}
                        />
                    </div>
                </Grid>
                <Grid tablet={{ col: true }}>
                    <div className="width-48-p">
                        <label>Concept code</label>
                        <input
                            className="field-space"
                            type="text"
                            name="conceptCode"
                            style={{ border: '1px solid black' }}
                            value={concept.conceptCode}
                            onInput={handleConcept}
                        />
                    </div>
                </Grid>
            </Grid>
            <Grid row className="effective-date-field">
                <Grid col={7}>
                    <input
                        type="radio"
                        name="effective"
                        value="always"
                        id="eAlways"
                        className="field-space"
                        checked={concept.effective === 'always'}
                        onChange={handleConcept}
                    />
                    <label htmlFor="rdLOCAL" className="radio-label">
                        Always Effective
                    </label>
                    <input
                        type="radio"
                        id="eUntil"
                        name="effective"
                        value="until"
                        className="right-radio"
                        checked={concept.effective === 'until'}
                        onChange={handleConcept}
                    />
                    <label htmlFor="eUntil" className="radio-label">
                        Effective Until
                    </label>
                </Grid>
                <Grid col={5}>
                    <FormGroup error={false}>
                        <DatePicker
                            id="effectivDate"
                            name="effectivDate"
                            // defaultValue={personalDetailsFields.dateOfBirth}
                            // onChange={handleDobChange}
                            maxDate={new Date().toISOString()}
                        />
                        {/* {isDobInvalid ? <ErrorMessage>DOB cannot be in the future</ErrorMessage> : ''}*/}
                    </FormGroup>
                </Grid>
            </Grid>
            <div className=" ds-u-text-align--right margin-bottom-1em">
                <Button type="submit" className="margin-right-2" unstyled onClick={handleAddConceptFrom}>
                    <Icon.Cancel className="margin-right-2px" />
                    <span> Cancel</span>
                </Button>
                <Button type="submit" unstyled onClick={handleAddConceptFrom}>
                    <Icon.Check className="margin-right-2px" />
                    <span> Add value set concept</span>
                </Button>
            </div>
        </div>
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
                                onBlur={(e: React.ChangeEvent<HTMLInputElement>) =>
                                    validateValuesetCode(e.target.value)
                                }
                                value={code}
                                onInput={(e: React.ChangeEvent<HTMLInputElement>) => setCode(e.target.value)}
                            />
                        </div>
                    </>
                ) : (
                    <div className="value_set_concept_container">
                        {isShowFrom ? (
                            renderConceptForm
                        ) : (
                            <div>
                                <p className="description">
                                    No value set concept is displayed. Please click the button below to add new value
                                    set concept.
                                </p>
                                <Concept />
                                <Button type="submit" unstyled onClick={handleAddConceptFrom}>
                                    <Icon.Add className="margin" />
                                    <span> Add value set concept</span>
                                </Button>
                            </div>
                        )}
                    </div>
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
