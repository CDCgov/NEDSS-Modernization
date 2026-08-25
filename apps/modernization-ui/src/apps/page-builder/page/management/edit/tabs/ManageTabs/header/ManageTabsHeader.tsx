import { Button, Icon } from '@trussworks/react-uswds';

import styles from './manage-tabs-header.module.scss';
type Props = {
    id: string;
    showAddTab: boolean;
    onAddNew: () => void;
};
export const ManageTabsHeader = ({ id, showAddTab, onAddNew }: Props) => {
    return (
        <div className={styles.manageTabsHeader}>
            <div id={id} className={styles.headerText}>
                Manage tabs
            </div>
            {showAddTab && (
                <>
                    <Button type="button" onClick={onAddNew}>
                        <Icon.Add aria-label="add" className="margin-right-05em add-tab-icon" />
                        <span>Add new tab</span>
                    </Button>
                </>
            )}
        </div>
    );
};
