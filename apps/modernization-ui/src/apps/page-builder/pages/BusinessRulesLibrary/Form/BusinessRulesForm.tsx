import { Button, Checkbox, Icon, Label, Modal, ModalRef, Radio } from '@trussworks/react-uswds';
import { RuleRequest, PagesQuestion, PagesSubSection, Rule } from 'apps/page-builder/generated';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import styles from './BusinessRulesForm.module.scss';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import { Input } from 'components/FormInputs/Input';
import { useEffect, useRef, useState } from 'react';
import { SourceQuestion } from '../SourceQuestion/SourceQuestion';
import { SourceValueProp } from '../Add/AddBusinessRules';
import { TargetQuestion } from '../TargetQuestion/TargetQuestion';
import { mapComparatorToString } from '../helpers/mapComparatorToString';
import { mapRuleFunctionToString } from '../helpers/mapRuleFunctionToString';
import SubSectionsDropdown from '../SubSectionDropdown';
import { useParams } from 'react-router-dom';
import { mapLogicForDateCompare } from '../helpers/mapLogicForDateCompare';
import './ModalWidth.scss';
import { checkForSemicolon, removeNumericAndSymbols } from '../helpers/errorMessageUtils';
import { SegmentedButtons } from 'components/SegmentedButtons/SegmentedButtons';

type Props = {
    isEdit: boolean;
    sourceValues: SourceValueProp[];
    editSourceQuestion?: PagesQuestion;
    editTargetQuestions?: PagesQuestion[];
    editTargetSubsections?: PagesSubSection[];
    onFetchSourceValues: (valueSet?: string) => void;
};

