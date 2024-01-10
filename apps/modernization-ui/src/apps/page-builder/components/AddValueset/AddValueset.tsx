import React, { RefObject, useState } from 'react';
import { GetQuestionResponse, QuestionControllerService } from 'apps/page-builder/generated';
import { Button, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import './AddValueset.scss';
import { ValueSetControllerService } from '../../generated';
import '../../pages/ValuesetLibrary/ValuesetTabs.scss';
import { useAlert } from 'alert';
import { authorization as getAuthorization } from 'authorization';
import { Concept } from '../Concept/Concept';

type Props = {
    modalRef?: RefObject<ModalRef>;
    updateCallback?: () => void;
};

export const AddValueset = ({ modalRef, updateCallback }: Props) => {
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

    const authorization = getAuthorization();
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
            const id = parseInt(localStorage.getItem('selectedQuestion')!);
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

    const reset = () => {
        setDesc('');
        setCode('');
        setName('');
        setActiveTab('details');
    };

    const disableBtn = isValidationFailure || !name || !code;

    return (
        <div className="add-valueset">
            <div className="add-valueset__content">
                <div className={`add-valueset__container ${activeTab !== 'details' ? 'concept-container' : ''}`}>
                    {activeTab === 'details' ? (
                        <div className="add-valueset__details">
                            <h3 className="main-header-title" data-testid="header-title">
                                Value set details
                            </h3>
                            <p className="description">
                                These fields will not be displayed to your users, it only makes it easier for others to
                                search for this question in the Value set library
                            </p>
                            <label>
                                All fields with <span className="mandatory-indicator">*</span> are required
                            </label>
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
                            <div className={`add-valueset__input ${isValuesetCodeNotValid ? 'error-border' : ''}`}>
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
                                    maxLength={50}
                                    style={{ border: isValuesetCodeNotValid ? '1px solid #dc3545' : '1px solid black' }}
                                    onBlur={(e: React.ChangeEvent<HTMLInputElement>) =>
                                        validateValuesetCode(e.target.value)
                                    }
                                    value={code}
                                    onInput={(e: React.ChangeEvent<HTMLInputElement>) => setCode(e.target.value)}
                                />
                            </div>
                            <div className={`add-valueset__input ${isValuesetNameNotValid ? 'error-border' : ''}`}>
                                <label>
                                    Value set name<span className="mandatory-indicator">*</span>
                                </label>
                                <p className="error-text">Value set Name Not Valid</p>
                                <input
                                    className="field-space"
                                    type="text"
                                    name="valuesetName"
                                    maxLength={50}
                                    style={{ border: isValuesetNameNotValid ? '1px solid #dc3545' : '1px solid black' }}
                                    onBlur={(e: any) => validateValuesetName(e.target.value)}
                                    value={name}
                                    onInput={(e: any) => setName(e.target.value)}
                                />
                            </div>
                            <div className="add-valueset__input">
                                <label>Value set description</label>
                                <input
                                    className="field-space"
                                    type="text"
                                    name="valuesetDesc"
                                    maxLength={200}
                                    style={{ border: '1px solid black' }}
                                    value={desc}
                                    onInput={(e: React.ChangeEvent<HTMLInputElement>) => setDesc(e.target.value)}
                                />
                            </div>
                            <div className="value-set-line-action-footer">
                                <ModalToggleButton
                                    modalRef={modalRef!}
                                    onClick={() => reset()}
                                    type="button"
                                    outline
                                    closer>
                                    Cancel
                                </ModalToggleButton>
                                <Button type="submit" onClick={() => setActiveTab('concepts')}>
                                    <span>Continue to value set concept</span>
                                </Button>
                            </div>
                        </div>
                    ) : (
                        <Concept
                            valueset={{
                                valueSetTypeCd: isLocalOrPhin,
                                valueSetCode: code,
                                valueSetNm: name,
                                codeSetDescTxt: desc
                            }}
                            updateCallback={updateCallback}
                        />
                    )}
                </div>
            </div>
            {activeTab !== 'details' ? (
                <div className="add-valueset__footer">
                    <ModalToggleButton outline modalRef={modalRef!} onClick={() => reset()} type="button">
                        Cancel
                    </ModalToggleButton>
                    <ModalToggleButton
                        className="submit-btn"
                        type="button"
                        modalRef={modalRef!}
                        onClick={handleSubmit}
                        disabled={disableBtn}>
                        Create and Add to question
                    </ModalToggleButton>
                </div>
            ) : null}
        </div>
    );
};
