import React, { RefObject } from 'react';

import styles from './page-content.module.scss';
import { Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { Icon as EQIcon } from 'components/Icon/Icon';

type PageSideMenuProps = {
    onAddSection: () => void;
    addSectionModalRef: RefObject<ModalRef>;
};

export const PageSideMenu = ({ onAddSection, addSectionModalRef }: PageSideMenuProps) => {
    return (
        <div className={styles.sideMenu}>
            <ul className={styles.list}>
                <li>
                    <ModalToggleButton type="button" modalRef={addSectionModalRef} unstyled>
                        <Icon.Add
                            size={4}
                            onClick={() => {
                                onAddSection();
                                console.log(onAddSection);
                            }}
                        />
                        Add section
                    </ModalToggleButton>
                </li>
                <li>
                    <EQIcon name="reorder" />
                    Reorder
                </li>
            </ul>
        </div>
    );
};
