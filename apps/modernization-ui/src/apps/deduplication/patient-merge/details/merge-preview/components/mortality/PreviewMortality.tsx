import React from 'react';

import { format, isValid, parseISO } from 'date-fns';

import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';
import { LinedMergePreviewCard } from '../shared/preview-card-lined/LinedMergePreviewCard';

export const PreviewMortality = ({
    mergeFormData,
    mergeCandidates,
}: {
    mergeFormData: PatientMergeForm;
    mergeCandidates: MergeCandidate[];
}) => {
    const getField = (uid?: string, field?: keyof MergeCandidate['mortality']) =>
        uid && field ? (mergeCandidates.find((mc) => mc.personUid === uid)?.mortality?.[field] ?? '---') : '---';

    const m = mergeFormData.mortality;
    const formatDate = (str?: string) => {
        const parsed = str ? parseISO(str) : undefined;
        return parsed && isValid(parsed) ? format(parsed, 'MM/dd/yyyy') : '---';
    };

    const items = [
        { label: 'As of', text: formatDate(getField(m.asOf, 'asOf') as string), lined: true },
        { label: 'Is the patient deceased', text: getField(m.deceased, 'deceased'), lined: true },
        { label: 'Date of death', text: formatDate(getField(m.dateOfDeath, 'dateOfDeath') as string), lined: true },
        { label: 'City of death', text: getField(m.deathCity, 'deathCity'), lined: true },
        { label: 'State of death', text: getField(m.deathState, 'deathState'), lined: true },
        { label: 'County of death', text: getField(m.deathState, 'deathCounty'), lined: true },
        { label: 'County of death', text: getField(m.deathState, 'deathCounty'), lined: true },
        { label: 'Country of death', text: getField(m.deathCountry, 'deathCountry'), lined: false },
    ];

    return <LinedMergePreviewCard id="mortality" title="Mortality" items={items} />;
};
