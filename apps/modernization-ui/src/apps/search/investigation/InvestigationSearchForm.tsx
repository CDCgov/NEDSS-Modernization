import React from 'react';
import GeneralSearchFields from './GeneralSearchFields';
import { UseFormReturn } from 'react-hook-form';
import { InvestigationFilterEntry } from './InvestigationFormTypes';
import styles from './InvestigationSearchForm.module.scss';
import CriteriaSearchFields from './CriteriaSearchFields';

type Props = {
    form: UseFormReturn<InvestigationFilterEntry>;
};

const InvestigationSearchForm = ({ form }: Props) => {
    return (
        <div className={styles.investigationSearchContainer}>
            <GeneralSearchFields form={form} />
            <CriteriaSearchFields form={form} />
        </div>
    );
};

export default InvestigationSearchForm;
