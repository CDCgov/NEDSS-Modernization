import { Sizing, ValueField } from 'design-system/field';
import { PatientEthnicityDemographic } from 'generated';

type EthnicityViewProp = {
    data?: PatientEthnicityDemographic;
    sizing?: Sizing;
};

const EthnicityView = ({ data, sizing }: EthnicityViewProp) => {
    return (
        <div>
            <ValueField label={'As of'} sizing={sizing}>
                {data?.asOf}
            </ValueField>
            <ValueField label={'Ethnicity'} sizing={sizing}>
                {data?.detailed?.map((detail) => detail.name).join(', ')}
            </ValueField>
            <ValueField label={'Spanish origin'} sizing={sizing}>
                {data?.ethnicGroup?.name}
            </ValueField>
            <ValueField label={'Reason unknown'} sizing={sizing}>
                {data?.unknownReason?.name}
            </ValueField>
        </div>
    );
};

export { EthnicityView };
