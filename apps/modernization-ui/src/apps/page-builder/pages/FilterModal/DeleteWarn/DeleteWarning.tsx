import { Button, Icon } from '@trussworks/react-uswds';
import React from 'react';
import { FilterPanel } from '../FilterPanel';

export const DeleteWarning = ({ handleAction }: any) => {
    const renderAction = (
        <>
            <Button type="submit" className="filter-btn" onClick={handleAction} outline>
                No, Go Back
            </Button>
            <Button type="submit" className="filter-btn" onClick={handleAction}>
                Yes, Delete
            </Button>
        </>
    );

    return (
        <FilterPanel footerAction={renderAction} header="Delete Saved Search">
            <div className="usa-alert_body-delete">
                <p className="">
                    <Icon.Warning className="margin-left-2" size={9} />
                    <span>
                        Would you like to delete the saved search <b>“My COVID-19 and Monkeypox cases in December”?</b>
                    </span>
                </p>
            </div>
        </FilterPanel>
    );
};
