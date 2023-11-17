import React from 'react';
import { Concept, Condition, PagesResponse } from 'apps/page-builder/generated';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';

type AddNewPageFieldProps = {
    conditions: Condition[];
    mmgs: Concept[];
    page: PagesResponse;
};
export const PageDetailsField = (props: AddNewPageFieldProps) => {
    return (
        <>
            <MultiSelectInput
                value={[]}
                options={props.conditions.map((m) => {
                    return {
                        name: m.conditionShortNm ?? '',
                        value: m.id
                    };
                })}
                label="Condition(s)"
            />
            <Input defaultValue={props.page?.name} label="Page name" className="pageName" type="text" disabled />
            <SelectInput
                label="MMG"
                name="messageMappingGuide"
                disabled
                defaultValue={''}
                options={props.mmgs.map((m) => {
                    return {
                        name: m.display ?? '',
                        value: m.conceptCode ?? ''
                    };
                })}
                required
            />
            <Input defaultValue={props.page.description} label="Page description" type="text" multiline disabled />
            <Input label="Data mart name" type="text" defaultValue={''} disabled />
        </>
    );
};
