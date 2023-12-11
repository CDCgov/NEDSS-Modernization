import { Button } from '@trussworks/react-uswds';
import styles from './PageHeader.module.scss';
import { PageTabs } from './PageTabs';

type Props = {
    tabs: string[];
    name: string;
    description?: string;
};
export const PageHeader = ({ name, description, tabs }: Props) => {
    return (
        <div className={styles.header}>
            <div className={styles.infoAndButtonWrapper}>
                <div className={styles.pageInfo}>
                    <div className={styles.pageName}>{name}</div>
                    <div className={styles.pageDescription}>{description}</div>
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
            <PageTabs tabs={tabs} />
        </div>
    );
};
