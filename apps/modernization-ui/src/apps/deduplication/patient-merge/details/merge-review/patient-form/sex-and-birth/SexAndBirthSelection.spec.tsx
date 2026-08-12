import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';

import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';

import { SexAndBirthSelection } from './SexAndBirthSelection';

const mergeCandidates: Partial<MergeCandidate>[] = [
    {
        sexAndBirth: {},
    },
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
