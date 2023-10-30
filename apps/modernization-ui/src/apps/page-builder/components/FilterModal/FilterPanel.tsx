import { Icon } from '@trussworks/react-uswds';
import React from 'react';
import './filter.scss';

export const FilterPanel = ({ header, footerAction, children }: any) => {
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
