import { Button, Icon } from '@trussworks/react-uswds';
import { useDataElementsContext } from '../context/DataElementsContext';
import { useDedupeContext } from '../context/DedupeContext';
import styles from './data-elements-configuration.module.scss';
import { Input } from 'components/FormInputs/Input';
<<<<<<< HEAD
import { DataElementsTable, dataElements } from './DataElementsTable';
import { useForm, FormProvider } from 'react-hook-form';

const DataElementsConfiguration = () => {
    const { setDataElements } = useDataElementsContext();
    const { setMode } = useDedupeContext();
    const form = useForm({
        mode: 'onBlur',
        defaultValues: { dataElements }
=======
import { DataElementsTable } from './DataElementsTable';
import { useForm, FormProvider, Controller } from 'react-hook-form';

const DataElementsConfiguration = () => {
    const { dataElements, setDataElements, belongingnessRatio, setBelongingnessRatio } = useDataElementsContext();
    const { setMode } = useDedupeContext();
    const form = useForm({
        mode: 'onBlur',
        defaultValues: { dataElements, belongingnessRatio: belongingnessRatio ?? undefined }
>>>>>>> case-dedup-ui
    });

    const { formState } = form;

<<<<<<< HEAD
    const handleSaveElements = () => {
        const values = form.getValues().dataElements;
        if (values) {
            localStorage.setItem('dataElements', JSON.stringify(values));
            setDataElements(values);
            setMode('match');
        }
    };
=======
    const onSubmit = form.handleSubmit((data) => {
        let finalBelongingnessRatio = data.belongingnessRatio;
        if (typeof finalBelongingnessRatio === 'string') {
            finalBelongingnessRatio = finalBelongingnessRatio === '' ? undefined : parseFloat(finalBelongingnessRatio);
        }
        console.log('SUBMIT', { ...data, belongingnessRatio: finalBelongingnessRatio });
        setDataElements(data.dataElements);
        setBelongingnessRatio(finalBelongingnessRatio);
        setMode('match');
    });
>>>>>>> case-dedup-ui

    return (
        <div className={styles.dataElements}>
            <div className={styles.header}>
                <h2>
                    <Icon.ArrowBack onClick={() => setMode('patient')} /> Data elements configuration
                </h2>
            </div>
            <div className={styles.body}>
                <div className={styles.sections}>
                    <FormProvider {...form}>
                        <div className={styles.section}>
                            <div className={styles.sectionHeader}>
                                <h4>Global settings</h4>
                            </div>
                            <div className={styles.sectionBody}>
<<<<<<< HEAD
                                <Input
                                    type="text"
                                    label="Belongingness ratio"
                                    defaultValue="0.5"
                                    orientation="horizontal"
=======
                                <Controller
                                    control={form.control}
                                    name="belongingnessRatio"
                                    render={({ field: { onChange, onBlur, value, name } }) => (
                                        <Input
                                            type="number"
                                            name={name}
                                            label="Belongingness ratio"
                                            value={value !== undefined ? String(value) : ''}
                                            defaultValue={value !== undefined ? String(value) : ''}
                                            onChange={onChange}
                                            onBlur={onBlur}
                                            orientation="horizontal"
                                        />
                                    )}
>>>>>>> case-dedup-ui
                                />
                            </div>
                        </div>
                        <div className={styles.section}>
                            <div className={styles.table}>
                                <div className={styles.sectionHeader}>
                                    <h4>Data elements</h4>
                                </div>
                                <div className={styles.sectionBody}>
                                    <DataElementsTable />
                                </div>
                            </div>
                        </div>
                    </FormProvider>
                </div>
                <div className={styles.sidebar}>
                    <h3>How to </h3>
                    <h4>Belongingness ratio</h4>
                    <p>
                        The belongingness ratio of a new record to an existing cluster of data is the percentage of
                        records already belonging to that cluster that the new record is a match with.
                    </p>
                    <h4>Data elements</h4>
                    <p>
                        This table represents all the possible data elements that are available to the patient match
                        algorithm.
                    </p>
                    <ol>
                        <li>
                            Checking a data element will make it available to be selected during pass configuration.
                        </li>
                        <li>
                            Once checked, the data element requires the M, U and Threshold values to be set before
                            saving.
                        </li>
                    </ol>
                    <ul>
                        <li>
                            <span>M -</span> Probability that a given field is the same in a pair of matching records.
                        </li>
                        <li>
                            <span>U -</span> Probability that a given field is the same in a pair of non-matching
                            records
                        </li>
                        <li>
                            <span>Threshold -</span> Values above which two strings are said to be “similar enough” that
                            they’re probably the same thing. Values that are less than the threshold will be calculated
                            as 0.
                        </li>
                    </ul>
                    <h4>Log odds</h4>
                    <p>
                        A score of the likelihood that something observed in a data set is due to a statistically
                        meaningful relationship or cause, rather than random chance. Calculated based on odds ratio.
                    </p>
                </div>
            </div>
            <div className={styles.footer}>
                <Button type="button" outline onClick={() => setMode('patient')}>
                    Cancel
                </Button>
<<<<<<< HEAD
                <Button type="button" onClick={handleSaveElements} disabled={!formState.isValid}>
=======
                <Button type="button" onClick={() => onSubmit()} disabled={!formState.isValid}>
>>>>>>> case-dedup-ui
                    Save data elements configuration
                </Button>
            </div>
        </div>
    );
};

export default DataElementsConfiguration;
