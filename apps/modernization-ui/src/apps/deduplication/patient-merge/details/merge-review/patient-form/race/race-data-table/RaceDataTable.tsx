import { MergePatient, MergeRace } from 'apps/deduplication/api/model/MergePatient';
import { format, parseISO } from 'date-fns';
import { Column } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { MergeDataTable } from '../../shared/merge-data-table/MergeDataTable';

type Props = {
    patientData: MergePatient;
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
