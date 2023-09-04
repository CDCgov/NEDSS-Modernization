import { EditPageHeader } from 'apps/page-builder/components/EditPageHeader/EditPageHeader';
import { EditPageTabs } from 'apps/page-builder/components/EditPageTabs/EditPageTabs';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import { useParams } from 'react-router-dom';
import { useContext, useEffect, useState } from 'react';
import './EditPage.scss';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';
import { EditPageContentComponent } from 'apps/page-builder/components/EditPageContent/EditPageContent';
import { EditPageSidebar } from 'apps/page-builder/components/EditPageSidebar/EditPageSidebar';
import { fetchPageDetails } from 'apps/page-builder/services/pagesAPI';
import { UserContext } from 'user';
import { PageDetails } from 'apps/page-builder/generated/models/PageDetails';
import { Tabs } from 'apps/page-builder/generated/models/Tabs';

export const EditPage = () => {
    const { pageId } = useParams();
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const [page, setPage] = useState<PageDetails>();
    const [tabs, setTabs] = useState<Tabs[]>([]);
    const [active, setActive] = useState(0);

    useEffect(() => {
        // Fetch page summary
        if (pageId) {
            fetchPageDetails(token, Number(pageId)).then((data: any) => {
                setPage(data);
            });
        }
    }, [pageId]);
    useEffect(() => {
        if (page) {
            setTabs(page.pageTabs);
        }
    }, [page]);

    return (
        <PageBuilder page="edit-page">
            {page ? (
                <div className="edit-page">
                    <PagesBreadcrumb currentPage={page.Name} />
                    <div className="edit-page__header">
                        <EditPageHeader page={page} />
                        <EditPageTabs tabs={tabs} active={active} setActive={setActive} />
                    </div>
                    <div className="edit-page__container">
                        <EditPageContentComponent content={page.pageTabs[active]} />
                        <EditPageSidebar />
                    </div>
                </div>
            ) : null}
        </PageBuilder>
    );
};
