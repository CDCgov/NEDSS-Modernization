import React, { RefObject, useEffect, useState } from 'react';
import './CreateQuestion.scss';
import { ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { Control, Controller } from 'react-hook-form';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import {
    CreateCodedQuestionRequest,
    CreateDateQuestionRequest,
    CreateNumericQuestionRequest,
    CreateTextQuestionRequest,
    MessagingInfo,
    ReportingInfo
} from '../../generated';
import { optionsType, QuestionFormType } from './CreateQuestion';
import { Heading } from '../../../../components/heading';
import { useConceptAPI } from '../Concept/useConceptAPI';
import { authorization } from '../../../../authorization';

type CreateQuestionFormType = CreateNumericQuestionRequest &
    CreateCodedQuestionRequest &
    CreateDateQuestionRequest &
    CreateTextQuestionRequest &
    ReportingInfo &
    MessagingInfo &
    QuestionFormType;

type DateQuestionProps = {
    control?: Control<CreateQuestionFormType, any>;
    addValueModalRef: RefObject<ModalRef>;
    valueSetName: string;
};

export const CreateCodedQuestion = ({ control, addValueModalRef, valueSetName }: DateQuestionProps) => {
    const isValueSet = valueSetName !== '';
    const [options, setOptions] = useState<optionsType[]>([]);

    const fetchConcepts = async () => {
        useConceptAPI(authorization(), valueSetName!).then((response: any) => {
            const data = response || [];
            const list: optionsType[] = [];
            data.map((each: { display: string; conceptCode: string }) => {
                list.push({ name: each.display, value: each.conceptCode });
            });
            setOptions(list);
        });
    };
    useEffect(() => {
        if (valueSetName) fetchConcepts();
    }, [valueSetName]);

    return (
        <div className="">
            <label>Value set</label>
            {isValueSet && (
                <Heading className="selected-value-set" level={4}>
                    {valueSetName?.toString()}
                </Heading>
            )}
            <ModalToggleButton modalRef={addValueModalRef} className="width-full" type="submit" outline>
                {isValueSet ? 'Change value set' : 'Search value set'}
            </ModalToggleButton>
            <br></br>
            {isValueSet && (
                <Controller
                    control={control}
                    name="defaultValue"
                    rules={{ required: { value: isValueSet, message: 'Default Value required' } }}
                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                        <SelectInput
                            defaultValue={value}
                            label="Default Value"
                            onChange={onChange}
                            options={options}
                            error={error?.message}
                            required={isValueSet}
                        />
                    )}
                />
            )}
        </div>
    );
};
