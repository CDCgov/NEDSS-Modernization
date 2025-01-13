import { Icon } from '@trussworks/react-uswds';
import { AlertProvider, useAlert } from 'alert';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { useEffect, useState } from 'react';
import { FormProvider, useFieldArray, useForm, useFormState, useWatch } from 'react-hook-form';
import { NavLink } from 'react-router-dom';
import { useDataElements } from '../api/useDataElements';
import { useMatchingConfiguration } from '../api/useMatchingConfiguration';
import { ConfigurationMessage } from './ConfigurationMessage';
import styles from './match-configuration.module.scss';
import { MatchingConfiguration } from './model/Pass';
import { PassForm } from './pass-form/PassForm';
import { PassList } from './pass-list/PassList';

export const MatchConfiguration = () => {
    return (
        <AlertProvider>
            <MatchConfigurationContent />
        </AlertProvider>
    );
};
const MatchConfigurationContent = () => {
    const { configuration, loading } = useDataElements();
    const { matchConfiguration, save, error } = useMatchingConfiguration();
    const [activePass, setActivePass] = useState<number>(0);
    const { showSuccess, showError } = useAlert();
    const form = useForm<MatchingConfiguration>({ mode: 'onBlur' });
    const watch = useWatch<MatchingConfiguration>({ control: form.control });
    const formState = useFormState({ control: form.control });
    const { remove } = useFieldArray<MatchingConfiguration>({ control: form.control, name: 'passes' });

    useEffect(() => {
        form.reset(matchConfiguration, { keepDefaultValues: false });
    }, [matchConfiguration]);

    const handleCancel = () => {
        form.reset(matchConfiguration, { keepDefaultValues: false });
    };

    const handleSumbit = () => {
        save(form.getValues());
        save(form.getValues(), () =>
            showSuccess({ message: 'You have successfully updated the match configuration.' })
        );
    };

    const handleDeletePass = () => {
        if (watch.passes && watch.passes?.length > 1) {
            remove(activePass);
        } else {
            form.reset({ passes: [] }, { keepDefaultValues: true });
        }
        setActivePass(0);
    };

    useEffect(() => {
        if (error) {
            showError({ message: error });
        }
    }, [error]);

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
                            <PassList activeIndex={activePass} onSetActive={setActivePass} />

                            <div className={styles.formAndButtonBar}>
                                <div>
                                    {configuration && watch.passes?.[activePass] && (
                                        <PassForm activePass={activePass} dataElements={configuration} />
                                    )}
                                </div>
                                <div className={styles.buttonBar}>
                                    <Button
                                        destructive
                                        onClick={handleDeletePass}
                                        disabled={watch.passes == undefined || watch.passes?.length < 1}>
                                        Delete pass
                                    </Button>
                                    <div>
                                        <Button outline onClick={handleCancel}>
                                            Cancel
                                        </Button>
                                        <Button
                                            disabled={!formState.isValid || watch.passes?.length === 0}
                                            onClick={handleSumbit}>
                                            Save configuration
                                        </Button>
                                    </div>
                                </div>
                            </div>
                        </FormProvider>
                    </Shown>
                </main>
            </Shown>
        </div>
    );
};
