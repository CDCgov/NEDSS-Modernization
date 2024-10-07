import { ReactNode, useEffect } from 'react';
import { DefaultValues, FieldValues, FormProvider, useForm } from 'react-hook-form';
import classNames from 'classnames';
import { AlertBanner } from 'alert';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { Column, DataTable } from 'design-system/table';
import { Icon } from 'design-system/icon';
import { useMultiValueEntryState } from './useMultiValueEntryState';

import styles from './RepeatingBlock.module.scss';

type Props<V extends FieldValues> = {
    id: string;
    title: string;
    columns: Column<V>[];
    defaultValues?: DefaultValues<V>; // Provide all default values to allow `isDirty` to function
    values?: V[];
    errors?: string[];
    onChange: (data: V[]) => void;
    isDirty: (isDirty: boolean) => void;
    formRenderer: () => ReactNode;
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
    formRenderer,
    viewRenderer
}: Props<V>) => {
    const form = useForm<V>({ defaultValues });
    const { status, selected, add, edit, update, remove, view, reset, state } = useMultiValueEntryState<V>({ values });

    useEffect(() => {
        onChange(state.data);
    }, [JSON.stringify(state.data)]);

    useEffect(() => {
        isDirty(form.formState.isDirty);
    }, [form.formState.isDirty]);

    const handleReset = () => {
        form.reset(defaultValues);
        reset();
    };

    const handleSubmit = (value: V) => {
        // Submit button performs various actions based on the current state
        if (state.status === 'adding') {
            add(value);
            form.reset(defaultValues);
        } else if (status === 'editing') {
            update(state.index, value);
            form.reset(defaultValues);
        }
    };

    const handleRemove = (index: number) => {
        if ((state.status === 'editing' || state.status === 'viewing') && state.index === index) {
            form.reset(defaultValues);
        }
        remove(index);
    };

    const handleEdit = (index: number) => {
        edit(index);
    };

    useEffect(() => {
        // Perform form reset after status update to allow time for rendering of form
        // fixes issue with coded values not being selected within the form
        if ('editing' === status) {
            form.reset(selected);
        }
    }, [status]);

    const iconColumn: Column<V> = {
        id: 'actions',
        name: '',
        render: (_entry: V, index: number) => (
            <div className={styles.iconContainer}>
                <div data-tooltip-position="top" aria-label="View">
                    <Icon name="visibility" onClick={() => view(index)} />
                </div>
                <div data-tooltip-position="top" aria-label="Edit">
                    <Icon name="edit" onClick={() => handleEdit(index)} />
                </div>
                <div data-tooltip-position="top" aria-label="Delete">
                    <Icon name="delete" onClick={() => handleRemove(index)} />
                </div>
            </div>
        )
    };

    return (
        <section id={id} className={styles.input}>
            <header>
                <Heading level={2}>{title}</Heading>
                <span className="required-before">All required fields for adding {title.toLowerCase()}</span>
            </header>
            <Shown when={errors && errors.length > 0}>
                <section>
                    <AlertBanner type="error">
                        <div className={styles.errors}>
                            <div className={styles.errorHeading}>Please fix the following errors: </div>
                            <ul>{errors?.map((e, i) => <li key={i}>{e}</li>)}</ul>
                        </div>
                    </AlertBanner>
                </section>
            </Shown>
            <div>
                <Shown when={state.data.length > 0}>
                    <DataTable<V>
                        className={styles.dataTable}
                        id={`${id}-data-table`}
                        columns={[...columns, iconColumn]}
                        data={state.data}
                    />
                </Shown>
            </div>
            <Shown when={status === 'viewing'}>{selected && viewRenderer(selected)}</Shown>
            <Shown when={status !== 'viewing'}>
                <FormProvider {...form}>
                    <div className={classNames(styles.form, { [styles.changed]: form.formState.isDirty })}>
                        {formRenderer()}
                    </div>
                </FormProvider>
            </Shown>
            <footer>
                <Shown when={status === 'editing' || status === 'adding'}>
                    <Button outline disabled={!form.formState.isValid} onClick={form.handleSubmit(handleSubmit)}>
                        <Icon name="add" />
                        {`${status === 'editing' ? 'Update' : 'Add'} ${title.toLowerCase()}`}
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
