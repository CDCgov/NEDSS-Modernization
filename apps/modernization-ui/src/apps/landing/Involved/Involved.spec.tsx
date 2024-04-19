import { render } from "@testing-library/react"
import { Involved } from "./Involved"

describe('when Involved renders', () => {
    it('should display headings', () => {
        const { container } = render(<Involved />);
        const heading = container.getElementsByTagName('span')[0];
        expect(heading).toHaveTextContent('Get involved');
    });
});
