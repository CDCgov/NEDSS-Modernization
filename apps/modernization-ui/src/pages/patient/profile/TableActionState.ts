import { useState } from 'react';

type TableActionState<T> =
    | { type: 'add' }
    | {
          type: 'update' | 'delete' | 'detail';
          selected: T;
      };

const useTableActionState = <T>() => {
    const [action, setAction] = useState<TableActionState<T> | undefined>(undefined);

    const reset = () => setAction(undefined);

    const prepareForAdd = () => setAction({ type: 'add' });

    const selectForDetail = (selected: T) => setAction({ type: 'detail', selected });

    const selectForEdit = (selected: T) => setAction({ type: 'update', selected });

    const selectForDelete = (selected: T) => setAction({ type: 'delete', selected });

    return { action, reset, prepareForAdd, selectForDetail, selectForEdit, selectForDelete };
};

export { useTableActionState };