export const BusinessRulesForm = ({
    sourceValues,
    onFetchSourceValues,
    isEdit,
    editSourceQuestion,
    editTargetQuestions,
    editTargetSubsections
}: Props) => {
    const form = useFormContext<RuleRequest>();
    const watch = useWatch(form);
    const sourceQuestionModalRef = useRef<ModalRef>(null);
    const targetQuestionModalRef = useRef<ModalRef>(null);
    const { ruleId } = useParams();

    const [sourceQuestion, setSourceQuestion] = useState<PagesQuestion | undefined>(undefined);
    const [targetQuestions, setTargetQuestion] = useState<PagesQuestion[] | undefined>([]);

    const [targetDescription, setTargetDescription] = useState<string[]>([]);

    const fieldTypeTab = [
        { value: Rule.ruleFunction.ENABLE, display: 'Enable' },
        { value: Rule.ruleFunction.DISABLE, display: 'Disable' },
        { value: Rule.ruleFunction.DATE_COMPARE, display: 'Date validation' },
        { value: Rule.ruleFunction.HIDE, display: 'Hide' },
        { value: Rule.ruleFunction.UNHIDE, display: 'Unhide' },
        { value: Rule.ruleFunction.REQUIRE_IF, display: 'Require if' }
    ];

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

    const logicList = watch.ruleFunction === Rule.ruleFunction.DATE_COMPARE ? dateCompare : nonDateCompare;

    useEffect(() => {
        setSourceQuestion(editSourceQuestion);
    }, [editSourceQuestion]);

    useEffect(() => {
        setTargetQuestion(editTargetQuestions);
        watch.targetType === Rule.targetType.SUBSECTION
            ? setTargetDescription(editTargetSubsections?.map((qtn) => `${qtn.name} (${qtn.questionIdentifier})`) ?? [])
            : setTargetDescription(editTargetQuestions?.map((qtn) => `${qtn.name} (${qtn.question})`) ?? []);
    }, [JSON.stringify(editTargetQuestions)]);

    const handleTargetTypeChange = (value: Rule.targetType) => {
        form.reset({
            ...form.getValues(),
            targetType: value,
            targetIdentifiers: [],
            targetValueText: [],
            description: ''
        });
    };

    const handleOpenSourceQuestion = () => {
        sourceQuestionModalRef.current?.toggleModal(undefined, true);
    };

    const handleCloseSourceQuestion = () => {
        sourceQuestionModalRef.current?.toggleModal(undefined, false);
    };

    const handleOpenTargetQuestion = () => {
        targetQuestionModalRef.current?.toggleModal(undefined, true);
    };

    const handleCloseTargetQuestion = () => {
        targetQuestionModalRef.current?.toggleModal(undefined, false);
    };

    const handleSourceQuestion = (question?: PagesQuestion) => {
        form.setValue('sourceText', `${question?.name} (${question?.question})`);
        form.setValue('sourceIdentifier', `${question?.question}`);
        setSourceQuestion(question);
        onFetchSourceValues(question?.valueSet ?? '');
    };

    const handleSourceValueChange = (data: string[]) => {
        // create a new array by comparing data and sourceValueList, for each item in data, find the corresponding item in sourceValueList and return it
        const matchedValues = data.map((value) => sourceValues.find((val) => val.value === value));
        const newValues = matchedValues.map((value) => ({ id: value?.value, text: value?.name }));
        form.setValue('sourceValues', newValues);
    };

    const handleTargetQuestion = (questions: PagesQuestion[]) => {
        const values = questions.map((val) => val.question ?? '');
        const text = questions.map((val) => val.name);
        form.setValue('targetIdentifiers', values);
        form.setValue('targetValueText', text);
        setTargetQuestion(questions);
        setTargetDescription(questions.map((val) => `${val.name} (${val.question})`));
    };

    const handleTargetSubsection = (subSections: PagesSubSection[]) => {
        const values = subSections.map((val) => val.questionIdentifier ?? '');
        const text = subSections.map((val) => val.name);
        form.setValue('targetIdentifiers', values);
        form.setValue('targetValueText', text);
        setTargetDescription(subSections.map((val) => `${val.name} (${val.questionIdentifier})`));
    };

    useEffect(() => {
        setTargetQuestion(undefined);
    }, [watch.targetType]);

    useEffect(() => {
        if (
            watch.targetIdentifiers &&
            (watch.anySourceValue ||
                (watch.comparator && watch.sourceValues) ||
                (watch.ruleFunction === Rule.ruleFunction.DATE_COMPARE && watch.comparator)) &&
            watch.sourceIdentifier &&
            targetDescription.length
        ) {
            const descrip = handleRuleDescription();
            form.setValue('description', descrip);
        } else {
            form.setValue('description', '');
        }
    }, [
        JSON.stringify(watch.targetIdentifiers),
        watch.anySourceValue,
        watch.comparator,
        watch.ruleFunction,
        JSON.stringify(watch.sourceValues),
        watch.sourceIdentifier,
        JSON.stringify(targetDescription)
    ]);

    const handleRuleDescription = (): string => {
        let description = '';
        const sourceText = watch.sourceText;
        const sourceValues = watch.sourceValues;
        const ruleFunction = watch.ruleFunction;
        const logic =
            ruleFunction === Rule.ruleFunction.DATE_COMPARE
                ? mapLogicForDateCompare(watch.comparator)
                : mapComparatorToString(watch.comparator);
        let sourceValueDescription = '';

        if (sourceValues?.length && !watch.anySourceValue) {
            sourceValueDescription = sourceValues?.map((value) => value.text).join(', ');
        }

        if (watch.anySourceValue) {
            sourceValueDescription = 'any source value';
        }

        const targetValues: string[] | undefined = targetDescription;

        if (
            ruleFunction !== Rule.ruleFunction.DATE_COMPARE &&
            logic &&
            sourceValueDescription &&
            targetValues?.length
        ) {
            description = `IF "${sourceText}" is ${logic} ${sourceValueDescription} ${mapRuleFunctionToString(
                form.getValues('ruleFunction')
            )} "${targetValues.join('", "')}"`;
        } else if (ruleFunction === Rule.ruleFunction.DATE_COMPARE && logic) {
            description = `'${sourceText}' must be ${logic} '${targetValues.join('", "')}'`;
        } else {
            description = '';
        }
        return description;
    };

    useEffect(() => {
        if (watch.anySourceValue) {
            form.reset({
                ...form.getValues(),
                comparator: Rule.comparator.EQUAL_TO,
                sourceValues: undefined
            });
        }
    }, [watch.anySourceValue]);

    return (
        <div className={styles.businessRules}>
            {isEdit && (
                <div className={styles.ruleId}>
                    <Label className={styles.title} htmlFor="ruleIdTitle">
                        Rule Id
                    </Label>
                    <Label className={styles.content} htmlFor="ruleId">
                        {ruleId}
                    </Label>
                </div>
            )}
            <Controller
                control={form.control}
                name="ruleFunction"
                rules={{ required: { value: true, message: 'Rule function is required' } }}
                render={({ field: { onChange, onBlur, value } }) => (
                    <div className={styles.ruleFunction}>
                        <div className={styles.title}>
                            <Label htmlFor="ruleFunction" requiredMarker>
                                Function
                            </Label>
                        </div>
                        {isEdit && (
                            <div className={styles.content}>
                                <Label htmlFor={'ruleFunction'}>
                                    {fieldTypeTab.find((tab) => tab.value === form.getValues('ruleFunction'))
                                        ?.display || 'Enable'}
                                </Label>
                            </div>
                        )}
                        {!isEdit && (
                            <div className={styles.functionBtns}>
                                <SegmentedButtons
                                    buttons={fieldTypeTab.map((tab) => ({
                                        value: tab.value,
                                        name: tab.display,
                                        label: tab.display
                                    }))}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    onClick={(button: any): void => {
                                        form.reset({
                                            ruleFunction: button.value,
                                            targetType: Rule.targetType.QUESTION,
                                            anySourceValue: false,
                                            targetIdentifiers: [],
                                            targetValueText: [],
                                            description: '',
                                            sourceIdentifier: '',
                                            sourceValues: [],
                                            sourceText: ''
                                        });
                                        setTargetQuestion(undefined);
                                        setSourceQuestion(undefined);
                                    }}
                                    value={value}
                                />
                            </div>
                        )}
                    </div>
                )}
            />
            {watch.ruleFunction && (
                <>
                    <div className={styles.sourceQuestion}>
                        <div className={styles.title}>
                            <Label htmlFor="sourceQuestion" requiredMarker>
                                Source question
                            </Label>
                        </div>
                        <div className={styles.content}>
                            {sourceQuestion === undefined ? (
                                <Button
                                    type="button"
                                    outline
                                    onClick={handleOpenSourceQuestion}
                                    data-testid="searchSourceQuestionBtn"
                                    className={styles.sourceBtn}>
                                    Search source question
                                </Button>
                            ) : (
                                <div className={styles.sourceQuestionDisplay}>
                                    <div className={styles.title}>
                                        {`${checkForSemicolon(removeNumericAndSymbols(sourceQuestion.name))} (${
                                            sourceQuestion.question
                                        })`}
                                    </div>
                                    <div className={styles.closeBtn}>
                                        <Icon.Close
                                            onClick={() => {
                                                setSourceQuestion(undefined);
                                                setTargetQuestion([]);
                                                setTargetDescription([]);
                                                form.setValue('targetIdentifiers', []);
                                                form.setValue('targetValueText', undefined);
                                                form.setValue('sourceValues', undefined);
                                                form.setValue('sourceIdentifier', '');
                                            }}
                                        />
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>

                    {watch.ruleFunction && watch.ruleFunction !== Rule.ruleFunction.DATE_COMPARE && (
                        <Controller
                            control={form.control}
                            name="anySourceValue"
                            render={({ field: { onBlur, onChange, value } }) => (
                                <div className={styles.anySourceValue}>
                                    <div className={styles.title}>
                                        <Label htmlFor="anySourceValue">Any source value</Label>
                                    </div>
                                    <div className={styles.content}>
                                        <Checkbox
                                            onChange={onChange}
                                            onBlur={onBlur}
                                            checked={value}
                                            id={'anySourceValue'}
                                            name={'anySourceValue'}
                                            label={''}
                                        />
                                    </div>
                                </div>
                            )}
                        />
                    )}

                    <Controller
                        control={form.control}
                        name="comparator"
                        rules={{
                            required: { value: watch.anySourceValue ? false : true, message: 'Logic is required' }
                        }}
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <div className={styles.comparator}>
                                <div className={styles.title}>
                                    <Label htmlFor="logic" requiredMarker>
                                        Logic
                                    </Label>
                                </div>
                                <div className={styles.content}>
                                    <SelectInput
                                        className={styles.input}
                                        defaultValue={watch.anySourceValue ? Rule.comparator.EQUAL_TO : value}
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        options={logicList}
                                        error={error?.message}
                                        disabled={watch.anySourceValue}
                                        dataTestid="LogicSelectDropdown"
                                    />
                                </div>
                            </div>
                        )}
                    />

                    {watch.ruleFunction && watch.ruleFunction !== Rule.ruleFunction.DATE_COMPARE && (
                        <>
                            <Controller
                                control={form.control}
                                name="sourceValues"
                                rules={{
                                    required: {
                                        value: watch.anySourceValue ? false : true,
                                        message: 'Source value(s) is required'
                                    }
                                }}
                                render={() => (
                                    <div className={styles.sourceValues}>
                                        <div className={styles.title}>
                                            <Label htmlFor="sourceValues" requiredMarker>
                                                Source value(s)
                                            </Label>
                                        </div>
                                        <div className={styles.content}>
                                            <div className="source-value-multi-select">
                                                <MultiSelectInput
                                                    value={form?.getValues('sourceValues')?.map((val) => val?.id || '')}
                                                    onChange={(value: string[]) => {
                                                        handleSourceValueChange(value);
                                                    }}
                                                    options={sourceValues}
                                                    disabled={form.watch('anySourceValue')}
                                                />
                                            </div>
                                        </div>
                                    </div>
                                )}
                            />
                            <Controller
                                control={form.control}
                                name="targetType"
                                rules={{ required: { value: true, message: 'Target type is required' } }}
                                render={({ field: { value } }) => (
                                    <div className={styles.targetType}>
                                        <div className={styles.title}>
                                            <Label htmlFor="targetType" requiredMarker>
                                                Target type
                                            </Label>
                                        </div>
                                        <div className={styles.content}>
                                            <Radio
                                                className={styles.radioBtn}
                                                type="radio"
                                                name="targetType"
                                                value="QUESTION"
                                                id="targetType_Qtn"
                                                checked={value === 'QUESTION'}
                                                onChange={() => handleTargetTypeChange(Rule.targetType.QUESTION)}
                                                label="Question"
                                            />
                                            <Radio
                                                className={styles.radioBtn}
                                                id="targetType_Sub"
                                                name="targetType"
                                                value="SUBSECTION"
                                                checked={value === 'SUBSECTION'}
                                                onChange={() => handleTargetTypeChange(Rule.targetType.SUBSECTION)}
                                                label="Subsection"
                                            />
                                        </div>
                                    </div>
                                )}
                            />
                        </>
                    )}

                    <div className={styles.targets}>
                        <div className={styles.title}>
                            <Label htmlFor="targetQuestionTitle" requiredMarker>
                                Target(s)
                            </Label>
                        </div>
                        <div className={styles.content}>
                            {(targetQuestions?.length || 0) > 0 && (
                                <div className={styles.displayTargetQuestions}>
                                    <div className={styles.title}>
                                        {targetQuestions?.map((question: PagesQuestion, key: number) => (
                                            <div key={key} className={styles.targetQuestion}>
                                                <Icon.Check />
                                                {`${checkForSemicolon(removeNumericAndSymbols(question.name ?? question.componentName))} (${
                                                    question.question
                                                })`}
                                            </div>
                                        ))}
                                    </div>
                                    <div className={styles.edit}>
                                        <Button
                                            type="button"
                                            outline
                                            data-testid="targetQuestionEditBtn"
                                            onClick={handleOpenTargetQuestion}
                                            className={styles.btn}>
                                            <Icon.Edit />
                                            <span>Edit</span>
                                        </Button>
                                    </div>
                                </div>
                            )}
                            {!(targetQuestions?.length || 0 > 0) && watch.targetType === Rule.targetType.QUESTION && (
                                <Button
                                    className={styles.targetBtn}
                                    data-testid="searchTargetQuestionBtn"
                                    type="button"
                                    outline
                                    onClick={handleOpenTargetQuestion}
                                    disabled={sourceQuestion === undefined}>
                                    Search target question
                                </Button>
                            )}

                            {watch.targetType === Rule.targetType.SUBSECTION && (
                                <div className={styles.subsectionTargets}>
                                    <SubSectionsDropdown
                                        sourceQuestion={sourceQuestion}
                                        onSelect={handleTargetSubsection}
                                    />
                                </div>
                            )}
                        </div>
                    </div>

                    <Controller
                        control={form.control}
                        name="description"
                        render={({ field: { onChange, onBlur, value } }) => (
                            <>
                                <div className={styles.description}>
                                    <div className={styles.title}>
                                        <Label htmlFor="description">Rule description</Label>
                                    </div>
                                    <div className={styles.content}>
                                        <Input
                                            onChange={onChange}
                                            type="text"
                                            multiline
                                            defaultValue={checkForSemicolon(removeNumericAndSymbols(value))}
                                            value={checkForSemicolon(removeNumericAndSymbols(value))}
                                            onBlur={onBlur}
                                        />
                                    </div>
                                </div>
                                {isEdit && watch.ruleFunction === Rule.ruleFunction.DATE_COMPARE && (
                                    <div className={styles.errorMessage}>
                                        <div className={styles.title}>
                                            <Label htmlFor="errorMessage" requiredMarker>
                                                Error message
                                            </Label>
                                        </div>
                                        <div className={styles.content}>
                                            <Input
                                                type="text"
                                                multiline
                                                defaultValue={checkForSemicolon(removeNumericAndSymbols(value))}
                                                onChange={onChange}
                                                onBlur={onBlur}
                                            />
                                        </div>
                                    </div>
                                )}
                            </>
                        )}
                    />
                </>
            )}
            <Modal id={'sourceQuestion'} ref={sourceQuestionModalRef} className={'source-question-modal'} isLarge>
                <SourceQuestion
                    ruleFunction={watch.ruleFunction}
                    onSubmit={handleSourceQuestion}
                    onCancel={handleCloseSourceQuestion}
                />
            </Modal>

            <Modal id={'targetQuestion'} ref={targetQuestionModalRef} className={'target-question-modal'} isLarge>
                <TargetQuestion
                    ruleFunction={watch.ruleFunction}
                    onCancel={handleCloseTargetQuestion}
                    sourceQuestion={sourceQuestion}
                    onSubmit={handleTargetQuestion}
                    selectedTargetQuestion={targetQuestions}
                    editTargetQuestion={isEdit ? editTargetQuestions : undefined}
                />
            </Modal>
        </div>
    );
};
