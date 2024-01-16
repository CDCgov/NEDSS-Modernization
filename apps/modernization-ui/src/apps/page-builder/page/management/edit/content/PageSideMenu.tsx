import styles from './page-content.module.scss';
import { Icon } from '@trussworks/react-uswds';
import { Icon as EQIcon } from 'components/Icon/Icon';

type PageSideMenuProps = {
    onAddSection: () => void;
};

export const PageSideMenu = ({ onAddSection }: PageSideMenuProps) => {
    return (
        <div className={styles.sideMenu}>
            <ul className={styles.list}>
                <li
                    onClick={() => {
                        onAddSection();
                    }}>
                    <Icon.Add size={4} />
                    Add section
                </li>
                <li>
                    <EQIcon name="reorder" />
                    Reorder
                </li>
            </ul>
        </div>
    );
};
