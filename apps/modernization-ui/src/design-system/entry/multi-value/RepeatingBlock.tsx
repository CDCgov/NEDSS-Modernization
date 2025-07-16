import { ReactNode, useEffect, useMemo } from 'react';
import { DefaultValues, FieldValues, FormProvider, useForm, UseFormReturn } from 'react-hook-form';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Sizing } from 'design-system/field';
import { Card, CardProps } from 'design-system/card';
import { Tooltip } from 'design-system/tool-tip';
import { Icon } from 'design-system/icon';
import { Tag } from 'design-system/tag';
import { AlertMessage } from 'design-system/message';
import { Column, DataTable, DataTableFeatures } from 'design-system/table';
import { Required } from '../required/Required';
import { Entry, MultiValueEntryInteraction, useMultiValueEntry } from './useMultiValueEntry';
import { entryIdentifierGenerator } from './entryIdentifierGenerator';
import { entryColumns } from './entryColumns';

import styles from './RepeatingBlock.module.scss';

type RepeatingBlockProps<V extends FieldValues> = {
    features?: DataTableFeatures;
    columns: Column<V>[];
    data?: V[];
    sizing?: Sizing;
    // view properties
    viewable?: boolean;
    viewRenderer: (entry: V, sizing?: Sizing) => ReactNode;
    // edit properties
    editable?: boolean;
    defaultValues?: DefaultValues<V>; // Provide all default values to allow `isDirty` to function
    errors?: ReactNode[];
    onChange?: (data: V[]) => void;
    isDirty?: (isDirty: boolean) => void;
    isValid?: (isValid: boolean) => void;
    formRenderer: (entry?: V, sizing?: Sizing) => ReactNode;
} & Pick<CardProps, 'id' | 'title' | 'collapsible'>;

