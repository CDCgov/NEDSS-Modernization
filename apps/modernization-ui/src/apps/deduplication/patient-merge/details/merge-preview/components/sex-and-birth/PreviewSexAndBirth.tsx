import React from 'react';
import { MergeCandidate, MergeSexAndBirth } from '../../../../../api/model/MergeCandidate';
import { LinedMergePreviewCard } from '../shared/preview-card-lined/LinedMergePreviewCard';
import { format, parseISO, differenceInYears } from 'date-fns';
import { PatientMergeForm, SexAndBirthValues } from '../../../merge-review/model/PatientMergeForm';

type PreviewSexAndBirthProps = {
    mergeFormData: PatientMergeForm;
    mergeCandidates: MergeCandidate[];
};

export const PreviewSexAndBirth = ({ mergeFormData, mergeCandidates }: PreviewSexAndBirthProps) => {
    // Helper to get candidate by personUid or undefined
    const getCandidateByUid = (uid?: string) => {
        if (!uid) return undefined;
        return mergeCandidates.find((mc) => mc.personUid === uid);
    };

    const sAndBValues: SexAndBirthValues = mergeFormData.sexAndBirth;
    const asOfPersonUid = sAndBValues.currentSex ?? sAndBValues.dateOfBirth ?? sAndBValues.birthCity;
    const asOfDateStr = sAndBValues.asOf ?? getCandidateByUid(asOfPersonUid)?.sexAndBirth?.asOf;
    console.log('as of date', asOfDateStr);
    const asOfDate = asOfDateStr ? parseISO(asOfDateStr) : undefined;

    const dobStr = (() => {
        const dobCandidate = getCandidateByUid(sAndBValues.dateOfBirth);
        return dobCandidate?.sexAndBirth?.dateOfBirth;
    })();
    const dobDate = dobStr ? parseISO(dobStr) : undefined;

    const getField = (uid?: string, field?: keyof MergeSexAndBirth) => {
        if (!uid || !field) return '---';
        const candidate = getCandidateByUid(uid);
        return candidate?.sexAndBirth?.[field] ?? '---';
    };

    const isValidDate = (date: Date | undefined): date is Date => !!date && !isNaN(date.getTime());
    const currentAge = isValidDate(dobDate) && isValidDate(asOfDate) ? differenceInYears(asOfDate, dobDate) : undefined;

    const items = [
        { label: 'As of', text: isValidDate(asOfDate) ? format(asOfDate, 'MM/dd/yyyy') : '---', lined: true },
        { label: 'Date of birth', text: dobDate ? format(dobDate, 'MM/dd/yyyy') : '---', lined: true },
        { label: 'Current age', text: currentAge !== undefined ? currentAge.toString() : '---', lined: true },
        { label: 'Current sex', text: getField(sAndBValues.currentSex, 'currentSex'), lined: true },
        { label: 'Unknown reason', text: getField(sAndBValues.currentSex, 'sexUnknown'), lined: true },
        { label: 'Transgender information', text: getField(sAndBValues.transgenderInfo, 'transgender'), lined: true },
        { label: 'Additional gender', text: getField(sAndBValues.additionalGender, 'additionalGender'), lined: true },
        { label: 'Birth sex', text: getField(sAndBValues.birthGender, 'birthGender'), lined: true },
        { label: 'Multiple birth', text: getField(sAndBValues.multipleBirth, 'multipleBirth'), lined: true },
        { label: 'Birth order', text: getField(sAndBValues.birthOrder, 'birthOrder'), lined: true },
        { label: 'Birth city', text: getField(sAndBValues.birthCity, 'birthCity'), lined: true },
        { label: 'Birth state', text: getField(sAndBValues.birthState, 'birthState'), lined: true },
        { label: 'Birth county', text: getField(sAndBValues.birthCounty, 'birthCounty'), lined: true },
        { label: 'Birth country', text: getField(sAndBValues.birthCountry, 'birthCountry'), lined: false }
    ];

    return <LinedMergePreviewCard id="sexAndBirth" title="Sex & birth" items={items} />;
};
