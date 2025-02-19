import { ReactNode, useEffect, useMemo } from 'react';
import { DefaultValues, FieldValues, FormProvider, useForm } from 'react-hook-form';
import classNames from 'classnames';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { Icon } from 'design-system/icon';
import { AlertMessage } from 'design-system/message';
import { Column, DataTable } from 'design-system/table';
import { useMultiValueEntryState } from './useMultiValueEntryState';

import styles from './RepeatingBlock.module.scss';

type RepeatingBlockProps<V extends FieldValues> = {
    id: string;
    title: string;
    columns: Column<V>[];
    defaultValues?: DefaultValues<V>; // Provide all default values to allow `isDirty` to function
    errors?: ReactNode[];
    values?: V[];
    onChange: (data: V[]) => void;
    isDirty: (isDirty: boolean) => void;
    isValid?: (isValid: boolean) => void;
    formRenderer: (entry?: V) => ReactNode;
    viewRenderer: (entry: V) => ReactNode;
};

const RepeatingBlock = <V extends FieldValues>({
    id,
    title,
    defaultValues,
    values = [],
    columns,
    errors,
    onChange,
    isDirty,
    isValid,
    formRenderer,
    viewRenderer
}: RepeatingBlockProps<V>) => {
    const form = useForm<V>({ mode: 'onSubmit', reValidateMode: 'onBlur', defaultValues });
    const { status, entries, selected, add, edit, update, remove, view, reset } = useMultiValueEntryState<V>({
        values
    });

    useEffect(() => {
        onChange(entries);
    }, [JSON.stringify(entries)]);

    useEffect(() => {
        isDirty(form.formState.isDirty);

        if (!form.formState.isDirty) {
            // If a user clears the form, remove internal form validation errors
            form.clearErrors();
        }
    }, [form.formState.isDirty]);

    useEffect(() => {
        // Perform form reset after status update to allow time for rendering of form
        // fixes issue with coded values not being selected within the form
        if (status === 'editing') {
            form.reset(selected);
        }
    }, [status, selected, form.reset]);

    const handleReset = () => {
        form.reset(defaultValues);
        reset();
    };

    const handleClear = () => {
        form.reset(defaultValues);
    };

    const handleAdd = (value: V) => {
        // form reset must be triggered prior to `add` call,
        // otherwise internal form state retains some values and fails to properly reset
        form.reset(defaultValues);
        add(value);
    };

    const handleUpdate = (value: V) => {
        form.reset(defaultValues);
        update(value);
    };

    const handleRemove = (value: V) => {
        if ((status === 'editing' || status === 'viewing') && selected === value) {
            form.reset(defaultValues);
        }
        remove(value);
    };

    const iconColumn: Column<V> = {
        id: 'actions',
        name: '',
        render: (value: V) => (
            <div className={styles.actions}>
                <div data-tooltip-position="top" aria-label="View" onClick={() => view(value)}>
                    <Icon
                        name="visibility"
                        className={classNames({ [styles.active]: status === 'viewing' && value === selected })}
                    />
                </div>
                <div data-tooltip-position="top" aria-label="Edit" onClick={() => edit(value)}>
                    <Icon
                        name="edit"
                        className={classNames({ [styles.active]: status === 'editing' && value === selected })}
                    />
                </div>
                <div data-tooltip-position="top" aria-label="Delete" onClick={() => handleRemove(value)}>
                    <Icon name="delete" />
                </div>
            </div>
        )
    };

    // Combine error message prop and internal form error messages into an array for display in the banner
    const errorMessages = useMemo<ReactNode[]>(() => {
        const formErrorMessages = Object.values(form.formState.errors).map((error) => error?.message?.toString());
        const messages: ReactNode[] = [...(errors ?? []), ...formErrorMessages];

        return messages.filter((a) => a != undefined);
    }, [JSON.stringify(form.formState.errors), errors]);

    useEffect(() => {
        isValid?.(!errorMessages || errorMessages.length === 0);
    }, [errorMessages]);

    return (
        <section id={id} className={styles.input}>
            <header>
                <Heading level={2}>{title}</Heading>
                <span className="required-before">All required fields for adding {title.toLowerCase()}</span>
            </header>

            <Shown when={errorMessages && errorMessages.length > 0}>
                <AlertMessage title="Please fix the following errors:" type="error">
                    <ul className={styles.errorList}>
                        {errorMessages.map((e, i) => (
                            <li key={i}>{e}</li>
                        ))}
                    </ul>
                </AlertMessage>
            </Shown>
            <div>
                <Shown when={entries.length > 0}>
                    <DataTable<V>
                        className={styles.dataTable}
                        id={`${id}-data-table`}
                        columns={[...columns, iconColumn]}
                        data={entries}
                    />
                </Shown>
            </div>
            <Shown when={status === 'viewing'}>{selected && viewRenderer(selected)}</Shown>
            <Shown when={status !== 'viewing'}>
                <FormProvider {...form}>
                    <div className={classNames(styles.form, { [styles.changed]: form.formState.isDirty })}>
                        {formRenderer(selected)}
                    </div>
                </FormProvider>
            </Shown>
            <footer>
                <Shown when={status === 'adding'}>
                    <Button outline onClick={form.handleSubmit(handleAdd)}>
                        <Icon name="add" />
                        {`Add ${title.toLowerCase()}`}
                    </Button>
                    <Shown when={form.formState.isDirty}>
                        <Button outline aria-details={`clear ${title.toLowerCase()}`} onClick={handleClear}>
                            Clear
                        </Button>
                    </Shown>
                </Shown>
                <Shown when={status === 'editing'}>
                    <Button outline onClick={form.handleSubmit(handleUpdate)}>
                        <Icon name="add" />
                        {`Update ${title.toLowerCase()}`}
                    </Button>
                    <Button
                        outline
                        aria-details={`cancel editing current ${title.toLowerCase()}`}
                        onClick={handleReset}>
                        Cancel
                    </Button>
                </Shown>
                <Shown when={status === 'viewing'}>
                    <Button outline onClick={handleReset}>
                        <Icon name="add" />
                        {`Add ${title.toLowerCase()}`}
                    </Button>
                </Shown>
            </footer>
        </section>
    );
};

export { RepeatingBlock };
export type { RepeatingBlockProps };
