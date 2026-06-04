import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { ReportLayout } from 'apps/report/layout/ReportLayout';
import { Button } from 'design-system/button';
import { useState } from 'react';
import { ConfigForm, formToRequest, ReportConfigurationContent } from './ReportConfigurationContent';

import styles from 'apps/report/layout/layout.module.scss';
import { FormProvider, useForm } from 'react-hook-form';
import { ReportControllerService } from 'generated';
import { useNavigate } from 'react-router';

const AddReportConfiguration = () => {
    const [error, setError] = useState<string | null>(null);
    const [submitting, setSubmitting] = useState<boolean>(false);

    const navigate = useNavigate();

    const form = useForm<ConfigForm>({
        mode: 'onSubmit',
    });

    const handleSubmit = form.handleSubmit(
        (data) => {
            setSubmitting(true);
            setError('');

            ReportControllerService.createReport({
                requestBody: formToRequest(data),
            })
                .then((reportId) => {
                    navigate(`/report/management/configuration/${reportId.reportUid}/${reportId.dataSourceUid}`);
                })
                .catch((err) => {
                    setError(JSON.stringify(err));
                })
                .finally(() => setSubmitting(false));
        },
        (errors) => {
            // TODO make this gather all errors and nicely format
            setError(JSON.stringify(errors));
        }
    );

    return (
        <ReportLayout
            title="Add Report"
            actions={
                <>
                    <Button secondary={true} onClick={() => window.alert('to do')} disabled={submitting}>
                        Cancel
                    </Button>
                    <Button onClick={handleSubmit} disabled={submitting}>
                        Submit
                    </Button>
                </>
            }
        >
            <div className={styles.columnContent}>
                {error && <AlertBanner type="error">{error}</AlertBanner>}
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
