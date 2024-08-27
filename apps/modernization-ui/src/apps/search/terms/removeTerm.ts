import { FieldValues, Path, UseFormReturn } from 'react-hook-form';
import { Term } from './terms';

const isSelectableNotMatching = (value: string) => (item: any) => 'value' in item && item.value !== value;

const doesNotEqual = (value: string) => (item: any) =>
    typeof item === 'string' ? value !== item : isSelectableNotMatching(value)(item);

const removeTerm =
    <C extends FieldValues>(form: UseFormReturn<C>, afterRemove: () => void) =>
    (term: Term) => {
        const formValues = form.getValues();

        const key = term.source as Path<C>;

        const value = formValues[key];

        if (Array.isArray(value)) {
            //  this will most likey be a Selectable
            const adjusted = value.filter(doesNotEqual(term.value));
            form.setValue(key, adjusted);
        } else {
            form.resetField(key);
        }

        afterRemove();
    };

export { removeTerm };
