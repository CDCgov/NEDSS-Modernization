import { Card } from 'apps/patient/add/extended/card/Card';
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
    const { dataElements } = useDataElements();
    const form = useForm<DataElementsConfiguration>({ mode: 'onBlur' });
    const nav = useNavigate();

    useEffect(() => {
        form.reset(dataElements, { keepDefaultValues: false });
    }, [dataElements]);

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
        </div>
    );
};
