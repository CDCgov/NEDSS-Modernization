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
    PagesSubSection,
    Rule,
    SourceQuestion,
    Target
} from 'apps/page-builder/generated';
import { mapComparatorToString } from './helpers/mapComparatorToString';
import { mapRuleFunctionToString } from './helpers/mapRuleFunctionToString';
import { mapLogicForDateCompare } from './helpers/mapLogicForDateCompare';
import SubSectionsDropdown from './SubSectionDropdown';

export type QuestionProps = {
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
    onSubmitDisability: (isDisabled: boolean) => void;
}

const BusinessRulesForm = ({ question, sourceValues, targets, onSubmitDisability }: Props) => {
    const form = useFormContext<CreateRuleRequest>();
    const TargetQtnModalRef = useRef<ModalRef>(null);
    const sourceModalRef = useRef<ModalRef>(null);
    const [targetQuestions, setTargetQuestions] = useState<QuestionProps[]>([]);
    const [targetSubSections, setTargetSubSections] = useState<PagesSubSection[]>([]);
    const [sourceValueList, setSourceValueList] = useState<FieldProps[]>([]);
    const [anySourceValueToggle, setAnySource] = useState<boolean>(false);
    const [targetDescriptions, setTargetDescriptions] = useState<string[]>();
    const { pageId, ruleId } = useParams();
    const [logicValue, setLogicValue] = useState('');

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

        const values = data.map((val) => val.question);
        const text = data.map((val) => val.name);
        form.setValue('targetIdentifiers', values);
        form.setValue('targetValueText', text);
        setTargetDescriptions(data.map((val) => `${val.name} (${val.question})`));
    };

    const handleChangeTargetSubsections = (data: PagesSubSection[]) => {
        setTargetSubSections(data);

        const values = data.map((val) => val.id.toString());
        form.setValue('targetIdentifiers', values);
        form.setValue(
            'targetValueText',
            data.map((val) => val.name)
        );
    };

    const handleChangeSource = (data: QuestionProps[]) => {
        // setSelectedSource(data);
        form.setValue('sourceIdentifier', data[0].question);
        form.setValue('sourceText', `${data[0].name} (${data[0].question})`);
        fetchSourceValueSets(data[0].valueSet);
    };

    useEffect(() => {
        handleRuleDescription();
    }, [
        form.watch('targetIdentifiers'),
        form.watch('targetValueText'),
        form.watch('sourceValues'),
        form.getValues('comparator'),
        form.getValues('ruleFunction'),
        form.watch('sourceIdentifier'),
        form.watch('sourceText')
    ]);

    useEffect(() => {
        if (question?.codeSetName) {
            fetchSourceValueSets(question.codeSetName);
        }
    }, [question]);

    useEffect(() => {
        setTargetDescriptions(
            targets?.map((target) => {
                const labelLength = target.label?.length;
                if (target.label && labelLength) {
                    const lastChar = target.label?.charAt(labelLength - 1);
                    if (lastChar === ':') {
                        return `${target.label.replace(':', '')} (${target.targetIdentifier})`;
                    }
                }
                return `${target.label} (${target.targetIdentifier})`;
            }) || []
        );
    }, [targets]);

    const targetValueIdentifiers = form.watch('targetIdentifiers') || [];
    const isTargetQuestionSelected =
        targetQuestions.length || targetValueIdentifiers.length || targetSubSections.length;

    const handleRuleDescription = () => {
        let description = '';
        const sourceText = form.watch('sourceText');
        const logic = mapComparatorToString(form.getValues('comparator'));
        const sourceValues = form.watch('sourceValues');
        let sourceValueDescription = '';

        if (sourceValues?.length && !anySourceValueToggle) {
            sourceValueDescription = sourceValues?.map((value) => value.text).join(', ');
        }

        if (anySourceValueToggle) {
            sourceValueDescription = 'any source value';
        }

        const targetValues =
            form.watch('targetType') == Rule.targetType.QUESTION
                ? targetDescriptions
                : targetSubSections.map((val) => val.name);

        if (ruleFunction !== Rule.ruleFunction.DATE_COMPARE && logic && sourceValues?.length && targetValues?.length) {
            description = `IF "${sourceText}" is ${logic} ${sourceValueDescription} ${mapRuleFunctionToString(
                form.getValues('ruleFunction')
            )} "${targetValues.join('", "')}"`;
            form.setValue('description', description);
        } else {
            description = '';
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
            name: 'Less than or equal to',
            value: Rule.comparator.LESS_THAN_OR_EQUAL_TO
        },
        {
            name: 'Greater than or equal to',
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

    const checkSubmitDisability = () => {
        const sourceQuestion = form.watch('sourceText');
        const anySourceValue = form.watch('anySourceValue');
        const sourceValue = form.getValues('sourceValues')?.length;
        const targetQuestionValue = targetQuestions?.length;
        const targetDescriptionValue = targetDescriptions?.length;
        return isTargetTypeEnabled
            ? sourceQuestion && (targetQuestionValue || targetDescriptionValue)
                ? anySourceValue
                    ? false
                    : !(logicValue && sourceValue)
                : true
            : !(sourceQuestion && logicValue && (targetQuestionValue || targetDescriptionValue));
    };

    onSubmitDisability(checkSubmitDisability());

    const removeNumericAndSymbols = (text: string | undefined) => {
        const firstChar = text?.charAt(0);
        if (firstChar && firstChar <= '9' && firstChar >= '0') {
            return text?.replace(/\d+/, '').replace('. ', '');
        }
        return text;
    };

    const checkForSemicolon = (text: string | undefined) => {
        const labelLength = text?.length;
        if (labelLength) {
            const lastChar = text.charAt(labelLength - 1);
            if (lastChar === ':') {
                return text.replace(':', '');
            }
        }
    };

    const handleResetSourceQuestion = () => {
        // setSelectedSource([]);
        form.setValue('sourceIdentifier', '');
        form.setValue('sourceText', '');
        form.setValue('sourceValues', []);
        sourceModalRef.current?.toggleModal(undefined, true);
    };

    const handleTargetTypeChange = (value: Rule.targetType) => {
        form.setValue('targetType', value);
        form.setValue('targetIdentifiers', []);
        form.setValue('targetValueText', []);
        setTargetQuestions([]);
        setTargetDescriptions([]);
        form.setValue('description', '');
    };

    useEffect(() => {
        if (anySourceValueToggle) {
            form.reset({
                ...form.getValues(),
                comparator: Rule.comparator.EQUAL_TO,
                sourceValues: []
            });
        }
    }, [anySourceValueToggle]);

    useEffect(() => {
        setAnySource(form.watch('anySourceValue'));
    }, [form.watch('anySourceValue')]);

    useEffect(() => {
        setLogicValue(form.watch('comparator'));
    }, [form.watch('comparator')]);

    const renderErrorListAsString = () => {
        let errors = '';
        const length = form.watch('targetValueText')?.length || 0;
        form.watch('targetValueText')?.map((val, k) => {
            const labelLength = val?.length;
            if (val && labelLength) {
                const lastChar = val.charAt(labelLength - 1);
                if (lastChar === ':') {
                    errors += `${val.replace(':', '')} (${form.watch('targetIdentifiers')[k]})${
                        length - 1 === k ? '' : ', '
                    }`;
                }
            } else {
                errors += `${val} (${form.watch('targetIdentifiers')[k]})${length - 1 === k ? '' : ', '}`;
            }
        });
        return errors;
    };

    return (
        <>
            <Grid row className="inline-field">
                <Grid col={3}>
                    <Label className="input-label" htmlFor="sourceQuestion" requiredMarker>
                        Source question
                    </Label>
                </Grid>
                <Grid col={9}>
                    {form.watch('sourceText') ? (
                        <div className="source-question-display">
                            {removeNumericAndSymbols(form.getValues('sourceText'))}
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
                    required: { value: anySourceValueToggle ?? false, message: 'This field is required.' }
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
                                defaultValue={anySourceValueToggle ? Rule.comparator.EQUAL_TO : value}
                                onChange={onChange}
                                onBlur={onBlur}
                                options={logicList}
                                error={error?.message}
                                disabled={anySourceValueToggle}
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
                    render={({ field: { value } }) => (
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
                                    onChange={() => handleTargetTypeChange(Rule.targetType.QUESTION)}
                                    label="Question"
                                />
                                <Radio
                                    className="radio-button"
                                    id="targetType_Sub"
                                    name="targetType"
                                    value="SUBSECTION"
                                    checked={value === 'SUBSECTION'}
                                    onChange={() => handleTargetTypeChange(Rule.targetType.SUBSECTION)}
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
                        Target(s)
                    </Label>
                </Grid>
                <Grid col={9}>
                    {!isTargetQuestionSelected && form.watch('targetType') != Rule.targetType.SUBSECTION ? (
                        <div className="width-48-p margin-bottom-1em">
                            <ModalToggleButton
                                modalRef={TargetQtnModalRef}
                                className="text-input"
                                type="submit"
                                outline>
                                Search target question
                            </ModalToggleButton>
                        </div>
                    ) : null}
                    {form.watch('targetType') === Rule.targetType.SUBSECTION && pageId ? (
                        <SubSectionsDropdown
                            pageId={pageId}
                            selectedSubsectionIdentifiers={targetValueIdentifiers}
                            onSelect={handleChangeTargetSubsections}
                        />
                    ) : null}
                    {isTargetQuestionSelected && form.watch('targetType') != Rule.targetType.SUBSECTION ? (
                        <div className="selected-target-questions-display">
                            {targetDescriptions?.map((target, index: number) => (
                                <div className="margin-bottom-1" key={index}>
                                    <Icon.Check />
                                    <span className="margin-left-1"> {removeNumericAndSymbols(target)}</span>
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
                    ) : null}
                </Grid>
            </Grid>
            <Controller
                control={form.control}
                name="description"
                render={({ field: { name, onChange, onBlur, value }, fieldState: { error } }) => (
                    <Grid row className="inline-field">
                        <Grid col={3} className="rule-description-label">
                            <Label htmlFor={name} className="input-label">
                                Rule description
                            </Label>
                        </Grid>
                        <Grid col={9}>
                            <Input
                                onChange={onChange}
                                type="text"
                                multiline
                                defaultValue={checkForSemicolon(removeNumericAndSymbols(value))}
                                value={checkForSemicolon(removeNumericAndSymbols(value))}
                                onBlur={onBlur}
                                name={name}
                                id={name}
                            />
                            {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                        </Grid>
                    </Grid>
                )}
            />
            {ruleFunction == Rule.ruleFunction.DATE_COMPARE && ruleId ? (
                <Grid row className="inline-field">
                    <Grid col={3}>
                        <Label className="input-label" htmlFor="ruleFunction" requiredMarker>
                            Error message
                        </Label>
                    </Grid>
                    <Grid col={9}>
                        <Input
                            readOnly={true}
                            type="text"
                            multiline
                            defaultValue={`'${removeNumericAndSymbols(
                                form.watch('sourceText')
                            )}' must be ${mapLogicForDateCompare(
                                form.watch('comparator')
                            )} '${renderErrorListAsString()}'`}
                            name={'errorMessage'}
                            id={'errorMessage'}
                        />
                    </Grid>
                </Grid>
            ) : null}
            {pageId && (
                <>
                    <TargetQuestion
                        modalRef={TargetQtnModalRef}
                        getList={handleChangeTargetQuestion}
                        pageId={pageId}
                        header="Target question"
                        ruleFunction={ruleFunction}
                    />
                    <TargetQuestion
                        modalRef={sourceModalRef}
                        getList={handleChangeSource}
                        multiSelected={false}
                        header="Source question"
                        isSource={true}
                        pageId={pageId}
                        ruleFunction={ruleFunction}
                    />
                </>
            )}
        </>
    );
};

export default BusinessRulesForm;
