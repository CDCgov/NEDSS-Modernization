import { Icon } from '@trussworks/react-uswds';
import styles from './page-content.module.scss';
import { Icon as EQIcon } from 'components/Icon/Icon';

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
                    className="manageSections">
                    <Icon.Edit size={3} />
                    Manage sections
                </li>
                <li
                    onClick={() => {
                        onAddSection();
                    }}
                    className="addSection">
                    <Icon.Add size={3} />
                    Add section
                </li>
                <li
                    onClick={() => {
                        onReorderModal();
                    }}>
                    <EQIcon name="reorder" />
                    Reorder
                </li>
            </ul>
        </div>
    );
};
