import { render } from '@testing-library/react';
import { DataEntryMenu } from './DataEntryMenu';

let mockPermissions: string[] = [];

jest.mock('user', () => ({
    useUser: () => ({ state: { user: { permissions: mockPermissions } } })
}));

describe('when displaying the Data entry menu', () => {
    beforeEach(() => {
        mockPermissions = [];
    });

    it('should display New patient when the user is able to find patients', () => {
        mockPermissions = ['find-patient'];

        const { getByText } = render(<DataEntryMenu />);

        const entry = getByText('New patient');

        expect(entry).toBeInTheDocument();
    });

    it('should not display New patient when the user is able to find patients', () => {
        const { queryByText } = render(<DataEntryMenu />);

        const entry = queryByText('New patient');

        expect(entry).not.toBeInTheDocument();
    });

    it('should display New organization when the user is able to manage organizations', () => {
        mockPermissions = ['manage-organization'];

        const { getByRole } = render(<DataEntryMenu />);

        const entry = getByRole('link', { name: 'New Organization' });

        expect(entry).toBeInTheDocument();
    });

    it('should not display New organization when the user cannot manage organizations', () => {
        const { queryByRole } = render(<DataEntryMenu />);

        const entry = queryByRole('link', { name: 'New Organization' });

        expect(entry).not.toBeInTheDocument();
    });

    it('should display Provider when the user is able to manage providers', () => {
        mockPermissions = ['manage-provider'];

        const { getByRole } = render(<DataEntryMenu />);

        const entry = getByRole('link', { name: 'New Provider' });

        expect(entry).toBeInTheDocument();
    });

    it('should not display Provider when the user cannot manage providers', () => {
        const { queryByRole } = render(<DataEntryMenu />);

        const entry = queryByRole('link', { name: 'New Provider' });

        expect(entry).not.toBeInTheDocument();
    });

    it('should display Morbidity report when the user is able to add Morbidity reports', () => {
        mockPermissions = ['add-observationmorbidityreport'];

        const { getByRole } = render(<DataEntryMenu />);

        const entry = getByRole('link', { name: 'Morbidity report' });

        expect(entry).toBeInTheDocument();
    });

    it('should not display Morbidity report when the user cannot manage add Morbidity reports', () => {
        const { queryByRole } = render(<DataEntryMenu />);

        const entry = queryByRole('link', { name: 'Morbidity report' });

        expect(entry).not.toBeInTheDocument();
    });
});
