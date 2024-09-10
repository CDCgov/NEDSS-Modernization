import { Closable, ClosablePanel } from 'design-system/panel/closable';
import { SortingSelectable } from './selectable';
import { SortPreference } from './SortPreference';
import { useSortingPreferences } from './useSortingPreferences';

type Props = Closable;

const SortingPreferencesPanel = ({ onClose }: Props) => {
    const { available, sortOn, active } = useSortingPreferences();

    const isActive = (selectable: SortingSelectable) =>
        selectable.property === active?.property && selectable.direction === active?.direction;

    return (
        <ClosablePanel title="Sort list by" headingLevel={2} onClose={onClose}>
            {available.map((selectable, index) => (
                <SortPreference key={index} selectable={selectable} active={isActive(selectable)} onSelect={sortOn} />
            ))}
        </ClosablePanel>
    );
};

export { SortingPreferencesPanel };
