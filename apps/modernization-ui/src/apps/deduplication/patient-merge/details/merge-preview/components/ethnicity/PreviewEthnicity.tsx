import React from 'react';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';
import { LinedMergePreviewCard } from '../shared/preview-card-lined/LinedMergePreviewCard';
import { format, isValid, parseISO } from 'date-fns';

type PreviewEthnicityProps = {
    mergeFormData: PatientMergeForm;
    mergeCandidates: MergeCandidate[];
};

export const PreviewEthnicity = ({ mergeFormData, mergeCandidates }: PreviewEthnicityProps) => {
    const ethnicityData = mergeCandidates.find((mc) => mc.personUid === mergeFormData.ethnicity)?.ethnicity;
    const asOfDate = ethnicityData?.asOf ? parseISO(ethnicityData.asOf) : undefined;
    const formattedAsOf = asOfDate && isValid(asOfDate) ? format(asOfDate, 'MM/dd/yyyy') : '---';

    const items = [
        { label: 'As of', text: formattedAsOf, lined: true },
        { label: 'Ethnicity', text: ethnicityData?.ethnicity ?? '---', lined: true },
        { label: 'Spanish Origin', text: ethnicityData?.spanishOrigin ?? '---', lined: true },
        { label: 'Reason unknown', text: ethnicityData?.reasonUnknown ?? '---', lined: false }
    ];

    return <LinedMergePreviewCard id="ethnicity" title="Ethnicity" items={items} />;
};
