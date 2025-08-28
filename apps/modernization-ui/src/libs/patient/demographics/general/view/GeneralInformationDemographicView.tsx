import { internalizeDate } from 'date';
import { Sizing, ValueField } from 'design-system/field';
import { GeneralInformationDemographic, labels } from '../general';
import { SensitiveValueField } from 'libs/sensitive';

type GeneralInformationDemographicViewProps = {
    sizing?: Sizing;
    demographic?: Partial<GeneralInformationDemographic>;
};

const GeneralInformationDemographicView = ({ sizing, demographic }: GeneralInformationDemographicViewProps) => {
    return (
        <>
            <ValueField label={labels.asOf} sizing={sizing}>
                {internalizeDate(demographic?.asOf)}
            </ValueField>
            <ValueField label={labels.maritalStatus} sizing={sizing}>
                {demographic?.maritalStatus?.name}
            </ValueField>
            <ValueField label={labels.maternalMaidenName} sizing={sizing}>
                {demographic?.maternalMaidenName}
            </ValueField>
            <ValueField label={labels.adultsInResidence} sizing={sizing}>
                {demographic?.adultsInResidence}
            </ValueField>
            <ValueField label={labels.childrenInResidence} sizing={sizing}>
                {demographic?.childrenInResidence}
            </ValueField>
            <ValueField label={labels.primaryOccupation} sizing={sizing}>
                {demographic?.primaryOccupation?.name}
            </ValueField>
            <ValueField label={labels.educationLevel} sizing={sizing}>
                {demographic?.educationLevel?.name}
            </ValueField>
            <ValueField label={labels.primaryLanguage} sizing={sizing}>
                {demographic?.primaryLanguage?.name}
            </ValueField>
            <ValueField label={labels.speaksEnglish} sizing={sizing}>
                {demographic?.speaksEnglish?.name}
            </ValueField>
            <SensitiveValueField label={labels.stateHIVCase} sizing={sizing}>
                {demographic?.stateHIVCase}
            </SensitiveValueField>
        </>
    );
};

export { GeneralInformationDemographicView };
