import { render } from "@testing-library/react";
import { AlertBanner } from "./AlertBanner"

describe('when AlertBanner renders', () => {
    it('should display success', () => {
        const { container } = render(
            <AlertBanner type="success" />
        );
        expect(container.getElementsByClassName('success').length).toBe(1);
    });
    it('should display warning', () => {
        const { container } = render(
            <AlertBanner type="warning" />
        );
        expect(container.getElementsByClassName('warning').length).toBe(1);
    });
    it('should display prompt', () => {
        const { container } = render(
            <AlertBanner type="prompt" />
        );
        expect(container.getElementsByClassName('prompt').length).toBe(1);
    });
    it('should display error', () => {
        const { container } = render(
            <AlertBanner type="error" />
        );
        expect(container.getElementsByClassName('error').length).toBe(1);
    });
});
