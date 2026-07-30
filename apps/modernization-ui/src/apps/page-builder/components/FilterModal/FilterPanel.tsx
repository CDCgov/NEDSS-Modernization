import { Icon } from '@trussworks/react-uswds';
import './filter.scss';
import { ReactNode } from 'react';

export const FilterPanel = ({
    header,
    footerAction,
    children,
}: {
    header: ReactNode;
    footerAction: ReactNode;
    children: ReactNode;
}) => {
    const toggleModal = () => {
        const filterBtn = document.getElementById('filter-model-btn');
        filterBtn?.click();
    };

    return (
        <div className="edit-filter-modal">
            <div className="header-block">
                <label className="header-title">{header}</label>
                <Icon.Close size={3} onClick={toggleModal} />
            </div>
            <div className="content-block">{children}</div>
            <div className="action-block">{footerAction}</div>
        </div>
    );
};
