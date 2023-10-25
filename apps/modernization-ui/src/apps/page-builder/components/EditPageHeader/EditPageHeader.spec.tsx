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
        const { container } = render(<EditPageHeader page={page} handleSaveDraft={mockFunction} />);
        const header = container.getElementsByTagName('h2');

        expect(header[0].innerHTML).toEqual('Test Page');
    });

    it('should display Page description', () => {
        const { container } = render(<EditPageHeader page={page} handleSaveDraft={mockFunction} />);
        const header = container.getElementsByTagName('h4');

        expect(header[0].innerHTML).toEqual('Test Page description');
    });
});
