import { useState } from 'react';

const useSelectionModel = <I>() => {
    const [selected, setSelected] = useState<I[]>([]);

    const reset = () => setSelected([]);

    const select = (item: I) => setSelected((existing) => [...existing, item]);

    const deselect = (item: I) => setSelected(without(item));

    return {
        selected,
        reset,
        select,
        deselect
    };
};

const without =
    <I>(item: I) =>
    (items: I[]) => {
        const index = items.indexOf(item);
        if (index !== -1) {
            return items.splice(index, 1);
        } else {
            return items;
        }
    };

export { useSelectionModel };
