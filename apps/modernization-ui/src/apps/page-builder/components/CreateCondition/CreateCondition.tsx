import { RefObject, useContext, useEffect, useState } from 'react';
import './CreateCondition.scss';
import { Form, Button, ModalRef, ModalToggleButton, Radio } from '@trussworks/react-uswds';
import { Condition, CreateConditionRequest } from '../../generated';
import { UserContext } from 'user';
import { useAlert } from 'alert';
import { Concept } from '../../generated/models/Concept';
import { fetchProgramAreaOptions } from 'apps/page-builder/services/programAreaAPI';
import {
    fetchCodingSystemOptions,
    fetchFamilyOptions,
    fetchGroupOptions
} from 'apps/page-builder/services/valueSetAPI';
import { Controller, useForm } from 'react-hook-form';
import { createCondition } from 'apps/page-builder/services/conditionAPI';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';

type Props = {
    modal?: RefObject<ModalRef>;
    conditionCreated?: (condition: Condition) => void;
};

export const CreateCondition = ({ modal, conditionCreated }: Props) => {
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const { handleSubmit, control, reset } = useForm<CreateConditionRequest, any>();
    const { showAlert } = useAlert();

    // DropDown Options
    const [familyOptions, setFamilyOptions] = useState([] as Concept[]);
    const [groupOptions, setGroupOptions] = useState([] as Concept[]);
    const [programAreaOptions, setProgramAreaOptions] = useState([] as Concept[]);
    const [systemOptions, setSystemOptions] = useState([] as Concept[]);

    useEffect(() => {
        fetchFamilyOptions(token).then((response) => setFamilyOptions(response));
        fetchGroupOptions(token).then((response) => setGroupOptions(response));
        fetchProgramAreaOptions(token).then((response) => setProgramAreaOptions(response));
        fetchCodingSystemOptions(token).then((response) => setSystemOptions(response));
    }, []);

    const onSubmit = handleSubmit(async (data) => {
        await createCondition(token, data)
            .then((response: Condition) => {
                showAlert({ type: 'success', header: 'Created', message: 'Condition created successfully' });
                resetInput();
                if (conditionCreated) {
                    conditionCreated(response);
                }
                modal?.current?.toggleModal(undefined, false);
            })
            .catch((error: any) => {
                console.log(error.body);
                showAlert({ type: 'error', header: 'Error', message: error.body.message });
            });
    });

    const resetInput = () => {
        reset();
    };

    return (
        <div className="create-condition">
            <Form onSubmit={onSubmit}>
                <div className="create-condition__container">
                    <h2 className="main-header-title" data-testid="header-title">
                        Create a new Condition
                    </h2>
                    <h4>Let's create a new condition to add to your page</h4>
                    <p>
                        First, we fill out some information about your new condition before creating it and associating
                        it to your new page
                    </p>
                    <p>
                        All fields with <span className="mandatory-indicator">*</span> are required
                    </p>
                    <br></br>
                    <Controller
                        control={control}
                        name="conditionShortNm"
                        rules={{ pattern: { value: /^\w*$/, message: 'Condition name not valid' } }}
                        render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                            <Input
                                id={name}
                                name={name}
                                type="text"
                                label="Condition name"
                                defaultValue={value}
                                error={error?.message}
                                onChange={onChange}
                            />
                        )}
                    />
                    <Controller
                        control={control}
                        name="codeSystemDescTxt"
                        rules={{ required: { value: true, message: 'Coding System is required' } }}
                        render={({ field: { onChange, value }, fieldState: { error } }) => (
                            <SelectInput
                                label="Coding System"
                                defaultValue={value}
                                onChange={onChange}
                                options={systemOptions.map((option) => {
                                    return {
                                        name: option.messagingConceptName || '',
                                        value: option.localCode || ''
                                    };
                                })}
                                error={error?.message}
                                required></SelectInput>
                        )}
                    />
                    <Controller
                        control={control}
                        name="code"
                        rules={{
                            required: { value: true, message: 'Condition Code required' },
                            pattern: { value: /^\w*$/, message: 'Condition Code invalid' }
                        }}
                        render={({ field: { onChange, value }, fieldState: { error } }) => (
                            <Input
                                onChange={onChange}
                                defaultValue={value}
                                label="Condition code"
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
                        render={({ field: { onChange, value }, fieldState: { error } }) => (
                            <SelectInput
                                label="Program area"
                                defaultValue={value}
                                onChange={onChange}
                                options={programAreaOptions.map((option) => {
                                    return {
                                        name: option.display!,
                                        value: option.value!
                                    };
                                })}
                                error={error?.message}
                                required></SelectInput>
                        )}
                    />
                    <label>
                        Is this a CDC reportable condition (NND)?
                        <span className="mandatory-indicator">*</span>
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
                    <label>
                        Is this reportable through Morbidity Reports?
                        <span className="mandatory-indicator">*</span>
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
                    <label>
                        Is this reportable in Aggregate (summary)?
                        <span className="mandatory-indicator">*</span>
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
                    <label>
                        Will this condition need the Contact Tracing Module?
                        <span className="mandatory-indicator">*</span>
                    </label>
                    <Controller
                        control={control}
                        name="contactTracingEnableInd"
                        defaultValue="N"
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
                    <br />
                    <p>Would you like to add any additional information?</p>
                    <p className="fields-info">These fields are optional, you can make changes to this later.</p>
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
                                        name: option.display!,
                                        value: option.value!
                                    };
                                })}></SelectInput>
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
                                options={groupOptions.map((option) => {
                                    return {
                                        name: option.display!,
                                        value: option.value!
                                    };
                                })}></SelectInput>
                        )}
                    />
                </div>
                <div className="create-condition__buttons">
                    {modal ? (
                        <ModalToggleButton
                            modalRef={modal}
                            type="reset"
                            closer
                            className="cancel-btn"
                            onClick={() => resetInput()}>
                            Cancel
                        </ModalToggleButton>
                    ) : (
                        <Button className="cancel-btn" type="reset" onClick={() => resetInput()}>
                            Cancel
                        </Button>
                    )}
                    <Button className="submit-btn" type="submit">
                        Create & add condition
                    </Button>
                </div>
            </Form>
        </div>
    );
};
