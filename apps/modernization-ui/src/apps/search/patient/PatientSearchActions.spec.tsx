import { permissions } from "libs/permission";
import { useAddPatientFromSearch } from "./add"
import { useFocusAddNewPatientButton } from "./useFocusAddNewPatientButton";
import { PatientSearchActions } from "./PatientSearchActions";
import { render } from "@testing-library/react";
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
});    