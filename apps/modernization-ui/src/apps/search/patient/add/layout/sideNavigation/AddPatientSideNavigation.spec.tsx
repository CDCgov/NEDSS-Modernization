import { render } from '@testing-library/react';
import { AddPatientSideNavigation } from './AddPatientSideNavigation';

describe('AddPatientSideNavigation', () => {
    it('should render the side navigation title', () => {
        const { getByText } = render(<AddPatientSideNavigation />);

        expect(getByText('Data entry')).toBeInTheDocument();
    });

    it('should render all navigation entries', () => {
        const { getByText } = render(<AddPatientSideNavigation />);

        expect(getByText('New patient')).toBeInTheDocument();
        expect(getByText('New organization')).toBeInTheDocument();
        expect(getByText('New provider')).toBeInTheDocument();
        expect(getByText('Morbidity report')).toBeInTheDocument();
    });

    it('should have the correct href for external links', () => {
        const { getByText } = render(<AddPatientSideNavigation />);

        expect(getByText('New organization').closest('a')).toHaveAttribute(
            'href',
            '/nbs/MyTaskList1.do?ContextAction=GlobalOrganization'
        );
        expect(getByText('New provider').closest('a')).toHaveAttribute(
            'href',
            '/nbs/MyTaskList1.do?ContextAction=GlobalProvider'
        );
        expect(getByText('Morbidity report').closest('a')).toHaveAttribute(
            'href',
            '/nbs/MyTaskList1.do?ContextAction=AddMorbDataEntry'
        );
    });
});
