import { Path, UseFormReturn } from 'react-hook-form';
import { Term } from 'apps/search/terms';
import { Selectable } from 'options';

export function removeTerm<T extends Record<string, any>>(
    form: UseFormReturn<T>,
    term: Term,
    search: () => void,
    clear: () => void,
    terms: Term[]
) {
    const formValues = form.getValues();
    const fieldNames = Object.keys(formValues);
    const matchingField = fieldNames.find((fieldName) => fieldName === term.source);

    if (matchingField && terms.length > 1) {
        if (Array.isArray(formValues[matchingField] as Selectable[])) {
            form.setValue(
                matchingField as Path<T>,
                form.getValues()?.[matchingField].filter((p: Selectable) => p.value !== term.value) ?? []
            );
        } else {
            form.resetField(matchingField as Path<T>);
        }
        search();
    } else {
        clear();
    }
}