const RepeatingBlock = <V extends FieldValues>({
    id,
    title,
    collapsible,
    features,
    columns,
    data = [],
    defaultValues,
    errors = [],
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

    const interaction = useMultiValueEntry<V>({
        values: data,
        identifierGenerator: entryIdentifierGenerator,
        onChange
    });

    useEffect(() => {
        // if the data changes use the new values
        interaction.using(data);
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
        if (interaction.status === 'editing' && interaction.selected) {
            form.reset(interaction.selected.value);
        } else {
            // Conversely, if status is not editing, reset to default values to clear form between state changes
            form.reset(defaultValues);
        }
    }, [interaction.status, interaction.selected?.value, form.reset]);

    const adjustedColumns = entryColumns(columns);

    const modifiedColumns =
        viewable || editable
            ? [
                  ...adjustedColumns,
                  {
                      id: 'actions',
                      label: 'Actions',
                      className: styles['action-header'],
                      render: (entry: Entry<V>) => (
                          <EntryActionColumn<V>
                              entry={entry}
                              sizing={sizing}
                              interaction={interaction}
                              viewable={viewable}
                              editable={editable}
                              form={form}
                              defaultValues={defaultValues}
                          />
                      )
                  }
              ]
            : adjustedColumns;

    // Combine error message prop and internal form error messages into an array for display in the banner
    const errorMessages = useMemo<ReactNode[]>(() => {
        const formErrorMessages = Object.values(form.formState.errors).map((error) => error?.message?.toString());
        const messages: ReactNode[] = [...errors, ...formErrorMessages];

        return messages.filter((a) => a != undefined);
    }, [JSON.stringify(form.formState.errors), errors]);

    useEffect(() => {
        isValid?.(errorMessages.length === 0);
    }, [errorMessages.length]);

    const opened = collapsible ? interaction.entries.length > 0 : true;

    return (
        <Card
            id={id}
            title={title}
            collapsible={collapsible}
            sizing={sizing}
            flair={<Tag size={sizing}>{interaction.entries.length}</Tag>}
            className={classNames(styles.card)}
            info={editable && <Required />}
            open={opened}
            footer={
                <Shown when={editable && interaction.status !== 'viewing'}>
                    <EditFooter
                        title={title}
                        sizing={sizing}
                        form={form}
                        interaction={interaction}
                        defaultValues={defaultValues}
                        clearable={form.formState.isDirty || errorMessages.length !== 0}
                    />
                </Shown>
            }>
            <Shown when={errorMessages.length > 0}>
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
                columns={modifiedColumns}
                data={interaction.entries}
                sizing={sizing}
                features={features}
            />
            <Shown when={viewable && interaction.status === 'viewing'}>
                {interaction.selected && (
                    <div className={styles.view}>{viewRenderer(interaction.selected.value, sizing)}</div>
                )}
            </Shown>

            <Shown when={editable && interaction.status !== 'viewing'}>
                <FormProvider {...form}>
                    <div className={classNames(styles.form, { [styles.changed]: form.formState.isDirty })}>
                        {formRenderer(interaction.selected?.value, sizing)}
                    </div>
                </FormProvider>
            </Shown>
        </Card>
    );
};

export { RepeatingBlock };
export type { RepeatingBlockProps };

type EntryActionColumnProps<T extends FieldValues> = {
    entry: Entry<T>;
    form: UseFormReturn<T>;
    interaction: MultiValueEntryInteraction<T>;
    viewable: boolean;
    editable: boolean;
    defaultValues?: DefaultValues<T>;
    sizing?: Sizing;
};

const EntryActionColumn = <E extends FieldValues>({
    entry,
    form,
    defaultValues,
    interaction,
    viewable,
    editable,
    sizing
}: EntryActionColumnProps<E>) => {
    const handleRemove = (identifier: string) => {
        if (interaction.selected?.id === identifier) {
            // the entry being removed is the one currently selected, reset the form.
            form.reset(defaultValues);
        }
        interaction.remove(identifier);
    };

    return (
        <ActionColumn
            sizing={sizing}
            viewable={viewable}
            onView={() => interaction.view(entry.id)}
            isViewing={interaction.status === 'viewing' && interaction.selected?.id === entry.id}
            editable={editable}
            onEdit={() => interaction.edit(entry.id)}
            isEditing={interaction.status === 'editing' && interaction.selected?.id === entry.id}
            onRemove={() => handleRemove(entry.id)}
        />
    );
};

type ActionColumnProps = {
    sizing?: Sizing;
    viewable: boolean;
    onView: () => void;
    isViewing: boolean;
    editable: boolean;
    onEdit: () => void;
    isEditing: boolean;
    onRemove: () => void;
};

const ActionColumn = ({
    sizing,
    viewable,
    onView,
    isViewing,
    editable,
    onEdit,
    isEditing,
    onRemove
}: ActionColumnProps) => {
    return (
        <div
            className={classNames(styles.actions, {
                [styles.small]: sizing === 'small',
                [styles.medium]: sizing === 'medium',
                [styles.large]: sizing === 'large'
            })}>
            <Shown when={viewable}>
                <Tooltip message="View">
                    {(id) => (
                        <Button
                            className={styles.action}
                            aria-labelledby={id}
                            tertiary
                            onClick={onView}
                            aria-pressed={isViewing}
                            icon={<Icon name="visibility" />}
                        />
                    )}
                </Tooltip>
            </Shown>
            <Shown when={editable}>
                <Tooltip message="Edit">
                    {(id) => (
                        <Button
                            className={styles.action}
                            aria-labelledby={id}
                            tertiary
                            onClick={onEdit}
                            aria-pressed={isEditing}
                            icon={<Icon name="edit" />}
                        />
                    )}
                </Tooltip>
                <Tooltip message="Delete">
                    {(id) => (
                        <Button
                            className={styles.action}
                            aria-labelledby={id}
                            tertiary
                            onClick={onRemove}
                            icon={<Icon name="delete" />}
                        />
                    )}
                </Tooltip>
            </Shown>
        </div>
    );
};

type EditFooterProps<T extends FieldValues> = {
    form: UseFormReturn<T>;
    interaction: MultiValueEntryInteraction<T>;
    title: string;
    clearable: boolean;
    defaultValues?: DefaultValues<T>;
    sizing?: Sizing;
};

const EditFooter = <E extends FieldValues>({
    form,
    interaction,
    title,
    clearable,
    defaultValues,
    sizing
}: EditFooterProps<E>) => {
    const handleClear = () => {
        form.reset(defaultValues);
    };

    const handleAdd = (value: E) => {
        // form reset must be triggered prior to `add` call,
        // otherwise internal form state retains some values and fails to properly reset
        form.reset(defaultValues);
        interaction.add(value);
    };

    const handleUpdate = (value: E) => {
        // set the form values to the updated values sync the pending state
        form.reset(defaultValues);
        interaction.update(value);
    };

    return (
        <span
            className={classNames(styles.controls, {
                [styles.small]: sizing === 'small',
                [styles.medium]: sizing === 'medium',
                [styles.large]: sizing === 'large'
            })}>
            <Shown when={interaction.status === 'adding'}>
                <Button
                    secondary
                    icon={<Icon name="add" />}
                    sizing={sizing}
                    aria-description={`add ${title}`}
                    onClick={form.handleSubmit(handleAdd)}>
                    {`Add ${title.toLowerCase()}`}
                </Button>
                <Shown when={clearable}>
                    <Button
                        secondary
                        sizing={sizing}
                        aria-description={`clear ${title}`}
                        onClick={handleClear}
                        onMouseDown={(e) => e.preventDefault()}>
                        Clear
                    </Button>
                </Shown>
            </Shown>
            <Shown when={interaction.status === 'editing'}>
                <Button
                    secondary
                    sizing={sizing}
                    aria-description={`update ${title}`}
                    onClick={form.handleSubmit(handleUpdate)}>
                    {`Update ${title.toLowerCase()}`}
                </Button>
                <Button
                    secondary
                    sizing={sizing}
                    aria-description={`cancel editing current ${title}`}
                    onClick={interaction.reset}>
                    Cancel
                </Button>
            </Shown>
        </span>
    );
};
