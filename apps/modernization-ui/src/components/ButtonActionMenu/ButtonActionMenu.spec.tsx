import { ButtonActionMenu } from "./ButtonActionMenu"
import { Button } from "components/button/Button";
import { render } from "@testing-library/react";

describe('When ButtonActionMenu renders', () => {
    const { container } = render(
        <ButtonActionMenu label="Add new">
            <>
                <Button type="button" onClick={() => jest.fn()}>Test this</Button>
                <Button type="button" onClick={() =>jest.fn() }>Test that</Button>
            </>
        </ButtonActionMenu>
    );
    it('should not display menu', () => {
        expect(container.getElementsByClassName('menu')).toHaveLength(0);
    })
})