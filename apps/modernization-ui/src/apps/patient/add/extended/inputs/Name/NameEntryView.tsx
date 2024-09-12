import { NameEntry } from 'apps/patient/profile/names/NameEntry';
import { usePatientNameCodedValues } from 'apps/patient/profile/names/usePatientNameCodedValues';
import { DataDisplay } from 'design-system/data-display/DataDisplay';

type Props = {
    entry: NameEntry;
};

export const NameEntryView = ({ entry }: Props) => {
    const coded = usePatientNameCodedValues();
    return (
        <>
            <DataDisplay title="As of" value={entry.asOf} required />
            <DataDisplay title="Type" value={coded.types.find((e) => e.value === entry.type)?.name} required />
            <DataDisplay title="Prefix" value={coded.prefixes.find((e) => e.value === entry.prefix)?.name} />
            <DataDisplay title="Last" value={entry.last} />
            <DataDisplay title="Second last" value={entry.secondLast} />
            <DataDisplay title="First" value={entry.first} />
            <DataDisplay title="Middle" value={entry.middle} />
            <DataDisplay title="Second middle" value={entry.secondMiddle} />
            <DataDisplay title="Suffix" value={coded.suffixes.find((e) => e.value === entry.suffix)?.name} />
            <DataDisplay title="Degree" value={coded.degrees.find((e) => e.value === entry.degree)?.name} />
        </>
    );
};
