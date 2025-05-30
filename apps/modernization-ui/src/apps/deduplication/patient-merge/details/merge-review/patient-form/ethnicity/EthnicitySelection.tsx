import { MergePatient } from 'apps/deduplication/api/model/MergePatient';
import { Section } from '../shared/section/Section';
import { Ethnicity } from './ethnicity/Ethnicity';

type Props = {
    mergePatients: MergePatient[];
};
export const EthnicitySelection = ({ mergePatients }: Props) => {
    return (
        <Section
            title="ETHNICITY"
            mergePatients={mergePatients}
            render={(p) => <Ethnicity personUid={p.personUid} ethnicity={p.ethnicity} />}
        />
    );
};
