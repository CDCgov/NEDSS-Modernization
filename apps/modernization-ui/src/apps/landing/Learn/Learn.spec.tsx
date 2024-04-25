import { render } from "@testing-library/react"
import { Learn } from "./Learn"

describe('when About renders', () => {
    it('should display headings', () => {
        const { container } = render(<Learn />);
        const heading = container.getElementsByTagName('h2')[0];
        expect(heading).toHaveTextContent('Website');
    });
});
