import { render, screen } from '@testing-library/react';
import { OrElseNoData } from './OrElseNoData';

describe('NoData Component', () => {
    it('should display "---" ', () => {
        render(<OrElseNoData>{undefined}</OrElseNoData>);

        expect(screen.getByText('---')).toBeInTheDocument();
    });

    it('should display content', () => {
        render(<OrElseNoData>{'testing'}</OrElseNoData>);

        expect(screen.getByText('testing')).toBeInTheDocument();
    });
});
