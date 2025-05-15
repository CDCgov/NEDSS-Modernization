import { render } from '@testing-library/react';
import { MergeReview } from './MergeReview';
import { MemoryRouter } from 'react-router';
import { useState } from "react";

const [pageState, setPageState] = useState<'review' | 'preview'>('review');
const Fixture = () => (
    <MemoryRouter>
        <MergeReview onPreviewClick={() => setPageState('preview')} />
    </MemoryRouter>
);
describe('MergeReview', () => {
    it('should display proper header', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('heading')).toHaveTextContent('Matches requiring review');
    });

    it('should display buttons in header', () => {
        const { getAllByRole } = render(<Fixture />);
        const buttons = getAllByRole('button');

        expect(buttons[0]).toHaveTextContent('Back');
        expect(buttons[0]).toHaveClass('secondary');
        expect(buttons[1]).toHaveTextContent('Preview merge');
        expect(buttons[1]).toHaveClass('secondary');
        expect(buttons[2]).toHaveTextContent('Keep all separate');
        expect(buttons[2]).not.toHaveClass('secondary');
        expect(buttons[3]).toHaveTextContent('Merge all');
        expect(buttons[3]).not.toHaveClass('secondary');
    });

    it('should display informational text', () => {
        const { getByText } = render(<Fixture />);
        expect(
            getByText(
                'Only one record is selected for Patient ID. By default, the oldest record is selected as the surviving ID. If this is not correct, select the appropriate record.'
            )
        ).toBeInTheDocument();
    });
});
