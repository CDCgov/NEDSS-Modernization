import {
    PageHeader,
    PageManagementLayout,
    PageManagementMenu,
    PageManagementProvider,
    useGetPageDetails,
    usePageManagement
} from 'apps/page-builder/page/management';
import { Loading } from 'components/Spinner';
import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import { PageContent } from './content/PageContent';
import { ManageSectionModal } from './section/manage/ManageSectionModal';
import { ModalRef } from '@trussworks/react-uswds';
import { useRef } from 'react';
import { ReorderModal } from './reorder/ReorderModal/ReorderModal';
import DragDropProvider from 'apps/page-builder/context/DragDropProvider';

export const Edit = () => {
    const { page, fetch, refresh, loading } = useGetPageDetails();

    const manageSectionModalRef = useRef<ModalRef>(null);
    const addSectionModalRef = useRef<ModalRef>(null);

    const handleManageSection = () => {
        manageSectionModalRef.current?.toggleModal(undefined, true);
    };

    const handleAddSection = () => {
        addSectionModalRef.current?.toggleModal(undefined, true);
    };

    return (
        <>
            {page ? (
                <PageManagementProvider page={page} fetch={fetch} refresh={refresh} loading={loading}>
                    <ManageSectionModal addSecModalRef={addSectionModalRef} manageSecModalRef={manageSectionModalRef} />
                    <EditPageContent handleManageSection={handleManageSection} handleAddSection={handleAddSection} />
                </PageManagementProvider>
            ) : (
                <Loading center />
            )}
        </>
    );
};

type EditPageContentProps = {
    handleManageSection?: () => void;
    handleAddSection?: () => void;
};

const EditPageContent = ({ handleManageSection, handleAddSection }: EditPageContentProps) => {
    const { page, selected, refresh } = usePageManagement();
    const reorderModalRef = useRef<ModalRef>(null);
    const handleReorderModal = () => {
        reorderModalRef.current?.toggleModal(undefined, true);
    };

    const handleUpdateSuccess = () => {
        refresh();
    };

    return (
        <PageManagementLayout name={page.name} mode={page.status}>
            <DragDropProvider pageData={page} successCallBack={handleUpdateSuccess}>
                <PageHeader page={page} tabs={page.tabs ?? []} onAddTabSuccess={refresh}>
                    <PageManagementMenu>
                        <NavLinkButton to={`/page-builder/pages/${page.id}/business-rules`} type="outline">
                            Business rules
                        </NavLinkButton>
                        <NavLinkButton data-testid="previewBtn" to={'..'}>
                            Preview
                        </NavLinkButton>
                    </PageManagementMenu>
                </PageHeader>
                {selected && (
                    <PageContent
                        tab={selected}
                        handleManageSection={handleManageSection}
                        handleAddSection={handleAddSection}
                        handleReorderModal={handleReorderModal}
                    />
                )}
                <ReorderModal modalRef={reorderModalRef} />
            </DragDropProvider>
        </PageManagementLayout>
    );
};
