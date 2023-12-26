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

export const Edit = () => {
    const { page, fetch } = useGetPageDetails();

    return page ? (
        <PageManagementProvider page={page} fetch={fetch}>
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
                    <NavLinkButton to={`/page-builder/pages/${page.id}/business-rules-library`} type="outline">
                        Business rules
                    </NavLinkButton>
                    <NavLinkButton to={'..'}>Preview</NavLinkButton>
                </PageManagementMenu>
            </PageHeader>
            {selected && <PageContent tab={selected} />}
        </PageManagementLayout>
    );
};
