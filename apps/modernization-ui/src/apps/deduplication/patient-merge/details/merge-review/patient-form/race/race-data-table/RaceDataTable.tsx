import { MergeCandidate, MergeRace } from 'apps/deduplication/api/model/MergeCandidate';
import { Column } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { MergeDataTable } from '../../shared/merge-data-table/MergeDataTable';
import { toDateDisplay } from '../../../../shared/toDateDisplay';
import styles from './race-data-table.module.scss';

type Props = {
    patientData: MergeCandidate;
    selectedRace?: MergeRace;
    onViewRace: (race: MergeRace) => void;
};
export const RaceDataTable = ({ patientData, selectedRace, onViewRace }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const { fields, append, remove } = useFieldArray({
        control: form.control,
        name: 'races'
    });

    const handleRaceSelection = (race: MergeRace) => {
        const index = fields.findIndex((f) => f.personUid === race.personUid && f.raceCode === race.raceCode);
        if (index > -1) {
            remove(index);
        } else {
            append({ personUid: race.personUid, raceCode: race.raceCode });
        }
    };

    const columns: Column<MergeRace>[] = [
        {
            id: 'as-of',
            name: 'As of',
            className: styles['date-header'],
            render: (r) => toDateDisplay(r.asOf)
        },
        {
            id: 'race',
            name: 'Race',
            className: styles['text-header'],
            render: (r) => r.race
        },
        {
            id: 'detailed-race',
            name: 'Detailed race',
            render: (r) => r.detailedRaces
        }
    ];
    return (
        <MergeDataTable<MergeRace>
            id={`race-data${patientData.personUid}`}
            columns={columns}
            data={patientData.races}
            rowId={(r) => `race-${r.personUid}-${r.raceCode}`}
            isSelected={(r) => fields.some((f) => f.personUid === r.personUid && f.raceCode === r.raceCode)}
            onSelect={(r) => handleRaceSelection(r)}
            isViewed={(r) => selectedRace === r}
            onView={(r) => onViewRace(r)}
        />
    );
};
