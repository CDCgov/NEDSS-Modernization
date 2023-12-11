import { Button } from '@trussworks/react-uswds';
import { PagesResponse } from 'apps/page-builder/generated';
import styles from './PageHeader.module.scss';
import { PageTabs } from './PageTabs';

type Props = {
    pageDetails: PagesResponse;
};
export const PageHeader = ({ pageDetails }: Props) => {
    const tabNames = (): string[] => {
        return pageDetails.tabs?.map((t) => t.name ?? '') ?? [];
    };
    return (
        <div className={styles.header}>
            <div className={styles.infoAndButtonWrapper}>
                <div className={styles.pageInfo}>
                    <div className={styles.pageName}>{pageDetails.name}</div>
                    <div className={styles.pageDescription}>{pageDetails.description}</div>
                </div>
                <div className={styles.buttons}>
                    <Button type="button" outline disabled>
                        Business rules
                    </Button>
                    <Button type="button" disabled>
                        Preview
                    </Button>
                </div>
            </div>
            <PageTabs tabs={tabNames()} />
        </div>
    );
};
