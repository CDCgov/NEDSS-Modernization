import { usePageNameOptionsAutocomplete } from 'apps/page-builder/options/page';
import { DateProperty, Property, ValueProperty } from 'filters/properties';
import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';
import { useConditionOptionsAutocomplete } from 'options/condition';
import { useUserOptionsAutocomplete } from 'options/users';

const statusOptions: Selectable[] = [
    { label: 'Draft', value: 'Draft', name: 'Draft' },
    { label: 'Published', value: 'Published', name: 'Published' },
    { label: 'Published with Draft', value: 'Published with Draft', name: 'Published with Draft' }
];

const startsWith = (criteria: string) => (option: Selectable) =>
    criteria ? option.name?.toLocaleLowerCase().startsWith(criteria) : true;

const completeByName = (options: Selectable[]) => (criteria: string) =>
    new Promise<Selectable[]>((resolve) => resolve(options.filter(startsWith(criteria.toLocaleLowerCase()))));

const usePageLibraryProperties = () => {
    const { complete: completePageName } = usePageNameOptionsAutocomplete();

    const name: ValueProperty = {
        value: 'name',
        name: 'Page name',
        type: 'value',
        complete: (criteria: string) => completePageName(criteria)
    };

    const { options: eventTypeOptions } = useConceptOptions('BUS_OBJ_TYPE', { lazy: false });

    const eventType: ValueProperty = {
        value: 'event-type',
        name: 'Event type',
        type: 'value',
        exactOnly: true,
        complete: completeByName(eventTypeOptions),
        all: eventTypeOptions
    };

    const { complete: completeConditions } = useConditionOptionsAutocomplete();

    const conditions: ValueProperty = {
        value: 'conditions',
        name: 'Related Condition(s)',
        type: 'value',
        complete: (criteria: string) => completeConditions(criteria)
    };

    const status: ValueProperty = {
        value: 'status',
        name: 'Status',
        type: 'value',
        exactOnly: true,
        complete: completeByName(statusOptions),
        all: statusOptions
    };

    const lastUpdated: DateProperty = {
        value: 'lastUpdate',
        name: 'Last updated',
        type: 'date'
    };

    const { complete: completeLastUpdatedBy } = useUserOptionsAutocomplete();

    const lastUpdatedBy: ValueProperty = {
        value: 'lastUpdatedBy',
        name: 'Last updated by',
        type: 'value',
        complete: (criteria: string) => completeLastUpdatedBy(criteria)
    };

    const properties: Property[] = [name, eventType, conditions, status, lastUpdated, lastUpdatedBy];

    return { properties };
};

export { usePageLibraryProperties };
