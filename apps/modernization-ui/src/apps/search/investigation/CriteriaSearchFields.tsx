import { Controller, useFormContext } from 'react-hook-form';
import {
    InvestigationFilterEntry,
    investigationStatusOptions,
    notificationStatusOptions,
    processingStatusOptions
} from './InvestigationFormTypes';
import { SingleSelect, MultiSelect } from 'design-system/select';
import { ConceptOptionsService } from 'generated';
import { useEffect, useState } from 'react';
import { Selectable } from 'options';
import { ConceptMultiSelect } from 'options/concepts/ConceptMultiSelect';

type Props = {
    outbreakCodeSetNm?: string;
};

const CriteriaSearchFields = ({ outbreakCodeSetNm = 'OUTBREAK_NM' }: Props) => {
    const form = useFormContext<InvestigationFilterEntry, Partial<InvestigationFilterEntry>>();

    const [outbreakNames, setOutbreakNames] = useState<Selectable[]>();

    useEffect(() => {
        ConceptOptionsService.conceptSearch({
            name: outbreakCodeSetNm,
            criteria: ''
        }).then((response) => {
            setOutbreakNames(response.options);
        });
    }, [outbreakCodeSetNm]);

    return (
        <>
            <Controller
                control={form.control}
                name="investigationStatus"
                render={({ field: { onChange, name, value } }) => (
                    <SingleSelect
                        data-testid={name}
                        name={name}
                        value={value}
                        onChange={onChange}
                        label="Investigation status"
                        options={investigationStatusOptions}
                        id={name}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="outbreaks"
                render={({ field: { name, onBlur, onChange, value } }) => (
                    <MultiSelect
                        label="Outbreak names"
                        onChange={onChange}
                        onBlur={onBlur}
                        name={name}
                        value={value}
                        options={outbreakNames ?? []}
                        id={name}
                    />
                )}
            />

            <Controller
                control={form.control}
                name="caseStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <ConceptMultiSelect onChange={onChange} name={name} value={value} valueSet="OUTBREAK_NM" />
                )}
            />
            <Controller
                control={form.control}
                name="processingStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelect
                        label="Current processing status"
                        onChange={onChange}
                        name={name}
                        value={value}
                        id={name}
                        options={processingStatusOptions}
                    />
                )}
            />
            <Controller
                control={form.control}
                name="notificationStatuses"
                render={({ field: { onChange, name, value } }) => (
                    <MultiSelect
                        label="Notification status"
                        onChange={onChange}
                        name={name}
                        value={value}
                        id={name}
                        options={notificationStatusOptions}
                    />
                )}
            />
        </>
    );
};

export default CriteriaSearchFields;
