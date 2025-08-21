import { useEffect } from 'react';
import { FieldValues, UseFormReturn } from 'react-hook-form';
import { NavigationBlockInteraction, NavigationBlockSettings, useNavigationBlock } from './useNavigationBlock';
import { exists } from 'utils';

type FormNavigationBlockSettings<V extends FieldValues, C, D extends FieldValues | undefined = undefined> = {
    /** The form that controls the blocking of navigation  */
    form: UseFormReturn<V, C, D>;
} & NavigationBlockSettings;

/**
 *  An adapter hook for {@link useNavigationBlock} that applies the navigation block when form data
 *  has changed however, form submission allows navigation.
 *
 * @param {FormNavigationBlockSettings} props
 * @return {NavigationBlockInteraction} Functions to control navigation.
 */
const useFormNavigationBlock = <V extends FieldValues, C, D extends FieldValues | undefined = undefined>({
    form,
    ...remaining
}: FormNavigationBlockSettings<V, C, D>): NavigationBlockInteraction => {
    const blocker = useNavigationBlock(remaining);

    useEffect(() => {
        if (!form.formState.isSubmitting && exists(form.formState.dirtyFields)) {
            blocker.block();
        } else {
            blocker.allow();
        }
    }, [form.formState.isSubmitting, exists(form.formState.dirtyFields), blocker.allow, blocker.block]);

    return blocker;
};

export { useFormNavigationBlock };
