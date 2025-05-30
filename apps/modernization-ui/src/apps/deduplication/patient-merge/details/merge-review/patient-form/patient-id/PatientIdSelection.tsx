import { MergePatient } from 'apps/deduplication/api/model/MergePatient';
import classNames from 'classnames';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Radio } from 'design-system/radio';
import { Controller, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../model/PatientMergeForm';
import styles from './patient-id-selection.module.scss';

type Props = {
    mergePatients: MergePatient[];
    onRemovePatient: (personUid: string) => void;
};
export const PatientIdSelection = ({ mergePatients, onRemovePatient }: Props) => {
    const form = useFormContext<PatientMergeForm>();

    return (
        <section className={styles.patientIdSelection}>
            {mergePatients.map((p) => (
                <Controller
                    key={`id-selection:${p.personUid}`}
                    control={form.control}
                    name="survivingRecord"
                    render={({ field: { value, name, onChange } }) => (
                        <div className={classNames(styles.idEntry, value === p.personUid ? styles.selected : '')}>
                            <Radio
                                id={`${name}-${p.personUid}`}
                                name={name}
                                label={p.personUid}
                                onChange={onChange}
                                value={p.personUid}
                                checked={value === p.personUid}
                            />
                            <Shown when={value !== p.personUid && mergePatients.length > 2}>
                                <Button
                                    secondary
                                    destructive
                                    sizing="small"
                                    onClick={() => onRemovePatient(p.personUid)}>
                                    Remove
                                </Button>
                            </Shown>
                        </div>
                    )}
                />
            ))}
        </section>
    );
};
