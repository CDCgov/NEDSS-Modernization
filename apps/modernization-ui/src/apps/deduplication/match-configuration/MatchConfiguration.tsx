import { Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { NavLink } from 'react-router-dom';
import styles from './match-configuration.module.scss';
import { useDataElements } from '../api/useDataElements';
import { ConfigurationMessage } from './ConfigurationMessage';
import { Shown } from 'conditional-render';
import { useMatchingConfiguration } from '../api/useMatchingConfiguration';
import { useEffect, useState } from 'react';
import { PassList } from './pass-list/PassList';
import { FormProvider, useForm } from 'react-hook-form';
import { PassForm } from './pass-form/PassForm';
import { MatchingConfiguration } from './model/Pass';

export const MatchConfiguration = () => {
    const { configuration, loading } = useDataElements();
    const { matchConfiguration } = useMatchingConfiguration();
    const [activePass, setActivePass] = useState<number>(0);
    const form = useForm<MatchingConfiguration>();

    useEffect(() => {
        form.reset(matchConfiguration, { keepDefaultValues: false });
    }, [matchConfiguration]);

    return (
        <div className={styles.matchConfiguration}>
            <header>
                <Heading level={1}>Patient match configuration</Heading>
                <NavLink to={'/deduplication/data-elements'}>
                    <Icon.Settings size={3} />
                    Data elements configuration
                </NavLink>
            </header>
            <Shown when={!loading}>
                <main>
                    <Shown when={configuration == null}>
                        <ConfigurationMessage />
                    </Shown>

                    <Shown when={configuration != null}>
                        <FormProvider {...form}>
                            <PassList onSetActive={setActivePass} />
                            <PassForm activePass={activePass} />
                        </FormProvider>
                    </Shown>
                </main>
            </Shown>
        </div>
    );
};
