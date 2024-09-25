import React from 'react';
import { render, screen, act } from '@testing-library/react';
import DataElementsContextProvider, { useDataElementsContext } from './DataElementsContext';

const TestComponent = () => {
    const { dataElements, setDataElements, belongingnessRatio, setBelongingnessRatio } = useDataElementsContext();
    
    return (
        <div>
            <div data-testid="dataElements">{JSON.stringify(dataElements)}</div>
            <div data-testid="belongingnessRatio">{belongingnessRatio}</div>
            <button onClick={() => setDataElements([{
                id: '1',
                name: 'element1',
                label: 'Element 1',
                category: 'Category 1',
                active: true,
                m: 0.5,
                u: 0.5,
                threshold: 0.5,
                oddsRatio: 0.5 / 0.5,
                logOdds: Math.log(0.5 / 0.5)
                }])}>Set Data Elements</button>
            <button onClick={() => setBelongingnessRatio(0.75)}>Set Belongingness Ratio</button>
        </div>
    );
};

describe('DataElementsContextProvider', () => {
    beforeEach(() => {
        localStorage.clear();
    });

    it('should render the children correctly', () => {
        render(
            <DataElementsContextProvider>
                <div data-testid="child">Child Component</div>
            </DataElementsContextProvider>
        );
        expect(screen.getByTestId('child')).toBeInTheDocument();
    });

    it('should have blank as initial state for dataElements and belongingnessRatio', () => {
        render(
            <DataElementsContextProvider>
                <TestComponent />
            </DataElementsContextProvider>
        );
        
        expect(screen.getByTestId('dataElements').textContent).toBe('');
        expect(screen.getByTestId('belongingnessRatio').textContent).toBe('');
    });

    it('should load dataElements from localStorage if present', () => {
        const mockDataElements = [{ id: '1', name: 'Mock Element' }];
        localStorage.setItem('dataElements', JSON.stringify(mockDataElements));

        render(
            <DataElementsContextProvider>
                <TestComponent />
            </DataElementsContextProvider>
        );

        expect(screen.getByTestId('dataElements').textContent).toBe(JSON.stringify(mockDataElements));
    });

    it('should update dataElements and belongingnessRatio when set functions are called', () => {
        render(
            <DataElementsContextProvider>
                <TestComponent />
            </DataElementsContextProvider>
        );
    
        act(() => {
            screen.getByText('Set Data Elements').click();
            screen.getByText('Set Belongingness Ratio').click();
        });
    
        const expectedDataElements = [
            {
                id: '1',
                name: 'element1',
                label: 'Element 1',
                category: 'Category 1',
                active: true,
                m: 0.5,
                u: 0.5,
                threshold: 0.5,
                oddsRatio: 1,
                logOdds: 0
            },
        ];
    
        expect(screen.getByTestId('dataElements').textContent).toBe(JSON.stringify(expectedDataElements));
        expect(screen.getByTestId('belongingnessRatio').textContent).toBe('0.75');
    });
});
