import React, { useContext, useEffect, useState } from 'react';
import './Concept.scss';
import { ConceptsContext } from '../../context/ConceptContext';
import { useConceptPI } from './useConceptAPI';
import { ConceptTable } from './ConceptTable';
import { UserContext } from '../../../../providers/UserContext';
import { Button, FormGroup, Grid, Icon, Radio, ComboBox, ErrorMessage } from '@trussworks/react-uswds';
import {
    AddConceptRequest,
    UpdateConceptRequest,
    MessagingInfo,
    ValueSet,
    ValueSetControllerService
} from '../../generated';
import { DatePickerInput } from '../../../../components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { Controller, useForm } from 'react-hook-form';
import { initialEntry } from 'apps/patient/add';

const initConcept = {
    localCode: '',
    codesetName: '',
    display: '',
    description: '',
    conceptCode: '',
    messagingConceptName: '',
    codeSystem: '',
    effectiveFromTime: new Date().toLocaleString(),
    effectiveToTime: new Date().toLocaleString(),
    duration: 'always',
    status: true
};

type Props = {
    valueset?: ValueSet;
    updateCallback?: () => void;
};

interface CodeSystemOption {
    label: string;
    value: string;
}

const messagingInfo: MessagingInfo = {
    codeSystem: '',
    conceptCode: '',
    conceptName: '',
    preferredConceptName: ''
};

const init = {
    adminComments: undefined,
    code: undefined,
    displayName: undefined,
    effectiveFromTime: initialEntry().asOf,
    effectiveToTime: undefined,
    messagingInfo: messagingInfo,
    shortDisplayName: undefined,
    statusCode: AddConceptRequest.statusCode.A
};

