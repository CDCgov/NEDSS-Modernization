import { render } from "@testing-library/react"
import { About } from "./About"

describe('when About renders', () => {
    it('should display headings', () => {
        const { container } = render(<About />);
        const heading = container.getElementsByTagName('h2')[0];
        expect(heading).toHaveTextContent('Documentation');
    });
});
