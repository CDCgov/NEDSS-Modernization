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
    const { page } = useGetPageDetails();

    return page ? (
        <PageManagementProvider page={page}>
            <PreviewPageContent />
        </PageManagementProvider>
    ) : (
        <Loading center />
    );
};

const PreviewPageContent = () => {
    const { page, selected } = usePageManagement();

    return (
        <PageManagementLayout name={page.name} mode={'draft'}>
            <PageHeader page={page} tabs={page.tabs ?? []}>
                <PageManagementMenu>
                    <Button type="button" outline>
                        Business Rules
                    </Button>
                    <Button outline type="button">
                        Save as Template
                    </Button>
                    <Button type="button" outline>
                        Create draft
                    </Button>
                    <Button type="button" outline>
                        Delete draft
                    </Button>
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
