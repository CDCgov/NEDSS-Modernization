import { ValueView } from 'design-system/data-display/ValueView';
import { Sizing } from 'design-system/field';
import { NameDemographic } from './names';

type NameDemographicViewProps = {
    entry: NameDemographic;
    sizing?: Sizing;
};

const NameDemographicView = ({ entry, sizing }: NameDemographicViewProps) => {
    return (
        <>
            <ValueView title="As of" value={entry.asOf} sizing={sizing} />
            <ValueView title="Type" value={entry.type?.name} sizing={sizing} />
            <ValueView title="Prefix" value={entry.prefix?.name} sizing={sizing} />
            <ValueView title="Last" value={entry.last} sizing={sizing} />
            <ValueView title="Second last" value={entry.secondLast} sizing={sizing} />
            <ValueView title="First" value={entry.first} sizing={sizing} />
            <ValueView title="Middle" value={entry.middle} sizing={sizing} />
            <ValueView title="Second middle" value={entry.secondMiddle} sizing={sizing} />
            <ValueView title="Suffix" value={entry.suffix?.name} sizing={sizing} />
            <ValueView title="Degree" value={entry.degree?.name} sizing={sizing} />
        </>
    );
};

export { NameDemographicView };
