import { ButtonActionMenu } from "./ButtonActionMenu"
import { Button } from "components/button/Button";
import { render } from "@testing-library/react";

describe('When ButtonActionMenu renders', () => {
    const { container } = render(
        <ButtonActionMenu
            label="Add new"
            items={[
                { label: 'Add new patient', action: jest.fn() },
                { label: 'Add new lab report', action: jest.fn() }
            ]}
        />
    );
    it('should not display menu', () => {
        expect(container.getElementsByClassName('menu')).toHaveLength(0);
    })
})