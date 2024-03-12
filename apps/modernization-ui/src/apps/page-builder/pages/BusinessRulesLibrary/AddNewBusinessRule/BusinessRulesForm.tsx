import { Button, ButtonGroup, Checkbox, Icon, Label, Modal, ModalRef, Radio } from '@trussworks/react-uswds';
import { CreateRuleRequest, PagesQuestion, Rule } from 'apps/page-builder/generated';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import styles from './BusinessRulesForm.module.scss';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import { Input } from 'components/FormInputs/Input';
import { useEffect, useRef, useState } from 'react';
import { SourceQuestion } from './SourceQuestion';
import { FieldProps } from './AddBusinessRules';
import { TargetQuestion } from './TargetQuestion';

type Props = {
    isEdit: boolean;
    sourceValues: FieldProps[];
    handleSourceValues: (valueSet?: string) => void;
};

export const BusinessRulesForm = ({ sourceValues, handleSourceValues }: Props) => {
    const form = useFormContext<CreateRuleRequest>();
    const watch = useWatch(form);
    const sourceQuestionModalRef = useRef<ModalRef>(null);
    const targetQuestionModalRef = useRef<ModalRef>(null);

    const [sourceQuestion, setSourceQuestion] = useState<PagesQuestion | undefined>(undefined);

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
        setSourceQuestion(question);
        handleSourceValues(question?.valueSet);
    };

    const handleSourceValueChange = (data: string[]) => {
        // create a new array by comparing data and sourceValueList, for each item in data, find the corresponding item in sourceValueList and return it
        const matchedValues = data.map((value) => sourceValues.find((val) => val.value === value));
        const newValues = matchedValues.map((value) => ({ id: value?.value, text: value?.name }));
        form.setValue('sourceValues', newValues);
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
                            <Icon.Close onClick={() => setSourceQuestion(undefined)} />
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
                                                onChange={(value) => {
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
                        <Label htmlFor="sourceQuestion" requiredMarker>
                            Target(s)
                        </Label>
                    </div>
                    <Button type="button" outline onClick={handleOpenTargetQuestion}>
                        Search target question
                    </Button>

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
                />
            </Modal>
        </>
    );
};
