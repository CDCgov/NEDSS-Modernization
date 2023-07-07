import { useState } from 'react';

type Actions<T> = {
    reset: () => void;
    prepareForAdd: () => void;
    selectForDetail: (item: T) => void;
    selectForEdit: (item: T) => void;
    selectForDelete: (item: T) => void;
};

type Selection<T> =
    | { type: 'add' }
    | {
          type: 'update' | 'delete' | 'detail';
          item: T;
      };

type TableActions<T> = {
    selected: Selection<T> | undefined;
    actions: Actions<T>;
};

const useTableActionState = <T>(): TableActions<T> => {
    const [selected, setSelected] = useState<Selection<T> | undefined>(undefined);

    const actions = {
        reset: () => setSelected(undefined),
        prepareForAdd: () => setSelected({ type: 'add' }),

        selectForDetail: (item: T) => setSelected({ type: 'detail', item: item }),

        selectForEdit: (item: T) => setSelected({ type: 'update', item: item }),

        selectForDelete: (item: T) => setSelected({ type: 'delete', item: item })
    };

    return { selected, actions };
};

export { useTableActionState };
export type { TableActions, Actions, Selection };
