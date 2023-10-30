import { render } from '@testing-library/react';
import { ReorderSection } from './ReorderSection';
import { PagesSection } from 'apps/page-builder/generated';

describe('when ReorderSection renders', () => {
    const section: PagesSection = {
        id: 123456,
        name: 'Test Section',
        subSections: [
            {
                id: 123,
                name: 'Subsection1',
                questions: [],
                visible: true
            },
            {
                id: 456,
                name: 'Subsection2',
                questions: [],
                visible: true
            }
        ],
        visible: true
    };
    it('should display Subsections', () => {
        const { container } = render(<ReorderSection section={section} />);
        const subsection = container.getElementsByClassName('reorder-subsection');
        expect(subsection.length).toEqual(2);
    });
});
