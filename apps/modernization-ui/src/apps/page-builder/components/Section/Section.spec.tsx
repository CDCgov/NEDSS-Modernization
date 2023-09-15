import { render } from '@testing-library/react';
import { Section } from 'apps/page-builder/generated/models/Section';
import { SectionComponent } from './Section';

describe('when Section renders', () => {
    const section: Section = {
        id: 123456,
        name: 'Test Section',
        sectionSubSections: [{
            id: 1234,
            name: 'Sub 1',
            pageQuestions: [],
            visible: 'T'
        },
        {
            id: 1234,
            name: 'Sub 1',
            pageQuestions: [],
            visible: 'T'
        }],
        visible: 'T'
    };
    const mockFunction = jest.fn();

    const { container } = render(
        <SectionComponent section={section} onAddSection={mockFunction} />
    );

    it('should display the Section name', () => {
        const name = container.getElementsByTagName('h2');
        expect(name[0].innerHTML).toEqual('Test Section');
    });

    it('should display two Subsections', () => {
        const subsections = container.getElementsByClassName('subsection');
        expect(subsections.length).toEqual(0);
    });
});