export const Concept = ({ valueset, updateCallback }: Props) => {
    const conceptForm = useForm<AddConceptRequest>({
        defaultValues: { ...init }
    });
    const { control, handleSubmit } = conceptForm;
    const { state } = useContext(UserContext);
    const { selectedConcept } = useContext(ConceptsContext);
    const authorization = `Bearer ${state.getToken()}`;

    const { searchQuery, sortDirection, currentPage, pageSize } = useContext(ConceptsContext);
    const [summaries, setSummaries] = useState([]);
    const [totalElements, setTotalElements] = useState(0);
    const [isShowFrom, setShowForm] = useState(false);
    const [isDelete, setIsDelete] = useState(false);
    const [concept, setConcept] = useState(initConcept);
    const [codeSystemOptionList, setCodeSystemOptionList] = useState<CodeSystemOption[]>([]);

    const handleValidation = (unique = true) => {
        return unique ? /^[a-zA-Z0-9_]*$/ : /^[a-zA-Z0-9\s?!,-_]*$/;
    };
    // @ts-ignore
    useEffect(async () => {
        setSummaries([]);
        const content: any = await useConceptPI(authorization, '');
        setSummaries(content);
        setTotalElements(content?.length);
        fetchCodeSystemOptions();
    }, [searchQuery, currentPage, pageSize, sortDirection]);

    useEffect(() => {
        setConcept(selectedConcept);
        selectedConcept.status && setShowForm(!isShowFrom);
    }, [selectedConcept]);

    const fetchCodeSystemOptions = () => {
        ValueSetControllerService.findConceptsByCodeSetNameUsingGet({
            authorization,
            codeSetNm: 'CODE_SYSTEM'
        }).then((response: any) => {
            const data = response || [];
            const codeSystemOptionList: any = [];
            data.map((each: { display: string; conceptCode: string }) => {
                codeSystemOptionList.push({ label: each.display, value: each.conceptCode });
            });
            setCodeSystemOptionList(codeSystemOptionList);
        });
    };

    const handleCancelFrom = () => {
        setShowForm(!isShowFrom);
        setConcept(initConcept);
    };

    const onSubmit = handleSubmit((data) => {
        if (valueset) {
            handleAddConceptForm(data);
        } else {
            handleSaveConceptForm();
        }
    });

    const handleAddConceptForm = (data: AddConceptRequest) => {
        const request: AddConceptRequest = {
            code: data.code,
            displayName: data.displayName,
            shortDisplayName: data.shortDisplayName,
            effectiveFromTime: data.effectiveFromTime,
            effectiveToTime: data.effectiveToTime,
            statusCode: data.statusCode,
            messagingInfo: {
                codeSystem: data.messagingInfo.codeSystem,
                conceptCode: data.code,
                conceptName: data.messagingInfo.conceptName,
                preferredConceptName: data.messagingInfo.preferredConceptName
            }
        };
        ValueSetControllerService.addConceptUsingPost({
            authorization,
            codeSetNm: valueset!.valueSetCode!,
            request
        }).then((response: any) => {
            setShowForm(!isShowFrom);
            updateCallback!();
            return response;
        });
        setShowForm(!isShowFrom);
    };

    const handleSaveConceptForm = () => {
        const request: UpdateConceptRequest = {
            active: concept.status,
            displayName: concept.display,
            effectiveFromTime: initialEntry().asOf,
            effectiveToTime: concept.effectiveToTime,
            longName: concept.codesetName
        };
        if (concept?.status) return handleUpdateConcept(request);
        ValueSetControllerService.createValueSetUsingPost({
            authorization,
            request
        }).then((response: any) => {
            setShowForm(!isShowFrom);
            return response;
        });
    };
    const handleUpdateConcept = (request: any) => {
        ValueSetControllerService.updateConceptUsingPut({
            authorization,
            codeSetNm: concept.codesetName,
            conceptCode: concept.conceptCode,
            request
        }).then((response: any) => {
            setShowForm(!isShowFrom);
            return response;
        });
    };
    const handleDeleteConcept = () => {
        ValueSetControllerService.deleteValueSetUsingPut({
            authorization,
            codeSetNm: concept.codesetName
        }).then((response: any) => {
            // setShowForm(!isShowFrom);
            setIsDelete(false);
            return response;
        });
    };
    const handleConcept = ({ target }: React.ChangeEvent<HTMLInputElement>) => {
        setConcept({ ...concept, [target.name]: target.value });
    };

    const renderConceptForm = (
        <div className="form-container" onSubmit={onSubmit}>
            {isDelete && (
                <div className="usa-alert__body-delete">
                    <p>
                        <Icon.Warning className="margin-left-2" size={3} />
                        <span>Are you sure you want to delete the concept?</span>
                    </p>
                    <div>
                        <Button type="submit" className="line-btn" unstyled onClick={handleDeleteConcept}>
                            <span> Yes, Delete</span>
                        </Button>
                        <div className={'vertical-divider'} />
                        <Button type="submit" className="line-btn" unstyled onClick={() => setIsDelete(false)}>
                            <span> Cancel</span>
                        </Button>
                    </div>
                </div>
            )}
            <p className="instruction">Please fill out the forms to add new value set concept.</p>
            <div className="concept__add-concept--local">
                <h4>Local concept code</h4>
                <div className="concept__fields">
                    <label className="fields-info">
                        All fields with <span className="mandatory-indicator">*</span> are required
                    </label>
                    <Controller
                        control={control}
                        name="displayName"
                        rules={{
                            required: { value: true, message: 'Display name is required' },
                            pattern: { value: handleValidation(false), message: 'Question label not valid' },
                            maxLength: 50
                        }}
                        render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                            <Input
                                className="field-space"
                                type="text"
                                name={name}
                                id={name}
                                label="UI Display name"
                                defaultValue={value}
                                onChange={onChange}
                                error={error?.message}
                                required
                            />
                        )}
                    />
                    <Grid row className="inline-field">
                        <Grid tablet={{ col: true }}>
                            <Controller
                                control={control}
                                name={'code'}
                                rules={{
                                    required: { value: true, message: 'Local code is required' },
                                    maxLength: 50
                                }}
                                render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                    <Input
                                        type="text"
                                        name={name}
                                        label={'Local code'}
                                        onChange={onChange}
                                        defaultValue={value}
                                        required
                                        error={error?.message}
                                    />
                                )}
                            />
                        </Grid>
                        <Grid tablet={{ col: true }}>
                            <Controller
                                control={control}
                                name={'messagingInfo.conceptCode'}
                                rules={{
                                    required: { value: true, message: 'Concept code is required' }
                                }}
                                render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                    <Input
                                        type="text"
                                        name={name}
                                        label={'Concept code'}
                                        onChange={onChange}
                                        defaultValue={value}
                                        required
                                        error={error?.message}
                                    />
                                )}
                            />
                        </Grid>
                    </Grid>
                    <Grid row className="effective-date-field">
                        <Grid col={6} className="effective-radio">
                            <Radio
                                type="radio"
                                name="duration"
                                value="always"
                                id="eAlways"
                                checked={concept.duration === 'always'}
                                onChange={handleConcept}
                                label="Always Effective"
                            />
                            <Radio
                                id="eUntil"
                                name="duration"
                                value="until"
                                checked={concept.duration === 'until'}
                                onChange={handleConcept}
                                label="Effective Until"
                            />
                        </Grid>
                        <Grid col={5}>
                            <Controller
                                control={control}
                                name={'effectiveToTime'}
                                render={({ field: { onChange, value } }) => (
                                    <FormGroup error={false}>
                                        <DatePickerInput
                                            id="effectivDate"
                                            name="effectivDate"
                                            defaultValue={value}
                                            flexBox
                                            onChange={onChange}
                                            disableFutureDates={false}
                                            errorMessage={''}
                                            required={concept.duration === 'always'}
                                        />
                                    </FormGroup>
                                )}
                            />
                        </Grid>
                    </Grid>
                    <Grid row className="effective-date-field">
                        <Controller
                            control={control}
                            name={'statusCode'}
                            render={({ field: { name, onChange, value } }) => (
                                <Grid col={6} className="effective-radio">
                                    <Radio
                                        name={name}
                                        value={AddConceptRequest.statusCode.A}
                                        id="cstatus"
                                        checked={value === AddConceptRequest.statusCode.A}
                                        onChange={onChange}
                                        label="Active"
                                    />
                                    <Radio
                                        id="incstatus"
                                        name={name}
                                        value={AddConceptRequest.statusCode.I}
                                        checked={value === AddConceptRequest.statusCode.I}
                                        onChange={onChange}
                                        label="Inactive"
                                    />
                                </Grid>
                            )}
                        />
                    </Grid>
                </div>
                <div className="concept__add-concept--messaging">
                    <h4>Messaging concept code</h4>
                    <div className="concept__fields">
                        <label className="fields-info">
                            All fields with <span className="mandatory-indicator">*</span> are required
                        </label>
                        <Controller
                            control={control}
                            name={'messagingInfo.conceptCode'}
                            rules={{
                                required: { value: true, message: 'Concept code is required' }
                            }}
                            render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                <Input
                                    type="text"
                                    name={name}
                                    label={'Concept code'}
                                    onChange={onChange}
                                    defaultValue={value}
                                    required
                                    error={error?.message}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name={'messagingInfo.conceptName'}
                            rules={{
                                required: { value: true, message: 'Concept name is required' }
                            }}
                            render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                <Input
                                    type="text"
                                    name={name}
                                    label={'Concept name'}
                                    onChange={onChange}
                                    defaultValue={value}
                                    required
                                    error={error?.message}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name={'messagingInfo.preferredConceptName'}
                            rules={{
                                required: { value: true, message: 'Preferred concept name is required' }
                            }}
                            render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                <Input
                                    type="text"
                                    name={name}
                                    label={'Preferred concept name'}
                                    onChange={onChange}
                                    defaultValue={value}
                                    required
                                    error={error?.message}
                                />
                            )}
                        />
                        <Controller
                            control={control}
                            name="messagingInfo.codeSystem"
                            rules={{
                                required: { value: true, message: 'Code system name is required' }
                            }}
                            render={({ field: { onChange, name, value }, fieldState: { error } }) => (
                                <>
                                    <label>
                                        Concept code <span className="mandatory-indicator">*</span>
                                    </label>
                                    <ComboBox
                                        id={'codeSystem'}
                                        onChange={onChange}
                                        name={name}
                                        defaultValue={value}
                                        options={codeSystemOptionList.map((option) => {
                                            return {
                                                label: option.label,
                                                value: option.value
                                            };
                                        })}
                                    />
                                    {error?.message ? <ErrorMessage>{error?.message}</ErrorMessage> : null}
                                </>
                            )}
                        />
                        <div className="concept__buttons">
                            {!isDelete && concept?.status ? (
                                <Button
                                    type="submit"
                                    className="margin-right-2 line-btn delete-ln-btn display-none"
                                    unstyled
                                    onClick={() => setIsDelete(true)}>
                                    <Icon.Delete className="margin-right-2px" />
                                    <span> Delete</span>
                                </Button>
                            ) : (
                                <div />
                            )}
                            <div />
                            <>
                                <Button type="submit" outline onClick={handleCancelFrom}>
                                    <span> Cancel</span>
                                </Button>
                                <Button
                                    type="submit"
                                    onClick={() => onSubmit()}
                                    disabled={!conceptForm.formState.isValid}>
                                    <span>Add concept</span>
                                </Button>
                            </>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
    const list = [
        { name: 'Value Set Type', value: valueset?.valueSetTypeCd },
        { name: 'Value Set code', value: valueset?.valueSetCode },
        { name: 'Value Set name', value: valueset?.valueSetNm },
        { name: 'Value Set description', value: valueset?.codeSetDescTxt }
    ];
    return (
        <div className="value_set_concept_container">
            <h3>Value set details</h3>
            <div>
                <ul className="list-details">
                    {list.map((ls, index) => (
                        <li key={index}>
                            <div className="title">{ls.name}</div>
                            <div className="details">{ls.value}</div>
                        </li>
                    ))}
                </ul>
            </div>
            <h3 className="main-header-title" data-testid="header-title">
                Add value set concept
            </h3>
            {isShowFrom ? (
                renderConceptForm
            ) : (
                <div>
                    {!summaries?.length && !searchQuery ? (
                        <p className="description">
                            No value set concept is displayed. Please click the button below to add new value set
                            concept.
                        </p>
                    ) : (
                        <div className="concept-local-library">
                            <div className="concept-local-library__container">
                                <div className="concept-local-library__table">
                                    <ConceptTable
                                        summaries={summaries}
                                        pages={{ currentPage, pageSize, totalElements }}
                                    />
                                </div>
                            </div>
                        </div>
                    )}
                    <div className="add-new-concept-container">
                        {!summaries || summaries.length < 1 ? (
                            <p>
                                No value set concept is displayed. Please click the button below to add new value set
                                concept.
                            </p>
                        ) : (
                            <p>Can't find what you're looking for? Click the link below to add a new concept.</p>
                        )}
                        <Button
                            type="submit"
                            className="add-new-concept line-btn"
                            onClick={() => setShowForm(!isShowFrom)}>
                            <span>Add New concept</span>
                        </Button>
                    </div>
                </div>
            )}
        </div>
    );
};
