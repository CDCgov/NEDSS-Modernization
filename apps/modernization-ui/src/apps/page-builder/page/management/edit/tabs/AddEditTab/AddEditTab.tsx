import { useEffect, useState } from 'react';
import { TextInput } from '@trussworks/react-uswds';
import { ToggleButton } from '../../../../../components/ToggleButton';
import { PagesTab } from 'apps/page-builder/generated';
import styles from './addedittab.module.scss';

type TabEntry = { name: string | undefined; visible: boolean; order: number };

type Props = {
    tabData?: PagesTab | null;
    onChanged: (change: TabEntry) => void;
};

export const AddEditTab = ({ tabData, onChanged }: Props) => {
    const [name, setName] = useState<string>(tabData?.name ?? '');
    const [visible, setVisible] = useState<boolean>(tabData?.visible ?? true);

    useEffect(() => {
        onChanged({ name: name, visible: visible, order: 0 });
    }, [name, onChanged, tabData, visible]);

    return (
        <div className={styles.addEditTab}>
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
            <div className={styles.toggle}>
                <label> Not Visible</label>
                <ToggleButton checked={visible} name="visible" onChange={() => setVisible((existing) => !existing)} />
                <label> Visible</label>
            </div>
        </div>
    );
};

export type { TabEntry };
