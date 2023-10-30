import { render } from '@testing-library/react';
import { ReorderModal } from './ReorderModal';
import { PagesTab } from 'apps/page-builder/generated';

describe('when ReorderModal renders', () => {
    const content: PagesTab = {
        id: 123456,
        name: 'Test Page',
        sections: [
            {
                id: 1234,
                name: 'Section1',
                subSections: [],
                visible: true
            },
            {
                id: 5678,
                name: 'Section2',
                subSections: [],
                visible: true
            }
        ],
        visible: true
    };
    const props = {
        modalRef: { current: null },
        pageName: 'Test Page',
        content: content
    };
    it('should display Sections', () => {
        const { getByText } = render(<ReorderModal {...props} />);
        expect(getByText('Test Page')).toBeTruthy();
    });
});
