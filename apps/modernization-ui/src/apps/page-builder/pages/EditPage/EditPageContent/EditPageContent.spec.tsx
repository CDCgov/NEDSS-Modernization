import { render } from '@testing-library/react';
import { PagesTab } from 'apps/page-builder/generated';
import { EditPageContentComponent } from './EditPageContent';

describe('when EditPageContent renders', () => {
    const content: PagesTab = {
        id: 123456,
        name: 'Test Name',
        order: 1,
        sections: [
            {
                id: 1119232,
                name: 'Patient',
                visible: true,
                order: 1,
                subSections: []
            },
            {
                id: 1119225,
                name: 'Vaccination',
                visible: true,
                order: 2,
                subSections: []
            }
        ],
        visible: true
    };
    const mockFunction = jest.fn();

    it('should display two sections', () => {
        const { container } = render(<EditPageContentComponent content={content} onAddSection={mockFunction} />);
        const sections = container.getElementsByClassName('section');

        expect(sections.length).toEqual(2);
    });
});
