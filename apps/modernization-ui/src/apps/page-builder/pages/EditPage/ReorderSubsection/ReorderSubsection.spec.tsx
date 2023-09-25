import { render } from "@testing-library/react";
import { ReorderSubsection } from "./ReorderSubsection";
import { PageSection } from "apps/page-builder/generated/models/PageSection";
import { PageSubSection } from "apps/page-builder/generated/models/PageSubSection";

describe('when ReorderSubsection renders', () => {
    const subsection: PageSubSection = {
        id: 123456,
        name: 'Test Section',
        pageQuestions: [{
            allowFutureDates: true,
            coInection: 'asdf',
            dataType: 'asdf',
            description: 'asdf',
            dispay: 'T',
            enabled: 'asdf',
            id: 123,
            labelOnScreen: 'asdf',
            mask: 'asdf',
            name: 'asdf',
            questionIdentifier: 'asdf',
            questionToolTip: 'asdf',
            questionType: 'asdf',
            required: 'asdf',
            subGroup: 'asdf',
        },{
            allowFutureDates: true,
            coInection: 'asdf',
            dataType: 'asdf',
            description: 'asdf',
            dispay: 'T',
            enabled: 'asdf',
            id: 123,
            labelOnScreen: 'asdf',
            mask: 'asdf',
            name: 'asdf',
            questionIdentifier: 'asdf',
            questionToolTip: 'asdf',
            questionType: 'asdf',
            required: 'asdf',
            subGroup: 'asdf',
        }],
        visible: 'T'
    }
    const { container } = render (
        <ReorderSubsection subsection={subsection} />
    )
    it('should display Questions', () => {
        const questions = container.getElementsByClassName('reorder-question');
        expect(questions.length).toEqual(2);
    });
});
