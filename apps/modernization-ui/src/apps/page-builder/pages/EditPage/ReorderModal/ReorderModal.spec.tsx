import { getByText, render } from '@testing-library/react';
import { PageTab } from "apps/page-builder/generated/models/PageTab"
import { ReorderModal } from "./ReorderModal";


describe('when ReorderModal renders', () => {
    const content: PageTab = {
        id: 123456,
        name: 'Test Page',
        tabSections:  [
            {
                id: 1234,
                name: 'Section1',
                sectionSubSections: [],
                visible: 'T'
            },
            {
                id: 5678,
                name: 'Section2',
                sectionSubSections: [],
                visible: 'T'
            }
        ],
        visible: 'T'
    };
    const props = {
        modalRef: { current: null },
        pageName: 'Test Page',
        content: content
    }
    it('should display Sections', () => {
        const { getByText } = render(
            <ReorderModal {...props} />
        )
        expect(getByText('Test Page')).toBeTruthy();
    });
});
