import { FormProvider, useForm } from 'react-hook-form';
import { PhoneEmailSelection } from './PhoneEmailSelection';
import { render } from '@testing-library/react';
import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';

const mergeCandidates: Partial<MergeCandidate>[] = [
    {
        phoneEmails: [
            {
                id: '123',
                asOf: '2022-06-07T14:24:44.970',
                type: 'House',
                use: 'Home',
                countryCode: '1',
                phoneNumber: '1234445555',
                extension: '12',
                email: 'email@email.com',
                url: 'url@url.com',
                comments: 'phone comment'
            }
        ]
    }
];
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <PhoneEmailSelection mergeCandidates={mergeCandidates as MergeCandidate[]} />
        </FormProvider>
    );
};
describe('PhoneEmailSelection', () => {
    it('should have the proper section title', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('PHONE & EMAIL')).toBeInTheDocument();
    });
});
