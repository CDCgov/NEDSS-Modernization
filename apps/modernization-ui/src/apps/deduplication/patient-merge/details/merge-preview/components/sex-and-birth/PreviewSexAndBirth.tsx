import React from 'react';
import { MergeCandidate, MergeSexAndBirth } from '../../../../../api/model/MergeCandidate';
import { LinedMergePreviewCard } from '../shared/preview-card-lined/LinedMergePreviewCard';
import { format, parseISO, differenceInYears, isValid } from 'date-fns';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';

type PreviewSexAndBirthProps = {
    mergeFormData: PatientMergeForm;
    mergeCandidates: MergeCandidate[];
};

export const PreviewSexAndBirth = ({ mergeFormData, mergeCandidates }: PreviewSexAndBirthProps) => {
    const sAndB = mergeFormData.sexAndBirth;

    const getCandidate = (uid?: string) => (uid ? mergeCandidates.find((c) => c.personUid === uid) : undefined);
    const getField = (uid?: string, field?: keyof MergeSexAndBirth) => {
        if (!uid || !field) return '---';
        return getCandidate(uid)?.sexAndBirth?.[field] ?? '---';
    };

    const parseDate = (str?: string) => {
        const d = str ? parseISO(str) : undefined;
        return isValid(d) ? d : undefined;
    };

    const asOfUid = sAndB.currentSex ?? sAndB.dateOfBirth ?? sAndB.birthCity;
    const asOfDate = parseDate(sAndB.asOf ?? getField(asOfUid, 'asOf'));
    const dobStr = getField(sAndB.dateOfBirth, 'dateOfBirth');
    const dobDate = parseDate(dobStr);
    const currentAge = dobDate ? differenceInYears(new Date(), dobDate) : undefined;

    const items = [
        { label: 'As of', text: asOfDate ? format(asOfDate, 'MM/dd/yyyy') : '---', lined: true },
        { label: 'Date of birth', text: dobDate ? format(dobDate, 'MM/dd/yyyy') : '---', lined: true },
        { label: 'Current age', text: currentAge !== undefined ? currentAge.toString() : '---', lined: true },
        { label: 'Current sex', text: getField(sAndB.currentSex, 'currentSex'), lined: true },
        { label: 'Unknown reason', text: getField(sAndB.currentSex, 'sexUnknown'), lined: true },
        { label: 'Transgender information', text: getField(sAndB.transgenderInfo, 'transgender'), lined: true },
        { label: 'Additional gender', text: getField(sAndB.additionalGender, 'additionalGender'), lined: true },
        { label: 'Birth sex', text: getField(sAndB.birthGender, 'birthGender'), lined: true },
        { label: 'Multiple birth', text: getField(sAndB.multipleBirth, 'multipleBirth'), lined: true },
        { label: 'Birth order', text: getField(sAndB.multipleBirth, 'birthOrder'), lined: true },
        { label: 'Birth city', text: getField(sAndB.birthCity, 'birthCity'), lined: true },
        { label: 'Birth state', text: getField(sAndB.birthState, 'birthState'), lined: true },
        { label: 'Birth county', text: getField(sAndB.birthState, 'birthCounty'), lined: true },
        { label: 'Birth country', text: getField(sAndB.birthCountry, 'birthCountry'), lined: false }
    ];

    return <LinedMergePreviewCard id="sexAndBirth" title="Sex & birth" items={items} />;
};
