import { PatientData, PatientRace } from 'apps/deduplication/api/model/PatientData';
import { format, parseISO } from 'date-fns';
import { Button } from 'design-system/button';
import { Checkbox } from 'design-system/checkbox';
import { Icon } from 'design-system/icon';
import { Column, DataTable } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import styles from './race-data-table.module.scss';

type Props = {
    patientData: PatientData;
    selectedRace?: PatientRace;
    onViewRace: (race: PatientRace) => void;
};
export const RaceDataTable = ({ patientData, selectedRace, onViewRace }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const { fields, append, remove } = useFieldArray({
        control: form.control,
        name: 'races'
    });

    const handleRaceSelection = (race: PatientRace) => {
        const index = fields.findIndex((f) => f.personUid === race.personUid && f.raceCode === race.raceCode);
        if (index > -1) {
            remove(index);
        } else {
            append({ personUid: race.personUid, raceCode: race.raceCode });
        }
    };

    const columns: Column<PatientRace>[] = [
        {
            id: 'selection',
            name: '',
            render: (r) => (
                <Checkbox
                    id={`race-select:${r.personUid}-${r.raceCode}`}
                    label=""
                    className={styles.checkBox}
                    onChange={() => handleRaceSelection(r)}
                    selected={fields.some((f) => f.personUid === r.personUid && f.raceCode === r.raceCode)}
                />
            )
        },
        {
            id: 'as-of',
            name: 'As of',
            render: (r) => format(parseISO(r.asOf), 'MM/dd/yyyy')
        },
        {
            id: 'race',
            name: 'Race',
            render: (r) => r.race
        },
        {
            id: 'detailed-race',
            name: 'Detailed race',
            render: (r) => r.detailedRaces
        },
        {
            id: 'view-icon',
            name: '',
            render: (r) => (
                <Button
                    unstyled
                    sizing="small"
                    icon={<Icon name="visibility" className={selectedRace === r ? styles.selected : ''} />}
                    onClick={() => onViewRace(r)}
                />
            )
        }
    ];
    return (
        <DataTable<PatientRace>
            id={`race-data${patientData.personUid}`}
            className={styles.dataTable}
            sizing="small"
            columns={columns}
            data={patientData.races ?? []}
        />
    );
};
