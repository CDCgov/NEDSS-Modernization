import { Actions } from './useTableActionState';

const tableActionStateAdapter =
    <T>(actions: Actions<T>, item: T) =>
    (type: string) => {
        if (type === 'edit') {
            actions.selectForEdit(item);
        } else if (type === 'delete') {
            actions.selectForDelete(item);
        } else if (type === 'details') {
            actions.selectForDetail(item);
        }
    };

export { tableActionStateAdapter };
