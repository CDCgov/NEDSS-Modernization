import { render } from '@testing-library/react';
import { PagesSection } from 'apps/page-builder/generated';
import { SectionComponent } from './Section';

describe('when Section renders', () => {
    const section: PagesSection = {
        id: 123456,
        name: 'Test Section',
        order: 1,
        subSections: [
            {
                id: 1234,
                name: 'Sub 1',
                visible: true,
                order: 1,
                questions: []
            },
            {
                id: 1234,
                name: 'Sub 1',
                visible: true,
                order: 2,
                questions: []
            }
        ],
        visible: true
    };
    const mockFunction = jest.fn();

    const { container } = render(<SectionComponent section={section} onAddSection={mockFunction} />);

    it('should display the Section name', () => {
        const name = container.getElementsByTagName('h2');
        expect(name[0].innerHTML).toEqual('Test Section');
    });

    it('should display two Subsections', () => {
        const subsections = container.getElementsByClassName('subsection');
        expect(subsections.length).toEqual(0);
    });
});
