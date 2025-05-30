import { FormProvider, useForm } from 'react-hook-form';
import { Ethnicity } from './Ethnicity';
import { render } from '@testing-library/react';
import { MergeEthnicity } from 'apps/deduplication/api/model/MergePatient';

const defaultData: MergeEthnicity = {
    asOf: '2014-03-11T00:00:00.000',
    ethnicity: 'Hispanic or Latino',
    spanishOrigin: 'Cuban',
    reasonUnknown: 'Did not ask'
};
const Fixture = ({ data = defaultData }: { data?: MergeEthnicity }) => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <Ethnicity personUid="123" ethnicity={data} />
        </FormProvider>
    );
};
describe('Ethnicity', () => {
    it('should render the proper fields and values', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('As of date')).toBeInTheDocument();
        expect(getByText('03/11/2014')).toBeInTheDocument();

        expect(getByText('Ethnicity')).toBeInTheDocument();
        expect(getByText('Hispanic or Latino')).toBeInTheDocument();

        expect(getByText('Spanish origin')).toBeInTheDocument();
        expect(getByText('Cuban')).toBeInTheDocument();

        expect(getByText('Reason unknown')).toBeInTheDocument();
        expect(getByText('Did not ask')).toBeInTheDocument();
    });

    it('should render "---" for emtpy fields', () => {
        const { getByText, getAllByText } = render(<Fixture data={{}} />);

        expect(getByText('As of date')).toBeInTheDocument();
        expect(getByText('Ethnicity')).toBeInTheDocument();
        expect(getByText('Spanish origin')).toBeInTheDocument();
        expect(getByText('Reason unknown')).toBeInTheDocument();

        expect(getAllByText('---')).toHaveLength(4);
    });
});
