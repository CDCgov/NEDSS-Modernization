import { Button, Icon } from '@trussworks/react-uswds';
import { useState } from 'react';
import { FilterEntryForm } from 'filters/entry/FilterEntryForm';
import { FilterEntry } from 'filters/entry/FilterEntry';
import { Filter } from 'filters';
import { AppliedFilters } from 'filters/applied/AppliedFilters';
import styles from './filter-panel.module.scss';
import { Property } from 'filters/properties';
import { asFilter } from 'filters/asDisplayableFilter';

type State = 'view' | 'add';

type FilterPanelProps = {
    label: string;
    properties: Property[];
    filters: Filter[];
    close: () => void;
    onApply: (filters: Filter[]) => void;
};

const FilterPanel = ({ label, properties, filters, onApply, close }: FilterPanelProps) => {
    const asDisplayable = asFilter(properties);

    const [state, setState] = useState<State>('view');

    const [displayable, setDisplayable] = useState<Filter[]>(filters);

    const handleAddNew = () => setState('add');

    const handleAddFilter = (entry: FilterEntry) => {
        const filter = asDisplayable(entry);

        if (filter) {
            setDisplayable((existing) => [...existing, filter]);
        }

        setState('view');
    };

    const handleEntryCancel = () => setState('view');

    const handlefilterRemove = (filter: string) => {
        setDisplayable((existing) => existing.filter((item) => item.id !== filter));
    };

    const handleApply = () => {
        close();
        onApply(displayable);
    };

    return (
        <div className={styles.filters}>
            <header>
                <label>Filter</label>
                <Icon.Close size={3} onClick={close} />
            </header>
            <section>
                <AppliedFilters label={label} filters={displayable} onRemove={handlefilterRemove} />
                {state === 'view' && <AddNewFilter onAddNew={handleAddNew} />}
            </section>
            {state === 'add' && (
                <FilterEntryForm properties={properties} onSave={handleAddFilter} onCancel={handleEntryCancel} />
            )}
            <footer>
                <Button type="button" onClick={handleApply}>
                    Apply
                </Button>
            </footer>
        </div>
    );
};

type AddNewFilterProp = {
    onAddNew: () => void;
};

const AddNewFilter = ({ onAddNew }: AddNewFilterProp) => {
    return (
        <Button className={styles.addNew} type="button" unstyled onClick={onAddNew}>
            <Icon.Add />
            Add Filter
        </Button>
    );
};

export { FilterPanel };
