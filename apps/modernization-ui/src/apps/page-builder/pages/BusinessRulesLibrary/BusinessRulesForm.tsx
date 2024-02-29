import { useEffect, useRef, useState } from 'react';
import { Checkbox, ErrorMessage, Grid, Icon, Label, ModalRef, ModalToggleButton, Radio } from '@trussworks/react-uswds';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import { Controller, useFormContext } from 'react-hook-form';
import TargetQuestion from '../../components/TargetQuestion/TargetQuestion';
import { useParams } from 'react-router-dom';
import { Input } from '../../../../components/FormInputs/Input';
import { authorization } from 'authorization';
import {
    Concept,
    ConceptControllerService,
    CreateRuleRequest,
    Rule,
    SourceQuestion,
    Target
} from 'apps/page-builder/generated';
import { mapComparatorToString } from './helpers/mapComparatorToString';
import { mapRuleFunctionToString } from './helpers/mapRuleFunctionToString';

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

interface Props {
    targets?: Target[];
    question?: SourceQuestion;
    sourceValues?: string[];
}

const BusinessRulesForm = ({ question, sourceValues }: Props) => {
    const form = useFormContext<CreateRuleRequest>();
    const TargetQtnModalRef = useRef<ModalRef>(null);
    const sourceModalRef = useRef<ModalRef>(null);
    const [targetQuestions, setTargetQuestions] = useState<QuestionProps[]>([]);
    const [sourceValueList, setSourceValueList] = useState<FieldProps[]>([]);
    const [selectedSource, setSelectedSource] = useState<QuestionProps[]>([]);
    const { pageId } = useParams();
    const [sourceDescription, setSourceDescription] = useState<string>(
        form.watch('sourceText') && form.watch('sourceIdentifier')
            ? `${form.watch('sourceText')} (${form.watch('sourceIdentifier')})`
            : ''
    );

    const fetchSourceValueSets = async (codeSetNm: string) => {
        const content: Concept[] = await ConceptControllerService.findConceptsUsingGet({
            authorization: authorization(),
            codeSetNm
        });
        const list = content?.map((src: Concept) => ({ name: src.display, value: src.conceptCode }));
        setSourceValueList(list);

        if (sourceValues?.length) {
            const matchedValues = sourceValues.map((valueName: string) => list.find((val) => val.name === valueName));
            const newValues = matchedValues.map((value) => ({ id: value?.value, text: value?.name }));
            form.setValue('sourceValues', newValues);
        }
    };

    const handleChangeTargetQuestion = (data: QuestionProps[]) => {
        setTargetQuestions(data);
        const value = data.map((val) => val.question);
        const text = data.map((val) => val.name);
        form.setValue('targetIdentifiers', value);
        form.setValue('targetValueText', text);
    };

    const handleChangeSource = (data: QuestionProps[]) => {
        setSelectedSource(data);
        form.setValue('sourceIdentifier', data[0].question);
        form.setValue('sourceText', `${data[0].name} (${data[0].question})`);
        setSourceDescription(`${data[0].name} (${data[0].question})`);
        fetchSourceValueSets(data[0].valueSet);
    };

    useEffect(() => {
        handleRuleDescription();
    }, [targetQuestions, selectedSource]);

    useEffect(() => {
        if (question?.codeSetName) {
            fetchSourceValueSets(question.codeSetName);
        }
    }, []);

    const targetValueIdentifier = form.watch('targetIdentifiers') || [];
    const isTargetQuestionSelected = targetQuestions.length || targetValueIdentifier.length;

    const handleRuleDescription = () => {
        let description = '';
        const logic = mapComparatorToString(form.getValues('comparator'));
        const sourceValues = form.watch('sourceValues');
        const sourceValueDescription = sourceValues?.map((value) => value.text).join(', ');

        if (selectedSource && targetQuestions.length && logic) {
            const targetValue = targetQuestions.map((val) => `${val.name} (${val.question})`);
            description = `IF "${sourceDescription}" is ${logic} ${sourceValueDescription} ${mapRuleFunctionToString(
                form.getValues('ruleFunction')
            )} "${targetValue}"`;
            form.setValue('description', description);
        }
    };

    const nonDateCompare = [
        {
            name: 'Equal to',
            value: Rule.comparator.EQUAL_TO
        },
        {
            name: 'Not equal to',
            value: Rule.comparator.NOT_EQUAL_TO
        }
    ];

    const dateCompare = [
        {
            name: 'Less than',
            value: Rule.comparator.LESS_THAN
        },
        {
            name: 'Less or equal to',
            value: Rule.comparator.LESS_THAN_OR_EQUAL_TO
        },
        {
            name: 'Greater or equal to',
            value: Rule.comparator.GREATER_THAN_OR_EQUAL_TO
        },
        {
            name: 'Greater than',
            value: Rule.comparator.GREATER_THAN
        }
    ];

    const ruleFunction = form.watch('ruleFunction');
    const logicList = ruleFunction === Rule.ruleFunction.DATE_COMPARE ? dateCompare : nonDateCompare;

    const handleSourceValueChange = (data: string[]) => {
        // create a new array by comparing data and sourceValueList, for each item in data, find the corresponding item in sourceValueList and return it
        const matchedValues = data.map((value) => sourceValueList.find((val) => val.value === value));
        const newValues = matchedValues.map((value) => ({ id: value?.value, text: value?.name }));
        form.setValue('sourceValues', newValues);
        handleRuleDescription();
    };

    const isTargetTypeEnabled =
        form.watch('ruleFunction') === Rule.ruleFunction.ENABLE ||
        form.watch('ruleFunction') === Rule.ruleFunction.DISABLE ||
        form.watch('ruleFunction') === Rule.ruleFunction.UNHIDE ||
        form.watch('ruleFunction') === Rule.ruleFunction.HIDE;

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
                    {form.watch('sourceText') ? (
                        <div className="source-question-display">
                            {form.getValues('sourceText')}
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

            {ruleFunction != Rule.ruleFunction.DATE_COMPARE && (
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
                                disabled={form.watch('anySourceValue')}
                                required
                            />
                        </Grid>
                    </Grid>
                )}
            />

            {ruleFunction != Rule.ruleFunction.DATE_COMPARE && (
                <Controller
                    control={form.control}
                    name="sourceValues"
                    render={() => (
                        <Grid row className="inline-field source">
                            <Grid col={3}>
                                <Label className="input-label" htmlFor="sourceValue" requiredMarker>
                                    Source value(s)
                                </Label>
                            </Grid>
                            <Grid col={9}>
                                <div className="text-input">
                                    <MultiSelectInput
                                        value={form?.getValues('sourceValues')?.map((val) => val?.id || '')}
                                        onChange={(value) => {
                                            console.log('e', value);
                                            handleSourceValueChange(value);
                                        }}
                                        options={sourceValueList}
                                        disabled={form.watch('anySourceValue')}
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
                            {form.getValues('targetValueText')?.map((target, index: number) => (
                                <div className="margin-bottom-1" key={index}>
                                    <Icon.Check />
                                    <span className="margin-left-1"> {`${target} (${target})`}</span>
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
                        header="Source question"
                        isSource={true}
                        pageId={pageId}
                    />
                </>
            )}
        </>
    );
};

export default BusinessRulesForm;
