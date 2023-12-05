import React, { useContext, useEffect, useRef, useState } from 'react';
import {
    Button,
    Checkbox,
    ErrorMessage,
    Grid,
    Icon,
    Label,
    ModalRef,
    ModalToggleButton,
    Radio
} from '@trussworks/react-uswds';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';
import { Controller, useFormContext } from 'react-hook-form';
import { FormValues } from './EditBusinessRules';
import { nonDateCompare, dateCompare } from '../../constant/constant';
import TargetQuestion from '../../components/SearchTargetQuestion/TargetQuestion';
import { useParams } from 'react-router-dom';
import { maxLengthRule } from '../../../../validation/entry';
import { Input } from '../../../../components/FormInputs/Input';
import { useConceptPI } from '../../components/Concept/useConceptAPI';
import { UserContext } from '../../../../providers/UserContext';

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

export const EditBusinessRulesFields = () => {
    const form = useFormContext<FormValues>();
    const TargetQtnModalRef = useRef<ModalRef>(null);
    const sourceModalRef = useRef<ModalRef>(null);
    const [targetQuestion, setTargetQuestion] = useState<QuestionProps[]>([]);
    const [sourceList, setSourceList] = useState<FieldProps[]>([]);
    const [selectedSource, setSelectedSource] = useState<QuestionProps[]>([]);
    const { pageId } = useParams();
    const { state } = useContext(UserContext);

    const fetchSourceRecord = async (valueSet: string) => {
        const token = `Bearer ${state.getToken()}`;
        const content: any = await useConceptPI(token, valueSet);
        const list = content?.map((src: any) => ({ name: src.longName, value: src.conceptCode }));
        setSourceList(list);
    };
    useEffect(() => {
        fetchSourceRecord('');
    }, []);

    const handleFetchQuestion = (data: QuestionProps[]) => {
        setTargetQuestion(data);
        const value = data.map((val) => val.question);
        const text = data.map((val) => val.name);
        form.setValue('targetValueIdentifier', value);
        form.setValue('targetValueText', text);
        setTimeout(() => {
            handleRuleDescription();
        }, 1000);
    };
    const handleFetchSource = (data: QuestionProps[]) => {
        setSelectedSource(data);
        form.setValue('sourceIdentifier', data[0].question);
        form.setValue('sourceText', data[0].name);
        fetchSourceRecord(data[0].valueSet);
        setTimeout(() => {
            handleRuleDescription();
        }, 1000);
    };
    const targetValueIdentifier = form.watch('targetValueIdentifier') || [];

    const targetQtn = targetQuestion.length > 1 || targetValueIdentifier?.length > 1;

    const openSourceModal = () => {
        if (sourceModalRef.current && sourceModalRef.current) {
            const sourceModalBtn = document.getElementById('sourceQuestionId');
            sourceModalBtn?.click();
        }
    };
    const clearFetchQuestion = () => {
        setTargetQuestion([]);
        form.setValue('targetValueIdentifier', []);
        form.setValue('targetValueText', []);
    };
    const handleRuleDescription = () => {
        let description = '';
        const logic = form.watch('comparator');
        if (selectedSource.length || targetQuestion.length || logic) {
            const targetValue = targetQuestion.map((val) => `${val.name} (${val.question})`);
            const sourceValue = selectedSource.map((val) => `${val.name} (${val.question})`);
            description = `${sourceValue} ${logic} ${targetValue}`;
        }
        form.setValue('ruleDescription', description);
    };

    const ruleFunction = form.watch('ruleFunction');
    const logicList = ruleFunction === 'Date Compare' ? dateCompare : nonDateCompare;

    return (
        <>
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
                                className=""
                                id="anySourceValue"
                                type="checkbox"
                                checked={value}
                                label=" "
                                name="anySourceValue"
                            />
                        </Grid>
                    </Grid>
                )}
            />
            <Controller
                control={form.control}
                name="sourceText"
                rules={{
                    required: { value: true, message: 'Source questions is required.' }
                }}
                render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                    <Grid row className="inline-field">
                        <Grid col={3}>
                            <label className="input-label">Source Questions</label>
                        </Grid>
                        <ModalToggleButton
                            modalRef={sourceModalRef}
                            id="sourceQuestionId"
                            className="display-none"
                            outline>
                            hide
                        </ModalToggleButton>
                        <Grid col={9}>
                            <Input
                                defaultValue={value}
                                onChange={onChange}
                                onClick={openSourceModal}
                                type="text"
                                onBlur={onBlur}
                                error={error?.message}
                                required
                            />
                        </Grid>
                    </Grid>
                )}
            />
            <Controller
                control={form.control}
                name="comparator"
                rules={{
                    required: { value: true, message: 'Logic is required.' }
                }}
                render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                    <Grid row className="inline-field">
                        <Grid col={3}>
                            <label className="input-label">Logic</label>
                        </Grid>
                        <Grid col={9}>
                            <SelectInput
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
            <Controller
                control={form.control}
                name="sourceValue"
                render={({ field: { onChange, value } }) => (
                    <Grid row className="inline-field">
                        <Grid col={3}>
                            <label className="input-label">Source value</label>
                        </Grid>
                        <Grid col={9}>
                            <MultiSelectInput onChange={onChange} value={value} options={sourceList} />
                        </Grid>
                    </Grid>
                )}
            />
            <Controller
                control={form.control}
                name="targetType"
                render={({ field: { onChange, value } }) => (
                    <Grid row className="inline-field">
                        <Grid col={3}>
                            <label className="input-label">Target type</label>
                        </Grid>
                        <Grid col={9} className="targetType-radio">
                            <Radio
                                type="radio"
                                name="targetType"
                                value="QUESTION"
                                id="targetType_Qtn"
                                checked={value === 'QUESTION'}
                                onChange={onChange}
                                label="Question"
                            />
                            <Radio
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
            <Grid row className="inline-field">
                <Grid col={3}>
                    <label className="input-label">Target Questions</label>
                </Grid>
                <Grid col={8}>
                    {!targetQtn ? (
                        <div className="width-48-p margin-bottom-1em">
                            <ModalToggleButton
                                modalRef={TargetQtnModalRef}
                                className="width-full margin-top-1em"
                                type="submit"
                                outline>
                                Search Target Question
                            </ModalToggleButton>
                        </div>
                    ) : (
                        <div className="que-valueset">
                            {targetQuestion?.map((qtn, index: number) => (
                                <div className="margin-bottom-1" key={index}>
                                    <Icon.Check />
                                    <span className="margin-left-1"> {`${qtn.name} (${qtn.question})`}</span>
                                </div>
                            ))}
                            {!targetQuestion.length &&
                                targetValueIdentifier?.map((targetValue: string, index: number) => (
                                    <div className="margin-bottom-1" key={index}>
                                        <Icon.Check />
                                        <span className="margin-left-1"> {targetValue}</span>
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
                    {targetQtn && (
                        <Button
                            type="submit"
                            className="margin-right-2 line-btn clear-target"
                            unstyled
                            onClick={clearFetchQuestion}>
                            <span>Clear all target selections</span>
                        </Button>
                    )}
                </Grid>
            </Grid>
            <Controller
                control={form.control}
                name="ruleDescription"
                rules={maxLengthRule(50)}
                render={({ field: { name, onChange, onBlur, value }, fieldState: { error } }) => (
                    <Grid row className="inline-field">
                        <Grid col={3}>
                            <Label htmlFor={name}>Rules Description</Label>
                        </Grid>
                        <Grid col={8}>
                            <Input
                                onChange={onChange}
                                type="text"
                                multiline
                                defaultValue={value}
                                onBlur={onBlur}
                                name={name}
                                id={name}
                            />
                            {error?.message && <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>}
                        </Grid>
                    </Grid>
                )}
            />
            <TargetQuestion modalRef={TargetQtnModalRef} getList={handleFetchQuestion} pageId={pageId!} />
            <TargetQuestion
                modalRef={sourceModalRef}
                getList={handleFetchSource}
                multiSelected={false}
                header="Source Question"
                pageId={pageId!}
            />
        </>
    );
};
