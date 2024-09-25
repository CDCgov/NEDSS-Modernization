import React from 'react';
import { render, screen, act } from '@testing-library/react';
import PatientMatchContextProvider, { usePatientMatchContext } from './PatientMatchContext';
import { BlockingCriteria, MatchingCriteria } from '../types';

// Test component to consume and manipulate context
const TestComponent = () => {
    const {
        blockingCriteria,
        setBlockingCriteria,
        removeBlockingCriteria,
        matchingCriteria,
        setMatchingCriteria,
        removeMatchingCriteria,
        totalLogOdds,
        setTotalLogOdds,
        lowerBound,
        upperBound,
        saveBounds
    } = usePatientMatchContext();

    return (
        <div>
            <div data-testid="blockingCriteria">{JSON.stringify(blockingCriteria)}</div>
            <div data-testid="matchingCriteria">{JSON.stringify(matchingCriteria)}</div>
            <div data-testid="totalLogOdds">{totalLogOdds}</div>
            <div data-testid="lowerBound">{lowerBound}</div>
            <div data-testid="upperBound">{upperBound}</div>
            <button onClick={() => setBlockingCriteria([{
                field: { name: 'LastName', label: 'Last Name', category: 'Personal', active: true, m: 0.9, u: 0.1, threshold: 0.5, oddsRatio: 0.5 / 0.5, logOdds: Math.log(0.5 / 0.5) },
                method: { value: 'EQUALS', name: 'Equals' }
                }])}>
                Set Blocking Criteria
            </button>

            <button onClick={() => setMatchingCriteria([{
                field: { name: 'FirstName', label: 'First Name', category: 'Personal', active: true, m: 0.8, u: 0.2, threshold: 0.5, oddsRatio: 0.5 / 0.5, logOdds: Math.log(0.5 / 0.5) },
                method: { value: 'STARTS_WITH', name: 'Starts with' }
                }])}>
                Set Matching Criteria
            </button>
            <button onClick={() => removeBlockingCriteria('LastName')}>Remove Blocking Criteria</button>
            <button onClick={() => removeMatchingCriteria('FirstName')}>Remove Matching Criteria</button>
            <button onClick={() => setTotalLogOdds(0.75)}>Set Total Log Odds</button>
            <button onClick={() => saveBounds(1, 10)}>Save Bounds</button>
        </div>
    );
};

describe('PatientMatchContextProvider', () => {
    it('should render children correctly', () => {
        render(
            <PatientMatchContextProvider>
                <div data-testid="child">Child Component</div>
            </PatientMatchContextProvider>
        );
        expect(screen.getByTestId('child')).toBeInTheDocument();
    });

    it('should have initial state values', () => {
        render(
            <PatientMatchContextProvider>
                <TestComponent />
            </PatientMatchContextProvider>
        );

        expect(screen.getByTestId('blockingCriteria').textContent).toBe('[]');
        expect(screen.getByTestId('matchingCriteria').textContent).toBe('[]');
        expect(screen.getByTestId('totalLogOdds').textContent).toBe('');
        expect(screen.getByTestId('lowerBound').textContent).toBe('');
        expect(screen.getByTestId('upperBound').textContent).toBe('');
    });

    it('should update blockingCriteria and matchingCriteria correctly', () => {
        render(
            <PatientMatchContextProvider>
                <TestComponent />
            </PatientMatchContextProvider>
        );

        act(() => {
            screen.getByText('Set Blocking Criteria').click();
            screen.getByText('Set Matching Criteria').click();
        });

        expect(screen.getByTestId('blockingCriteria').textContent).toBe(
            JSON.stringify([{ field: { name: 'LastName', label: 'Last Name' }, method: { value: 'EQUALS', name: 'Equals' } }])
        );
        expect(screen.getByTestId('matchingCriteria').textContent).toBe(
            JSON.stringify([{ field: { name: 'FirstName', label: 'First Name' }, method: { value: 'STARTS_WITH', name: 'Starts with' } }])
        );
    });

    it('should remove blockingCriteria and matchingCriteria correctly', () => {
        render(
            <PatientMatchContextProvider>
                <TestComponent />
            </PatientMatchContextProvider>
        );

        act(() => {
            screen.getByText('Set Blocking Criteria').click();
            screen.getByText('Set Matching Criteria').click();
        });

        act(() => {
            screen.getByText('Remove Blocking Criteria').click();
            screen.getByText('Remove Matching Criteria').click();
        });

        expect(screen.getByTestId('blockingCriteria').textContent).toBe('[]');
        expect(screen.getByTestId('matchingCriteria').textContent).toBe('[]');
    });

    it('should update totalLogOdds and save bounds correctly', () => {
        render(
            <PatientMatchContextProvider>
                <TestComponent />
            </PatientMatchContextProvider>
        );

        act(() => {
            screen.getByText('Set Total Log Odds').click();
            screen.getByText('Save Bounds').click();
        });

        expect(screen.getByTestId('totalLogOdds').textContent).toBe('0.75');
        expect(screen.getByTestId('lowerBound').textContent).toBe('1');
        expect(screen.getByTestId('upperBound').textContent).toBe('10');
    });
});
