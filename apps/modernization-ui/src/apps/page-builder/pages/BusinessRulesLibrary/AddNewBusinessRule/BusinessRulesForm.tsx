import { Button, ButtonGroup, Checkbox, Icon, Label, Modal, ModalRef, Radio } from '@trussworks/react-uswds';
import { CreateRuleRequest, PagesQuestion, Rule } from 'apps/page-builder/generated';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import styles from './BusinessRulesForm.module.scss';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import { Input } from 'components/FormInputs/Input';
import { useEffect, useRef, useState } from 'react';
import { SourceQuestion } from './SourceQuestion';
import { SourceValueProp } from './AddBusinessRules';
import { TargetQuestion } from './TargetQuestion';
import { mapComparatorToString } from '../helpers/mapComparatorToString';
import { mapRuleFunctionToString } from '../helpers/mapRuleFunctionToString';

type Props = {
    isEdit: boolean;
    sourceValues: SourceValueProp[];
    editSourceQuestion?: PagesQuestion;
    editTargetQuestions?: PagesQuestion[];
    onFetchSourceValues: (valueSet?: string) => void;
};

export const BusinessRulesForm = ({
    sourceValues,
    onFetchSourceValues,
    isEdit,
    editSourceQuestion,
    editTargetQuestions
}: Props) => {
    const form = useFormContext<CreateRuleRequest>();
    const watch = useWatch(form);
    const sourceQuestionModalRef = useRef<ModalRef>(null);
    const targetQuestionModalRef = useRef<ModalRef>(null);

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
        if (isEdit) {
            onFetchSourceValues();
        }
    }, [isEdit]);

    useEffect(() => {
        setSourceQuestion(editSourceQuestion);
    }, [editSourceQuestion]);

    useEffect(() => {
        setTargetQuestion(editTargetQuestions);
        setTargetDescription(editTargetQuestions?.map((qtn) => `${qtn.name} (${qtn.question})`) ?? []);
    }, [editTargetQuestions]);

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

    useEffect(() => {
        if (
            watch.targetIdentifiers &&
            (watch.anySourceValue || (watch.comparator && watch.sourceValues)) &&
            watch.sourceIdentifier &&
            targetDescription
        ) {
            const descrip = handleRuleDescription();
            descrip !== form.getValues('description') && form.setValue('description', handleRuleDescription());
        }
    }, [
        watch.targetIdentifiers,
        watch.anySourceValue,
        watch.comparator,
        watch.ruleFunction,
        watch.sourceValues,
        watch.sourceIdentifier,
        targetDescription
    ]);

    const handleRuleDescription = (): string => {
        let description = '';
        const sourceText = watch.sourceText;
        const logic = mapComparatorToString(watch.comparator ?? undefined);
        const sourceValues = watch.sourceValues;
        const ruleFunction = watch.ruleFunction;
        const targetType = watch.targetType;
        let sourceValueDescription = '';

        if (sourceValues?.length && !watch.anySourceValue) {
            sourceValueDescription = sourceValues?.map((value) => value.text).join(', ');
        }

        if (watch.anySourceValue) {
            sourceValueDescription = 'any source value';
        }

        const targetValues: string[] | undefined = targetType === Rule.targetType.QUESTION ? targetDescription : [];

        if (ruleFunction !== Rule.ruleFunction.DATE_COMPARE && logic && sourceValues?.length && targetValues?.length) {
            description = `IF "${sourceText}" is ${logic} ${sourceValueDescription} ${mapRuleFunctionToString(
                form.getValues('ruleFunction')
            )} "${targetValues.join('", "')}"`;
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
                sourceValues: []
            });
        }
    }, [watch.anySourceValue]);

    return (
        <>
            <Controller
                control={form.control}
                name="ruleFunction"
                render={({ field: { onBlur, onChange, value } }) => (
                    <ButtonGroup type="segmented">
                        {fieldTypeTab.map((field, index) => (
                            <Button
                                key={index}
                                type="button"
                                outline={watch.ruleFunction !== field.value}
                                onChange={onChange}
                                onBlur={onBlur}
                                value={value}
                                onClick={() => {
                                    form.reset({
                                        ruleFunction: field.value,
                                        targetType: Rule.targetType.QUESTION,
                                        anySourceValue: false
                                    });
                                }}>
                                {field.display}
                            </Button>
                        ))}
                    </ButtonGroup>
                )}
            />
            {watch.ruleFunction && (
                <>
                    <div className={styles.sourceQuestionTitle}>
                        <Label htmlFor="sourceQuestion" requiredMarker>
                            Source question
                        </Label>
                    </div>
                    {sourceQuestion === undefined ? (
                        <Button type="button" outline onClick={handleOpenSourceQuestion}>
                            Search source question
                        </Button>
                    ) : (
                        <div className="sourceQuestionDisplay">
                            {`${sourceQuestion.name} (${sourceQuestion.question})`}
                            <Icon.Close
                                onClick={() => {
                                    setSourceQuestion(undefined);
                                    form.setValue('sourceValues', undefined);
                                }}
                            />
                        </div>
                    )}

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
                        render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                            <div className={styles.comparator}>
                                <div className={styles.title}>
                                    <Label htmlFor="logic" requiredMarker>
                                        Logic
                                    </Label>
                                </div>
                                <div className={styles.content}>
                                    <SelectInput
                                        className="text-input"
                                        defaultValue={watch.anySourceValue ? Rule.comparator.EQUAL_TO : value}
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        options={logicList}
                                        error={error?.message}
                                        disabled={watch.anySourceValue}
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
                                render={() => (
                                    <div className={styles.sourceValues}>
                                        <div className={styles.title}>
                                            <Label htmlFor="sourceValues" requiredMarker>
                                                Source value(s)
                                            </Label>
                                        </div>
                                        <div className={styles.content}>
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
                                )}
                            />
                            <Controller
                                control={form.control}
                                name="targetType"
                                render={({ field: { value } }) => (
                                    <div className={styles.targetType}>
                                        <div className={styles.title}>
                                            <Label htmlFor="targetType" requiredMarker>
                                                Target type
                                            </Label>
                                        </div>
                                        <div className={styles.content}>
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
                                        </div>
                                    </div>
                                )}
                            />
                        </>
                    )}

                    <div className={styles.targetQuestionTitle}>
                        <Label htmlFor="targetQuestionTitle" requiredMarker>
                            Target(s)
                        </Label>
                    </div>
                    {targetQuestions?.length ?? 0 > 0 ? (
                        <div className={styles.displayTargetQuestions}>
                            <>
                                {targetQuestions?.map((question: PagesQuestion, key: number) => (
                                    <div key={key} className={styles.targetQuestion}>
                                        <Icon.Check />
                                        {`${question.name} (${question.question})`}
                                    </div>
                                ))}
                                <Button type="button" outline onClick={handleOpenTargetQuestion}>
                                    <Icon.Edit />
                                    <span>Edit</span>
                                </Button>
                            </>
                        </div>
                    ) : (
                        <Button
                            type="button"
                            outline
                            onClick={handleOpenTargetQuestion}
                            disabled={sourceQuestion === undefined}>
                            Search target question
                        </Button>
                    )}

                    <Controller
                        control={form.control}
                        name="description"
                        render={({ field: { onChange, onBlur, value } }) => (
                            <div className={styles.description}>
                                <div className={styles.title}>
                                    <Label htmlFor="description">Rule description</Label>
                                </div>
                                <div className={styles.content}>
                                    <Input
                                        onChange={onChange}
                                        type="text"
                                        multiline
                                        defaultValue={value}
                                        value={value}
                                        onBlur={onBlur}
                                    />
                                </div>
                            </div>
                        )}
                    />
                </>
            )}
            <Modal id={'sourceQuestion'} ref={sourceQuestionModalRef} isLarge>
                <SourceQuestion
                    ruleFunction={watch.ruleFunction}
                    onSubmit={handleSourceQuestion}
                    onCancel={handleCloseSourceQuestion}
                />
            </Modal>

            <Modal id={'targetQuestion'} ref={targetQuestionModalRef} isLarge>
                <TargetQuestion
                    ruleFunction={watch.ruleFunction}
                    onCancel={handleCloseTargetQuestion}
                    sourceQuestion={sourceQuestion}
                    onSubmit={handleTargetQuestion}
                    editTargetQuestion={isEdit ? editTargetQuestions : undefined}
                />
            </Modal>
        </>
    );
};
