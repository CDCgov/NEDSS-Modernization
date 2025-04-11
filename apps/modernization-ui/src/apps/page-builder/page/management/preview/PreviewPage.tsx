import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { useAlert } from 'alert';
import {
    PageHeader,
    PageManagementLayout,
    PageManagementMenu,
    PageManagementProvider,
    useGetPageDetails,
    usePageManagement
} from 'apps/page-builder/page/management';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { Loading } from 'components/Spinner';
import { LinkButton } from 'components/button';
import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import { ConfirmationModal } from 'confirmation';
import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router';
import { PageControllerService } from '../../../generated/services/PageControllerService';
import { PublishPage } from './PublishPage/PublishPage';
import { SaveTemplate } from './SaveTemplate/SaveTemplate';
import { PageInformation } from './information/PageInformation';
import styles from './preview-page.module.scss';
import { StaticTabContent } from './staticTabContent/StaticTabContent';
import { PreviewTab } from './tab';
import { PagesTab } from 'apps/page-builder/generated';

const PreviewPage = () => {
    const { page, fetch, refresh } = useGetPageDetails();

    return page ? (
        <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={false}>
            <PreviewPageContent />
        </PageManagementProvider>
    ) : (
        <Loading center />
    );
};

const PreviewPageContent = () => {
    const { page, selected, displayStaticTab, refresh, select, selectStaticTab } = usePageManagement();
    const [visibleTabs, setVisibleTabs] = useState<PagesTab[]>([]);
    const saveTemplateRef = useRef<ModalRef>(null);
    const deleteDraftRef = useRef<ModalRef>(null);
    const publishDraftRef = useRef<ModalRef>(null);
    const publishingLoaderRef = useRef<ModalRef>(null);
    const { showAlert } = useAlert();
    const navigate = useNavigate();
    const [isPublishing, setIsPublishing] = useState(false);

    useEffect(() => {
        if (page.tabs) {
            const filteredTabs = page.tabs.filter((t) => t.visible);
            setVisibleTabs(filteredTabs);
            if (filteredTabs.length > 0) {
                select(filteredTabs[0]);
            } else {
                selectStaticTab('contactRecord');
            }
        } else {
            setVisibleTabs([]);
        }
    }, [page.tabs]);

    const handleCreateDraft = () => {
        PageControllerService.savePageDraft({
            id: page.id
        })
            .then((response) => {
                showAlert({
                    type: 'success',
                    title: 'Success',
                    message: `${page.name} is in Draft mode. You can edit the page details, rules, and layout.`
                });
                if (response?.templateId) {
                    navigate(`/page-builder/pages/${response.templateId}`);
                }
                refresh();
            })
            .catch((error) => {
                if (error instanceof Error) {
                    console.error(error);
                    showAlert({
                        type: 'error',
                        title: 'error',
                        message: error.message
                    });
                } else {
                    console.error(error);
                    showAlert({
                        type: 'error',
                        title: 'error',
                        message: 'An unknown error occurred'
                    });
                }
            });
    };

    const handleDeleteDraft = () => {
        PageControllerService.deletePageDraft({
            id: page.id
        })
            .then(() => {
                deleteDraftRef.current?.toggleModal();
                showAlert({
                    type: 'success',
                    title: 'Success',
                    message: `${page.name} draft was successfully deleted.`
                });
                navigate('/page-builder/pages');
            })
            .catch((error) => {
                if (error instanceof Error) {
                    console.error(error);
                    showAlert({
                        type: 'error',
                        title: 'error',
                        message: error.message
                    });
                } else {
                    console.error(error);
                    showAlert({
                        type: 'error',
                        title: 'error',
                        message: 'An unknown error occurred'
                    });
                }
            });
    };

    return (
        <>
            <PageManagementLayout name={page.name} mode={page.status}>
                <PageHeader page={page} tabs={visibleTabs}>
                    <PageManagementMenu>
                        <NavLinkButton
                            to={`/page-builder/pages/${page.id}/business-rules`}
                            type="outline"
                            dataTestId="businessRulesBtn">
                            Business rules
                        </NavLinkButton>
                        <ModalToggleButton modalRef={saveTemplateRef} outline type="button">
                            Save as template
                        </ModalToggleButton>
                        {page.status !== 'Published' && (
                            <>
                                <ModalToggleButton modalRef={deleteDraftRef} type="button" outline>
                                    Delete draft
                                </ModalToggleButton>
                                <NavLinkButton
                                    className="editDraftBtn"
                                    to={`/page-builder/pages/${page.id}/edit`}
                                    type="outline">
                                    Edit draft
                                </NavLinkButton>
                            </>
                        )}
                        <div className={styles.icons}>
                            <LinkButton
                                href={`/nbs/page-builder/api/v1/pages/${page.id}/preview`}
                                className={styles.link}
                                target="_blank"
                                rel="noopener noreferrer"
                                data-tooltip-position="top"
                                aria-label="Preview in NBS Classic">
                                <Icon.Visibility size={3} />
                            </LinkButton>
                            {page.status !== 'Published' ? (
                                <LinkButton
                                    href={`https://app.int1.nbspreview.com/nbs/ManagePage.do?method=loadManagePagePort&initLoad=true`}
                                    className={styles.link}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    data-tooltip-position="top"
                                    aria-label="Page porting">
                                    <Icon.ContentCopy size={3} />
                                </LinkButton>
                            ) : null}
                            <LinkButton
                                href={`/nbs/page-builder/api/v1/pages/${page.id}/print`}
                                className={styles.link}
                                target="_blank"
                                rel="noopener noreferrer"
                                data-tooltip-position="top"
                                aria-label="Print this page">
                                <Icon.Print size={3} />
                            </LinkButton>
                            {page.status === 'Published' ? (
                                <Button id="create-new-draft-button" type="button" onClick={handleCreateDraft}>
                                    Create new draft
                                </Button>
                            ) : (
                                <ModalToggleButton
                                    modalRef={publishDraftRef}
                                    type="button"
                                    data-testid="publishBtn"
                                    className="publishBtn">
                                    Publish
                                </ModalToggleButton>
                            )}
                        </div>
                    </PageManagementMenu>
                </PageHeader>
                <div className={styles.preview}>
                    <aside>
                        <PageInformation />
                    </aside>
                    <main>
                        {selected && <PreviewTab tab={selected} />}
                        {!selected && displayStaticTab && <StaticTabContent tab={displayStaticTab} />}
                    </main>
                </div>
            </PageManagementLayout>
            <ModalComponent
                modalRef={saveTemplateRef}
                modalHeading="Save as template"
                modalBody={<SaveTemplate modalRef={saveTemplateRef} />}
            />
            <ConfirmationModal
                modal={deleteDraftRef}
                title="Delete draft"
                message="Warning"
                detail={
                    <>
                        Are you sure you want to delete the draft version of <b>{page.name}</b>? Once the draft is
                        deleted, it will no longer be available in the system; any changes you have made will be lost.
                    </>
                }
                confirmText="Yes, delete"
                cancelText="No, go back"
                onConfirm={() => {
                    handleDeleteDraft();
                }}
                onCancel={() => {
                    deleteDraftRef.current?.toggleModal();
                }}
            />
            {!isPublishing ? (
                <ModalComponent
                    modalRef={publishDraftRef}
                    modalHeading="Publish page"
                    size="wide"
                    closer
                    modalBody={<PublishPage modalRef={publishDraftRef} onPublishing={setIsPublishing} />}
                />
            ) : (
                <ModalComponent
                    modalRef={publishingLoaderRef}
                    size="width"
                    closer
                    modalBody={
                        <div className={styles.loaderContent}>
                            <Loading center className={styles.loaderIcon} />
                            <div className={styles.loaderText}>
                                <h2>Publishing...</h2>
                            </div>
                            <div className={styles.loaderText}>
                                <h2>This may take a moment</h2>
                            </div>
                        </div>
                    }
                />
            )}
        </>
    );
};

export { PreviewPage };
