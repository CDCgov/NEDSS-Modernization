import { ValueView } from 'design-system/data-display/ValueView';
import { Ethnicity } from './Ethnicity';
import { Sizing } from 'design-system/field';

type EthnicityViewProp = {
    data?: Ethnicity;
    sizing?: Sizing;
};

const EthnicityView = ({ data, sizing }: EthnicityViewProp) => {
    return (
        <div>
            <ValueView title={'As of'} value={data?.asOf} sizing={sizing} />
            <ValueView title={'Ethnicity'} value={data?.detailed} sizing={sizing} />
            <ValueView title={'Spanish origin'} value={data?.ethnicGroup?.name} sizing={sizing} />
            <ValueView title={'Reason unknown'} value={data?.unknownReason?.name} sizing={sizing} />
        </div>
    );
};

export { EthnicityView };
