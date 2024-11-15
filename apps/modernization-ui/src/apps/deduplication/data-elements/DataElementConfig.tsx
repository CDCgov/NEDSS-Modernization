import { AlertProvider, useAlert } from 'alert';
import { Card } from 'apps/patient/add/extended/card/Card';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Icon } from 'design-system/icon';
import { useEffect } from 'react';
import { Controller, FormProvider, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useDataElements } from '../api/useDataElements';
import styles from './data-elements.module.scss';
import { DataElementsConfiguration } from './DataElement';
import { DataElementsForm } from './form/DataElementsForm';
import { TableNumericInput } from './form/TableNumericInput';
import { HowTo } from './HowTo';

export const DataElementConfig = () => {
    return (
        <AlertProvider>
            <DataElementConfigContent />
        </AlertProvider>
    );
};
const initial: DataElementsConfiguration = {
    belongingnessRatio: undefined,
    firstName: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    lastName: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    suffix: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    birthDate: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    mrn: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    ssn: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    sex: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    gender: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    race: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    address: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    city: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    state: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    zip: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    county: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined },
    telephone: { active: false, m: undefined, u: undefined, logOdds: undefined, threshold: undefined }
};

const DataElementConfigContent = () => {
    const { showSuccess, showError } = useAlert();
    const { configuration, save, error } = useDataElements();
    const form = useForm<DataElementsConfiguration>({ mode: 'onBlur', defaultValues: initial });
    const nav = useNavigate();

    useEffect(() => {
        form.reset(configuration, { keepDefaultValues: false, keepDirty: false });
    }, [configuration]);

    useEffect(() => {
        if (error) {
            showError({ message: error });
        }
    }, [error]);

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
                <Icon
                    onClick={() => nav({ pathname: '/deduplication/match-configuration' })}
                    name="arrow_back"
                    size="large"
                />
                <Heading level={1}>Data elements configuration</Heading>
            </div>
            <div className={styles.content}>
                <main>
                    <Card id="globalSettings" title="Global settings">
                        <div className={styles.belongingnessCard}>
                            <Controller
                                control={form.control}
                                name="belongingnessRatio"
                                rules={{
                                    required: { value: true, message: 'Belongingness ratio is required' }
                                }}
                                render={({ field: { onChange, onBlur, value, name }, fieldState: { error } }) => (
                                    <TableNumericInput
                                        label="Belongingness ratio"
                                        name={name}
                                        value={value}
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        error={error?.message}
                                        max={1}
                                        min={0}
                                        step={0.01}
                                    />
                                )}
                            />
                        </div>
                    </Card>
                    <FormProvider {...form}>
                        <DataElementsForm />
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
