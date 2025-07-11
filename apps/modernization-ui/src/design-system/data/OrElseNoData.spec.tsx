import { render, screen } from '@testing-library/react';
import { OrElseNoData } from './OrElseNoData';

describe('OrElseNoData', () => {
    it.each([undefined, null])('should display "---" when %s', (value) => {
        render(<OrElseNoData>{value}</OrElseNoData>);

        expect(screen.getByText('---')).toBeInTheDocument();
    });

    it.each(['testing', 0])('should display content: %s', (value) => {
        render(<OrElseNoData>{value}</OrElseNoData>);

        expect(screen.getByText(`${value}`)).toBeInTheDocument();
    });
});
