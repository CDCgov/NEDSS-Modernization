import { Closeable, CloseablePanel } from 'design-system/panel/closeable';
import { SortingSelectable } from './selectable';
import { useSorting } from 'sorting';
import { SortPreference } from './SortPreference';

type Props = { selection: SortingSelectable[] } & Closeable;

const SortingPreferencesPanel = ({ selection, onClose }: Props) => {
    const { sortBy, property, direction } = useSorting();

    const isActive = (selectable: SortingSelectable) =>
        selectable.property === property && selectable.direction === direction;

    const handleSelection = (selectable: SortingSelectable) => {
        sortBy(selectable.property, selectable.direction);
    };

    return (
        <CloseablePanel title="Sort by list" headingLevel={2} onClose={onClose}>
            {selection.map((selectable, index) => (
                <SortPreference
                    key={index}
                    selectable={selectable}
                    active={isActive(selectable)}
                    onSelect={handleSelection}
                />
            ))}
        </CloseablePanel>
    );
};

export { SortingPreferencesPanel };
