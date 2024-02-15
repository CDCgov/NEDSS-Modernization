import { Button, Icon, Tooltip } from '@trussworks/react-uswds';
import {
    PageHeader,
    PageManagementLayout,
    PageManagementMenu,
    PageManagementProvider,
    useGetPageDetails,
    usePageManagement
} from 'apps/page-builder/page/management';
import { Loading } from 'components/Spinner';
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
                    {page.status === 'Draft' && (
                        <>
                            <Button type="button" outline>
                                Delete draft
                            </Button>
                            <Button type="button" outline>
                                Edit draft
                            </Button>
                        </>
                    )}
                    <Tooltip position="top" label="Preview in NBS Classic">
                        <a href={`/nbs/page-builder/api/v1/pages/${page.id}/preview`} className={styles.link}>
                            <Icon.Visibility size={3} />
                        </a>
                    </Tooltip>
                    <Tooltip position="top" label="Clone this page">
                        <a href={`/nbs/page-builder/api/v1/pages/${page.id}/clone`} className={styles.link}>
                            <Icon.ContentCopy size={3} />
                        </a>
                    </Tooltip>
                    <Tooltip position="top" label="Print this page">
                        <a href={`/nbs/page-builder/api/v1/pages/${page.id}/print`} className={styles.link}>
                            <Icon.Print size={3} />
                        </a>
                    </Tooltip>
                    {page.status === 'Published' ? (
                        <Button type="button">Create draft</Button>
                    ) : (
                        <Button type="button">Publish</Button>
                    )}
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
