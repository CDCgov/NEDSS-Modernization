import { render } from "@testing-library/react"
import { Vision } from "./Vision"

describe('when Vision renders', () => {
    it('should display 3 text blocks', () => {
        const { container } = render(<Vision />);
        const text = container.getElementsByTagName('p');
        expect(text).toHaveLength(3);
    });
});
