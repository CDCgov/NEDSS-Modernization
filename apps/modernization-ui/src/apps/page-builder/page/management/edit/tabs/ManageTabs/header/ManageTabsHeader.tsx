import { Button, Icon } from '@trussworks/react-uswds';
import styles from './manage-tabs-header.module.scss';
type Props = {
    showAddTab: boolean;
    onAddNew: () => void;
};
export const ManageTabsHeader = ({ showAddTab, onAddNew }: Props) => {
    return (
        <div className={styles.manageTabsHeader}>
            <div className={styles.headerText}>Manage tabs</div>
            {showAddTab && (
                <>
                    <Button type="button" onClick={onAddNew}>
                        <Icon.Add className="margin-right-05em add-tab-icon" />
                        <span>Add new tab</span>
                    </Button>
                </>
            )}
        </div>
    );
};
