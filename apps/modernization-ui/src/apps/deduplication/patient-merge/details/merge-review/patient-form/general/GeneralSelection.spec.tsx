import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { useForm, FormProvider } from 'react-hook-form';
import { SexAndBirthSelection } from '../sex-and-birth/SexAndBirthSelection';
import { render } from '@testing-library/react';
import { GeneralSelection } from './GeneralSelection';

const mergeCandidates: Partial<MergeCandidate>[] = [
    {
        general: {}
    }
];
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <GeneralSelection mergeCandidates={mergeCandidates as MergeCandidate[]} />
        </FormProvider>
    );
};
describe('GeneralSelection', () => {
    it('should render proper section title', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('GENERAL PATIENT INFORMATION')).toBeInTheDocument();
    });
});
