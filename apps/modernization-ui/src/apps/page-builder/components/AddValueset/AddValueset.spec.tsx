import { render } from '@testing-library/react';
import { AddValueset } from './AddValueset';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router-dom';

describe('General information component tests', () => {
    it('should display Add Valueset form', () => {
        const { getByTestId } = render(
            <BrowserRouter>
                <AlertProvider>
                    <AddValueset />
                </AlertProvider>
            </BrowserRouter>
        );
        expect(getByTestId('error-text').innerHTML).toBe('Value set code Not Valid');
    });
});
describe('When page loads', () => {
    it('Add to question button should be disabled', () => {
        const { container } = render(
            <BrowserRouter>
                <AlertProvider>
                    <AddValueset />
                </AlertProvider>
            </BrowserRouter>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled'));
    });
});

describe('Add valueset component tests', () => {
    it('should render a grid with 4 inputs labels which are Value set name, description and Code', () => {
        const { getByText } = render(
            <BrowserRouter>
                <AlertProvider>
                    <AddValueset />
                </AlertProvider>
            </BrowserRouter>
        );
        expect(getByText('Value set name')).toBeInTheDocument();
        expect(getByText('Value set description')).toBeTruthy();
        expect(getByText('Value set code')).toBeTruthy();
        expect(getByText('LOCAL')).toBeTruthy();
        expect(getByText('PHIN')).toBeTruthy();
    });
});

// describe('Submit valueset component tests', () => {
//     it('should require to validate input', () => {
//         const { getByText } = render(
//             <AlertProvider>
//                 <ImportTemplate />
//             </AlertProvider>
//         );
//         const setName =getByText('Value set name')
//         expect(getByText('Value set name')).toBeInTheDocument();
//         expect(getByText('Value set description')).toBeTruthy();
//         expect(getByText('Value set code')).toBeTruthy();
//         expect(getByText('LOCAL')).toBeTruthy();
//         expect(getByText('PHIN')).toBeTruthy();
//     });
// });
