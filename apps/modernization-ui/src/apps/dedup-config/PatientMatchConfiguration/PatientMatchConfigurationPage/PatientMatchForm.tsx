import styles from './patient-match-form.module.scss';
import { BlockingCriteriaSection } from '../BlockingCriteria/BlockingCriteriaSection';
import { MatchingCriteriaSection } from '../MatchingCriteria/MatchingCriteriaSection';
import { MatchingBounds } from '../MatchingBounds/MatchingBounds';
import { useForm, FormProvider, Controller } from 'react-hook-form';
import { Toggle } from 'design-system/toggle/Toggle';
import { Button } from 'components/button';
import { PassConfiguration } from 'apps/dedup-config/types';
import { usePatientMatchContext } from 'apps/dedup-config/context/PatientMatchContext';
import { useAlert } from 'alert';

type Props = {
    passConfiguration: PassConfiguration;
    onDeleteConfiguration: () => void;
};

const PatientMatchForm = ({ passConfiguration, onDeleteConfiguration }: Props) => {
    const { blockingCriteria, matchingCriteria } = usePatientMatchContext();
    const { showSuccess } = useAlert();
    const patientMatchForm = useForm({
        mode: 'onBlur',
        defaultValues: {
            ...passConfiguration,
            lowerBound: passConfiguration.lowerBound ?? undefined,
            upperBound: passConfiguration.upperBound ?? undefined
        }
    });

    const saveConfiguration = () => {
        const formValues = patientMatchForm.getValues();
        console.log('FORM VALUES', formValues);
        const formValuesString = JSON.stringify([
            {
                ...formValues,
                blockingCriteria,
                matchingCriteria,
                lowerBound: formValues.lowerBound, // Make sure these are included
                upperBound: formValues.upperBound
            }
        ]);

        localStorage.setItem('passConfigurations', formValuesString);
        showSuccess({
            message: (
                <>
                    Successfully saved <strong>{formValues.name}</strong>
                </>
            )
        });
    };

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
                    <Button type="button" destructive onClick={onDeleteConfiguration}>
                        Delete pass configuration
                    </Button>
                    <div className={styles.saveButton}>
                        <Button type="reset" outline>
                            Cancel
                        </Button>
                        <Button
                            type="submit"
                            disabled={!patientMatchForm.formState.isValid}
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
