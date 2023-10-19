import { render } from '@testing-library/react';
import { PagesSubSection } from 'apps/page-builder/generated';
import { SubsectionComponent } from './Subsection';
import { BrowserRouter } from 'react-router-dom';

describe('when Subsection renders', () => {
    const subsection: PagesSubSection = {
        id: 123456,
        name: 'Test Subsection',
        questions: [],
        visible: true
    };
    const { container } = render(
        <BrowserRouter>
            <SubsectionComponent subsection={subsection} />
        </BrowserRouter>
    );

    it('should display Subsection name', () => {
        const name = container.getElementsByTagName('h2');
        expect(name[0].innerHTML).toEqual('Test Subsection');
    });
});
