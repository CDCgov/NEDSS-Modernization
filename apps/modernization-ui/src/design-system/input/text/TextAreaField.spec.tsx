import { render } from "@testing-library/react";
import { TextAreaField } from "./TextAreaField";
import { axe } from "jest-axe";

describe('when entering text values for a field', () => {
  it('should render with no accessibility violations', async () => {
      const { container } = render(<TextAreaField id={'testing-input'} label={'Numeric Input test'} />);

      expect(await axe(container)).toHaveNoViolations();
  });

  it('should set required attribute when required is true', () => {
      const { getByRole } = render(<TextAreaField id={'testing-input'} label={'Test TextArea test'} required />);
      const textarea = getByRole('textbox', { name: 'Test TextArea test' });
      expect(textarea).toHaveAttribute('required');
  });

  it('should set aria-required attribute when required is true', () => {
      const { getByRole } = render(<TextAreaField id={'testing-input'} label={'Test TextArea test'} required />);
      const textarea = getByRole('textbox', { name: 'Test TextArea test' });
      expect(textarea).toHaveAttribute('aria-required', 'true');
  });
});