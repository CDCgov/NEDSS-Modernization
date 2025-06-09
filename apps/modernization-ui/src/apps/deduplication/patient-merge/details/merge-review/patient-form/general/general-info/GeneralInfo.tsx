import { MergeGeneralInfo } from 'apps/deduplication/api/model/MergeCandidate';
import { Controller, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { MergeDataDisplay } from '../../shared/merge-data-display/MergeDataDisplay';
import { toDateDisplay } from '../../shared/toDateDisplay';

type Props = {
    personUid: string;
    generalInfo: MergeGeneralInfo;
};
export const GeneralInfo = ({ personUid, generalInfo }: Props) => {
    const form = useFormContext<PatientMergeForm>();

    return (
        <section>
            <Controller
                control={form.control}
                name="generalInfo.asOf"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="As of date"
                        display={toDateDisplay(generalInfo.asOf)}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                        underlined
                    />
                )}
            />
            <Controller
                control={form.control}
                name="generalInfo.maritalStatus"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Marital status"
                        display={generalInfo.maritalStatus}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                        underlined
                    />
                )}
            />
            <Controller
                control={form.control}
                name="generalInfo.mothersMaidenName"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Mother's maiden name"
                        display={generalInfo.mothersMaidenName}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                        underlined
                    />
                )}
            />
            <Controller
                control={form.control}
                name="generalInfo.numberOfAdultsInResidence"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Number of adults in residence"
                        display={generalInfo.numberOfAdultsInResidence}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                        underlined
                    />
                )}
            />
            <Controller
                control={form.control}
                name="generalInfo.numberOfChildrenInResidence"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Number of children in residence"
                        display={generalInfo.numberOfChildrenInResidence}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                        underlined
                    />
                )}
            />
            <Controller
                control={form.control}
                name="generalInfo.primaryOccupation"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Primary occupation"
                        display={generalInfo.primaryOccupation}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                        underlined
                    />
                )}
            />
            <Controller
                control={form.control}
                name="generalInfo.educationLevel"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Highest level of education"
                        display={generalInfo.educationLevel}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                        underlined
                    />
                )}
            />
            <Controller
                control={form.control}
                name="generalInfo.primaryLanguage"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Primary language"
                        display={generalInfo.primaryLanguage}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                        underlined
                    />
                )}
            />
            <Controller
                control={form.control}
                name="generalInfo.speaksEnglish"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Speaks english"
                        display={generalInfo.speaksEnglish}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                        underlined
                    />
                )}
            />
            <Controller
                control={form.control}
                name="generalInfo.stateHivCaseId"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="State HIV case ID"
                        display={generalInfo.stateHivCaseId}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                    />
                )}
            />
        </section>
    );
};
