import React from 'react';
import { format, isValid, parseISO } from 'date-fns';
import { LinedMergePreviewCard } from '../shared/preview-card-lined/LinedMergePreviewCard';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';

export const PreviewGeneralPatientInfo = ({
    mergeFormData,
    mergeCandidates
}: {
    mergeFormData: PatientMergeForm;
    mergeCandidates: MergeCandidate[];
}) => {
    const getCandidateByUid = (uid?: string) => mergeCandidates.find((mc) => mc.personUid === uid);

    const gValues = mergeFormData.generalInfo;
    const getField = (uid?: string, field?: keyof MergeCandidate['general']) => {
        if (!uid || !field) return '---';
        const c = getCandidateByUid(uid);
        return c?.general?.[field] ?? '---';
    };

    const parseDate = (dateStr?: string) => {
        const parsed = dateStr ? parseISO(dateStr) : undefined;
        return parsed && isValid(parsed) ? parsed : undefined;
    };

    const asOfDate = parseDate(gValues?.asOf);

    const items = [
        { label: 'As of', text: asOfDate ? format(asOfDate, 'MM/dd/yyyy') : '---', lined: true },
        { label: 'Marital status', text: getField(gValues.maritalStatus, 'maritalStatus'), lined: true },
        { label: "Mother's maiden name", text: getField(gValues.mothersMaidenName, 'mothersMaidenName'), lined: true },
        {
            label: 'Number of adults in residence',
            text: getField(gValues.numberOfAdultsInResidence, 'numberOfAdultsInResidence'),
            lined: true
        },
        {
            label: 'Number of children in residence',
            text: getField(gValues.numberOfChildrenInResidence, 'numberOfChildrenInResidence'),
            lined: true
        },
        { label: 'Primary occupation', text: getField(gValues.primaryOccupation, 'primaryOccupation'), lined: true },
        { label: 'Highest level of education', text: getField(gValues.educationLevel, 'educationLevel'), lined: true },
        { label: 'Primary language', text: getField(gValues.primaryLanguage, 'primaryLanguage'), lined: true },
        { label: 'Speaks English', text: getField(gValues.speaksEnglish, 'speaksEnglish'), lined: true },
        { label: 'State HIV case ID', text: getField(gValues.stateHivCaseId, 'stateHivCaseId'), lined: false }
    ];

    return <LinedMergePreviewCard id="generalInfo" title="General patient information" items={items} />;
};
