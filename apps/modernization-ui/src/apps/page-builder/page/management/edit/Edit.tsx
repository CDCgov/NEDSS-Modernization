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
import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import { useParams } from 'react-router-dom';

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
    const { pageId } = useParams();

    return (
        <PageManagementLayout name={page.name} mode={'edit'}>
            <PageHeader page={page} tabs={page.tabs ?? []}>
                <PageManagementMenu>
                    <NavLinkButton to={`/page-builder/pages/${pageId}/business-rules-library`} type="outline">
                        Business rules
                    </NavLinkButton>
                    <NavLinkButton to={'..'}>Preview</NavLinkButton>
                </PageManagementMenu>
            </PageHeader>
            {selected && <PageContent tab={selected} />}
        </PageManagementLayout>
    );
};
