import { fireEvent, render } from '@testing-library/react';
import { AlertProvider } from 'alert';
import { BrowserRouter } from 'react-router-dom';
import { AddTab } from './index';

describe('When page loads', () => {
    it('Create and add to page button should be disabled', () => {
        const { container } = render(
            <BrowserRouter>
                <AddTab />
            </BrowserRouter>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled'));
    });
});

describe('Add tabs component tests', () => {
    it('should render a grid with 3 inputs labels which are Tab Name, Tab Description, Visible', () => {
        const { getByText } = render(
            <AlertProvider>
                <AddTab />
            </AlertProvider>
        );
        expect(getByText('Tab Name')).toBeInTheDocument();
        expect(getByText('Tab Description')).toBeInTheDocument();
        expect(getByText('Visible')).toBeInTheDocument();
    });

    it('Check validation', () => {
        const { getByTestId, queryByText } = render(
            <AlertProvider>
                <AddTab />
            </AlertProvider>
        );
        const nameElement = getByTestId('tab-name');
        fireEvent.change(nameElement, { target: { value: 'tabs' } });
        fireEvent.blur(nameElement);
        const nameErrorText = queryByText('Tab Name Not Valid');
        expect(nameErrorText).not.toBeInTheDocument();
    });
});
