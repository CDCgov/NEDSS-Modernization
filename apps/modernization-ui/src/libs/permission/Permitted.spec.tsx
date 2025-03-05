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

    it('should not render children when not all permission is included', () => {
        const { queryByText } = render(<Permitted include={['permitted', 'not-permitted']}>Content</Permitted>);

        expect(queryByText('Content')).not.toBeInTheDocument();
    });

    it('should render children when any permission is included', () => {
        const { queryByText } = render(
            <Permitted include={['permitted', 'not-permitted']} mode="any">
                Content
            </Permitted>
        );

        expect(queryByText('Content')).toBeInTheDocument();
    });

    it('should not render children when permission is excluded', () => {
        const { queryByText } = render(<Permitted exclude={['permitted']}>Content</Permitted>);

        expect(queryByText('Content')).not.toBeInTheDocument();
    });

    it('should not render children when all permissions are excluded', () => {
        const { queryByText } = render(<Permitted exclude={['permitted', 'other-permitted']}>Content</Permitted>);

        expect(queryByText('Content')).not.toBeInTheDocument();
    });

    it('should not render children when any permissions are excluded', () => {
        const { queryByText } = render(
            <Permitted exclude={['permitted', 'some-permitted']} mode="any">
                Content
            </Permitted>
        );

        expect(queryByText('Content')).not.toBeInTheDocument();
    });

    it('should not render children when any of the permissions are excluded', () => {
        const { queryByText } = render(
            <Permitted exclude={['permitted', 'some-permitted']} mode="any">
                Content
            </Permitted>
        );

        expect(queryByText('Content')).not.toBeInTheDocument();
    });

    it('should render children when any of the permissions excluded are not in user permission list', () => {
        const { queryByText } = render(<Permitted exclude={['not-permitted']}>Content</Permitted>);

        expect(queryByText('Content')).toBeInTheDocument();
    });

    it('should render children when not all permissions are excluded', () => {
        const { queryByText } = render(
            <Permitted exclude={['some-permitted', 'other-permitted']} mode="all">
                Content
            </Permitted>
        );

        expect(queryByText('Content')).toBeInTheDocument();
    });

    it('should render children when included and excluded are set', () => {
        const { queryByText } = render(
            <Permitted include={['permitted']} exclude={['not-permitted']}>
                Content
            </Permitted>
        );

        expect(queryByText('Content')).toBeInTheDocument();
    });

    it('should not render children when included and excluded overlap', () => {
        const { queryByText } = render(
            <Permitted include={['permitted']} exclude={['permitted']}>
                Content
            </Permitted>
        );

        expect(queryByText('Content')).not.toBeInTheDocument();
    });
});
