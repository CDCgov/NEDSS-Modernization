import { FormProvider, useForm } from 'react-hook-form';
import { InvestigationDisplay } from './InvestigationsDisplay';
import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { render } from '@testing-library/react';

const mergeCandidates: Partial<MergeCandidate>[] = [
    {
        investigations: []
    }
];
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <InvestigationDisplay mergeCandidates={mergeCandidates as MergeCandidate[]} />
        </FormProvider>
    );
};
describe('InvestigationDisplay', () => {
    it('should render proper section title', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('INVESTIGATIONS')).toBeInTheDocument();
    });
});
