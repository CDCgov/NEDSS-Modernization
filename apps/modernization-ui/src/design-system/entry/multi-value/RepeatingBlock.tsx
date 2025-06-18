import { ReactNode, useEffect, useMemo } from 'react';
import { DefaultValues, FieldValues, FormProvider, useForm } from 'react-hook-form';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Sizing } from 'design-system/field';
import { Card, CardProps } from 'design-system/card';
import { Icon } from 'design-system/icon';
import { Tag } from 'design-system/tag';
import { AlertMessage } from 'design-system/message';
import { Column, DataTable, DataTableFeatures } from 'design-system/table';
import { Required } from '../required/Required';
import { Entry, useMultiValueEntry } from './useMultiValueEntry';
import { entryIdentifierGenerator } from './entryIdentifierGenerator';
import { entryColumns } from './entryColumns';

import styles from './RepeatingBlock.module.scss';

type RepeatingBlockProps<V extends FieldValues> = {
    features?: DataTableFeatures;
    columns: Column<V>[];
    data?: V[];
    defaultValues?: DefaultValues<V>; // Provide all default values to allow `isDirty` to function
    errors?: ReactNode[];
    sizing?: Sizing;
    viewable?: boolean;
    editable?: boolean;
    onChange?: (data: V[]) => void;
    isDirty?: (isDirty: boolean) => void;
    isValid?: (isValid: boolean) => void;
    formRenderer: (entry?: V) => ReactNode;
    viewRenderer: (entry: V) => ReactNode;
} & Pick<CardProps, 'id' | 'title' | 'collapsible'>;

const RepeatingBlock = <V extends FieldValues>({
    id,
    title,
    collapsible,
    features,
    columns,
    data = [],
    defaultValues,
    errors,
    sizing,
    viewable = true,
    editable = true,
    onChange,
    isDirty,
    isValid,
    formRenderer,
    viewRenderer
}: RepeatingBlockProps<V>) => {
    const form = useForm<V>({ mode: 'onSubmit', reValidateMode: 'onBlur', defaultValues });

    const { status, entries, selected, using, add, edit, update, remove, view, reset } = useMultiValueEntry<V>({
        values: data,
        identifierGenerator: entryIdentifierGenerator,
        onChange
    });

    useEffect(() => {
        // if the data changes use the new values
        using(data);
    }, [JSON.stringify(data)]);

    useEffect(() => {
        isDirty?.(form.formState.isDirty);

        if (!form.formState.isDirty) {
            // If a user clears the form, remove internal form validation errors
            form.clearErrors();
        }
    }, [form.formState.isDirty]);

    useEffect(() => {
        // Perform form reset after status update to allow time for rendering of form
        // fixes issue with coded values not being selected within the form
        if (status === 'editing' && selected) {
            form.reset(selected.value);
        } else {
            // Conversely, if status is not editing, reset to default values to clear form between state changes
            form.reset(defaultValues);
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

    const handleRemove = (identifier: string) => {
        if ((status === 'editing' || status === 'viewing') && selected?.id === identifier) {
            form.reset(defaultValues);
        }
        remove(identifier);
    };

    const adjustedColumns = entryColumns(columns);

    const actions: Column<Entry<V>> = {
        id: 'actions',
        label: 'Actions',
        className: styles['action-header'],
        render: (entry: Entry<V>) => (
            <div className={styles.actions}>
                {viewable && (
                    <Button
                        tertiary
                        data-tooltip-position="top"
                        aria-label="View"
                        onClick={() => view(entry.id)}
                        className={classNames({
                            [styles.active]: status === 'viewing' && selected?.id === entry.id
                        })}
                        icon={<Icon name="visibility" />}
                    />
                )}
                {editable && (
                    <>
                        <Button
                            tertiary
                            data-tooltip-position="top"
                            aria-label="Edit"
                            onClick={() => edit(entry.id)}
                            className={classNames({
                                [styles.active]: status === 'editing' && selected?.id === entry.id
                            })}
                            icon={<Icon name="edit" />}
                        />
                        <Button
                            tertiary
                            data-tooltip-position="top"
                            aria-label="Delete"
                            onClick={() => handleRemove(entry.id)}
                            icon={<Icon name="delete" />}
                        />
                    </>
                )}
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

    const opened = collapsible ? entries.length > 0 : true;

    return (
        <Card
            id={id}
            title={title}
            collapsible={collapsible}
            sizing={sizing}
            flair={<Tag size={sizing}>{entries.length}</Tag>}
            className={classNames(styles.card)}
            info={editable && <Required />}
            open={opened}
            footer={
                <Shown when={editable}>
                    <span
                        className={classNames(styles.controls, {
                            [styles.small]: sizing === 'small',
                            [styles.medium]: sizing === 'medium',
                            [styles.large]: sizing === 'large'
                        })}>
                        <Shown when={status === 'adding'}>
                            <Button
                                secondary
                                icon={<Icon name="add" />}
                                sizing={sizing}
                                aria-description={`add ${title.toLowerCase()}`}
                                onClick={form.handleSubmit(handleAdd)}>
                                {`Add ${title.toLowerCase()}`}
                            </Button>
                            <Shown when={form.formState.isDirty || errorMessages.length !== 0}>
                                <Button
                                    secondary
                                    sizing={sizing}
                                    aria-description={`clear ${title.toLowerCase()}`}
                                    onClick={handleClear}
                                    onMouseDown={
                                        (e) => e.preventDefault() /* prevent need to double click after blur */
                                    }>
                                    Clear
                                </Button>
                            </Shown>
                        </Shown>
                        <Shown when={status === 'editing'}>
                            <Button
                                secondary
                                sizing={sizing}
                                aria-description={`update ${title.toLowerCase()}`}
                                onClick={form.handleSubmit(handleUpdate)}>
                                {`Update ${title.toLowerCase()}`}
                            </Button>
                            <Button
                                secondary
                                sizing={sizing}
                                aria-description={`cancel editing current ${title.toLowerCase()}`}
                                onClick={handleReset}>
                                Cancel
                            </Button>
                        </Shown>
                    </span>
                </Shown>
            }>
            <Shown when={errorMessages && errorMessages.length > 0}>
                <AlertMessage title="Please fix the following errors:" type="error">
                    <ul className={styles.errorList}>
                        {errorMessages.map((e, i) => (
                            <li key={i}>{e}</li>
                        ))}
                    </ul>
                </AlertMessage>
            </Shown>
            <DataTable<Entry<V>>
                className={classNames(styles.table, {
                    [styles.small]: sizing === 'small',
                    [styles.medium]: sizing === 'medium',
                    [styles.large]: sizing === 'large'
                })}
                id={`${id}-table`}
                columns={[...adjustedColumns, actions]}
                data={entries}
                sizing={sizing}
                features={features}
            />
            <Shown when={viewable && status === 'viewing'}>
                {selected && <div className={styles.view}>{viewRenderer(selected.value)}</div>}
            </Shown>

            <Shown when={editable && status !== 'viewing'}>
                <FormProvider {...form}>
                    <div className={classNames(styles.form, { [styles.changed]: form.formState.isDirty })}>
                        {formRenderer(selected?.value)}
                    </div>
                </FormProvider>
            </Shown>
        </Card>
    );
};

export { RepeatingBlock };
export type { RepeatingBlockProps };
