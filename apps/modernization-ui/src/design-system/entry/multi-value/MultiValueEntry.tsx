import { Icon } from '@trussworks/react-uswds';
import { AlertBanner } from 'alert';
import classNames from 'classnames';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Column, DataTable } from 'design-system/table';
import { ReactNode, useEffect, useState } from 'react';
import { Control, DefaultValues, FieldValues, FormProvider, useForm } from 'react-hook-form';
import styles from './MultiValueEntry.module.scss';

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
    const form = useForm<V>({ mode: 'onBlur', defaultValues });
    const [data, setData] = useState<V[]>([]);
    const [state, setState] = useState<'view' | 'edit' | 'new'>('new');
    const [activeIndex, setActiveIndex] = useState<number | undefined>();

    useEffect(() => {
        onChange(data);
    }, [data]);

    useEffect(() => {
        isDirty(form.formState.isDirty);
    }, [form.formState.isDirty]);

    const handleSubmit = (value: V) => {
        // Submit button performs various actions based on the current state
        if (state === 'new') {
            setData((current) => [...current, value]);
        } else if (state === 'edit' && activeIndex !== undefined) {
            setData((current) => {
                const newValue = [...current];
                newValue[activeIndex] = value;
                return newValue;
            });
        }
        handleReset();
    };

    const handleView = (entry: V, index: number) => {
        form.reset(entry, { keepDefaultValues: true });
        setActiveIndex(index);
        setState('view');
    };

    const handleEdit = (entry: V, index: number) => {
        form.reset(entry, { keepDefaultValues: true });
        setActiveIndex(index);
        setState('edit');
    };

    const handleDelete = (index: number) => {
        setData((current) => {
            const updated = [...current];
            updated.splice(index, 1);
            return updated;
        });
        // If currently editing the entry to be deleted, reset
        if (state !== 'new' && activeIndex === index) {
            handleReset();
        }
    };

    const handleReset = () => {
        form.reset();
        setActiveIndex(undefined);
        setState('new');
    };

    const iconColumn: Column<V> = {
        id: '',
        name: '',
        render: (entry: V, index: number) => (
            <div className={styles.iconContainer}>
                <div data-tooltip-position="top" aria-label="View">
                    <Icon.Visibility onClick={() => handleView(entry, index)} />
                </div>
                <div data-tooltip-position="top" aria-label="Edit">
                    <Icon.Edit onClick={() => handleEdit(entry, index)} />
                </div>
                <div data-tooltip-position="top" aria-label="Delete">
                    <Icon.Delete onClick={() => handleDelete(index)} />
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
                data={data}
            />
            <FormProvider {...form}>
                {state === 'view' && activeIndex !== undefined ? (
                    viewRenderer(data[activeIndex])
                ) : (
                    <div className={classNames(styles.form, form.formState.isDirty ? styles.changed : '')}>
                        {formRenderer(form.control)}
                    </div>
                )}
            </FormProvider>
            <footer>
                {(state === 'edit' || state === 'new') && (
                    <Button outline disabled={!form.formState.isValid} onClick={form.handleSubmit(handleSubmit)}>
                        <Icon.Add />
                        {`${state === 'edit' ? 'Update' : 'Add'} ${title.toLowerCase()}`}
                    </Button>
                )}
                {state === 'view' && (
                    <Button outline disabled={!form.formState.isValid} onClick={handleReset}>
                        <Icon.Add />
                        {`Add ${title.toLowerCase()}`}
                    </Button>
                )}
            </footer>
        </section>
    );
};
