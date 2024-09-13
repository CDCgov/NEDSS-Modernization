import styles from './patient-match-form.module.scss';
import { BlockingCriteria } from '../BlockingCriteria/BlockingCriteria';
import { MatchingCriteria } from '../MatchingCriteria/MatchingCriteria';
import { MatchingBounds } from '../MatchingBounds/MatchingBounds';
import { useForm, FormProvider, Controller } from 'react-hook-form';
import { Toggle } from 'design-system/toggle/Toggle';
import { Button } from 'components/button';
import { PassConfiguration } from 'apps/dedup-config/types';

type Props = {
    passConfiguration: PassConfiguration;
};

const PatientMatchForm = ({ passConfiguration }: Props) => {
    const patientMatchForm = useForm({
        mode: 'onBlur',
        defaultValues: {
            ...passConfiguration
        }
    });

    return (
        <div className={styles.form}>
            <FormProvider {...patientMatchForm}>
                <div className={styles.fields}>
                    <BlockingCriteria />
                    <MatchingCriteria />
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
                    <Button type="button" destructive onClick={() => patientMatchForm.reset()}>
                        Delete pass configuration
                    </Button>
                    <div className={styles.saveButton}>
                        <Button type="reset" outline>
                            Cancel
                        </Button>
                        <Button type="submit" disabled={!patientMatchForm.formState.isValid}>
                            Save pass configuration
                        </Button>
                    </div>
                </div>
            </FormProvider>
        </div>
    );
};

export default PatientMatchForm;
