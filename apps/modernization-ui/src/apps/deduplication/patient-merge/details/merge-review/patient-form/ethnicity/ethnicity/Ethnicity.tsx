import { MergeEthnicity } from 'apps/deduplication/api/model/MergeCandidate';
import { format, parseISO } from 'date-fns';
import { Controller, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { MergeDataDisplay } from '../../shared/merge-data-display/MergeDataDisplay';

type Props = {
    personUid: string;
    ethnicity: MergeEthnicity;
};
export const Ethnicity = ({ personUid, ethnicity }: Props) => {
    const form = useFormContext<PatientMergeForm>();

    const parseDate = (date?: string) => {
        if (date == undefined) {
            return '---';
        }
        return format(parseISO(date), 'MM/dd/yyyy');
    };

    return (
        <section>
            <Controller
                control={form.control}
                name="ethnicity"
                render={({ field }) => (
                    <MergeDataDisplay
                        label="As of date"
                        value={parseDate(ethnicity.asOf)}
                        selectable={{
                            id: `ethnicity-${field.name}-${personUid}`,
                            selectValue: personUid,
                            ...field
                        }}
                    />
                )}
            />
            <MergeDataDisplay label="Ethnicity" value={ethnicity.ethnicity} groupType="linked" />
            <MergeDataDisplay label="Spanish origin" value={ethnicity.spanishOrigin} groupType="linked" />
            <MergeDataDisplay label="Reason unknown" value={ethnicity.reasonUnknown} groupType="last" />
        </section>
    );
};
