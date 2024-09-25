import React from 'react';
import { render, screen, act } from '@testing-library/react';
import DedupeContextProvider, { useDedupeContext } from './DedupeContext';

const TestComponent = () => {
    const { mode, setMode } = useDedupeContext();

    return (
        <div>
            <div data-testid="mode">{mode}</div>
            <button onClick={() => setMode('blocking')}>Set Mode to Blocking</button>
        </div>
    );
};

describe('DedupeContextProvider', () => {
    it('should render children correctly', () => {
        render(
            <DedupeContextProvider>
                <div data-testid="child">Child Component</div>
            </DedupeContextProvider>
        );

        expect(screen.getByTestId('child')).toBeInTheDocument();
    });

    it('should have initial mode set to "patient"', () => {
        render(
            <DedupeContextProvider>
                <TestComponent />
            </DedupeContextProvider>
        );

        expect(screen.getByTestId('mode').textContent).toBe('patient');
    });

    it('should update the mode when setMode is called', () => {
        render(
            <DedupeContextProvider>
                <TestComponent />
            </DedupeContextProvider>
        );

        act(() => {
            screen.getByText('Set Mode to Blocking').click();
        });

        expect(screen.getByTestId('mode').textContent).toBe('blocking');
    });

    it('should throw an error if useDedupeContext is used outside the provider', () => {
        const consoleErrorSpy = jest.spyOn(console, 'error').mockImplementation(() => {}); // Suppress expected console error

        expect(() => {
            render(<TestComponent />);
        }).toThrow('useDedupeContext must be used inside DedupeContextProvider');

        consoleErrorSpy.mockRestore();
    });
});
