import { OverlayPanel } from 'overlay';
import { Button } from 'design-system/button';
import { Sizing } from 'design-system/field';
import { ColumnPreferencesPanel } from './ColumnPreferencesPanel';

import styles from './column-preference-action.module.scss';

type ColumnPreferenceActionProps = {
    sizing?: Sizing;
    position?: 'right' | 'left';
};

const ColumnPreferencesAction = ({ sizing, position = 'right' }: ColumnPreferenceActionProps) => {
    return (
        <OverlayPanel
            className={styles.preferences}
            position={position}
            toggle={({ toggle }) => (
                <Button
                    aria-label="Settings"
                    data-tooltip-position="top"
                    data-tooltip-offset="center"
                    secondary
                    icon="settings"
                    onClick={toggle}
                    sizing={sizing}
                />
            )}
            render={(close) => <ColumnPreferencesPanel close={close} />}
        />
    );
};

export { ColumnPreferencesAction };
