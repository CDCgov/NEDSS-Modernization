import { render } from '@testing-library/react';
import { useSearchFromAddPatient } from './useSearchFromAddPatient';

const TestComponent = () => {
    const { toSearch } = useSearchFromAddPatient();

    return (
        <div>
            <button onClick={() => toSearch('test criteria')}>Cancel</button>
            <div data-testid="patient-name">test</div>
            <div data-testid="patient-age">test</div>
        </div>
    );
};

describe('useSearchFromAddPatient', () => {
    it('should throw an error if outside of SearchFromAddPatientProvider', () => {
        const consoleError = jest.spyOn(console, 'error').mockImplementation(() => {});

        expect(() => render(<TestComponent />)).toThrow(
            'useSearchFromAddPatient must be used within a SearchFromAddPatientProvider'
        );

        consoleError.mockRestore();
    });
});
