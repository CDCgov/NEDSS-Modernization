import { AlertProvider, useAlert } from 'alert';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Icon } from 'design-system/icon';
import { useEffect } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useDataElements } from '../api/useDataElements';
import defaultDataElements from '../data-elements/constants/defaultDataElements';
import { DataElements } from './types/DataElement';
import DataElementForm from './form/DataElementForm';
import { HowTo } from './HowTo';
import styles from './data-elements.module.scss';

export const DataElementConfig = () => {
    return (
        <AlertProvider>
            <DataElementConfigContent />
        </AlertProvider>
    );
};

const DataElementConfigContent = () => {
    const { showSuccess, showError } = useAlert();
    const { configuration, save, error } = useDataElements();
    const form = useForm<DataElements>({ mode: 'onBlur', defaultValues: defaultDataElements });
    const nav = useNavigate();

    useEffect(() => {
        form.reset(configuration ?? defaultDataElements);
    }, [configuration, form]); // Keeps default values synced

    useEffect(() => {
        if (error) {
            showError({ message: error });
        }
    }, [error, showError]);

    const handleCancel = () => {
        nav({ pathname: '/deduplication/match-configuration' });
    };

    const handleSubmit = () => {
        save(form.getValues(), () =>
            showSuccess({ message: 'You have successfully updated the data elements configuration.' })
        );
    };

    return (
        <div className={styles.dataElements}>
            <div className={styles.heading}>
                <Icon onClick={handleCancel} name="arrow_back" size="large" />
                <Heading level={1}>Data elements configuration</Heading>
            </div>
            <div className={styles.content}>
                <main>
                    <FormProvider {...form}>
                        <DataElementForm />
                    </FormProvider>
                </main>
                <HowTo />
            </div>
            <div className={styles.buttonBar}>
                <Button outline onClick={handleCancel}>
                    Cancel
                </Button>
                <Button onClick={handleSubmit} disabled={!form.formState.isDirty || !form.formState.isValid}>
                    Save data elements configuration
                </Button>
            </div>
        </div>
    );
};
