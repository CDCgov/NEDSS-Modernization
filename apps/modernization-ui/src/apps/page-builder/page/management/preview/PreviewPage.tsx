import { useRef, useState } from 'react';
import { Button, Icon, ModalRef, ModalToggleButton, Tooltip } from '@trussworks/react-uswds';
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
import { ConfirmationModal } from 'confirmation';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { SaveTemplate } from './SaveTemplate/SaveTemplate';
import { PublishPage } from './PublishPage/PublishPage';
import { PageControllerService } from '../../../generated/services/PageControllerService';
import { authorization as getAuthorization } from 'authorization';
import { useAlert } from 'alert';
import { useNavigate } from 'react-router-dom';
import { Heading } from '../../../../../components/heading';

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
    const { page, selected, refresh } = usePageManagement();
    const saveTemplateRef = useRef<ModalRef>(null);
    const deleteDraftRef = useRef<ModalRef>(null);
    const publishDraftRef = useRef<ModalRef>(null);
    const publishingLoaderRef = useRef<ModalRef>(null);
    const authorization = getAuthorization();
    const { showAlert } = useAlert();
    const navigate = useNavigate();
    const [isPublishing, setIsPublishing] = useState(false);

    const handleCreateDraft = () => {
        try {
            PageControllerService.savePageDraftUsingPut({
                authorization,
                id: page.id
            }).then(() => {
                showAlert({
                    type: 'success',
                    header: 'Success',
                    message: `${page.name} is in Draft mode. You can edit the page details, rules, and layout.`
                });
                refresh();
            });
        } catch (error) {
            if (error instanceof Error) {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: error.message
                });
            } else {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: 'An unknown error occurred'
                });
            }
        }
    };

    const handleDeleteDraft = () => {
        try {
            PageControllerService.deletePageDraftUsingDelete({
                authorization,
                id: page.id
            }).then(() => {
                deleteDraftRef.current?.toggleModal();
                showAlert({
                    type: 'success',
                    header: 'Success',
                    message: `${page.name} draft was successfully deleted.`
                });
                navigate('/page-builder/pages');
            });
        } catch (error) {
            if (error instanceof Error) {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: error.message
                });
            } else {
                console.error(error);
                showAlert({
                    type: 'error',
                    header: 'error',
                    message: 'An unknown error occurred'
                });
            }
        }
    };

    return (
        <>
            <PageManagementLayout name={page.name} mode={page.status}>
                <PageHeader page={page} tabs={page.tabs ?? []}>
                    <PageManagementMenu>
                        <NavLinkButton to={`/page-builder/pages/${page.id}/business-rules`} type="outline">
                            Business rules
                        </NavLinkButton>
                        <ModalToggleButton modalRef={saveTemplateRef} outline type="button">
                            Save as Template
                        </ModalToggleButton>
                        {page.status !== 'Published' && (
                            <>
                                <ModalToggleButton modalRef={deleteDraftRef} type="button" outline>
                                    Delete draft
                                </ModalToggleButton>
                                <NavLinkButton to={`/page-builder/pages/${page.id}/edit`} type="outline">
                                    Edit draft
                                </NavLinkButton>
                            </>
                        )}
                        <Tooltip position="top" label="Preview in NBS Classic">
                            <a
                                href={`/nbs/page-builder/api/v1/pages/${page.id}/preview`}
                                className={styles.link}
                                target="_blank"
                                rel="noopener noreferrer">
                                <Icon.Visibility size={3} />
                            </a>
                        </Tooltip>
                        {page.status !== 'Published' ? (
                            <Tooltip position="top" label="Clone this page">
                                <a
                                    href={`/nbs/page-builder/api/v1/pages/${page.id}/clone`}
                                    className={styles.link}
                                    target="_blank"
                                    rel="noopener noreferrer">
                                    <Icon.ContentCopy size={3} />
                                </a>
                            </Tooltip>
                        ) : null}
                        <Tooltip position="top" label="Print this page">
                            <a
                                href={`/nbs/page-builder/api/v1/pages/${page.id}/print`}
                                className={styles.link}
                                target="_blank"
                                rel="noopener noreferrer">
                                <Icon.Print size={3} />
                            </a>
                        </Tooltip>
                        {page.status === 'Published' ? (
                            <Button type="button" onClick={handleCreateDraft}>
                                Create new draft
                            </Button>
                        ) : (
                            <ModalToggleButton modalRef={publishDraftRef} type="button">
                                Publish
                            </ModalToggleButton>
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
                    closer
                    modalBody={
                        <div className={styles.loaderContent}>
                            <Loading center className={styles.loaderIcon} />
                            <div className={styles.loaderText}>
                                <Heading level={3}>Publishing...</Heading>
                            </div>
                            <div className={styles.loaderText}>
                                <Heading level={3}>This may take a moment</Heading>
                            </div>
                        </div>
                    }
                />
            )}
        </>
    );
};

export { PreviewPage };
