import { Button, Form, ModalRef, ModalToggleButton, Radio } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import { createCondition } from 'apps/page-builder/services/conditionAPI';
import { fetchProgramAreaOptions } from 'apps/page-builder/services/programAreaAPI';
import {
    fetchCodingSystemOptions,
    fetchFamilyOptions,
    fetchGroupOptions
} from 'apps/page-builder/services/valueSetAPI';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { RefObject, useEffect, useState } from 'react';
import { Controller, useForm, useWatch } from 'react-hook-form';
import { Condition, CreateConditionRequest, ProgramArea } from '../../generated';
import { Concept } from '../../generated/models/Concept';
import './CreateCondition.scss';
import { useConfiguration } from 'configuration';

type Props = {
    modal: RefObject<ModalRef>;
    conditionCreated?: (condition: Condition) => void;
};

const codingSystemDefault = 'CONDITION_LIST_CDC';

export const CreateCondition = ({ modal, conditionCreated }: Props) => {
    const { handleSubmit, control, reset, resetField, formState } = useForm<CreateConditionRequest>({
        mode: 'onBlur',
        defaultValues: { codeSystemDescTxt: codingSystemDefault }
    });
    const formWatch = useWatch({ control });
    const { showAlert } = useAlert();
    const { properties } = useConfiguration();

    // DropDown Options
    const [familyOptions, setFamilyOptions] = useState([] as Concept[]);
    const [groupOptions, setGroupOptions] = useState([] as Concept[]);
    const [programAreaOptions, setProgramAreaOptions] = useState([] as ProgramArea[]);
    const [systemOptions, setSystemOptions] = useState([] as Concept[]);

    useEffect(() => {
        fetchFamilyOptions().then((response) => setFamilyOptions(response));
        fetchGroupOptions().then((response) => setGroupOptions(response));
        fetchProgramAreaOptions().then((response) => setProgramAreaOptions(response ?? []));
        fetchCodingSystemOptions().then((response) => setSystemOptions(response));
    }, []);

    const onSubmit = handleSubmit(async (data) => {
        await createCondition(data)
            .then((response: Condition) => {
                showAlert({ type: 'success', title: 'Created', message: 'Condition created successfully' });
                resetInput();
                if (conditionCreated) {
                    conditionCreated(response);
                }
                modal?.current?.toggleModal(undefined, false);
            })
            .catch((error: any) => {
                console.log(error.body);
                showAlert({ type: 'error', title: 'Error', message: error.body.message });
            });
    });

    const resetInput = () => {
        reset();
    };

    const isStdOrHivProgramArea = (programArea: string | undefined): boolean => {
        return (
            programArea !== undefined &&
            (properties.hivProgramAreas.includes(programArea) || properties.stdProgramAreas.includes(programArea))
        );
    };

    useEffect(() => {
        // if new selected program area code is not hiv or std, clear co-infection group
        if (!isStdOrHivProgramArea(formWatch.progAreaCd)) {
            resetField('coinfectionGrpCd');
        }
    }, [formWatch.progAreaCd]);

    return (
        <div className="create-condition">
            <Form onSubmit={onSubmit}>
                <div className="create-condition__container">
                    <h4 className="main-header-title" data-testid="header-title">
                        Condition details
                    </h4>
                    <p>
                        These fields will be displayed to your users.
                        <br />
                        All fields with <span className="mandatory">*</span> are required.
                    </p>
                    <Controller
                        control={control}
                        name="conditionShortNm"
                        rules={{
                            pattern: {
                                value: /^[A-Za-z0-9_+():*-=;,./]+$/,
                                message: 'Valid characters are A-Z, a-z, 0-9, or * ( ) _ + - = ; : / . ,'
                            },
                            required: { value: true, message: 'Condition name is required' }
                        }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <Input
                                id={name}
                                name={name}
                                type="text"
                                data-testid="conditionName"
                                label="Condition Name"
                                defaultValue={value}
                                error={error?.message}
                                onChange={onChange}
                                onBlur={onBlur}
                                required
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="codeSystemDescTxt"
                        rules={{ required: { value: true, message: 'Coding System is required' } }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <SelectInput
                                label="Coding System"
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                options={systemOptions.map((option) => {
                                    return {
                                        name: option.preferredConceptName ?? '',
                                        value: option.localCode ?? ''
                                    };
                                })}
                                error={error?.message}
                                required
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="code"
                        rules={{
                            required: { value: true, message: 'Condition Code required' },
                            pattern: {
                                value: /^\w*$/,
                                message: 'Valid characters are A-Z, a-z, 0-9, or _'
                            }
                        }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <Input
                                onChange={onChange}
                                onBlur={onBlur}
                                defaultValue={value}
                                label="Condition Code"
                                type="text"
                                error={error?.message}
                                required
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="progAreaCd"
                        rules={{ required: { value: true, message: 'Program area required' } }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <SelectInput
                                label="Program Area"
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                options={programAreaOptions.map((option) => {
                                    return {
                                        name: option.display!,
                                        value: option.value!
                                    };
                                })}
                                error={error?.message}
                                required
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="familyCd"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                label="Condition family"
                                defaultValue={value}
                                onChange={onChange}
                                options={familyOptions.map((option) => {
                                    return {
                                        name: option.display,
                                        value: option.localCode
                                    };
                                })}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="coinfectionGrpCd"
                        render={({ field: { onChange, value } }) => (
                            <SelectInput
                                label="Co-infection group"
                                defaultValue={value}
                                onChange={onChange}
                                disabled={!isStdOrHivProgramArea(formWatch.progAreaCd)}
                                options={groupOptions.map((option) => {
                                    return {
                                        name: option.display,
                                        value: option.localCode
                                    };
                                })}
                            />
                        )}
                    />
                    <hr />
                    <h4>Condition behavior</h4>
                    <label htmlFor="nndInd">
                        Is this a CDC reportable condition (NND)? <span className="mandatory">*</span>
                    </label>
                    <Controller
                        control={control}
                        name="nndInd"
                        defaultValue="Y"
                        render={({ field: { onChange, value } }) => (
                            <div className="radio-group">
                                <Radio
                                    id="reportableCondition_Y"
                                    name="reportableCondition"
                                    value="Y"
                                    label="Yes"
                                    onChange={(e: any) => onChange(e.target.value)}
                                    checked={value === 'Y'}
                                />
                                <Radio
                                    id="reportableCondition_N"
                                    name="reportableCondition"
                                    value="N"
                                    label="No"
                                    onChange={(e: any) => onChange(e.target.value)}
                                    checked={value === 'N'}
                                />
                            </div>
                        )}
                    />
                    <label htmlFor="reportableMorbidityInd">
                        Is this reportable through Morbidity Reports? <span className="mandatory">*</span>
                    </label>
                    <Controller
                        control={control}
                        name="reportableMorbidityInd"
                        defaultValue="Y"
                        render={({ field: { onChange, value } }) => (
                            <div className="radio-group">
                                <Radio
                                    id="mobilityReports_Y"
                                    name="mobilityReports"
                                    value="Y"
                                    label="Yes"
                                    onChange={(e: any) => onChange(e.target.value)}
                                    checked={value === 'Y'}
                                />
                                <Radio
                                    id="mobilityReports_N"
                                    name="mobilityReports"
                                    value="N"
                                    label="No"
                                    onChange={(e: any) => onChange(e.target.value)}
                                    checked={value === 'N'}
                                />
                            </div>
                        )}
                    />
                    <label htmlFor="reportableSummaryInd">
                        Is this reportable in Aggregate (summary)? <span className="mandatory">*</span>
                    </label>
                    <Controller
                        control={control}
                        name="reportableSummaryInd"
                        defaultValue="N"
                        render={({ field: { onChange, value } }) => (
                            <div className="radio-group">
                                <Radio
                                    id="reportableAggregate_Y"
                                    name="reportableAggregate"
                                    value="Y"
                                    label="Yes"
                                    onChange={(e: any) => onChange(e.target.value)}
                                    checked={value === 'Y'}
                                />
                                <Radio
                                    id="reportableAggregate_N"
                                    name="reportableAggregate"
                                    value="N"
                                    label="No"
                                    onChange={(e: any) => onChange(e.target.value)}
                                    checked={value === 'N'}
                                />
                            </div>
                        )}
                    />
                    <label htmlFor="contactTracingEnableInd">
                        Will this condition need the Contact Tracing Module? <span className="mandatory">*</span>
                    </label>
                    <Controller
                        control={control}
                        name="contactTracingEnableInd"
                        defaultValue="Y"
                        render={({ field: { onChange, value } }) => (
                            <div className="radio-group">
                                <Radio
                                    id="tracingModule_Y"
                                    name="tracingModule"
                                    value="Y"
                                    label="Yes"
                                    onChange={(e: any) => onChange(e.target.value)}
                                    checked={value === 'Y'}
                                />
                                <Radio
                                    id="tracingModule_N"
                                    name="tracingModule"
                                    value="N"
                                    label="No"
                                    onChange={(e: any) => onChange(e.target.value)}
                                    checked={value === 'N'}
                                />
                            </div>
                        )}
                    />
                </div>
                <div className="create-condition__buttons">
                    <ModalToggleButton modalRef={modal} type="reset" closer className="cancel-btn" onClick={resetInput}>
                        Cancel
                    </ModalToggleButton>

                    <Button disabled={!formState.isValid} type={'submit'}>
                        Create and add to page
                    </Button>
                </div>
            </Form>
        </div>
    );
};
