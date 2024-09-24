import styles from './patient-match-form.module.scss';
import { BlockingCriteriaSection } from '../BlockingCriteria/BlockingCriteriaSection';
import { MatchingCriteriaSection } from '../MatchingCriteria/MatchingCriteriaSection';
import { MatchingBounds } from '../MatchingBounds/MatchingBounds';
import { useForm, FormProvider, Controller } from 'react-hook-form';
import { Toggle } from 'design-system/toggle/Toggle';
import { Button } from 'components/button';
import { PassConfiguration } from 'apps/dedup-config/types';
import { useAlert } from 'alert';
import { usePatientMatchContext } from 'apps/dedup-config/context/PatientMatchContext';
import { useEffect } from 'react';

type Props = {
    passConfiguration: PassConfiguration;
    onDeleteConfiguration: () => void;
    onSaveConfiguration: (config: PassConfiguration) => void;
    onCancel: () => void;
    isAdding: boolean;
};

const PatientMatchForm = ({
    passConfiguration,
    onDeleteConfiguration,
    onSaveConfiguration,
    onCancel,
    isAdding
}: Props) => {
    const { blockingCriteria } = usePatientMatchContext();
    const { showSuccess } = useAlert();
    const patientMatchForm = useForm({
        mode: 'onBlur',
        defaultValues: {
            ...passConfiguration,
            lowerBound: passConfiguration.lowerBound ?? undefined,
            upperBound: passConfiguration.upperBound ?? undefined
        }
    });

    const generateName = () => {
        const first = `${blockingCriteria[0].field.name}`;
        let second = '';

        if (blockingCriteria.length > 1) {
            second = `${blockingCriteria[1].field.name}`;
            return `${first}_${second}`;
        } else {
            return first;
        }
    };

    const saveConfiguration = () => {
        const name = generateName();
        patientMatchForm.setValue('name', name);
        onSaveConfiguration(patientMatchForm.getValues());
        showSuccess({
            message: (
                <>
                    Successfully saved <strong>{name}</strong>
                </>
            )
        });
    };

    useEffect(() => {
        if (isAdding) {
            patientMatchForm.reset();
        }
    }, [isAdding]);

    console.log('saving', patientMatchForm.getValues('matchingCriteria'));

    return (
        <div className={styles.form}>
            <FormProvider {...patientMatchForm}>
                <div className={styles.fields}>
                    <BlockingCriteriaSection />
                    <MatchingCriteriaSection />
                    <MatchingBounds />
                    <Controller
                        control={patientMatchForm.control}
                        name="active"
                        render={({ field: { onChange, value, name } }) => (
                            <Toggle
                                value={value}
                                label={'Activate this pass configuration'}
                                name={name}
                                onChange={onChange}
                                sizing="compact"
                            />
                        )}
                    />
                </div>
                <div className={styles.footer}>
                    {isAdding ? (
                        <div></div>
                    ) : (
                        <Button type="button" destructive onClick={onDeleteConfiguration}>
                            Delete pass configuration
                        </Button>
                    )}

                    <div className={styles.saveButton}>
                        <Button type="reset" outline onClick={onCancel}>
                            Cancel
                        </Button>
                        <Button
                            type="submit"
                            disabled={
                                !patientMatchForm.getValues('blockingCriteria')?.length &&
                                !patientMatchForm.getValues('matchingCriteria')?.length
                            }
                            onClick={saveConfiguration}>
                            Save pass configuration
                        </Button>
                    </div>
                </div>
            </FormProvider>
        </div>
    );
};

export default PatientMatchForm;
