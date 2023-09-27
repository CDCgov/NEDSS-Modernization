import { render } from '@testing-library/react';
import { PagedDetail } from 'apps/page-builder/generated';
import { EditPageHeader } from './EditPageHeader';

describe('when EditPageHeader renders', () => {
    const page: PagedDetail = {
        id: 123456,
        Name: 'Test Page',
        pageDescription: 'Test Page description',
        pageTabs: [
            {
                id: 1119232,
                name: 'Patient',
                tabSections: [],
                visible: 'T'
            },
            {
                id: 1119225,
                name: 'Vaccination',
                tabSections: [],
                visible: 'T'
            }
        ],
        pageRules: []
    };
    it('should display Page name', () => {
        const { container } = render(<EditPageHeader page={page} />);
        const header = container.getElementsByTagName('h2');

        expect(header[0].innerHTML).toEqual('Test Page');
    });
    it('should display Page description', () => {
        const { container } = render(<EditPageHeader page={page} />);
        const header = container.getElementsByTagName('h4');

        expect(header[0].innerHTML).toEqual('Test Page description');
    });
});
