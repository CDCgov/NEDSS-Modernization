import { Icon } from '@trussworks/react-uswds';
import classNames from 'classnames';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { AlertMessage } from 'design-system/message';
import { Column, DataTable } from 'design-system/table';
import { ReactNode, useEffect, useMemo } from 'react';
import { Control, DefaultValues, FieldValues, FormProvider, useForm, useFormState } from 'react-hook-form';
import styles from './RepeatingBlock.module.scss';
import { useMultiValueEntryState } from './useMultiValueEntryState';

type Props<V extends FieldValues> = {
    id?: string;
    title: string;
    columns: Column<V>[];
    defaultValues?: DefaultValues<V>; // Provide all default values to allow `isDirty` to function
    errors?: ReactNode[];
    onChange: (data: V[]) => void;
    isDirty: (isDirty: boolean) => void;
    formRenderer: (control: Control<V>) => ReactNode;
    viewRenderer: (entry: V) => ReactNode;
};
export const RepeatingBlock = <V extends FieldValues>({
    id,
    title,
    defaultValues,
    columns,
    errors,
    onChange,
    isDirty,
    formRenderer,
    viewRenderer
}: Props<V>) => {
    const form = useForm<V>({ mode: 'onSubmit', reValidateMode: 'onSubmit', defaultValues });
    const { errors: formErrors } = useFormState({ control: form.control });
    const { add, edit, update, remove, view, reset, state } = useMultiValueEntryState<V>();

    useEffect(() => {
        onChange(state.data);
    }, [JSON.stringify(state.data)]);

    useEffect(() => {
        isDirty(form.formState.isDirty);
    }, [form.formState.isDirty]);

    const handleReset = () => {
        form.reset();
        reset();
    };

    const handleSubmit = (value: V) => {
        // Submit button performs various actions based on the current state
        if (state.status === 'adding') {
            add(value);
            form.reset();
        } else if (state.status === 'editing') {
            update(state.index, value);
            form.reset();
        }
    };

    const handleRemove = (index: number) => {
        if ((state.status === 'editing' || state.status === 'viewing') && state.index === index) {
            form.reset();
        }
        remove(index);
    };

    const handleEdit = (index: number) => {
        edit(index);
    };

    useEffect(() => {
        // Perform form reset after status update to allow time for rendering of form
        // fixes issue with coded values not being selected within the form
        if ('editing' === state.status) {
            form.reset(state.data[state.index], { keepDefaultValues: true });
        }
    }, [state.status]);

    const iconColumn: Column<V> = {
        id: '',
        name: '',
        render: (entry: V, index: number) => (
            <div className={styles.iconContainer}>
                <div data-tooltip-position="top" aria-label="View">
                    <Icon.Visibility onClick={() => view(index)} />
                </div>
                <div data-tooltip-position="top" aria-label="Edit">
                    <Icon.Edit onClick={() => handleEdit(index)} />
                </div>
                <div data-tooltip-position="top" aria-label="Delete">
                    <Icon.Delete onClick={() => handleRemove(index)} />
                </div>
            </div>
        )
    };

    // Combine error message prop and internal form error messages into an array for display in the banner
    const errorMessages = useMemo<ReactNode[]>(() => {
        const formErrorMessages = Object.values(formErrors).map((error) => error?.message?.toString());
        const messages: ReactNode[] = [...(errors ?? []), ...formErrorMessages];

        return messages.filter((a) => a != undefined);
    }, [JSON.stringify(formErrors), errors]);

    // If a user clears the form, remove internal form validation errors
    useEffect(() => {
        if (!form.formState.isDirty) {
            form.clearErrors();
        }
    }, [form.formState.isDirty]);

    return (
        <section id={id} className={styles.input}>
            <header>
                <Heading level={2}>{title}</Heading>
                <span className="required-before">All required fields for adding {title.toLowerCase()}</span>
            </header>
            {errorMessages && errorMessages.length > 0 && (
                <section>
                    <AlertMessage title="Please fix the following errors:" type="error">
                        <ul className={styles.errorList}>
                            {errorMessages.map((e, i) => (
                                <li key={i}>{e}</li>
                            ))}
                        </ul>
                    </AlertMessage>
                </section>
            )}
            <div>
                {state.data.length > 0 && (
                    <DataTable<V>
                        className={styles.dataTable}
                        id={`${title}-data-table`}
                        columns={[...columns, iconColumn]}
                        data={state.data}
                    />
                )}
            </div>
            <FormProvider {...form}>
                {state.status === 'viewing' ? (
                    viewRenderer(state.data[state.index])
                ) : (
                    <div className={classNames(styles.form, form.formState.isDirty ? styles.changed : '')}>
                        {formRenderer(form.control)}
                    </div>
                )}
            </FormProvider>
            <footer>
                {(state.status === 'editing' || state.status === 'adding') && (
                    <Button outline onClick={form.handleSubmit(handleSubmit)}>
                        <Icon.Add />
                        {`${state.status === 'editing' ? 'Update' : 'Add'} ${title.toLowerCase()}`}
                    </Button>
                )}
                {state.status === 'viewing' && (
                    <Button outline onClick={handleReset}>
                        <Icon.Add />
                        {`Add ${title.toLowerCase()}`}
                    </Button>
                )}
            </footer>
        </section>
    );
};
