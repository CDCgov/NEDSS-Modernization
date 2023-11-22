import { useEffect, useState } from 'react';
import { TextInput } from '@trussworks/react-uswds';
import { ToggleButton } from '../ToggleButton';
import './AddEditTab.scss';
import { PagesTab } from 'apps/page-builder/generated';

type Props = {
    tabData?: PagesTab;
    setTabDetails: (arg: { name: string; visible: boolean }) => void;
};

// Adding or Managing Tabs
// we are moving the adding/moving logic into EditPageTabs to make children 'dumber' components

export const AddEditTab = ({ tabData, setTabDetails }: Props) => {
    const [name, setName] = useState('');
    const [visible, setVisible] = useState<boolean>(true);

    useEffect(() => {
        if (tabData) {
            setName(tabData.name!);
            setVisible(tabData.visible!);
        }
    }, [tabData]);

    useEffect(() => {
        setTabDetails({ name: name, visible: visible });
    }, [name, visible]);

    return (
        <div className="add-edit-tab">
            <div>
                <label>Tab Name</label>
                <TextInput
                    className="field-space"
                    type="text"
                    id="tab-name"
                    data-testid="tab-name"
                    name="name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
            </div>
            <div className="add-edit-tab__toggle">
                <label> Not Visible</label>
                <ToggleButton
                    checked={visible}
                    name="visible"
                    onChange={() => {
                        setVisible(!visible);
                    }}
                />
                <label> Visible</label>
            </div>
        </div>
    );
};
