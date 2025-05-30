import { MergePatient } from 'apps/deduplication/api/model/MergePatient';
import React from 'react';
import { Section } from '../shared/section/Section';
import { AdminComment } from '../admin-comments/admin-comment/AdminComment';

type Props = {
    mergePatients: MergePatient[];
};
export const EthnicitySelection = ({ mergePatients }: Props) => {
    return (
        <Section
            title="ADMINISTRATIVE COMMENTS"
            mergePatients={mergePatients}
            render={(p) => <AdminComment personUid={p.personUid} adminComments={p.adminComments} />}
        />
    );
};
