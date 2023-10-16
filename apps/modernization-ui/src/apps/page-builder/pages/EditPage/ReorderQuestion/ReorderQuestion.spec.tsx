import { render } from "@testing-library/react";
import { ReorderQuestion } from "./ReorderQuestion";
import { PageQuestion } from "apps/page-builder/generated/models/PageQuestion";

describe('when ReorderSubsection renders', () => {
    const question: PageQuestion = {
        allowFutureDates: true,
        coInection: 'asdf',
        dataType: 'asdf',
        description: 'asdf',
        dispay: 'T',
        enabled: 'asdf',
        id: 123,
        labelOnScreen: 'asdf',
        mask: 'asdf',
        name: 'Test Question',
        questionIdentifier: 'asdf',
        questionToolTip: 'asdf',
        questionType: 'asdf',
        required: 'asdf',
        subGroup: 'asdf',
    }
    const { getByText } = render (
        <ReorderQuestion question={question} />
    )
    it('should display Question Name', () => {
        expect(getByText('Test Question')).toBeTruthy();
    });
});
