import { permissions } from "libs/permission";
import { useAddPatientFromSearch } from "./add"
import { useFocusAddNewPatientButton } from "./useFocusAddNewPatientButton";
import { PatientSearchActions } from "./PatientSearchActions";
import { getByText, render } from "@testing-library/react";
import { axe } from "jest-axe";

jest.mock('./add/useAddPatientFromSearch', () => ({
    useAddPatientFromSearch: jest.fn()
}));

jest.mock('lib/permission', () => ({
    permissions: {
        patient: {
            add: 'patient:add'
        }
    },
    Permitted: ({ children,permission }: { children: React.ReactNode; permission: string }) => (
        <div className="permitted-wrapper" data-permission={permission}>
            {children}
        </div>
    )
}));

jest.mock('./useFocusAddNewPatientButton', () => ({
    useFocusAddNewPatientButton: jest.fn()
}));

describe('PatinetSearchActions', () => {
    const mockAdd = jest.fn();

    beforeEach(() => {
        jest.clearAllMocks();
        (useAddPatientFromSearch as jest.Mock).mockReturnValue({ add: mockAdd });
    });

    test('should render with no accessibility violations', async () => {
        const { container } = render(<PatientSearchActions disabled={false} />);
        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render the button in disabled state when disabled prop is true', () => {
        const { getByText } = render(<PatientSearchActions disabled={true} />);

        const button = getByText('Add new patient').closest('button');
        button?.click();
    });

    it('should call the add function when the button is clicked', () => {
        const { getByText } = render(<PatientSearchActions disabled={false} />);

        const button = getByText('Add new patient').closest('button');
        button?.click();
        expect(mockAdd).toHaveBeenCalledTimes(1);
    });

    it('should wrap the button with the Permitted component with correct permission', () => {
        const { container } = render(<PatientSearchActions disabled={false} />);

        const permittedWrapper = container.querySelector('.permitted-wrapper');
        expect(permittedWrapper).toHaveAttribute('data-permission', permissions.patient.add);
    });

    it('should initialize the focus hook', () => {
        render(<PatientSearchActions disabled={false} />);

        expect(useFocusAddNewPatientButton).toHaveBeenCalledTimes(1);
    })

});    