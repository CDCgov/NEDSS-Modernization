import { useEffect, useRef, useState } from 'react';
import { Checkbox, ErrorMessage, Grid, Icon, ModalRef, ModalToggleButton, Radio } from '@trussworks/react-uswds';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import { Controller, useFormContext } from 'react-hook-form';
import { nonDateCompare, dateCompare } from '../../constant/constant';
import TargetQuestion from '../../components/TargetQuestion/TargetQuestion';
import { useParams } from 'react-router-dom';
import { Input } from '../../../../components/FormInputs/Input';
import { useConceptAPI } from '../../components/Concept/useConceptAPI';
import { authorization } from 'authorization';
import { Concept, CreateRuleRequest } from 'apps/page-builder/generated';

type QuestionProps = {
    id: number;
    name: string;
    question: string;
    selected: boolean;
    valueSet: string;
};

type FieldProps = {
    name: string;
    value: string;
};

const BusinessRulesForm = () => {
    const form = useFormContext<CreateRuleRequest>();
    const TargetQtnModalRef = useRef<ModalRef>(null);
    const sourceModalRef = useRef<ModalRef>(null);
    const [targetQuestion, setTargetQuestion] = useState<QuestionProps[]>([]);
    const [sourceValueList, setSourceValueList] = useState<FieldProps[]>([]);
    const [selectedSource, setSelectedSource] = useState<QuestionProps[]>([]);
    const { pageId } = useParams();
    const [sourceDescription, setSourceDescription] = useState<string>(
        form.watch('sourceText') && form.watch('sourceIdentifier')
            ? `${form.watch('sourceText')} (${form.watch('sourceIdentifier')})`
            : ''
    );

    const fetchSourceValueSets = async (valueSet: string) => {
        const content: Concept[] = await useConceptAPI(authorization(), valueSet);
        const list = content?.map((src: any) => ({ name: src.longName, value: src.conceptCode }));
        setSourceValueList(list);
    };

    const handleChangeTargetQuestion = (data: QuestionProps[]) => {
        setTargetQuestion(data);
        const value = data.map((val) => val.question);
        const text = data.map((val) => val.name);
        form.setValue('targetValueIdentifier', value);
        form.setValue('targetValueText', text);
    };

    const handleChangeSource = (data: QuestionProps[]) => {
        setSelectedSource(data);
        form.setValue('sourceIdentifier', data[0].question);
        form.setValue('sourceText', data[0].name);
        setSourceDescription(`${data[0].name} (${data[0].question})`);
        fetchSourceValueSets(data[0].valueSet);
    };

    useEffect(() => {
        handleRuleDescription();
    }, [targetQuestion, selectedSource]);

    const targetValueIdentifier = form.watch('targetValueIdentifier') || [];

    const isTargetQuestionSelected = targetQuestion.length || targetValueIdentifier.length;

    const handleRuleDescription = () => {
        let description = '';
        const logic = form.watch('comparator');
        const sourceValue = form.watch('sourceValue');
        const sourceValueDescription = `${sourceValue?.sourceValueText?.join(' ')} `;
        if (selectedSource.length && targetQuestion.length && logic) {
            const targetValue = targetQuestion.map((val) => `${val.name} (${val.question})`);
            description = `${sourceDescription} ${logic} ${sourceValueDescription} ${form.watch(
                'ruleFunction'
            )} ${targetValue}`;
            form.setValue('ruleDescription', description);
        }
    };

    const ruleFunction = form.watch('ruleFunction');
    const logicList = ruleFunction == 'Date validation' ? dateCompare : nonDateCompare;

    const handleSourceValueChange = (data: string[]) => {
        const values = form.getValues('sourceValue');
        if (values) {
            values.sourceValueText = [...data];
            form.setValue('sourceValue', values);
        } else {
            form.setValue('sourceValue', { sourceValueText: data, sourceValueId: [] });
        }
        handleRuleDescription();
    };

    useEffect(() => {
        if (form.watch('sourceValue')) {
            const test = form.getValues('sourceValue');
            handleSourceValueChange(test?.sourceValueText || []);
        }
    }, []);

    const isTargetTypeEnabled =
        form.watch('ruleFunction') === 'Enable' ||
        form.watch('ruleFunction') === 'Disable' ||
        form.watch('ruleFunction') === 'Hide' ||
        form.watch('ruleFunction') === 'Unhide';

    const handleResetSourceQuestion = () => {
        setSelectedSource([]);
        setSourceDescription('');
        form.setValue('sourceIdentifier', '');
        form.setValue('sourceText', '');
        form.setValue('sourceValue', { sourceValueText: [], sourceValueId: [] });
        sourceModalRef.current?.toggleModal(undefined, true);
    };

    return (
        <>
            <Grid row className="inline-field">
                <Grid col={3}>
                    <label className="input-label">Source Question</label>
                </Grid>
                <Grid col={9}>
                    {selectedSource.length ? (
                        <div className="source-question-display">
                            {selectedSource[0].name}
                            <Icon.Close onClick={handleResetSourceQuestion} />
                        </div>
                    ) : (
                        <ModalToggleButton
                            modalRef={sourceModalRef}
                            id="sourceQuestionId"
                            outline
                            className="text-input"
                            type="submit">
                            Search source question
                        </ModalToggleButton>
                    )}
                </Grid>
            </Grid>

            {ruleFunction != 'Date validation' && (
                <Controller
                    control={form.control}
                    name="anySourceValue"
                    render={({ field: { onChange, value } }) => (
                        <Grid row className="inline-field">
                            <Grid col={3}>
                                <label className="input-label">Any source value</label>
                            </Grid>
                            <Grid col={9} className="height-3">
                                <Checkbox
                                    onChange={onChange}
                                    className="any-source-value-checkbox"
                                    id="anySourceValue"
                                    type="checkbox"
                                    checked={value}
                                    label=""
                                    name="anySourceValue"
                                />
                            </Grid>
                        </Grid>
                    )}
                />
            )}
            <Controller
                control={form.control}
                name="comparator"
                rules={{
                    required: { value: true, message: 'This field is required.' }
                }}
                render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                    <Grid row className="inline-field">
                        <Grid col={3}>
                            <label className="input-label">Logic</label>
                        </Grid>
                        <Grid col={9}>
                            <SelectInput
                                className="text-input"
                                defaultValue={value}
                                onChange={onChange}
                                onBlur={onBlur}
                                options={logicList}
                                error={error?.message}
                                required
                            />
                        </Grid>
                    </Grid>
                )}
            />

            {ruleFunction != 'Date validation' && (
                <Controller
                    control={form.control}
                    name="sourceValue"
                    render={() => (
                        <Grid row className="inline-field">
                            <Grid col={3}>
                                <label className="input-label">Source value(s)</label>
                            </Grid>
                            <Grid col={9}>
                                <div className="text-input">
                                    <MultiSelectInput
                                        onChange={(e) => {
                                            handleSourceValueChange(e);
                                        }}
                                        options={sourceValueList}
                                    />
                                </div>
                            </Grid>
                        </Grid>
                    )}
                />
            )}
            {isTargetTypeEnabled && (
                <Controller
                    control={form.control}
                    name="targetType"
                    render={({ field: { onChange, value } }) => (
                        <Grid row className="inline-field">
                            <Grid col={3}>
                                <label className="input-label">Target type</label>
                            </Grid>
                            <Grid col={9} className="radio-group">
                                <Radio
                                    className="radio-button"
                                    type="radio"
                                    name="targetType"
                                    value="QUESTION"
                                    id="targetType_Qtn"
                                    checked={value === 'QUESTION'}
                                    onChange={onChange}
                                    label="Question"
                                />
                                <Radio
                                    className="radio-button"
                                    id="targetType_Sub"
                                    name="targetType"
                                    value="SUBSECTION"
                                    checked={value === 'SUBSECTION'}
                                    onChange={onChange}
                                    label="Subsection"
                                />
                            </Grid>
                        </Grid>
                    )}
                />
            )}
            <Grid row className="inline-field">
                <Grid col={3}>
                    <label className="input-label">Target Question(s)</label>
                </Grid>
                <Grid col={9}>
                    {!isTargetQuestionSelected ? (
                        <div className="width-48-p margin-bottom-1em">
                            <ModalToggleButton
                                modalRef={TargetQtnModalRef}
                                className="text-input"
                                type="submit"
                                outline>
                                Search target question
                            </ModalToggleButton>
                        </div>
                    ) : (
                        <div className="selected-target-questions-display">
                            {targetQuestion?.map((qtn, index: number) => (
                                <div className="margin-bottom-1" key={index}>
                                    <Icon.Check />
                                    <span className="margin-left-1"> {`${qtn.name} (${qtn.question})`}</span>
                                </div>
                            ))}

                            <div className="target-edit-btn">
                                <ModalToggleButton
                                    modalRef={TargetQtnModalRef}
                                    type="submit"
                                    className="line-btn"
                                    unstyled>
                                    <Icon.Edit className="margin-right-1" />
                                    <span>Edit</span>
                                </ModalToggleButton>
                            </div>
                        </div>
                    )}
                </Grid>
            </Grid>
            <Controller
                control={form.control}
                name="ruleDescription"
                render={({ field: { name, onChange, onBlur, value }, fieldState: { error } }) => (
                    <Grid row className="inline-field">
                        <Grid col={3} className="rule-description-label">
                            <label htmlFor={name} className="input-label">
                                Rule Description
                            </label>
                        </Grid>
                        <Grid col={9}>
                            <Input
                                onChange={onChange}
                                type="text"
                                multiline
                                defaultValue={value}
                                value={value}
                                onBlur={onBlur}
                                name={name}
                                id={name}
                            />
                            {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                        </Grid>
                    </Grid>
                )}
            />
            {pageId && (
                <>
                    <TargetQuestion
                        modalRef={TargetQtnModalRef}
                        getList={handleChangeTargetQuestion}
                        pageId={pageId}
                        header="Target question"
                    />
                    <TargetQuestion
                        modalRef={sourceModalRef}
                        getList={handleChangeSource}
                        multiSelected={false}
                        header="Source Question"
                        pageId={pageId}
                    />
                </>
            )}
        </>
    );
};

export default BusinessRulesForm;
