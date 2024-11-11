import { Card } from 'apps/patient/add/extended/card/Card';
import { Input } from 'components/FormInputs/Input';
import { Heading } from 'components/heading';
import { Icon } from 'design-system/icon';
import { useEffect } from 'react';
import { Controller, FormProvider, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useDataElements } from '../api/useDataElements';
import styles from './data-elements.module.scss';
import { DataElements } from './DataElement';
import { DataElementsForm } from './form/DataElementsForm';
import { HelpText } from './HowTo';

export const DataElementConfig = () => {
    const { dataElements } = useDataElements();
    const form = useForm<DataElements>({ mode: 'onBlur' });
    const nav = useNavigate();

    useEffect(() => {
        console.log('triggered');
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
                        <Controller
                            control={form.control}
                            name="belongingnessRatio"
                            render={({ field: { onChange, onBlur, value, name } }) => (
                                <Input
                                    type="number"
                                    name={name}
                                    label="Belongingness ratio"
                                    defaultValue={value?.toString()}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    orientation="horizontal"
                                    className={styles.belongingnessInput}
                                    step={0.01}
                                />
                            )}
                        />
                    </Card>
                    <FormProvider {...form}>
                        <DataElementsForm />
                    </FormProvider>
                </main>
                <HelpText />
            </div>
        </div>
    );
};
