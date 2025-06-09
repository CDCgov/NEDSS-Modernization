import React from 'react';
import { MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { PatientMergeForm } from '../../../merge-review/model/PatientMergeForm';
import { Card } from 'design-system/card/Card';
import { toMMDDYYYY } from '../../utils/dateUtils';
import styles from './AdministrativeComments.module.scss';

type AdministrativeCommentsProps = {
    mergeCandidates: MergeCandidate[];
    mergeFormData: PatientMergeForm;
};

export const AdministrativeComments = ({ mergeCandidates, mergeFormData }: AdministrativeCommentsProps) => {
    const adminCommentsUid = mergeFormData.adminComments;
    const adminCandidate = mergeCandidates.find((c) => c.personUid === adminCommentsUid);

    if (!adminCandidate || !adminCandidate.adminComments) {
        return <div className={styles.noComments}>---</div>;
    }

    const { date = '', comment = '' } = adminCandidate.adminComments;

    return (
        <Card
            id="admin-comments"
            title="Administrative comments"
            subtext={toMMDDYYYY(date) || 'No date available'}
            className={styles.adminCommentsCard}
            level={2}
            collapsible={false}>
            <p className={styles.comment}>{comment || '---'}</p>
        </Card>
    );
};
