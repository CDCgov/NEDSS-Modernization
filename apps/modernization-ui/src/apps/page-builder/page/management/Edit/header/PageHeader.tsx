import { Button } from '@trussworks/react-uswds';
import styles from './PageHeader.module.scss';
import { PageTabs } from './PageTabs';

type Props = {
    tabs: { name: string; id: number }[];
    name: string;
    description?: string;
    onTabSelect?: (tab: number) => void;
};
export const PageHeader = ({ name, description, tabs, onTabSelect }: Props) => {
    return (
        <div className={styles.header}>
            <div className={styles.infoAndButtonWrapper}>
                <div className={styles.pageInfo}>
                    <div className={styles.pageName}>{name}</div>
                    <div className={styles.pageDescription}>{description}</div>
                </div>
                <div className={styles.buttons}>
                    <Button type="button" outline>
                        Business rules
                    </Button>
                    <Button type="button">Preview</Button>
                </div>
            </div>
            <PageTabs tabs={tabs} onTabSelect={onTabSelect} />
        </div>
    );
};
