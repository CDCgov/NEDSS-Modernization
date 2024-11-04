import { render } from '@testing-library/react';
import { AddPatientInPageNavigation } from './AddPatientInPageNavigation';

const sections = [
    { id: 'administrative', label: 'Administrative' },
    { id: 'names', label: 'Name' },
    { id: 'addresses', label: 'Address' },
    { id: 'phoneEmails', label: 'Phone & email' },
    { id: 'identifications', label: 'Identification' },
    { id: 'races', label: 'Race' },
    { id: 'ethnicity', label: 'Ethnicity' },
    { id: 'sexAndBirth', label: 'Sex & birth' },
    { id: 'mortality', label: 'Mortality' },
    { id: 'generalInformation', label: 'General patient information' }
];

class MockIntersectionObserver {
    observe = jest.fn();
    unobserve = jest.fn();
    disconnect = jest.fn();
    constructor(callback: (entries: IntersectionObserverEntry[]) => void) {
        callback([{ isIntersecting: true, target: { id: 'section1' } } as IntersectionObserverEntry]);
    }
}

Object.defineProperty(window, 'IntersectionObserver', {
    writable: true,
    configurable: true,
    value: MockIntersectionObserver
});

describe('AddPatientExtendedInPageNav', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('should render all section labels', () => {
        const { getByText } = render(<AddPatientInPageNavigation sections={sections} />);

        sections.forEach((section) => {
            expect(getByText(section.label)).toBeInTheDocument();
        });
    });

    it('should render the navigation title', () => {
        const { getByText } = render(<AddPatientInPageNavigation sections={sections} />);

        expect(getByText('On this page')).toBeInTheDocument();
    });
});
