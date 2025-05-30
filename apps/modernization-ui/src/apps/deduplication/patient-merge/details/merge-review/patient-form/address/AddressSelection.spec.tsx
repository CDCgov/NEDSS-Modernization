import { FormProvider, useForm } from 'react-hook-form';
import { AddressSelection } from './AddressSelection';
import { render } from '@testing-library/react';
import { MergePatient } from 'apps/deduplication/api/model/MergePatient';

const mergePatients: Partial<MergePatient>[] = [
    {
        addresses: [
            {
                id: '123',
                asOf: '2022-06-07T14:24:44.970',
                type: 'House',
                use: 'Home',
                address: '123 Main st',
                address2: 'Building 2',
                city: 'Atlanta',
                state: 'Georgia',
                zipcode: '12345',
                county: 'Fulton county',
                censusTract: '0224',
                country: 'United States',
                comments: 'Comment'
            }
        ]
    }
];
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <AddressSelection mergePatients={mergePatients as MergePatient[]} />
        </FormProvider>
    );
};
describe('AddressSelection', () => {
    it('should have the proper section title', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('ADDRESS')).toBeInTheDocument();
    });
});
