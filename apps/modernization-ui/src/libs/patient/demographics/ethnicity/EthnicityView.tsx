import { internalizeDate } from 'date';
import { Sizing, ValueField } from 'design-system/field';
import { renderSelectables } from 'options';
import { EthnicityDemographic } from './ethnicity';

type EthnicityViewProp = {
    data?: EthnicityDemographic;
    sizing?: Sizing;
};

const EthnicityView = ({ data, sizing }: EthnicityViewProp) => {
    return (
        <div>
            <ValueField label={'As of'} sizing={sizing}>
                {internalizeDate(data?.asOf)}
            </ValueField>
            <ValueField label={'Ethnicity'} sizing={sizing}>
                {data?.ethnicGroup?.name}
            </ValueField>
            <ValueField label={'Spanish origin'} sizing={sizing}>
                {renderSelectables(data?.detailed)}
            </ValueField>
            <ValueField label={'Reason unknown'} sizing={sizing}>
                {data?.unknownReason?.name}
            </ValueField>
        </div>
    );
};

export { EthnicityView };
