import { render, screen, within } from '@testing-library/react';
import { TreatmentList } from './TreatmentList';

describe('TreatmentList', () => {
    it('should display each treatment', () => {
        render(<TreatmentList>{['one', 'two', 'five', 'other']}</TreatmentList>);

        const list = screen.queryByRole('list');

        expect(within(list!).getByText('one'));
        expect(within(list!).getByText('two'));
        expect(within(list!).getByText('five'));
        expect(within(list!).getByText('other'));
    });

    it('should not display a list when treatments are not given', () => {
        render(<TreatmentList />);

        expect(screen.queryByRole('list')).not.toBeInTheDocument();
    });

    it('should not display a list when there are no treatments', () => {
        render(<TreatmentList>{[]}</TreatmentList>);

        expect(screen.queryByRole('list')).not.toBeInTheDocument();
    });
});
