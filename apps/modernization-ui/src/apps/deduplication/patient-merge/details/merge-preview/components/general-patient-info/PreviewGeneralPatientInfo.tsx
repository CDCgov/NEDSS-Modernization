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
    const g = mergeFormData.generalInfo;

    const getField = (uid?: string, field?: keyof MergeCandidate['general']) =>
        uid && field ? (mergeCandidates.find((c) => c.personUid === uid)?.general?.[field] ?? '---') : '---';

    const parseValidDate = (str?: string) => {
        const date = str ? parseISO(str) : undefined;
        return isValid(date) ? date : undefined;
    };

    const asOfRaw = getField(g.asOf, 'asOf');
    const asOfDate = parseValidDate(asOfRaw);

    const items = [
        { label: 'As of', text: asOfDate ? format(asOfDate, 'MM/dd/yyyy') : '---', lined: true },
        { label: 'Marital status', text: getField(g.maritalStatus, 'maritalStatus'), lined: true },
        { label: "Mother's maiden name", text: getField(g.mothersMaidenName, 'mothersMaidenName'), lined: true },
        {
            label: 'Number of adults in residence',
            text: getField(g.numberOfAdultsInResidence, 'numberOfAdultsInResidence'),
            lined: true
        },
        {
            label: 'Number of children in residence',
            text: getField(g.numberOfChildrenInResidence, 'numberOfChildrenInResidence'),
            lined: true
        },
        { label: 'Primary occupation', text: getField(g.primaryOccupation, 'primaryOccupation'), lined: true },
        { label: 'Highest level of education', text: getField(g.educationLevel, 'educationLevel'), lined: true },
        { label: 'Primary language', text: getField(g.primaryLanguage, 'primaryLanguage'), lined: true },
        { label: 'Speaks English', text: getField(g.speaksEnglish, 'speaksEnglish'), lined: true },
        { label: 'State HIV case ID', text: getField(g.stateHivCaseId, 'stateHivCaseId'), lined: false }
    ];

    return <LinedMergePreviewCard id="generalInfo" title="General patient information" items={items} />;
};
