import { render } from "@testing-library/react";
import { ReorderSection } from "./ReorderSection";
import { PageSection } from "apps/page-builder/generated/models/PageSection";

describe('when ReorderSection renders', () => {
    const section: PageSection = {
        id: 123456,
        name: 'Test Section',
        sectionSubSections: [{
            id: 123,
            name: 'Subsection1',
            pageQuestions: [],
            visible: 'T'
        },{
            id: 456,
            name: 'Subsection2',
            pageQuestions: [],
            visible: 'T'
        }],
        visible: 'T'
    }
    it('should display Subsections', () => {
        const { container } = render (
            <ReorderSection section={section} />
        )
        const subsection = container.getElementsByClassName('reorder-subsection');
        expect(subsection.length).toEqual(2);
    });
});
