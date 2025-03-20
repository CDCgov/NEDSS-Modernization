import { FieldValues, UseFormReturn } from 'react-hook-form';
import { NavigationBlock, NavigationBlockProps } from './block';
import { useNavigationBlock } from './useNavigationBlock';
import { useEffect } from 'react';
import { exists } from 'utils';

type FormNavigationBlockProps<V extends FieldValues, C, D extends FieldValues | undefined = undefined> = {
    form: UseFormReturn<V, C, D>;
} & NavigationBlockProps;

/**
 *  An adapter hook for {@link useNavigationBlock} that applies the navigation block when form data
 *  has changed however, form submission allows navigation.
 *
 * @param {FormNavigationBlockProps} props
 * @return {NavigationBlock} Functions to control navigation.
 */
const useFormNavigationBlock = <V extends FieldValues, C, D extends FieldValues | undefined = undefined>({
    form,
    ...remaining
}: FormNavigationBlockProps<V, C, D>): NavigationBlock => {
    const blocker = useNavigationBlock(remaining);

    useEffect(() => {
        if (!form.formState.isSubmitting && form.formState.isDirty && exists(form.formState.dirtyFields)) {
            // isDirty can sometimes be true without any dirtyFields present, only block when the form is dirty AND dirtyFields has keys.
            blocker.block();
        } else {
            blocker.allow();
        }
    }, [
        form.formState.isSubmitting,
        form.formState.isDirty,
        exists(form.formState.dirtyFields),
        blocker.allow,
        blocker.block
    ]);

    return blocker;
};

export { useFormNavigationBlock };
