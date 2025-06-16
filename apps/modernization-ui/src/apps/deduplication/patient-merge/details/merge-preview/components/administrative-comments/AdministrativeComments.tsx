import React from 'react';
import { format, parseISO, isValid } from 'date-fns';
import { Card } from 'design-system/card/Card';
import styles from './AdministrativeComments.module.scss';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';

type Props = {
    mergeCandidates: MergeCandidate[];
    mergeFormData: PatientMergeForm;
};

export const AdministrativeComments = ({ mergeCandidates, mergeFormData }: Props) => {
    const candidate = mergeCandidates.find((c) => c.personUid === mergeFormData.adminComments);
    const comment = candidate?.adminComments?.comment?.trim() ?? '---';
    const date = candidate?.adminComments?.date;

    const formattedDate =
        comment !== '---' && date && isValid(parseISO(date)) ? format(parseISO(date), 'MM/dd/yyyy') : undefined;

    return (
        <Card
            id="admin-comments"
            title="Administrative comments"
            subtext={formattedDate}
            className={styles.adminCommentsCard}
            level={2}
            collapsible={false}>
            <p className={styles.comment}>{comment}</p>
        </Card>
    );
};
