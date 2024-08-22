import { Icon } from '@trussworks/react-uswds';
import { AlertBanner } from 'alert';
import classNames from 'classnames';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Column, DataTable } from 'design-system/table';
import { ReactNode, useEffect } from 'react';
import { Control, DefaultValues, FieldValues, FormProvider } from 'react-hook-form';
import styles from './MultiValueEntry.module.scss';
import { useMultiValueEntryState } from './useMultiValueEntryState';

type Props<V extends FieldValues> = {
    title: string;
    columns: Column<V>[];
    defaultValues?: DefaultValues<V>; // Provide all default values to allow `isDirty` to function
    errors?: string[];
    onChange: (data: V[]) => void;
    isDirty: (isDirty: boolean) => void;
    formRenderer: (control: Control<V>) => ReactNode;
    viewRenderer: (entry: V) => ReactNode;
};
export const MultiValueEntry = <V extends FieldValues>({
    title,
    defaultValues,
    columns,
    errors,
    onChange,
    isDirty,
    formRenderer,
    viewRenderer
}: Props<V>) => {
    const { add, edit, update, remove, view, reset, state, form } = useMultiValueEntryState<V>(defaultValues);

    useEffect(() => {
        onChange(state.data);
    }, [JSON.stringify(state.data)]);

    useEffect(() => {
        isDirty(form.formState.isDirty);
    }, [form.formState.isDirty]);

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

    const iconColumn: Column<V> = {
        id: '',
        name: '',
        render: (entry: V, index: number) => (
            <div className={styles.iconContainer}>
                <div data-tooltip-position="top" aria-label="View">
                    <Icon.Visibility onClick={() => view(index)} />
                </div>
                <div data-tooltip-position="top" aria-label="Edit">
                    <Icon.Edit onClick={() => edit(index)} />
                </div>
                <div data-tooltip-position="top" aria-label="Delete">
                    <Icon.Delete onClick={() => remove(index)} />
                </div>
            </div>
        )
    };

    return (
        <section className={styles.input}>
            <header>
                <Heading level={2}>{title}</Heading>
                <span>
                    <span className="required"></span>All required fields for adding {title.toLowerCase()}
                </span>
            </header>
            {errors && errors.length > 0 && (
                <section>
                    <AlertBanner type="error">
                        <div className={styles.errors}>
                            <div className={styles.errorHeading}>Please fix the following errors: </div>
                            <ul>
                                {errors.map((e, i) => (
                                    <li key={i}>{e}</li>
                                ))}
                            </ul>
                        </div>
                    </AlertBanner>
                </section>
            )}

            <DataTable<V>
                className={styles.dataTable}
                id={`${title}-data-table`}
                columns={[...columns, iconColumn]}
                data={state.data}
            />
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
                    <Button outline disabled={!form.formState.isValid} onClick={form.handleSubmit(handleSubmit)}>
                        <Icon.Add />
                        {`${state.status === 'editing' ? 'Update' : 'Add'} ${title.toLowerCase()}`}
                    </Button>
                )}
                {state.status === 'viewing' && (
                    <Button outline disabled={!form.formState.isValid} onClick={reset}>
                        <Icon.Add />
                        {`Add ${title.toLowerCase()}`}
                    </Button>
                )}
            </footer>
        </section>
    );
};
