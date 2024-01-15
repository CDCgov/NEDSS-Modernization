import { PageContent } from './content/PageContent';
import { Loading } from 'components/Spinner';
import {
    PageManagementProvider,
    useGetPageDetails,
    PageManagementLayout,
    PageManagementMenu,
    PageHeader,
    usePageManagement
} from 'apps/page-builder/page/management';
import { NavLinkButton } from 'components/button/nav/NavLinkButton';
import { ManageSectionModal } from './section/manage/ManageSectionModal';
import { ModalRef } from '@trussworks/react-uswds';
import { useRef, useState } from 'react';
import { PagesTab } from 'apps/page-builder/generated';

export const Edit = () => {
    const { page, fetch } = useGetPageDetails();

    const manageSectionModalRef = useRef<ModalRef>(null);
    const addSectionModalRef = useRef<ModalRef>(null);
    const [currentTab, setCurrentTab] = useState<PagesTab>();

    const handleManageSection = (tab: PagesTab) => {
        setCurrentTab(tab);
        manageSectionModalRef.current?.toggleModal(undefined, true);
    };

    const handleAddSection = (tab: PagesTab) => {
        setCurrentTab(tab);
        addSectionModalRef.current?.toggleModal(undefined, true);
    };

    return (
        <>
            {page ? (
                <>
                    <PageManagementProvider page={page} fetch={fetch}>
                        <EditPageContent
                            handleManageSection={handleManageSection}
                            handleAddSection={handleAddSection}
                        />
                        <ManageSectionModal
                            pageId={page?.id}
                            tab={currentTab}
                            refresh={() => fetch(page!.id)}
                            addSecModalRef={addSectionModalRef}
                            manageSecModalRef={manageSectionModalRef}
                        />
                    </PageManagementProvider>
                </>
            ) : (
                <Loading center />
            )}
        </>
    );
};

type EditPageContentProps = {
    handleManageSection?: (tab: PagesTab) => void;
    handleAddSection?: (tab: PagesTab) => void;
};

const EditPageContent = ({ handleManageSection, handleAddSection }: EditPageContentProps) => {
    const { page, selected, fetch } = usePageManagement();

    const refresh = () => {
        fetch(page.id);
    };

    return (
        <PageManagementLayout name={page.name} mode={'edit'}>
            <PageHeader page={page} tabs={page.tabs ?? []}>
                <PageManagementMenu>
                    <NavLinkButton to={`/page-builder/pages/${page.id}/business-rules-library`} type="outline">
                        Business rules
                    </NavLinkButton>
                    <NavLinkButton to={'..'}>Preview</NavLinkButton>
                </PageManagementMenu>
            </PageHeader>
            {selected && (
                <PageContent
                    tab={selected}
                    refresh={refresh}
                    handleManageSection={handleManageSection}
                    handleAddSection={handleAddSection}
                />
            )}
        </PageManagementLayout>
    );
};
