import { ReportLayout } from 'apps/report/layout/ReportLayout';
import { Button, NavLinkButton } from 'design-system/button';
import { useState } from 'react';
import { ConfigForm, formToRequest, ReportConfigurationContent } from './ReportConfigurationContent';
import { FormProvider, useForm } from 'react-hook-form';
import { ReportControllerService, ReportConfiguration } from 'generated';
import { useLoaderData, useNavigate, useParams } from 'react-router';
import { LoadingBlock } from 'libs/loading/block';
import { ApiErrorBanner } from 'design-system/errors/ApiError';

import styles from 'apps/report/layout/layout.module.scss';

const EditReportConfiguration = () => {
    const params = useParams();
    const reportUid = parseInt(params.reportUid ?? '0');
    const dataSourceUid = parseInt(params.dataSourceUid ?? '0');
    const viewUrl = `/report/management/configuration/${reportUid}/${dataSourceUid}`;
    const [error, setError] = useState<unknown | null>(null);
    const [submitting, setSubmitting] = useState<boolean>(false);
    const config = useLoaderData<ReportConfiguration>();

    const navigate = useNavigate();

    const form = useForm<ConfigForm>({
        mode: 'onSubmit',
        reValidateMode: 'onSubmit',
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
            .catch(setError)
            .finally(() => setSubmitting(false));
    });

    return !config ? (
        <LoadingBlock />
    ) : (
        <ReportLayout
            title="Edit report"
            actions={
                <>
                    <NavLinkButton secondary={true} to={viewUrl} disabled={submitting}>
                        Cancel
                    </NavLinkButton>
                    <Button onClick={handleSubmit} disabled={submitting}>
                        Submit
                    </Button>
                </>
            }
        >
            <div className={styles.columnContent}>
                {!!error && <ApiErrorBanner action="editing" item="report" error={error} />}
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
