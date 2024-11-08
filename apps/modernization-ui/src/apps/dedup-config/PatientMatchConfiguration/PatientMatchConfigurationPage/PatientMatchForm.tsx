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
import savePassConfiguration from './api';

type Props = {
    passConfiguration: PassConfiguration;
    onDeleteConfiguration: () => void;
    onSaveConfiguration: (config: PassConfiguration) => void;
    onCancel: () => void;
    isAdding: boolean;
    selectedFields: string[];
};

const PatientMatchForm = ({
    passConfiguration,
    onDeleteConfiguration,
    onSaveConfiguration,
    onCancel,
    isAdding,
    selectedFields
}: Props) => {
    const { blockingCriteria } = usePatientMatchContext();
    const { showSuccess, showError } = useAlert();
    const patientMatchForm = useForm({
        mode: 'onBlur',
        defaultValues: {
            ...passConfiguration,
            lowerBound: passConfiguration.lowerBound ?? undefined,
            upperBound: passConfiguration.upperBound ?? undefined,
            blockingCriteria: blockingCriteria.map((Criteria: any) => ({
                method: Criteria.method || 'DefaultMethod',
                field: Criteria.field.name || {
                    name: 'DefaultField',
                    label: '',
                    category: '',
                    active: false,
                    m: 0,
                    u: 0,
                    threshold: 0,
                    oddsRatio: 0,
                    logOdds: 0
                }
            })),
            matchingCriteria:
                passConfiguration.matchingCriteria?.map((mc) => ({
                    method: mc.method || 'DefaultMethod',
                    field: mc.field || {
                        name: 'DefaultField',
                        label: '',
                        category: '',
                        active: false,
                        m: 0,
                        u: 0,
                        threshold: 0,
                        oddsRatio: 0,
                        logOdds: 0
                    }
                })) || []
        }
    });

    useEffect(() => {
        console.log('Selected Fields:', selectedFields);
    }, [selectedFields]);

    console.log(
        'bc.field:',
        blockingCriteria.map((bc) => ({ method: bc.method, field: bc.field }))
    );
    const generateName = () => {
        const first = blockingCriteria[0]?.field?.name || 'Unnamed';
        let second = '';
        if (blockingCriteria.length > 1) {
            second = blockingCriteria[1]?.field?.name || '';
            return `${first}_${second}`;
        }
        return first;
    };

    const saveConfiguration = async () => {
        const name = generateName();
        patientMatchForm.setValue('name', name);

        const formValues = patientMatchForm.getValues();
        console.log('Form Values:', formValues);

        const transformedBlockingCriteria =
            formValues.blockingCriteria?.map((criteria) => ({
                method: criteria.method || 'DefaultMethod',
                field: criteria.field?.name || 'FallbackField'
            })) || [];

        const transformedMatchingCriteria =
            formValues.matchingCriteria?.map((criteria) => ({
                method: String(criteria.method) || 'DefaultMethod',
                field: criteria.field?.name || 'FallbackField'
            })) || [];

        const configurationData = {
            ...formValues,
            blockingCriteria: transformedBlockingCriteria,
            matchingCriteria: transformedMatchingCriteria,
            lowerBound: formValues.lowerBound,
            upperBound: formValues.upperBound
        };

        console.log('Attempting to save pass configuration with data:', configurationData);

        try {
            const result = await savePassConfiguration(configurationData);
            onSaveConfiguration(result);
            showSuccess({ message: `Successfully saved ${formValues.name}` });
        } catch (error) {
            console.error('Error saving configuration:', error);
            showError({ message: 'Failed to save pass configuration. Please try again.' });
        }
    };

    useEffect(() => {
        if (isAdding) {
            patientMatchForm.reset();
        }
    }, [isAdding]);

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
                                label="Activate this pass configuration"
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
