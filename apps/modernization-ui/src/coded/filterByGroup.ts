import { GroupedCodedValue } from './CodedValue';

const filterByGroup = (items: GroupedCodedValue[], group: string | null) => {
    if (group && items.length >= 0) {
        return items.filter((item) => item.group === group);
    }
    return [];
};

export { filterByGroup };
