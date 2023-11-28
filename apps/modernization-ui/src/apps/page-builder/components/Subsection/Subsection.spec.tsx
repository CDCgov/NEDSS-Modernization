import { render } from '@testing-library/react';
import { PagesSubSection } from 'apps/page-builder/generated';
import { SubsectionComponent } from './Subsection';
import { PageProvider } from 'page';

describe('when Subsection renders', () => {
    const subsection: PagesSubSection = {
        id: 123456,
        name: 'Test Subsection',
        questions: [],
        visible: true
    };

    const { container } = render(
        <PageProvider>
            <SubsectionComponent subsection={subsection} />
        </PageProvider>
    );

    it('should display Subsection name', () => {
        const name = container.getElementsByTagName('h2');
        expect(name[0].innerHTML).toEqual('Test Subsection');
    });
});
