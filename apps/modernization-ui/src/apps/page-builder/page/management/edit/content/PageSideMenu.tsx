import { Icon } from '@trussworks/react-uswds';
import styles from './page-content.module.scss';

type PageSideMenuProps = {
    onAddSection: () => void;
    onManageSection: () => void;
};

export const PageSideMenu = ({ onAddSection, onManageSection }: PageSideMenuProps) => {
    return (
        <div className={styles.sideMenu}>
            <ul className={styles.list}>
                <li
                    onClick={() => {
                        onManageSection();
                    }}>
                    <Icon.Edit size={3} />
                    Manage sections
                </li>
                <li
                    onClick={() => {
                        onAddSection();
                    }}>
                    <Icon.Add size={3} />
                    Add section
                </li>
            </ul>
        </div>
    );
};
