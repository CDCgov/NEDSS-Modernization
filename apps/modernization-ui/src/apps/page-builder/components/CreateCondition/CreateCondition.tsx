import { ChangeEvent, RefObject, useEffect, useState } from 'react';

import { Button, Form, ModalRef, ModalToggleButton, Radio } from '@trussworks/react-uswds';
import { Controller, useForm, useWatch } from 'react-hook-form';

import { createCondition } from 'apps/page-builder/services/conditionAPI';
import { fetchProgramAreaOptions } from 'apps/page-builder/services/programAreaAPI';
import {
    fetchCodingSystemOptions,
    fetchFamilyOptions,
    fetchGroupOptions,
} from 'apps/page-builder/services/valueSetAPI';
import { Input } from 'components/FormInputs/Input';
import { useConfiguration } from 'configuration';
import { SingleSelect } from 'design-system/select';
import { useAlert } from 'libs/alert';
import { Selectable } from 'options';
import { logErrorToUserConsole } from 'utils/logging';

import { Condition, CreateConditionRequest } from '../../generated';

import './CreateCondition.scss';

type Props = {
    modal: RefObject<ModalRef>;
    conditionCreated?: (condition: Condition) => void;
};

const codingSystemDefault = 'CONDITION_LIST_CDC';

export const CreateCondition = ({ modal, conditionCreated }: Props) => {
    const { handleSubmit, control, reset, resetField, formState } = useForm<CreateConditionRequest>({
        mode: 'onBlur',
        defaultValues: { codeSystemDescTxt: codingSystemDefault },
    });
    const formWatch = useWatch({ control });
    const { showAlert } = useAlert();
    const { properties } = useConfiguration();

    // DropDown Options
    const [familyOptions, setFamilyOptions] = useState<Selectable[]>([]);
    const [groupOptions, setGroupOptions] = useState<Selectable[]>([]);
    const [programAreaOptions, setProgramAreaOptions] = useState<Selectable[]>([]);
    const [systemOptions, setSystemOptions] = useState<Selectable[]>([]);

    useEffect(() => {
        fetchFamilyOptions().then((response) =>
            setFamilyOptions(
                response.map((option) => ({
                    name: option.display,
                    value: option.localCode,
                }))
            )
        );
        fetchGroupOptions().then((response) =>
            setGroupOptions(
                response.map((option) => ({
                    name: option.display,
                    value: option.localCode,
                }))
            )
        );
        fetchProgramAreaOptions().then((response) =>
            setProgramAreaOptions(
                response?.map((option) => ({
                    name: option.display!,
                    value: option.value!,
                })) ?? []
            )
        );
        fetchCodingSystemOptions().then((response) =>
            setSystemOptions(
                response.map((option) => ({
                    name: option.preferredConceptName ?? '',
                    value: option.localCode ?? '',
                }))
            )
        );
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
            .catch((error) => {
                logErrorToUserConsole(error.body);
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
                                message: 'Valid characters are A-Z, a-z, 0-9, or * ( ) _ + - = ; : / . ,',
                            },
                            required: { value: true, message: 'Condition name is required' },
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
                                required={true}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="codeSystemDescTxt"
                        rules={{ required: { value: true, message: 'Coding System is required' } }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <SingleSelect
                                id={name}
                                label="Coding System"
                                value={systemOptions.find((o) => o.value === value)}
                                onChange={onChange}
                                onBlur={onBlur}
                                options={systemOptions}
                                error={error?.message}
                                required={true}
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
                                message: 'Valid characters are A-Z, a-z, 0-9, or _',
                            },
                        }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <Input
                                onChange={onChange}
                                onBlur={onBlur}
                                defaultValue={value}
                                label="Condition Code"
                                type="text"
                                error={error?.message}
                                required={true}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="progAreaCd"
                        rules={{ required: { value: true, message: 'Program area required' } }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <SingleSelect
                                id={name}
                                label="Program Area"
                                value={programAreaOptions.find((o) => o.value === value)}
                                onChange={onChange}
                                onBlur={onBlur}
                                options={programAreaOptions}
                                error={error?.message}
                                required={true}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="familyCd"
                        render={({ field: { onChange, value, name } }) => (
                            <SingleSelect
                                id={name}
                                label="Condition family"
                                value={familyOptions.find((o) => o.value === value)}
                                onChange={onChange}
                                options={familyOptions}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="coinfectionGrpCd"
                        render={({ field: { onChange, value, name } }) => (
                            <SingleSelect
                                id={name}
                                label="Co-infection group"
                                value={groupOptions.find((o) => o.value === value)}
                                onChange={onChange}
                                disabled={!isStdOrHivProgramArea(formWatch.progAreaCd)}
                                options={groupOptions}
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
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e.target.value)}
                                    checked={value === 'Y'}
                                />
                                <Radio
                                    id="reportableCondition_N"
                                    name="reportableCondition"
                                    value="N"
                                    label="No"
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e.target.value)}
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
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e.target.value)}
                                    checked={value === 'Y'}
                                />
                                <Radio
                                    id="mobilityReports_N"
                                    name="mobilityReports"
                                    value="N"
                                    label="No"
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e.target.value)}
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
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e.target.value)}
                                    checked={value === 'Y'}
                                />
                                <Radio
                                    id="reportableAggregate_N"
                                    name="reportableAggregate"
                                    value="N"
                                    label="No"
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e.target.value)}
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
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e.target.value)}
                                    checked={value === 'Y'}
                                />
                                <Radio
                                    id="tracingModule_N"
                                    name="tracingModule"
                                    value="N"
                                    label="No"
                                    onChange={(e: ChangeEvent<HTMLInputElement>) => onChange(e.target.value)}
                                    checked={value === 'N'}
                                />
                            </div>
                        )}
                    />
                </div>
                <div className="create-condition__buttons">
                    <ModalToggleButton
                        modalRef={modal}
                        type="reset"
                        closer={true}
                        className="cancel-btn"
                        onClick={resetInput}>
                        Cancel
                    </ModalToggleButton>

                    <Button disabled={!formState.isValid} type="submit">
                        Create and add to page
                    </Button>
                </div>
            </Form>
        </div>
    );
};
