import { MergeName } from 'apps/deduplication/api/model/MergeCandidate';
import { DetailsSection } from '../../shared/details-section/DetailsSection';
import { toDateDisplay } from '../../../../shared/toDateDisplay';

type Props = {
    name: MergeName;
};
export const NameDetails = ({ name }: Props) => {
    return (
        <DetailsSection
            details={[
                { label: 'As of date', value: toDateDisplay(name.asOf) },
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
