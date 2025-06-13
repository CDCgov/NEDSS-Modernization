import React from 'react';
import { format, parseISO, isValid } from 'date-fns';
import { LinedMergePreviewCard } from '../shared/preview-card-lined/LinedMergePreviewCard';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';

export const PreviewMortality = ({
    mergeFormData,
    mergeCandidates
}: {
    mergeFormData: PatientMergeForm;
    mergeCandidates: MergeCandidate[];
}) => {
    const getCandidateByUid = (uid?: string) => mergeCandidates.find((mc) => mc.personUid === uid);

    const mValues = mergeFormData.mortality;
    const getField = (uid?: string, field?: keyof MergeCandidate['mortality']) => {
        if (!uid || !field) return '---';
        const c = getCandidateByUid(uid);
        return c?.mortality?.[field] ?? '---';
    };
    const parseDate = (dateStr?: string) => {
        const parsed = dateStr ? parseISO(dateStr) : undefined;
        return parsed && isValid(parsed) ? parsed : undefined;
    };

    const asOfDate = parseDate(mValues?.asOf);

    const items = [
        { label: 'As of', text: asOfDate ? format(asOfDate, 'MM/dd/yyyy') : '---', lined: true },
        { label: 'Is the patient deceased', text: getField(mValues.deceased, 'deceased'), lined: true },
        { label: 'Date of death', text: getField(mValues.dateOfDeath, 'dateOfDeath'), lined: true },
        { label: 'City of death', text: getField(mValues.deathCity, 'deathCity'), lined: true },
        { label: 'State of death', text: getField(mValues.deathState, 'deathState'), lined: true },
        { label: 'County of death', text: getField(mValues.deathCounty, 'deathCounty'), lined: true },
        { label: 'Country of death', text: getField(mValues.deathCountry, 'deathCountry'), lined: false }
    ];

    return <LinedMergePreviewCard id="mortality" title="Mortality" items={items} />;
};
