import { render } from '@testing-library/react';
import { PagesSubSection } from 'apps/page-builder/generated';
import { SubsectionComponent } from './Subsection';
import { PageProvider } from 'page';
import { MemoryRouter } from 'react-router-dom';

describe('when Subsection renders', () => {
    const subsection: PagesSubSection = {
        id: 123456,
        name: 'Test Subsection',
        questions: [],
        visible: true
    };

    const { container } = render(
        <MemoryRouter>
            <PageProvider>
                <SubsectionComponent subsection={subsection} />
            </PageProvider>
        </MemoryRouter>
    );

    it('should display Subsection name', () => {
        const name = container.getElementsByTagName('h2');
        expect(name[0].innerHTML).toEqual('Test Subsection');
    });
});
