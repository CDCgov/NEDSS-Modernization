import { render } from '@testing-library/react';
import { PagesResponse } from 'apps/page-builder/generated';
import { EditPageHeader } from './EditPageHeader';

describe('when EditPageHeader renders', () => {
    const page: PagesResponse = {
        id: 123456,
        name: 'Test Page',
        description: 'Test Page description',
        tabs: [
            {
                id: 1119232,
                name: 'Patient',
                sections: [],
                visible: true
            },
            {
                id: 1119225,
                name: 'Vaccination',
                sections: [],
                visible: true
            }
        ],
        rules: []
    };
    const mockFunction = jest.fn();

    it('should display Page name', () => {
        const { getByText } = render(<EditPageHeader page={page} handleSaveDraft={mockFunction} />);

        expect(getByText('Test Page')).toBeInTheDocument();
    });

    it('should display Page description', () => {
        const { getByText } = render(<EditPageHeader page={page} handleSaveDraft={mockFunction} />);

        expect(getByText('Test Page description')).toBeInTheDocument();
    });

    it('should display link to classic preview', () => {
        const { container } = render(<EditPageHeader page={page} handleSaveDraft={mockFunction} />);

        const anchor = container.getElementsByTagName('a')[0];
        expect(anchor.href).toBe('http://localhost/nbs/PreviewPage.do?from=L&waTemplateUid=123456&method=viewPageLoad');
        expect(anchor.target).toBe('_blank');
        expect(anchor.rel).toBe('noreferrer');
    });
});
