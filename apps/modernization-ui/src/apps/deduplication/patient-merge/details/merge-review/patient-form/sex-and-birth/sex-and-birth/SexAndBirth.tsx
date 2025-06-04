import { MergeSexAndBirth } from 'apps/deduplication/api/model/MergeCandidate';
import { Controller, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { format, parseISO } from 'date-fns';
import { MergeDataDisplay } from '../../shared/merge-data-display/MergeDataDisplay';
import { calculateAge } from 'utils/util';

type Props = {
    personUid: string;
    sexAndBirth: MergeSexAndBirth;
};
export const SexAndBirth = ({ personUid, sexAndBirth }: Props) => {
    const form = useFormContext<PatientMergeForm>();

    const parseDate = (date?: string) => {
        if (date == undefined) {
            return '---';
        }
        return format(parseISO(date), 'MM/dd/yyyy');
    };

    const parseAge = (date?: string) => {
        if (date == undefined) {
            return '---';
        }
        return calculateAge(parseISO(date));
    };

    return (
        <section>
            <Controller
                control={form.control}
                name="sexAndBirth.asOf"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="As of date"
                        display={parseDate(sexAndBirth.asOf)}
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
                name="sexAndBirth.dateOfBirth"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="DOB"
                        display={parseDate(sexAndBirth.dateOfBirth)}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                    />
                )}
            />
            <MergeDataDisplay
                label="Current age"
                display={parseAge(sexAndBirth.dateOfBirth)}
                groupType="blank"
                underlined
            />
            <Controller
                control={form.control}
                name="sexAndBirth.currentSex"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Current sex"
                        display={sexAndBirth.currentSex}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                    />
                )}
            />
            <MergeDataDisplay label="Unknown reason" display={sexAndBirth.sexUnknown} groupType="last" underlined />
            <Controller
                control={form.control}
                name="sexAndBirth.transgenderInfo"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Transgender information"
                        display={sexAndBirth.transgender}
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
                name="sexAndBirth.additionalGender"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Additional gender"
                        display={sexAndBirth.additionalGender}
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
                name="sexAndBirth.birthGender"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Birth sex"
                        display={sexAndBirth.birthGender}
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
                name="sexAndBirth.multipleBirth"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Multiple birth"
                        display={sexAndBirth.multipleBirth}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                    />
                )}
            />
            <MergeDataDisplay label="Birth order" display={sexAndBirth.birthOrder} groupType="last" underlined />
            <Controller
                control={form.control}
                name="sexAndBirth.birthCity"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Birth city"
                        display={sexAndBirth.birthCity}
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
                name="sexAndBirth.birthState"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Birth state"
                        display={sexAndBirth.birthState}
                        selectable={{
                            id: `${field.name}-${personUid}`,
                            formValue: personUid,
                            ...field
                        }}
                    />
                )}
            />
            <MergeDataDisplay label="Birth county" display={sexAndBirth.birthCounty} groupType="last" underlined />
            <Controller
                control={form.control}
                name="sexAndBirth.birthCountry"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="Birth country"
                        display={sexAndBirth.birthCountry}
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
