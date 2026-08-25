import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';

import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';

import { InvestigationDisplay } from './InvestigationsDisplay';

const mergeCandidates: Partial<MergeCandidate>[] = [
    {
        investigations: [],
    },
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
