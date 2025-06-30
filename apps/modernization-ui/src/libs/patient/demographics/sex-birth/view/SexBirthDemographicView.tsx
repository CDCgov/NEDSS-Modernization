import { AgeResolver, internalizeDate } from 'date';
import { Sizing, ValueField } from 'design-system/field';
import { SexBirthDemographic, labels } from '../sexBirth';

type SexBirthDemographicViewProps = {
    ageResolver: AgeResolver;
    sizing?: Sizing;
    demographic?: SexBirthDemographic;
};

const SexBirthDemographicView = ({ ageResolver, sizing, demographic }: SexBirthDemographicViewProps) => {
    return (
        <>
            <ValueField label={labels.asOf} sizing={sizing}>
                {internalizeDate(demographic?.asOf)}
            </ValueField>
            <ValueField label={labels.bornOn} sizing={sizing}>
                {internalizeDate(demographic?.bornOn)}
            </ValueField>
            <ValueField label={labels.age} sizing={sizing}>
                {ageResolver(demographic?.bornOn)}
            </ValueField>
            <ValueField label={labels.current} sizing={sizing}>
                {demographic?.current?.name}
            </ValueField>
            <ValueField label={labels.unknownReason} sizing={sizing}>
                {demographic?.unknownReason?.name}
            </ValueField>
            <ValueField label={labels.transgenderInformation} sizing={sizing}>
                {demographic?.transgenderInformation?.name}
            </ValueField>
            <ValueField label={labels.additionalGender} sizing={sizing}>
                {demographic?.additionalGender}
            </ValueField>
            <ValueField label={labels.sex} sizing={sizing}>
                {demographic?.sex?.name}
            </ValueField>
            <ValueField label={labels.multiple} sizing={sizing}>
                {demographic?.multiple?.name}
            </ValueField>
            <ValueField label={labels.order} sizing={sizing}>
                {demographic?.order}
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

export { SexBirthDemographicView };
