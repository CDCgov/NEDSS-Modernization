import { render } from '@testing-library/react';
import { PageDetails } from 'apps/page-builder/generated/models/PageDetails';
import { EditPageTabs } from './EditPageTabs';

describe('when EditPageTabs renders', () => {
    const page: PageDetails = {
        id: 123456,
        Name: 'Test Page',
        pageDescription: 'Test Page description',
        pageTabs: [{
            id: 1119232,
            name: "Patient",
            tabSections: [],
            visible: "T"
        },{
            id: 1119225,
            name: "Vaccination",
            tabSections: [],
            visible: "T"
        }],
        pageRules: []
    };
    const mockFunction = jest.fn();

    it('should display two tabs', () => {
        const { container } = render(
            <EditPageTabs tabs={page.pageTabs} active={1} setActive={mockFunction} />
        );
        const tabs = container.getElementsByClassName('edit-page-tabs__tab');
        
        expect(tabs.length).toEqual(3); // Includes 'Add new tab' tab
    });
});