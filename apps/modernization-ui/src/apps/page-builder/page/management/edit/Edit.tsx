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
    const { page, fetch, refresh } = useGetPageDetails();

    const manageSectionModalRef = useRef<ModalRef>(null);
    const addSectionModalRef = useRef<ModalRef>(null);
    const reorderModalRef = useRef<ModalRef>(null);

    const handleManageSection = () => {
        manageSectionModalRef.current?.toggleModal(undefined, true);
    };

    const handleAddSection = () => {
        addSectionModalRef.current?.toggleModal(undefined, true);
    };

    const handleReorderModal = () => {
        reorderModalRef.current?.toggleModal(undefined, true);
    };

    const handleUpdateSuccess = () => {
        refresh();
    };

    return (
        <>
            {page ? (
                <PageManagementProvider page={page} fetch={fetch} refresh={refresh}>
                    <ManageSectionModal addSecModalRef={addSectionModalRef} manageSecModalRef={manageSectionModalRef} />
                    <EditPageContent
                        handleManageSection={handleManageSection}
                        handleAddSection={handleAddSection}
                        handleReorderModal={handleReorderModal}
                    />
                    <DragDropProvider pageData={page} currentTab={0} successCallBack={handleUpdateSuccess}>
                        <ReorderModal modalRef={reorderModalRef} pageName={page.name} />
                    </DragDropProvider>
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
    handleReorderModal: () => void;
};

const EditPageContent = ({ handleManageSection, handleAddSection, handleReorderModal }: EditPageContentProps) => {
    const { page, selected, refresh } = usePageManagement();

    return (
        <PageManagementLayout name={page.name} mode={page.status}>
            <PageHeader page={page} tabs={page.tabs ?? []} selected={selected} onAddTabSuccess={refresh}>
                <PageManagementMenu>
                    <NavLinkButton to={`/page-builder/pages/${page.id}/business-rules`} type="outline">
                        Business rules
                    </NavLinkButton>
                    <NavLinkButton to={'..'}>Preview</NavLinkButton>
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
        </PageManagementLayout>
    );
};
