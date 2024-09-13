import { AlertBanner } from 'alert';
import classNames from 'classnames';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { ReactNode, useEffect } from 'react';
import { Control, DefaultValues, FieldValues, FormProvider, useForm } from 'react-hook-form';
import { useSelectValueEntryState } from './useSelectValueEntryState';
import styles from './SingleValueEntry.module.scss';

type Props<V extends FieldValues> = {
    id?: string;
    title: string;
    defaultValues?: DefaultValues<V>;
    errors?: string[];
    onChange: (data: V) => void;
    isDirty: (isDirty: boolean) => void;
    formRenderer: (control: Control<V>) => ReactNode;
    isFooter?: boolean;
};

export const SingleValueEntry = <V extends FieldValues>({
    id,
    title,
    defaultValues,
    errors,
    onChange,
    isDirty,
    formRenderer,
    isFooter
}: Props<V>) => {
    const form = useForm<V>({ mode: 'onBlur', defaultValues });
    const { update, reset, state } = useSelectValueEntryState<V>();

    useEffect(() => {
        if (state.data) {
            onChange(state.data);
        }
    }, [state.data]);

    useEffect(() => {
        isDirty(form.formState.isDirty);
    }, [form.formState.isDirty]);

    const handleReset = () => {
        form.reset();
        reset();
    };

    const handleSubmit = (value: V) => {
        update(value);
        form.reset(value, { keepDefaultValues: true });
    };

    return (
        <section id={id} className={styles.input}>
            <header>
                <Heading level={2}>{title}</Heading>
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
            <FormProvider {...form}>
                <div className={classNames(styles.form, form.formState.isDirty ? styles.changed : '')}>
                    {formRenderer(form.control)}
                </div>
            </FormProvider>
            {isFooter && (
                <footer>
                    <Button outline disabled={!form.formState.isValid} onClick={form.handleSubmit(handleSubmit)}>
                        {`Save ${title.toLowerCase()}`}
                    </Button>
                    <Button outline onClick={handleReset}>
                        Reset
                    </Button>
                </footer>
            )}
        </section>
    );
};
