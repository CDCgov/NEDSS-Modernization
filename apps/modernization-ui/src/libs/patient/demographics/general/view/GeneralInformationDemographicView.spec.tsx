import { render, screen } from '@testing-library/react';
import { GeneralInformationDemographicView } from './GeneralInformationDemographicView';

describe('GeneralInformationDemographicView', () => {
    it('should display "As of" when present', () => {
        render(<GeneralInformationDemographicView demographic={{ asOf: '2020-03-17' }} />);

        const actual = screen.getByRole('definition', { name: 'As of' });

        expect(actual).toHaveTextContent('03/17/2020');
    });

    it('should display "Marital status" when present', () => {
        render(
            <GeneralInformationDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    maritalStatus: {
                        name: 'marital status name',
                        value: 'marital-status-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Marital status' });

        expect(actual).toHaveTextContent('marital status name');
    });

    it(`should display "Mother's maiden name" when present`, () => {
        render(
            <GeneralInformationDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    maternalMaidenName: 'maternal maiden name value'
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: "Mother's maiden name" });

        expect(actual).toHaveTextContent('maternal maiden name value');
    });

    it('should display "Number of adults in residence" when present', () => {
        render(
            <GeneralInformationDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    adultsInResidence: 1049
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Number of adults in residence' });

        expect(actual).toHaveTextContent('1049');
    });

    it('should display "Number of children in residence" when present', () => {
        render(
            <GeneralInformationDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    childrenInResidence: 1063
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Number of children in residence' });

        expect(actual).toHaveTextContent('1063');
    });

    it('should display "Primary occupation" when present', () => {
        render(
            <GeneralInformationDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    primaryOccupation: {
                        name: 'primary occupation name',
                        value: 'primary-occupation-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Primary occupation' });

        expect(actual).toHaveTextContent('primary occupation name');
    });

    it('should display "Highest level of education" when present', () => {
        render(
            <GeneralInformationDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    educationLevel: {
                        name: 'education level name',
                        value: 'education-level-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Highest level of education' });

        expect(actual).toHaveTextContent('education level name');
    });

    it('should display "Primary language" when present', () => {
        render(
            <GeneralInformationDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    primaryLanguage: {
                        name: 'primary language name',
                        value: 'primary-language-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Primary language' });

        expect(actual).toHaveTextContent('primary language name');
    });

    it('should display "Speaks English" when present', () => {
        render(
            <GeneralInformationDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    speaksEnglish: {
                        name: 'speaks english name',
                        value: 'speaks-english-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Speaks English' });

        expect(actual).toHaveTextContent('speaks english name');
    });

    it('should display "State HIV case ID" when present and allowed', () => {
        render(
            <GeneralInformationDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    stateHIVCase: {
                        value: 'state-HIV-case-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'State HIV case ID' });

        expect(actual).toHaveTextContent('state-HIV-case-value');
    });

    it('should not display "State HIV case ID" when present and restricted', () => {
        render(
            <GeneralInformationDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    stateHIVCase: {
                        reason: 'restricted'
                    }
                }}
            />
        );

        const actual = screen.queryByRole('definition', { name: 'State HIV case ID' });

        expect(actual).not.toBeInTheDocument();
    });
});
