import { render } from '@testing-library/react';
import { ReorderSubsection } from './ReorderSubsection';
import { PagesSubSection } from 'apps/page-builder/generated';

describe('when ReorderSubsection renders', () => {
    const subsection: PagesSubSection = {
        id: 123456,
        name: 'Test Section',
        questions: [
            {
                allowFutureDates: true,
                coInfection: true,
                dataType: 'asdf',
                description: 'asdf',
                display: true,
                enabled: true,
                id: 123,
                mask: 'asdf',
                name: 'asdf',
                question: 'asdf',
                tooltip: 'asdf',
                standard: 'asdf',
                required: true,
                subGroup: 'asdf'
            },
            {
                allowFutureDates: true,
                coInfection: false,
                dataType: 'asdf',
                description: 'asdf',
                display: true,
                enabled: true,
                id: 123,
                mask: 'asdf',
                name: 'asdf',
                question: 'asdf',
                tooltip: 'asdf',
                standard: 'asdf',
                required: false,
                subGroup: 'asdf'
            }
        ],
        visible: true
    };
    const { container } = render(<ReorderSubsection subsection={subsection} />);
    it('should display Questions', () => {
        const questions = container.getElementsByClassName('reorder-question');
        expect(questions.length).toEqual(2);
    });
});
