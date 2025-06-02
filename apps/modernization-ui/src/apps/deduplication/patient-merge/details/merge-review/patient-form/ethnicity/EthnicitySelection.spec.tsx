import { FormProvider, useForm } from 'react-hook-form';
import { EthnicitySelection } from './EthnicitySelection';
import { render } from '@testing-library/react';
import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';

const data: Partial<MergeCandidate> = {
    personUid: '123',
    ethnicity: {
        asOf: '2014-03-11T00:00:00.000',
        ethnicity: 'Hispanic or Latino',
        spanishOrigin: 'Cuban',
        reasonUnknown: 'Did not ask'
    }
};
const Fixture = () => {
    const form = useForm();

    return (
        <FormProvider {...form}>
            <EthnicitySelection mergeCandidates={[data as MergeCandidate]} />
        </FormProvider>
    );
};
describe('EthnicitySelection', () => {
    it('should render the proper section title', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('ETHNICITY')).toBeInTheDocument();
    });
});
