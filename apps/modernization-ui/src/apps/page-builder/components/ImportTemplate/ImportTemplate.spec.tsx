import { render } from '@testing-library/react';
import { ImportTemplate } from './ImportTemplate';
import { BrowserRouter } from 'react-router-dom';
import React from 'react';

describe('General information component tests', () => {
    it('should display Import template form', () => {
        const ref = React.useRef(null);
        const { getByTestId } = render(<ImportTemplate modal={ref} onTemplateCreated={() => {}} />);
        expect(getByTestId('header-title').innerHTML).toBe('Import template');
    });
});

describe('When page loads', () => {
    it('Import button should be disabled', () => {
        const ref = React.useRef(null);
        const { container } = render(
            <BrowserRouter>
                <ImportTemplate modal={ref} onTemplateCreated={() => {}} />
            </BrowserRouter>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled'));
    });
});

describe('Import Template component tests', () => {
    it('should render a grid with 1 inputs labels which is  Choose file ', () => {
        const ref = React.useRef(null);
        const { getByText } = render(<ImportTemplate modal={ref} onTemplateCreated={() => {}} />);
        expect(getByText('Choose file')).toBeInTheDocument();
    });
});
