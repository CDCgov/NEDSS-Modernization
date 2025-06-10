import { StandardButtonProps } from './buttons';
import { resolveClasses } from './resolveClasses';
import { resolveContent } from './resolveContent';

type ButtonProps = StandardButtonProps & Omit<JSX.IntrinsicElements['button'], 'children'>;

const Button = ({ type = 'button', ...remaining }: ButtonProps) => (
    <button className={resolveClasses(remaining)} {...remaining} type={type}>
        {resolveContent(remaining)}
    </button>
);

export { Button };
export type { ButtonProps };
