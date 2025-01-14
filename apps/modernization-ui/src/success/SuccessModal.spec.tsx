import { render } from "@testing-library/react"
import { SuccessModal } from "./SuccessModal"
import { Button } from "@trussworks/react-uswds";

describe('when SuccessModal renders', () => {
    const modal = { current: null };
    const { container } = render(
        <SuccessModal
        modal={modal}
        title="Test heading"
        actions={
            <>
                <Button
                    type="button"
                    outline
                    onClick={() => jest.fn()}>
                    Test 1
                </Button>
                <Button
                    type="button"
                    outline
                    onClick={() => jest.fn()}>
                    Test 2
                </Button>
                <Button
                    type="button"
                    onClick={() => jest.fn()}>
                    Test 3
                </Button>
            </>
        }>
        <h3>Test subheader</h3>
        <p>Test descrption.</p>
    </SuccessModal>
    );

    it('should display heading title', () => {
        const heading = container.getElementsByTagName('h2');
        expect(heading).toBeTruthy();
    });

    it('should display modal content', () => {
        const subheading = container.getElementsByTagName('h3');
        expect(subheading).toBeTruthy();
    });

    it('should display action buttons', () => {
        const actions = container.getElementsByTagName('button');
        expect(actions).toBeTruthy();
    })
})