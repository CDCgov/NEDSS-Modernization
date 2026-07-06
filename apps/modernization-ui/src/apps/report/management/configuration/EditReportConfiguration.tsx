import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { ReportLayout } from 'apps/report/layout/ReportLayout';
import { Button, LinkButton } from 'design-system/button';
import { useState } from 'react';
import { ConfigForm, formToRequest, ReportConfigurationContent } from './ReportConfigurationContent';
import { FormProvider, useForm } from 'react-hook-form';
import { ReportControllerService, ReportConfiguration } from 'generated';
import { useLoaderData, useNavigate, useParams } from 'react-router';

import styles from 'apps/report/layout/layout.module.scss';
import { LoadingBlock } from 'libs/loading/block';

const EditReportConfiguration = () => {
    const params = useParams();
    const reportUid = parseInt(params.reportUid ?? '0');
    const dataSourceUid = parseInt(params.dataSourceUid ?? '0');
    const viewUrl = `/report/management/configuration/${reportUid}/${dataSourceUid}`;
    const [error, setError] = useState<string | null>(null);
    const [submitting, setSubmitting] = useState<boolean>(false);
    const config = useLoaderData<ReportConfiguration>();

    const navigate = useNavigate();

    const form = useForm<ConfigForm>({
        mode: 'onSubmit',
    });

    const handleSubmit = form.handleSubmit((data) => {
        setSubmitting(true);
        setError(null);

        ReportControllerService.editReport({
            reportUid,
            dataSourceUid,
            requestBody: formToRequest(data),
        })
            .then(() => {
                navigate(viewUrl);
            })
            .catch((err) => {
                setError(JSON.stringify(err));
            })
            .finally(() => setSubmitting(false));
    });

    return !config ? (
        <LoadingBlock />
    ) : (
        <ReportLayout
            title="Edit Report"
            actions={
                <>
                    <LinkButton secondary={true} href={viewUrl} disabled={submitting}>
                        Cancel
                    </LinkButton>
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
                        <ReportConfigurationContent isEditable={true} config={config} />
                    </form>
                </FormProvider>
            </div>
        </ReportLayout>
    );
};

export { EditReportConfiguration };
