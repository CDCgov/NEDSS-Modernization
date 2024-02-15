import { useEffect, useRef, useState } from 'react';
import { Checkbox, ErrorMessage, Grid, Icon, Label, ModalRef, ModalToggleButton, Radio } from '@trussworks/react-uswds';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import { Controller, useFormContext } from 'react-hook-form';
import TargetQuestion from '../../components/TargetQuestion/TargetQuestion';
import { useParams } from 'react-router-dom';
import { Input } from '../../../../components/FormInputs/Input';
import { useConceptAPI } from '../../components/Concept/useConceptAPI';
import { authorization } from 'authorization';
import { Concept, CreateRuleRequest, SourceValue, Rule } from 'apps/page-builder/generated';

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
        form.setValue('targetIdentifiers', value);
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

    const targetValueIdentifier = form.watch('targetIdentifiers') || [];

    const isTargetQuestionSelected = targetQuestion.length || targetValueIdentifier.length;

    const handleRuleDescription = () => {
        let description = '';
        const logic = form.watch('comparator');
        const sourceValues = form.watch('sourceValues');
        const sourceValueDescription = sourceValues?.map((value) => value.text).join(', ');
        if (selectedSource.length && targetQuestion.length && logic) {
            const targetValue = targetQuestion.map((val) => `${val.name} (${val.question})`);
            description = `${sourceDescription} ${logic} ${sourceValueDescription} ${form.watch(
                'ruleFunction'
            )} ${targetValue}`;
            form.setValue('description', description);
        }
    };

    const nonDateCompare = [
        {
            name: 'Equal to',
            value: '='
        },
        {
            name: 'Not equal to',
            value: '!='
        }
    ];

    const dateCompare = [
        {
            name: 'Less than',
            value: '<'
        },
        {
            name: 'Less or equal to',
            value: '<='
        },
        {
            name: 'Greater or equal to',
            value: '>='
        },
        {
            name: 'Greater than',
            value: '>'
        }
    ];

    const ruleFunction = form.watch('ruleFunction');
    const logicList = ruleFunction === Rule.RuleFunction.DATE_COMPARE ? dateCompare : nonDateCompare;

    const handleSourceValueChange = (data: SourceValue[]) => {
        form.setValue('sourceValues', data);
        handleRuleDescription();
    };

    const isTargetTypeEnabled =
        form.watch('ruleFunction') === Rule.RuleFunction.ENABLE ||
        form.watch('ruleFunction') === Rule.RuleFunction.DISABLE ||
        form.watch('ruleFunction') === Rule.RuleFunction.UNHIDE ||
        form.watch('ruleFunction') === Rule.RuleFunction.HIDE;

    const handleResetSourceQuestion = () => {
        setSelectedSource([]);
        setSourceDescription('');
        form.setValue('sourceIdentifier', '');
        form.setValue('sourceText', '');
        form.setValue('sourceValues', []);
        sourceModalRef.current?.toggleModal(undefined, true);
    };

    return (
        <>
            <Grid row className="inline-field">
                <Grid col={3}>
                    <Label className="input-label" htmlFor="sourceQuestion" requiredMarker>
                        Source Question
                    </Label>
                </Grid>
                <Grid col={9}>
                    {selectedSource.length ? (
                        <div className="source-question-display">
                            {selectedSource[0].name}
                            <Icon.Close onClick={handleResetSourceQuestion} />
                        </div>
                    ) : (
                        <ModalToggleButton
                            name="sourceQuestion"
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

            {ruleFunction != Rule.RuleFunction.DATE_COMPARE && (
                <Controller
                    control={form.control}
                    name="anySourceValue"
                    render={({ field: { onChange, value } }) => (
                        <Grid row className="inline-field">
                            <Grid col={3}>
                                <Label className="input-label" htmlFor="anySourceValue">
                                    Any source value
                                </Label>
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
                            <Label className="input-label" htmlFor="sourceQuestion" requiredMarker>
                                Logic
                            </Label>
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

            {ruleFunction != Rule.RuleFunction.DATE_COMPARE && (
                <Controller
                    control={form.control}
                    name="sourceValues"
                    render={() => (
                        <Grid row className="inline-field">
                            <Grid col={3}>
                                <Label className="input-label" htmlFor="sourceValue" requiredMarker>
                                    Source value(s)
                                </Label>
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
                                <Label className="input-label" htmlFor="targetType" requiredMarker>
                                    Target type
                                </Label>
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
                    <Label className="input-label" htmlFor="targetQuestions" requiredMarker>
                        Target Question(s)
                    </Label>
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
                name="description"
                render={({ field: { name, onChange, onBlur, value }, fieldState: { error } }) => (
                    <Grid row className="inline-field">
                        <Grid col={3} className="rule-description-label">
                            <Label htmlFor={name} className="input-label">
                                Rule Description
                            </Label>
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
