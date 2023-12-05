import { Button, Icon } from '@trussworks/react-uswds';
import { Loading } from 'components/Spinner';
import { LinkButton } from 'components/button';
import {
    PageManagementProvider,
    useGetPageDetails,
    PageManagementLayout,
    PageHeader,
    PageManagementMenu,
    usePageManagement
} from 'apps/page-builder/page/management';
import { PageInformation } from './information/PageInformation';

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
                    <Button type="button" outline>
                        Edit draft
                    </Button>
                    <LinkButton
                        href={`/nbs/page-builder/api/v1/pages/${page.id}/preview`}
                        label="open a preview of the page">
                        <Icon.Visibility size={3} />
                    </LinkButton>
                    <LinkButton href={'#'}>
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
