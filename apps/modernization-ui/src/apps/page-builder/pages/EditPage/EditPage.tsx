import { EditPageHeader } from 'apps/page-builder/components/EditPageHeader/EditPageHeader';
import { EditPageTabs } from 'apps/page-builder/components/EditPageTabs/EditPageTabs';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import './EditPage.scss';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';
import { EditPageContent } from 'apps/page-builder/components/EditPageContent/EditPageContent';
import { EditPageSidebar } from 'apps/page-builder/components/EditPageSidebar/EditPageSidebar';

export const EditPage = () => {
    const { pageId } = useParams();
    const [page] = useState({
        name: 'Tetanus disease',
        description: 'This is the description'
    });

    const [tabs] = useState([
        { name: 'Patient' },
        { name: 'General' },
        { name: 'Interviewer' },
        { name: 'Case classification & identification' },
        { name: 'Case demographics' }
    ]);
    const [active, setActive] = useState('Patient');
    useEffect(() => {
        // Fetch page summary
    }, [pageId]);

    return (
        <PageBuilder page="edit-page">
            <div className="edit-page">
                <PagesBreadcrumb currentPage={page.name} />
                <div className="edit-page__header">
                    <EditPageHeader page={page} />
                    <EditPageTabs tabs={tabs} active={active} setActive={setActive} />
                </div>
                <div className="edit-page__container">
                    {/* EDIT PAGE HERE */}
                    <EditPageContent />
                    <EditPageSidebar />
                </div>
            </div>
        </PageBuilder>
    );
};
