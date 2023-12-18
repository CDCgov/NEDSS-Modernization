import { Button } from '@trussworks/react-uswds';
import { PageContent } from './content/PageContent';
import { Loading } from 'components/Spinner';
import {
    PageManagementProvider,
    useGetPageDetails,
    PageManagementLayout,
    PageManagementMenu,
    PageHeader,
    usePageManagement
} from 'apps/page-builder/page/management';
import { useState } from 'react';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { AlertProvider } from 'alert';

export const Edit = () => {
    const { page } = useGetPageDetails();

    return page ? (
        <PageManagementProvider page={page}>
            <EditPageContent />
        </PageManagementProvider>
    ) : (
        <Loading center />
    );
};

const EditPageContent = () => {
    const { page, selected } = usePageManagement();
    const [alertType, setAlertType] = useState<string>('');
    const [alertMessage, setAlertMessage] = useState<string | null>(null);

    return (
        <PageManagementLayout name={page.name} mode={'edit'}>
            
            <PageHeader page={page} tabs={page.tabs ?? []}>
                <PageManagementMenu>
                    <Button type="button" outline>
                        Business rules
                    </Button>
                    <Button type="button">Preview</Button>
                </PageManagementMenu>
            </PageHeader>
            {selected && <PageContent tab={selected} alertMessage={setAlertMessage} alertType={setAlertType} />}
        </PageManagementLayout>
    );
};
