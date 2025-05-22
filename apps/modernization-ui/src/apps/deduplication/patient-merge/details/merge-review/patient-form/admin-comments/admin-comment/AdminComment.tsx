import { AdminComments } from 'apps/deduplication/api/model/PatientData';
import { format, parseISO } from 'date-fns';
import { Radio } from 'design-system/radio';
import { Controller, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import styles from './admin-comment.module.scss';
import { LengthConstrained } from '../length-constrained/LengthConstrained';
import { GroupLine } from '../../shared/group-line/GroupLine';

type Props = {
    personUid: string;
    adminComments: AdminComments;
};
export const AdminComment = ({ personUid, adminComments }: Props) => {
    const form = useFormContext<PatientMergeForm>();

    const parseDate = (date?: string) => {
        if (date == undefined) {
            return;
        }
        return format(parseISO(date), 'MM/dd/yyyy');
    };

    return (
        <div className={styles.adminComment}>
            <Controller
                key={`comment-selection:${personUid}`}
                control={form.control}
                name="adminComments"
                render={({ field: { value, name, onChange } }) => (
                    <div className={styles.asOfDate}>
                        <Radio
                            id={`${name}-${personUid}`}
                            name={name}
                            sizing="small"
                            label="As of date"
                            onChange={onChange}
                            value={personUid}
                            checked={value === personUid}
                        />
                        <div>{parseDate(adminComments.date)}</div>
                    </div>
                )}
            />
            <div className={styles.comment}>
                <GroupLine last />
                <div className={styles.commentText}>
                    <span className={styles.label}>Comments: </span>
                    {adminComments?.comment ? <LengthConstrained content={adminComments.comment} limit={50} /> : '--'}
                </div>
            </div>
        </div>
    );
};
