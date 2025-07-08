import { internalizeDate } from 'date';
import { Sizing, ValueField } from 'design-system/field';
import { MortalityDemographic, labels } from '../mortality';

type MortalityDemographicViewProps = {
    sizing?: Sizing;
    demographic?: MortalityDemographic;
};

const MortalityDemographicView = ({ sizing, demographic }: MortalityDemographicViewProps) => {
    return (
        <>
            <ValueField label={labels.asOf} sizing={sizing}>
                {internalizeDate(demographic?.asOf)}
            </ValueField>
            <ValueField label={labels.deceased} sizing={sizing}>
                {demographic?.deceased?.name}
            </ValueField>
            <ValueField label={labels.deceasedOn} sizing={sizing}>
                {internalizeDate(demographic?.deceasedOn)}
            </ValueField>
            <ValueField label={labels.city} sizing={sizing}>
                {demographic?.city}
            </ValueField>
            <ValueField label={labels.state} sizing={sizing}>
                {demographic?.state?.name}
            </ValueField>
            <ValueField label={labels.county} sizing={sizing}>
                {demographic?.county?.name}
            </ValueField>
            <ValueField label={labels.country} sizing={sizing}>
                {demographic?.country?.name}
            </ValueField>
        </>
    );
};

export { MortalityDemographicView };
