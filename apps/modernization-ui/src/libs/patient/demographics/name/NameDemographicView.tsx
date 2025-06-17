import { Sizing, ValueField } from 'design-system/field';
import { NameDemographic } from './names';
import { internalizeDate } from 'date';

type NameDemographicViewProps = {
    entry: NameDemographic;
    sizing?: Sizing;
    centered?: boolean;
};

const NameDemographicView = ({ entry, sizing, centered = true }: NameDemographicViewProps) => {
    return (
        <>
            <ValueField title="As of" sizing={sizing} centered={centered}>
                {internalizeDate(entry.asOf)}
            </ValueField>
            <ValueField title="Type" sizing={sizing} centered={centered}>
                {entry.type?.name}
            </ValueField>
            <ValueField title="Prefix" sizing={sizing} centered={centered}>
                {entry.prefix?.name}
            </ValueField>
            <ValueField title="Last" sizing={sizing} centered={centered}>
                {entry.last}
            </ValueField>
            <ValueField title="Second last" sizing={sizing} centered={centered}>
                {entry.secondLast}
            </ValueField>
            <ValueField title="First" sizing={sizing} centered={centered}>
                {entry.first}
            </ValueField>
            <ValueField title="Middle" sizing={sizing} centered={centered}>
                {entry.middle}
            </ValueField>
            <ValueField title="Second middle" sizing={sizing} centered={centered}>
                {entry.secondMiddle}
            </ValueField>
            <ValueField title="Suffix" sizing={sizing} centered={centered}>
                {entry.suffix?.name}
            </ValueField>
            <ValueField title="Degree" sizing={sizing} centered={centered}>
                {entry.degree?.name}
            </ValueField>
        </>
    );
};

export { NameDemographicView };
export type { NameDemographicViewProps };
