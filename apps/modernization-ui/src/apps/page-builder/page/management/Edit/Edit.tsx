import { PagesTab } from 'apps/page-builder/generated';
import { useGetPageDetails } from 'apps/page-builder/hooks/api/useGetPageDetails';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import styles from './Edit.module.scss';
import { PageContent } from './content/PageContent';
import { PageHeader } from './header/PageHeader';
import { PageStatusBar } from './PageStatusBar';

export const Edit = () => {
    const { fetch, pageDetails } = useGetPageDetails();
    const { pageId } = useParams();
    const [selectedTab, setSelectdTab] = useState<PagesTab | undefined>();

    const tabs = (): { name: string; id: number }[] => {
        return (
            pageDetails?.tabs?.map((t) => {
                return { name: t.name ?? '', id: t.id ?? 0 };
            }) ?? []
        );
    };

    useEffect(() => {
        if (pageId) {
            fetch(+pageId);
        }
    }, [pageId]);

    useEffect(() => {
        const tabs = pageDetails?.tabs ?? [];
        setSelectdTab(tabs[0]);
    }, [pageDetails]);

    const handleTabChange = (tab: number) => {
        const newSelection = pageDetails?.tabs?.filter((t) => t.id === tab)[0];
        if (newSelection) {
            setSelectdTab(newSelection);
        }
    };

    return (
        <div className={styles.editPage}>
            <PageStatusBar name={pageDetails?.name} pageStatus="EDIT MODE" />
            {pageDetails && (
                <>
                    <PageHeader
                        onTabSelect={handleTabChange}
                        name={pageDetails.name}
                        description={pageDetails.description}
                        tabs={tabs()}
                    />
                    {selectedTab && <PageContent tab={selectedTab} />}
                </>
            )}
        </div>
    );
};
