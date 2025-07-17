import { StandardButtonProps, withoutStandardButtonProperties } from './buttons';
import { resolveClasses } from './resolveClasses';
import { resolveContent } from './resolveContent';

type ButtonProps = StandardButtonProps & Omit<JSX.IntrinsicElements['button'], 'children'>;

const Button = ({ type = 'button', ...remaining }: ButtonProps) => (
    <button {...withoutStandardButtonProperties(remaining)} className={resolveClasses(remaining)} type={type}>
        {resolveContent(remaining)}
    </button>
);

export { Button };
export type { ButtonProps, StandardButtonProps };
