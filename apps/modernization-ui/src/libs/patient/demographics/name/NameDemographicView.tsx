import { Sizing, ValueField } from 'design-system/field';
import { NameDemographic } from './names';
import { internalizeDate } from 'date';

type NameDemographicViewProps = {
    entry: NameDemographic;
    sizing?: Sizing;
};

const NameDemographicView = ({ entry, sizing }: NameDemographicViewProps) => {
    return (
        <>
            <ValueField title="As of" sizing={sizing}>
                {internalizeDate(entry.asOf)}
            </ValueField>
            <ValueField title="Type" sizing={sizing}>
                {entry.type?.name}
            </ValueField>
            <ValueField title="Prefix" sizing={sizing}>
                {entry.prefix?.name}
            </ValueField>
            <ValueField title="Last" sizing={sizing}>
                {entry.last}
            </ValueField>
            <ValueField title="Second last" sizing={sizing}>
                {entry.secondLast}
            </ValueField>
            <ValueField title="First" sizing={sizing}>
                {entry.first}
            </ValueField>
            <ValueField title="Middle" sizing={sizing}>
                {entry.middle}
            </ValueField>
            <ValueField title="Second middle" sizing={sizing}>
                {entry.secondMiddle}
            </ValueField>
            <ValueField title="Suffix" sizing={sizing}>
                {entry.suffix?.name}
            </ValueField>
            <ValueField title="Degree" sizing={sizing}>
                {entry.degree?.name}
            </ValueField>
        </>
    );
};

export { NameDemographicView };
export type { NameDemographicViewProps };
