import { FormProvider, useForm } from 'react-hook-form';
import { SexAndBirthSelection } from './SexAndBirthSelection';
import { render } from '@testing-library/react';
import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';

const mergeCandidates: Partial<MergeCandidate>[] = [
    {
        sexAndBirth: {}
    }
];
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <SexAndBirthSelection mergeCandidates={mergeCandidates as MergeCandidate[]} />
        </FormProvider>
    );
};
describe('SexAndBirthSelection', () => {
    it('should render proper section title', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('SEX & BIRTH')).toBeInTheDocument();
    });
});
