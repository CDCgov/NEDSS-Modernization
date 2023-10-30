import { render } from '@testing-library/react';
import { ReorderQuestion } from './ReorderQuestion';
import { PagesQuestion } from 'apps/page-builder/generated';

describe('when ReorderSubsection renders', () => {
    const question: PagesQuestion = {
        allowFutureDates: true,
        coInfection: true,
        dataType: 'asdf',
        description: 'asdf',
        display: true,
        enabled: true,
        id: 123,
        mask: 'asdf',
        name: 'Test Question',
        question: 'asdf',
        tooltip: 'asdf',
        standard: 'asdf',
        required: false,
        subGroup: 'asdf'
    };
    const { getByText } = render(<ReorderQuestion question={question} />);
    it('should display Question Name', () => {
        expect(getByText('Test Question')).toBeTruthy();
    });
});
