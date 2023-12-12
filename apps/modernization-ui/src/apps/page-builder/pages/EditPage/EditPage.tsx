import { EditPageHeader } from 'apps/page-builder/pages/EditPage/EditPageHeader/EditPageHeader';
import { EditPageTabs } from 'apps/page-builder/pages/EditPage/EditPageTabs/EditPageTabs';
import { PageBuilder } from '../PageBuilder/PageBuilder';
import { useParams } from 'react-router-dom';
import { useContext, useEffect, useRef, useState } from 'react';
import './EditPage.scss';
import { PagesBreadcrumb } from 'apps/page-builder/components/PagesBreadcrumb/PagesBreadcrumb';
import { EditPageContentComponent } from 'apps/page-builder/pages/EditPage/EditPageContent/EditPageContent';
import { EditPageSidebar } from 'apps/page-builder/pages/EditPage/EditPageSidebar/EditPageSidebar';
import { fetchPageDetails, savePageAsDraft } from 'apps/page-builder/services/pagesAPI';
import { UserContext } from 'user';
import { PagesResponse } from 'apps/page-builder/generated';
import AddSectionModal from 'apps/page-builder/components/AddSection/AddSectionModal';
import { ModalRef } from '@trussworks/react-uswds';
import { Spinner } from 'components/Spinner/Spinner';
import { AlertBanner } from 'apps/page-builder/components/AlertBanner/AlertBanner';
import { ReorderModal } from './ReorderModal/ReorderModal';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';

export const EditPage = () => {
    const { pageId } = useParams();
    const { state } = useContext(UserContext);
    const token = `Bearer ${state.getToken()}`;
    const [page, setPage] = useState<PagesResponse>();
    const [active, setActive] = useState(0);
    const addSectionModalRef = useRef<ModalRef>(null);
    const reorderModalRef = useRef<ModalRef>(null);
    const [alertType, setAlertType] = useState<string>('');
    const [alertMessage, setAlertMessage] = useState<string | null>(null);

    useEffect(() => {
        if (pageId) {
            fetchPageDetails(token, Number(pageId)).then((data) => {
                setPage(data);
            });
        }
    }, [pageId]);

    const handleUpdateSuccess = () => {
        fetchPageDetails(token, Number(pageId)).then((data) => setPage(data));
    };

    const handleSaveDraft = () => {
        savePageAsDraft(token, Number(pageId))
            .then(() => {
                setAlertMessage('Page successfully saved as Draft');
                setAlertType('success');
            })
            .catch((error) => {
                setAlertMessage(error.body.message);
                setAlertType('error');
            });
    };

    return (
        <PageBuilder>
            {page && page.id ? (
                <DragDropProvider pageData={page} currentTab={active} successCallBack={handleUpdateSuccess}>
                    <div className="edit-page">
                        <PagesBreadcrumb currentPage={page.name} />
                        {alertMessage ? <AlertBanner type={alertType}>{alertMessage}</AlertBanner> : null}
                        <div className="edit-page__header">
                            <EditPageHeader page={page} handleSaveDraft={handleSaveDraft} />
                            {page.tabs ? (
                                <EditPageTabs
                                    tabs={page.tabs}
                                    active={active}
                                    setActive={setActive}
                                    onAddSuccess={handleUpdateSuccess}
                                />
                            ) : null}
                        </div>
                        <div className="edit-page__container">
                            <div className="edit-page__content">
                                {page.tabs?.[active] ? (
                                    <EditPageContentComponent
                                        content={page.tabs[active]}
                                        onAddSection={handleUpdateSuccess}
                                    />
                                ) : null}

                                <EditPageSidebar
                                    addSectionModalRef={addSectionModalRef}
                                    reorderModalRef={reorderModalRef}
                                />
                            </div>
                        </div>
                    </div>
                    {pageId && page.tabs?.[active].id ? (
                        <AddSectionModal
                            modalRef={addSectionModalRef}
                            pageId={pageId}
                            tabId={page.tabs[active]?.id}
                            onAddSection={handleUpdateSuccess}
                        />
                    ) : null}
                    {page.name && page.tabs?.[active] ? (
                        <ReorderModal modalRef={reorderModalRef} pageName={page.tabs[active].name} />
                    ) : null}
                </DragDropProvider>
            ) : (
                <Spinner />
            )}
        </PageBuilder>
    );
};
