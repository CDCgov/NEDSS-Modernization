import { MergeName } from 'apps/deduplication/api/model/MergePatient';
import { format, parseISO } from 'date-fns';
import { DetailsSection } from '../../shared/details-section/DetailsSection';

type Props = {
    name: MergeName;
};
export const NameDetails = ({ name }: Props) => {
    return (
        <DetailsSection
            details={[
                { label: 'As of date', value: name.asOf ? format(parseISO(name.asOf), 'MM/dd/yyyy') : '---' },
                { label: 'Type', value: name.type },
                { label: 'Prefix', value: name.prefix },
                { label: 'Last', value: name.last },
                { label: 'Second last', value: name.secondLast },
                { label: 'First', value: name.first },
                { label: 'Middle', value: name.middle },
                { label: 'Second middle', value: name.secondMiddle },
                { label: 'Suffix', value: name.suffix },
                { label: 'Degree', value: name.degree }
            ]}
        />
    );
};
