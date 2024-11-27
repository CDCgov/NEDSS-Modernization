import { FieldValues, Path, PathValue, UseFormReturn } from 'react-hook-form';
import { Term } from './terms';
import { selectField } from 'utils/util';
import { removeAndTrim } from 'utils';

const isSelectableNotMatching = (value: string) => (item: any) => 'value' in item && item.value !== value;

const doesNotEqual = (value: string) => (item: any) =>
    typeof item === 'string' ? value !== item : isSelectableNotMatching(value)(item);

const removeTerm =
    <C extends FieldValues>(form: UseFormReturn<C>, afterRemove: () => void) =>
    (term: Term) => {
        const formValues = form.getValues();

        const key = term.source as Path<C>;

        const value = selectField(formValues, key) as PathValue<C, Path<C>>;

        if (Array.isArray(value)) {
            // this will most likely be a Selectable
            const adjusted = value.filter(doesNotEqual(term.value));
            form.setValue(key, adjusted);
        } else if (term.partial && typeof value === 'string') {
            // extract and remove term from string: "123, 456" -> remove 123 and surrounding delimiters -> "456"
            const adjusted = removeAndTrim(value as string, term.value);
            form.setValue(key, adjusted as PathValue<C, Path<C>>);
        } else {
            form.resetField(key);
        }

        afterRemove();
    };

export { removeTerm };
