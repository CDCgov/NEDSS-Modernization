import { Option } from "generated";
import { SegmentedButtons } from "./SegmentedButtons"
import { render } from "@testing-library/react"

describe('when SegmentedButtons renders', () => {

    it('should display three buttons', () => {
        const buttons: Option[] = [
            {name: "one", value: "1", label: "one"},
            {name: "two", value: "2", label: "two"},
            {name: "three", value: "3", label: "three"},
        ];
        const value = "1";
        const { container } = render(<SegmentedButtons buttons={buttons} value={value} />);
        const buttonArray = container.getElementsByTagName('button');
        expect((buttonArray.length)).toBe(3);
        expect(buttonArray[0]).toHaveClass('active');
    });
});
