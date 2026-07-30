import { Button, Icon } from '@trussworks/react-uswds';
import './filter.scss';
import { ReactNode } from 'react';

export const FilterWrapper = ({
    isModalHidden,
    name,
    toggleModal,
    children,
}: {
    isModalHidden: boolean;
    name: ReactNode;
    toggleModal: () => void;
    children: ReactNode;
}) => {
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
