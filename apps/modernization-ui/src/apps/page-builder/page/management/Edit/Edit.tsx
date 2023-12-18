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
            {selected && <PageContent tab={selected} />}
        </PageManagementLayout>
    );
};
