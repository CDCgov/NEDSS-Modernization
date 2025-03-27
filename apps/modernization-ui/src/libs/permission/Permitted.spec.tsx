import { render } from '@testing-library/react';
import { Permitted } from './Permitted';

const mockPermissions: string[] = ['permitted', 'other-permitted'];
const mockAllows = (p: string) => mockPermissions.includes(p);
const mockAllowFn = jest.fn(mockAllows);

jest.mock('./usePermissions', () => ({
    usePermissions: () => ({ permissions: mockPermissions, allows: mockAllowFn })
}));

describe('Permitted', () => {
    beforeEach(() => {
        mockAllowFn.mockImplementation(mockAllows);
    });

    it('should not render children when not allowed using permission property', () => {
        const { queryByText } = render(<Permitted permission="not-permitted">Content</Permitted>);

        expect(queryByText('Content')).not.toBeInTheDocument();
    });

    it('should render children when allowed using permission property', () => {
        const { queryByText } = render(<Permitted permission="permitted">Content</Permitted>);

        expect(queryByText('Content')).toBeInTheDocument();
    });

    it('should render children when the permission predicate passes', () => {
        const { queryByText } = render(<Permitted permission={() => true}>Content</Permitted>);

        expect(queryByText('Content')).toBeInTheDocument();
    });

    it('should render children when the permission predicate fails', () => {
        const { queryByText } = render(<Permitted permission={() => false}>Content</Permitted>);

        expect(queryByText('Content')).not.toBeInTheDocument();
    });
});
