import { render, screen } from '@testing-library/react';
import { NoData } from './NoData';

describe('NoData Component', () => {
    it('should display "---" ', () => {
        render(<NoData />);

        expect(screen.getByText('---')).toBeInTheDocument();
    });
});
