import { render } from '@testing-library/react';
import { ImportTemplate } from './ImportTemplate';
import { BrowserRouter } from 'react-router-dom';

describe('General information component tests', () => {
    it('should display Import template form', () => {
        const { getByTestId } = render(<ImportTemplate />);
        expect(getByTestId('header-title').innerHTML).toBe('Import template');
    });
});

describe('When page loads', () => {
    it('Import button should be disabled', () => {
        const { container } = render(
            <BrowserRouter>
                <ImportTemplate />
            </BrowserRouter>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled'));
    });
});

describe('Import Template component tests', () => {
    it('should render a grid with 1 inputs labels which is  Choose file ', () => {
        const { getByText } = render(<ImportTemplate />);
        expect(getByText('Choose file')).toBeInTheDocument();
    });
});
