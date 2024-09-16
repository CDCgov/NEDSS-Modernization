import { internalizeDate } from 'date';
import { AdministrativeEntryFields } from 'apps/patient/profile/administrative/AdministrativeEntryFields';
import { AdministrativeEntry } from 'apps/patient/profile/administrative/AdministrativeEntry';
import { Heading } from 'components/heading';
import styles from './Administrative.module.scss';
import { FormProvider, useForm } from 'react-hook-form';
import classNames from 'classnames';

const defaultValue: AdministrativeEntry = {
    asOf: internalizeDate(new Date()),
    comment: ''
};

type Props = {
    onChange: (data: AdministrativeEntry) => void;
};

export const Administrative = ({ onChange }: Props) => {
    const form = useForm<AdministrativeEntry>({
        mode: 'onBlur',
        defaultValues: defaultValue
    });

    form.watch((data) => {
        onChange({
            asOf: data.asOf,
            comment: data.comment
        });
    });

    return (
        <section id="administrative" className={styles.input}>
            <header>
                <Heading level={2}>Administrative</Heading>
            </header>
            <FormProvider {...form}>
                <div className={classNames(styles.form)}>
                    <AdministrativeEntryFields />
                </div>
            </FormProvider>
        </section>
    );
};
