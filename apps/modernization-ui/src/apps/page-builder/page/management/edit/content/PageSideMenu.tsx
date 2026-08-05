import { Icon } from '@trussworks/react-uswds';
import { Icon as EQIcon } from 'components/Icon/Icon';

import styles from './page-content.module.scss';

type PageSideMenuProps = {
    onAddSection: () => void;
    onManageSection: () => void;
    onReorderModal: () => void;
};

export const PageSideMenu = ({ onAddSection, onManageSection, onReorderModal }: PageSideMenuProps) => {
    return (
        <div className={styles.sideMenu}>
            <ul className={styles.list}>
                <li
                    onClick={() => {
                        onManageSection();
                    }}
                    className="manageSections"
                >
                    <Icon.Edit aria-label="edit" size={3} />
                    Manage sections
                </li>
                <li
                    onClick={() => {
                        onAddSection();
                    }}
                    className="addSection"
                >
                    <Icon.Add aria-label="add" size={3} />
                    Add section
                </li>
                <li
                    onClick={() => {
                        onReorderModal();
                    }}
                >
                    <EQIcon alt="reorder" name="reorder" />
                    Reorder
                </li>
            </ul>
        </div>
    );
};
