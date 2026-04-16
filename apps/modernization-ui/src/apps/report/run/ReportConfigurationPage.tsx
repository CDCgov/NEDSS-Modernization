import { Button } from 'design-system/button';
import { permissions, Permitted } from 'libs/permission';
import { ReportRunLayout } from './layout/ReportRunLayout';
import { ReportConfiguration } from 'generated';

const ReportConfigurationPage = ({
    config,
    handleSubmit,
}: {
    config: ReportConfiguration;
    handleSubmit: (isExport: boolean) => void;
}) => {
    return (
        <ReportRunLayout
            config={config}
            actions={
                <>
                    <Permitted permission={permissions.reports.run}>
                        <Button onClick={() => handleSubmit(false)}>Run</Button>
                    </Permitted>
                    <Permitted permission={permissions.reports.export}>
                        <Button onClick={() => handleSubmit(true)}>Export</Button>
                    </Permitted>
                </>
            }
        >
            <div>
                <p>Config:</p>
                <p>{config ? JSON.stringify(config) : 'loading'}</p>
            </div>
        </ReportRunLayout>
    );
};

export { ReportConfigurationPage };
