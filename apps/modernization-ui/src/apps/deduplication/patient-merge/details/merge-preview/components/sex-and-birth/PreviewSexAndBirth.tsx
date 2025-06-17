import React from 'react';
import { MergeCandidate, MergeSexAndBirth } from '../../../../../api/model/MergeCandidate';
import { LinedMergePreviewCard } from '../shared/preview-card-lined/LinedMergePreviewCard';
import { format, parseISO, differenceInYears, isValid } from 'date-fns';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';

type PreviewSexAndBirthProps = {
    mergeFormData: PatientMergeForm;
    mergeCandidates: MergeCandidate[];
};

const parseDate = (str?: string) => {
    const d = str ? parseISO(str) : undefined;
    return d && isValid(d) ? d : undefined;
};

const formatDate = (str?: string) => {
    const d = parseDate(str);
    return d ? format(d, 'MM/dd/yyyy') : '---';
};

const getField = (mergeCandidates: MergeCandidate[], uid?: string, field?: keyof MergeSexAndBirth) =>
    uid && field ? (mergeCandidates.find((c) => c.personUid === uid)?.sexAndBirth?.[field] ?? '---') : '---';

export const PreviewSexAndBirth = ({ mergeFormData, mergeCandidates }: PreviewSexAndBirthProps) => {
    const sAndB = mergeFormData.sexAndBirth;
    const dobStr = getField(mergeCandidates, sAndB.dateOfBirth, 'dateOfBirth') as string;
    const dobDate = parseDate(dobStr);
    const age = dobDate ? differenceInYears(new Date(), dobDate) : undefined;
    const items = [
        { label: 'As of', text: formatDate(getField(mergeCandidates, sAndB.asOf, 'asOf') as string), lined: true },
        { label: 'Date of birth', text: formatDate(dobStr), lined: true },
        { label: 'Current age', text: age !== undefined ? `${age}` : '---', lined: true },
        { label: 'Current sex', text: getField(mergeCandidates, sAndB.currentSex, 'currentSex'), lined: true },
        { label: 'Unknown reason', text: getField(mergeCandidates, sAndB.currentSex, 'sexUnknown'), lined: true },
        {
            label: 'Transgender information',
            text: getField(mergeCandidates, sAndB.transgenderInfo, 'transgender'),
            lined: true
        },
        {
            label: 'Additional gender',
            text: getField(mergeCandidates, sAndB.additionalGender, 'additionalGender'),
            lined: true
        },
        { label: 'Birth sex', text: getField(mergeCandidates, sAndB.birthGender, 'birthGender'), lined: true },
        { label: 'Multiple birth', text: getField(mergeCandidates, sAndB.multipleBirth, 'multipleBirth'), lined: true },
        { label: 'Birth order', text: getField(mergeCandidates, sAndB.multipleBirth, 'birthOrder'), lined: true },
        { label: 'Birth city', text: getField(mergeCandidates, sAndB.birthCity, 'birthCity'), lined: true },
        { label: 'Birth state', text: getField(mergeCandidates, sAndB.birthState, 'birthState'), lined: true },
        { label: 'Birth county', text: getField(mergeCandidates, sAndB.birthState, 'birthCounty'), lined: true },
        { label: 'Birth country', text: getField(mergeCandidates, sAndB.birthCountry, 'birthCountry'), lined: false }
    ];

    return <LinedMergePreviewCard id="sexAndBirth" title="Sex & birth" items={items} />;
};
