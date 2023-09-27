import React, { useContext, useState } from 'react';
import { Button, TextInput } from '@trussworks/react-uswds';
import { ToggleButton } from '../ToggleButton';
import './AddTab.scss';
import { TabControllerService } from 'apps/page-builder/generated';
import { UserContext } from 'user';
import { useParams } from 'react-router-dom';

type Props = {
    onAddTab: () => void;
    onCancel: () => void;
};

export const AddTab = ({ onAddTab, onCancel }: Props) => {
    const [tabDetails, setTabDetails] = useState({ name: '', desc: '', visible: true });
    const { state } = useContext(UserContext);
    const { pageId } = useParams();
    const token = `Bearer ${state.getToken()}`;

    const handleTabInput = ({ target }: any) => {
        setTabDetails({
            ...tabDetails,
            [target.name]: target?.type === 'checkbox' ? target?.checked : target.value
        });
    };

    const handleAddTab = async () => {
        if (pageId) {
            try {
                await TabControllerService.createTabUsingPost({
                    page: parseInt(pageId),
                    authorization: token,
                    request: {
                        name: tabDetails.name,
                        description: tabDetails.desc,
                        visible: tabDetails.visible
                    }
                });
                onAddTab();
            } catch (e) {
                console.error(e);
            }
        }
    };

    const renderTabForm = (
        <div className="form-container margin-top-1em">
            <div>
                <label>Tab Name</label>
                <TextInput
                    className="field-space"
                    type="text"
                    id="tab-name"
                    data-testid="tab-name"
                    name="name"
                    value={tabDetails.name}
                    onChange={handleTabInput}
                />
            </div>
            <div>
                <label>Tab Description</label>
                <TextInput
                    className="field-space"
                    type="text"
                    id="tab-description"
                    data-testid="tab-description"
                    name="desc"
                    value={tabDetails.desc}
                    onChange={handleTabInput}
                />
            </div>
            <div className="visible-toggle-container">
                <label> Not Visible</label>
                <ToggleButton checked={tabDetails.visible} name="visible" onChange={handleTabInput} />
                <label> Visible</label>
            </div>
            <div className="margin-bottom-1em add-tab-modal ds-u-text-align--right ">
                <Button className="submit-btn" disabled={!tabDetails.name} onClick={handleAddTab} type="button">
                    Add tab
                </Button>
                <Button type="button" onClick={onCancel} className="cancel-btn">
                    Cancel
                </Button>
            </div>
        </div>
    );

    return <div className="add-valueset add-tab">{renderTabForm}</div>;
};
