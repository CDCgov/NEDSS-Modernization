import { render } from '@testing-library/react';
import { Permitted } from './Permitted';

const mockPermissions: string[] = [];
const mockAllowed = jest.fn();

jest.mock('./usePermissions', () => ({
    usePermissions: () => ({ permissions: mockPermissions, allows: mockAllowed })
}));

describe('Permitted', () => {
    it('should not render children when not allowed', () => {
        mockAllowed.mockReturnValue(false);

        const { queryByText } = render(<Permitted permission="not-permitted">Content</Permitted>);

        expect(queryByText('Content')).not.toBeInTheDocument();
    });

    it('should  render children when allowed', () => {
        mockAllowed.mockReturnValue(true);
        const { queryByText } = render(<Permitted permission="permitted">Content</Permitted>);

        expect(queryByText('Content')).toBeInTheDocument();
    });
});
