import { EditPageHeader } from 'apps/page-builder/components/EditPageHeader/EditPageHeader';
import { EditPageTabs } from 'apps/page-builder/components/EditPageTabs/EditPageTabs';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import { useParams } from 'react-router-dom';
import { useContext, useEffect, useRef, useState } from 'react';
import './EditPage.scss';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';
import { EditPageContentComponent } from 'apps/page-builder/components/EditPageContent/EditPageContent';
import { EditPageSidebar } from 'apps/page-builder/components/EditPageSidebar/EditPageSidebar';
import { fetchPageDetails, savePageAsDraft } from 'apps/page-builder/services/pagesAPI';
import { UserContext } from 'user';
import { PageTab, PagedDetail } from 'apps/page-builder/generated';
import AddSectionModal from 'apps/page-builder/components/AddSection/AddSectionModal';
import { ModalRef } from '@trussworks/react-uswds';
import { Spinner } from 'components/Spinner/Spinner';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { ReorderModal } from './ReorderModal/ReorderModal';

export const EditPage = () => {
    const { pageId } = useParams();
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const [page, setPage] = useState<PagedDetail>();
    const [tabs, setTabs] = useState<PageTab[] | undefined>();
    const [active, setActive] = useState(0);
    const addSectionModalRef = useRef<ModalRef>(null);
    const reorderModalRef = useRef<ModalRef>(null);
    const [alertType, setAlertType] = useState<string>('');
    const [alertMessage, setAlertMessage] = useState<string | null>(null);

    const getPageDetails = () => {
        if (pageId) {
            fetchPageDetails(token, Number(pageId)).then((data: any) => {
                setPage(data);
            });
        }
    };

    useEffect(() => {
        // Fetch page summary
        getPageDetails();
    }, [pageId]);

    useEffect(() => {
        if (page) {
            setTabs(page.pageTabs);
        }
    }, [page]);

    const handleAddSuccess = async () => {
        if (pageId) {
            await fetchPageDetails(token, Number(pageId)).then((data: any) => {
                setPage(data);
            });
        }
    };

    const handleSaveDraft = () => {
        savePageAsDraft(token, Number(pageId))
            .then((response) => {
                console.log(response);
                setAlertMessage('Page successfully saved as Draft');
                setAlertType('success');
            })
            .catch((error) => {
                setAlertMessage(error.body.message);
                setAlertType('error');
            });
    };

    return (
        <PageBuilder page="edit-page">
            {page ? (
                <div className="edit-page">
                    <PagesBreadcrumb currentPage={page.Name} />
                    <div className="edit-page__header">
                        <EditPageHeader page={page} handleSaveDraft={handleSaveDraft} />
                        {tabs ? (
                            <EditPageTabs
                                tabs={tabs}
                                active={active}
                                setActive={setActive}
                                onAddSuccess={handleAddSuccess}
                            />
                        ) : null}
                    </div>
                    <div className="edit-page__container">
                        <div className="edit-page__content">
                            {alertMessage ? <AlertBanner type={alertType}>{alertMessage}</AlertBanner> : null}

                            {page.pageTabs?.[active] ? (
                                <EditPageContentComponent
                                    content={page.pageTabs[active]}
                                    onAddSection={handleAddSuccess}
                                />
                            ) : null}

                            <EditPageSidebar
                                addSectionModalRef={addSectionModalRef}
                                reorderModalRef={reorderModalRef}
                            />
                        </div>
                    </div>
                </div>
            ) : (
                <Spinner />
            )}
            {page && pageId && page.pageTabs?.[active].id ? (
                <AddSectionModal
                    modalRef={addSectionModalRef}
                    pageId={pageId}
                    tabId={page.pageTabs[active]?.id}
                    onAddSection={handleAddSuccess}
                />
            ) : null}
            {page && page.Name && page.pageTabs?.[active] ? (
                <ReorderModal modalRef={reorderModalRef} pageName={page.Name} content={page.pageTabs[active]} />
            ) : null}
        </PageBuilder>
    );
};
