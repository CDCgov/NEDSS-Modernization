import { SearchResulstInteraction } from 'apps/search';
import { useCallback, useEffect } from 'react';
import { FieldValues, UseFormReturn } from 'react-hook-form';

type Interaction = {
    search: () => void;
    clear: () => void;
};

type Options<C extends FieldValues, R> = {
    form: UseFormReturn<C>;
    interaction: SearchResulstInteraction<C, R>;
};

const useSearchFormAdapter = <C extends FieldValues, R>({ form, interaction }: Options<C, R>): Interaction => {
    useEffect(() => {
        if (interaction.status === 'resetting') {
            form.reset();
        } else if (interaction.status === 'initializing') {
            form.reset(interaction.criteria, { keepDefaultValues: true });
        }
    }, [form.reset, interaction.status, interaction.criteria]);

    const search = useCallback(form.handleSubmit(interaction.search), [form.handleSubmit, interaction.search]);
    const clear = useCallback(() => {
        console.log('clearing from the form adapter');
        interaction.reset();
    }, [interaction.reset]);

    return { search, clear };
};

export { useSearchFormAdapter };
