import { ReportLayout } from 'apps/report/layout/ReportLayout';
import { Button, LinkButton } from 'design-system/button';
import { useState } from 'react';
import { ConfigForm, formToRequest, ReportConfigurationContent } from './ReportConfigurationContent';
import { FormProvider, useForm } from 'react-hook-form';
import { ReportControllerService } from 'generated';
import { NBS_LIST_REPORT_CONFIG_PAGE } from './constants';
import { useNavigate } from 'react-router';

import styles from 'apps/report/layout/layout.module.scss';
import { ApiErrorBanner } from 'design-system/errors/ApiError';

const AddReportConfiguration = () => {
    const [error, setError] = useState<unknown | null>(null);
    const [submitting, setSubmitting] = useState<boolean>(false);

    const navigate = useNavigate();

    const form = useForm<ConfigForm>({
        mode: 'onSubmit',
        reValidateMode: 'onSubmit',
    });

    const handleSubmit = form.handleSubmit((data) => {
        setSubmitting(true);
        setError(null);

        ReportControllerService.createReport({
            requestBody: formToRequest(data),
        })
            .then((reportId) => {
                navigate(`/report/management/configuration/${reportId.reportUid}/${reportId.dataSourceUid}`);
            })
            .catch(setError)
            .finally(() => setSubmitting(false));
    });

    return (
        <ReportLayout
            title="Add Report"
            actions={
                <>
                    <LinkButton secondary={true} href={NBS_LIST_REPORT_CONFIG_PAGE} disabled={submitting}>
                        Cancel
                    </LinkButton>
                    <Button onClick={handleSubmit} disabled={submitting}>
                        Submit
                    </Button>
                </>
            }
        >
            <div className={styles.columnContent}>
                {!!error && <ApiErrorBanner action="adding" item="report" error={error} />}
                <FormProvider {...form}>
                    <form className={styles.columnContent} onSubmit={handleSubmit}>
                        <ReportConfigurationContent isEditable={true} />
                    </form>
                </FormProvider>
            </div>
        </ReportLayout>
    );
};

export { AddReportConfiguration };
