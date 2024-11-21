import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';

import { defaultConfiguration } from 'configuration/defaults';
import { Features } from 'configuration';
import { displayProfileLegalName } from './displayProfileLegalName';
import { PatientSearchResult } from 'generated/graphql/schema';

let mockFeatures: Features = {
    ...defaultConfiguration.features
};

const withModernizedPatientProfile = (enabled: boolean) => ({
    ...defaultConfiguration.features,
    patient: {
        ...defaultConfiguration.features.patient,
        profile: {
            enabled: enabled
        }
    }
});

jest.mock('configuration', () => ({
    useConfiguration: () => ({ ready: false, loading: false, load: jest.fn(), features: mockFeatures })
}));

describe('when navigating to the patient profile from a list view search result', () => {
    describe('and the modernized patient profile is enabled', () => {
        it('should link to the modernized patient profile with the legal name as the link text', () => {
            mockFeatures = withModernizedPatientProfile(true);

            const searchResult: PatientSearchResult = {
                status: 'ACTIVE',
                patient: 307,
                shortId: 84001,
                legalName: {
                    first: 'John',
                    last: 'Doe'
                },
                addresses: [],
                detailedPhones: [],
                emails: [],
                identification: [],
                names: [],
                phones: []
            };

            const { getByText } = render(<BrowserRouter>{displayProfileLegalName(searchResult)}</BrowserRouter>);
            const link = getByText('Doe, John');
            expect(link).toHaveAttribute('href', '/patient-profile/84001/summary');
        });

        it('should link to the modernized patient profile with "No Data" as the link text when there is no legal name', () => {
            mockFeatures = withModernizedPatientProfile(true);

            const searchResult: PatientSearchResult = {
                status: 'ACTIVE',
                patient: 307,
                shortId: 84001,
                legalName: null,
                addresses: [],
                detailedPhones: [],
                emails: [],
                identification: [],
                names: [],
                phones: []
            };

            const { getByText } = render(<BrowserRouter>{displayProfileLegalName(searchResult)}</BrowserRouter>);
            const link = getByText('No Data');
            expect(link).toHaveAttribute('href', '/patient-profile/84001/summary');
        });
    });

    describe('and the modernized patient profile is disabled', () => {
        it('should link to the NBS6 patient profile with the legal name as the link text', () => {
            mockFeatures = withModernizedPatientProfile(false);

            const searchResult: PatientSearchResult = {
                status: 'ACTIVE',
                patient: 307,
                shortId: 84001,
                legalName: {
                    first: 'John',
                    last: 'Doe'
                },
                addresses: [],
                detailedPhones: [],
                emails: [],
                identification: [],
                names: [],
                phones: []
            };

            const { getByText } = render(<BrowserRouter>{displayProfileLegalName(searchResult)}</BrowserRouter>);
            const link = getByText('Doe, John');
            expect(link).toHaveAttribute('href', '/nbs/api/patient/307/file/redirect');
        });

        it('should link to the NBS6 patient profile with "No Data" as the link text when there is no legal name', () => {
            mockFeatures = withModernizedPatientProfile(false);

            const searchResult: PatientSearchResult = {
                status: 'ACTIVE',
                patient: 307,
                shortId: 84001,
                legalName: null,
                addresses: [],
                detailedPhones: [],
                emails: [],
                identification: [],
                names: [],
                phones: []
            };

            const { getByText } = render(<BrowserRouter>{displayProfileLegalName(searchResult)}</BrowserRouter>);
            const link = getByText('No Data');
            expect(link).toHaveAttribute('href', '/nbs/api/patient/307/file/redirect');
        });
    });
});
