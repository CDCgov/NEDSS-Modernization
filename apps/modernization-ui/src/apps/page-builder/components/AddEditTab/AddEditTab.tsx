import { useEffect, useState } from 'react';
import { TextInput } from '@trussworks/react-uswds';
import { ToggleButton } from '../ToggleButton';
import './AddEditTab.scss';

type TabEntry = { name: string; visible: boolean; order: number };

type Props = {
    tabData: TabEntry;
    onChanged: (change: TabEntry) => void;
};

// Adding or Managing Tabs
// we are moving the adding/moving logic into EditPageTabs to make children 'dumber' components

export const AddEditTab = ({ tabData, onChanged }: Props) => {
    const [name, setName] = useState(tabData?.name);
    const [visible, setVisible] = useState<boolean>(tabData?.visible);

    useEffect(() => {
        onChanged({ ...tabData, name: name, visible: visible });
    }, [name, onChanged, tabData, visible]);

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
                <ToggleButton checked={visible} name="visible" onChange={() => setVisible((existing) => !existing)} />
                <label> Visible</label>
            </div>
        </div>
    );
};

export type { TabEntry };
