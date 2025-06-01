import { StandardButtonProps } from './buttons';
import { resolveClasses } from './resolveClasses';
import { resolveContent } from './resolveContent';

type ButtonProps = {
    /** Deprecated - replaced by secondary */
    outline?: boolean;
    /** Deprecated - replaced by tertiary */
    unstyled?: boolean;
} & StandardButtonProps &
    Omit<JSX.IntrinsicElements['button'], 'children'>;

const Button = ({ type = 'button', ...remaining }: ButtonProps) => {
    const classes = resolveClasses(remaining);

    return (
        <button className={classes} {...remaining} type={type}>
            {resolveContent(remaining)}
        </button>
    );
};

export { Button };
export type { ButtonProps };
