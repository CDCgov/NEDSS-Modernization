import { render, fireEvent } from '@testing-library/react';
import TabButton from './TabButton';

describe('TabButton', () => {
  test('renders button with correct title', () => {
    const title = 'Test Title';
    const { getByText } = render(<TabButton title={title} active={true} onClick={() => {}} />);
    const buttonElement = getByText(title);
    expect(buttonElement).toBeInTheDocument();
  });

  test('calls onClick handler when clicked', () => {
    const onClickMock = jest.fn();
    const { getByText } = render(<TabButton title="Test Title" active={true} onClick={onClickMock} />);
    const buttonElement = getByText('Test Title');
    fireEvent.click(buttonElement);
    expect(onClickMock).toHaveBeenCalled();
  });

  test('applies active class when active prop is true', () => {
    const { getByText } = render(<TabButton title="Test Title" active={true} onClick={() => {}} />);
    const buttonElement = getByText('Test Title');
    expect(buttonElement).toHaveClass('active');
  });

  test('does not apply active class when active prop is false', () => {
    const { getByText } = render(<TabButton title="Test Title" active={false} onClick={() => {}} />);
    const buttonElement = getByText('Test Title');
    expect(buttonElement).not.toHaveClass('active');
  });
});
