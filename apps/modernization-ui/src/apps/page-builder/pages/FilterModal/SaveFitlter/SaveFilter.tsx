import { Button, TextInput, Radio } from '@trussworks/react-uswds';
import React from 'react';
import { FilterPanel } from '../FilterPanel';
// import { TextArea } from 'components/FormInputs/TextArea';

export const SaveFilter = ({ handleAction }: any) => {
    const renderAction = (
        <>
            <Button type="submit" className="filter-btn" onClick={handleAction} outline>
                Cancel
            </Button>
            <Button type="submit" className="filter-btn" onClick={handleAction}>
                Save
            </Button>
        </>
    );

    return (
        <FilterPanel footerAction={renderAction} header="Saved Search">
            <div className="save-search-container">
                <div className="input-field">
                    <label>Name of saved search</label>
                    <TextInput
                        className="field-space"
                        type="text"
                        name="localCode"
                        id="localCode"
                        style={{ border: '1px solid black' }}
                    />
                </div>
                <div className="input-field">
                    <label>Description</label>
                    <TextInput
                        className="field-space"
                        type="text"
                        name="localCode"
                        id="localCode"
                        style={{ border: '1px solid black' }}
                    />
                    {/* <TextArea className="field-space" name="localCode" id="localCode" />*/}
                </div>
                <div className="input-field">
                    <label>Who can view this queue?</label>
                    <Radio type="radio" name="duration" value="Onlyme" id="eAlways" label="Only me" />
                    <Radio id="eUntil" name="duration" value="Myjurisdiction" label="My jurisdiction" />
                    <Radio id="entireState" name="duration" value="EntireState" label="Entire state" />
                </div>
            </div>
        </FilterPanel>
    );
};
