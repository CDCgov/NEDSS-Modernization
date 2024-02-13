import { Button, Icon } from '@trussworks/react-uswds';
import {
    PageHeader,
    PageManagementLayout,
    PageManagementMenu,
    PageManagementProvider,
    useGetPageDetails,
    usePageManagement
} from 'apps/page-builder/page/management';
import { Loading } from 'components/Spinner';
import { LinkButton } from 'components/button';
import { PageInformation } from './information/PageInformation';
import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import styles from './preview-page.module.scss';
import { PreviewTab } from './tab';

const PreviewPage = () => {
    const { page, fetch, refresh } = useGetPageDetails();

    return page ? (
        <PageManagementProvider page={page} fetch={fetch} refresh={refresh}>
            <PreviewPageContent />
        </PageManagementProvider>
    ) : (
        <Loading center />
    );
};

const PreviewPageContent = () => {
    const { page, selected } = usePageManagement();

    return (
        <PageManagementLayout name={page.name} mode={page.status}>
            <PageHeader page={page} tabs={page.tabs ?? []}>
                <PageManagementMenu>
                    <NavLinkButton to={`/page-builder/pages/${page.id}/business-rules`} type="outline">
                        Business rules
                    </NavLinkButton>
                    <Button outline type="button">
                        Save as Template
                    </Button>
                    {page.status === 'Published' ? (
                        <Button type="button" outline>
                            Create draft
                        </Button>
                    ) : (
                        <>
                            <Button type="button" outline>
                                Delete draft
                            </Button>
                            <Button type="button" outline>
                                Edit draft
                            </Button>
                        </>
                    )}
                    <NavLinkButton type="outline" to="edit">
                        Edit draft
                    </NavLinkButton>
                    <LinkButton
                        href={`/nbs/page-builder/api/v1/pages/${page.id}/preview`}
                        label="open a preview of the page">
                        <Icon.Visibility size={3} />
                    </LinkButton>
                    <LinkButton href={`/nbs/page-builder/api/v1/pages/${page.id}/clone`}>
                        <Icon.ContentCopy size={3} />
                    </LinkButton>
                    <LinkButton
                        href={`/nbs/page-builder/api/v1/pages/${page.id}/print`}
                        label="open simplified page view for printing">
                        <Icon.Print size={3} />
                    </LinkButton>
                    <Button type="button">Publish</Button>
                </PageManagementMenu>
            </PageHeader>
            <div className={styles.preview}>
                <aside>
                    <PageInformation />
                </aside>
                <main>{selected && <PreviewTab tab={selected} />}</main>
            </div>
        </PageManagementLayout>
    );
};

export { PreviewPage };
