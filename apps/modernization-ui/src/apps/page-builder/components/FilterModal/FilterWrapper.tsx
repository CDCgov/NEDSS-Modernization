import { Button, Icon } from '@trussworks/react-uswds';
import React from 'react';
import './filter.scss';

export const FilterWrapper = ({ isModalHidden, name, toggleModal, children }: any) => {
    return (
        <div className="filter-config-panel">
            <div className="filter-bar">
                <Button type="submit" id="filter-model-btn" className="filter-btn" onClick={toggleModal} outline>
                    <Icon.FilterAlt />
                    <label className="filter-label"></label>
                    {name}
                </Button>
            </div>
            {!isModalHidden && children}
        </div>
    );
};
