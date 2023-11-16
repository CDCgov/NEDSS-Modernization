import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { ImportTemplate } from './ImportTemplate';
const modal = { current: null, isShowing: false };

describe('General information component tests', () => {
    it('should display Import template form', () => {
        const { getByText } = render(<ImportTemplate modal={modal} onTemplateCreated={() => {}} />);
        expect(getByText('Import a new template')).toBeInTheDocument();
    });
});

describe('When page loads', () => {
    it('Import button should be disabled', () => {
        const { container } = render(
            <BrowserRouter>
                <ImportTemplate modal={modal} onTemplateCreated={() => {}} />
            </BrowserRouter>
        );
        const btn = container.getElementsByClassName('usa-button')[0];
        expect(btn.hasAttribute('disabled'));
    });
});

describe('Import Template component tests', () => {
    it('should render a grid with 1 inputs labels which is  Choose file ', () => {
        const { getByText } = render(<ImportTemplate modal={modal} onTemplateCreated={() => {}} />);
        expect(getByText('Choose file')).toBeInTheDocument();
    });
});
