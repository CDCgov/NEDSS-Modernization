import { MemoryRouter } from 'react-router-dom';
import { render } from '@testing-library/react';
import { displayGender, displayProfileLink } from './BasicPatient';

describe('when displaying a search result with basic patient information', () => {
    it('should resolve the patient name when present', () => {
        const patient = {
            firstName: 'patient-first-name',
            lastName: 'patient-last-name',
            shortId: 919
        };

        const { getByRole } = render(<MemoryRouter>{displayProfileLink(patient)}</MemoryRouter>);

        const actual = getByRole('link', { name: 'patient-first-name patient-last-name' });

        expect(actual).toHaveAttribute('href', '/patient-profile/919');
    });

    it('should resolve the patient name as No Data when not present', () => {
        const patient = {
            shortId: 919
        };

        const { getByRole } = render(<MemoryRouter>{displayProfileLink(patient)}</MemoryRouter>);

        const actual = getByRole('link', { name: 'No Data' });

        expect(actual).toHaveAttribute('href', '/patient-profile/919');
    });

    it('should resolve the patient gender when present', () => {
        const result = {
            currSexCd: 'M'
        };

        const actual = displayGender(result);

        expect(actual).toEqual('Male');
    });

    it('should resolve the patient gender to Other when not present', () => {
        const result = {};

        const actual = displayGender(result);

        expect(actual).toEqual('Unknown');
    });
});
