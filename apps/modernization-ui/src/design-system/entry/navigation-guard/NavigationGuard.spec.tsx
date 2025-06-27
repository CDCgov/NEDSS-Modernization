import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { NavigationGuard } from './NavigationGuard';
import * as navigation from 'navigation';
import * as storage from 'storage';

// Mock dependencies
jest.mock('navigation');
jest.mock('storage');

const mockUnblock = jest.fn();
const mockReset = jest.fn();

const Fixture = ({
    blocked = true,
    activated = true,
    value = false,
    allowed,
    cancelText,
    save = jest.fn()
}: {
    blocked?: boolean;
    activated?: boolean;
    value?: boolean;
    allowed?: string | string[];
    cancelText?: string;
    save?: (value?: boolean) => void;
} = {}) => {
    (navigation.useFormNavigationBlock as jest.Mock).mockReturnValue({
        blocked,
        unblock: mockUnblock,
        reset: mockReset
    });
    (storage.useLocalStorage as jest.Mock).mockReturnValue({ value, save });
    const form = {} as any; // Mock form object, adjust as needed

    return <NavigationGuard id="nav" form={form} activated={activated} allowed={allowed} cancelText={cancelText} />;
};

describe('NavigationGuard', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('renders confirmation modal when navigation is blocked', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('Discard unsaved data?')).toBeInTheDocument();
        expect(getByText('Yes, cancel')).toBeInTheDocument();
        expect(getByText('No, back to form')).toBeInTheDocument();
        expect(getByText(/any data you entered will be lost/i)).toBeInTheDocument();
        expect(getByText("Don't show again")).toBeInTheDocument();
    });

    it('does not render confirmation modal when navigation is not blocked', () => {
        const { queryByText } = render(<Fixture blocked={false} />);
        expect(queryByText('Discard unsaved data?')).not.toBeInTheDocument();
    });

    it('calls unblock and save with false if checkbox not checked', async () => {
        userEvent.setup();
        const save = jest.fn();
        const { getByText } = render(<Fixture save={save} />);
        const confirmButton = getByText('Yes, cancel');
        // Click confirm without checking the checkbox
        await userEvent.click(confirmButton);
        await waitFor(() => {
            expect(save).toHaveBeenCalledWith(false);
            expect(mockUnblock).toHaveBeenCalled();
        });
    });

    it('calls unblock and save with correct value when confirmed', async () => {
        userEvent.setup();
        const save = jest.fn();
        const { getByText } = render(<Fixture save={save} />);
        // Check the "Don't show again" checkbox
        const checkboxLabel = getByText("Don't show again");
        const confirmButton = getByText('Yes, cancel');
        await userEvent.click(checkboxLabel);
        // Click confirm with checkbox checked
        await userEvent.click(confirmButton);
        await waitFor(() => {
            expect(save).toHaveBeenCalledWith(true);
            expect(mockUnblock).toHaveBeenCalled();
        });
    });

    it('calls blocker reset when cancel is clicked', async () => {
        userEvent.setup();
        const { getByText } = render(<Fixture />);
        const cancelButton = getByText('No, back to form');
        await userEvent.click(cancelButton);
        expect(mockReset).toHaveBeenCalled();
    });

    it('shows custom cancelText if provided', () => {
        const { getByText } = render(<Fixture cancelText="Custom warning message" />);
        expect(getByText('Custom warning message')).toBeInTheDocument();
    });

    it('does not activate navigation block if activated is false', () => {
        render(<Fixture activated={false} />);
        expect(navigation.useFormNavigationBlock).toHaveBeenCalledWith(
            expect.objectContaining({ activated: false, form: expect.anything(), allowed: undefined })
        );
    });

    it('passes allowed prop to useFormNavigationBlock', () => {
        const allowed = ['/safe-route'];
        render(<Fixture allowed={allowed} />);
        expect(navigation.useFormNavigationBlock).toHaveBeenCalledWith(expect.objectContaining({ allowed }));
    });

    it('does not block navigation if value from localStorage is true', () => {
        render(<Fixture value={true} />);
        expect(navigation.useFormNavigationBlock).toHaveBeenCalledWith(
            expect.objectContaining({ activated: false, form: expect.anything(), allowed: undefined })
        );
    });
});
