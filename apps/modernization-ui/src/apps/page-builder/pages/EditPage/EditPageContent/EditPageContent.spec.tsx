import { render } from '@testing-library/react';
import { PagesTab } from 'apps/page-builder/generated';
import { EditPageContentComponent } from './EditPageContent';
import { PageProvider } from 'page';

describe('when EditPageContent renders', () => {
    const content: PagesTab = {
        id: 123456,
        name: 'Test Name',
        sections: [
            {
                id: 1119232,
                name: 'Patient',
                subSections: [],
                visible: true
            },
            {
                id: 1119225,
                name: 'Vaccination',
                subSections: [],
                visible: true
            }
        ],
        visible: true
    };
    const mockFunction = jest.fn();

    it('should display two sections', () => {
        const { container } = render(
            <PageProvider>
                <EditPageContentComponent content={content} onContentChange={mockFunction} />
            </PageProvider>
        );
        const sections = container.getElementsByClassName('section');

        expect(sections.length).toEqual(2);
    });
});
