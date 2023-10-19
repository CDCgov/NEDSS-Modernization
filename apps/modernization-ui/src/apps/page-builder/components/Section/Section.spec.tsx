import { render } from '@testing-library/react';
import { PagesSection } from 'apps/page-builder/generated';
import { SectionComponent } from './Section';
import { BrowserRouter } from 'react-router-dom';

describe('when Section renders', () => {
    const section: PagesSection = {
        id: 123456,
        name: 'Test Section',
        subSections: [
            {
                id: 1234,
                name: 'Sub 1',
                questions: [],
                visible: true
            },
            {
                id: 1234,
                name: 'Sub 1',
                questions: [],
                visible: true
            }
        ],
        visible: true
    };
    const mockFunction = jest.fn();

    const { container } = render(
        <BrowserRouter>
            <SectionComponent section={section} onAddSection={mockFunction} />
        </BrowserRouter>
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
