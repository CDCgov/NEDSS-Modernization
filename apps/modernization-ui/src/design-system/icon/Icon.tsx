import { SVGProps as ReactSVGProps } from 'react';
import classNames from 'classnames';
import { Sizing } from 'design-system/field';
import { Icons } from './types';

import styles from './icon.module.scss';

import uswds from '@uswds/uswds/img/sprite.svg';
import extended from './extended-sprite.svg';

export type IconProps = {
    name: Icons;
    sizing?: Sizing;
} & Omit<ReactSVGProps<SVGSVGElement>, 'width' | 'height'>;

const Icon = ({ name, sizing, role = 'img', className, ...props }: IconProps) => {
    const location = resolveLocation(name);

    const hidden = props['aria-hidden'] || !(props['aria-label'] || props['aria-labelledby']);

    return (
        <svg
            className={classNames(styles.icon, className, sizing && styles[sizing])}
            role={role}
            aria-hidden={hidden}
            {...props}>
            <use xlinkHref={location} />
        </svg>
    );
};

const resolveLocation = (name: Icons) => {
    switch (name) {
        case 'drag':
        case 'table':
        case 'file':
        case 'file-pdf':
        case 'sort_asc_alpha':
        case 'sort_des_alpha':
        case 'sort_asc_numeric':
        case 'sort_des_numeric':
        case 'sort_asc_default':
        case 'sort_des_default': {
            return `${extended}#${name}`;
        }
        case 'calendar':
        case 'down-arrow-blue':
        case 'down-arrow-white':
        case 'expand':
        case 'expand-more':
        case 'group':
        case 'icon-dot-gov':
        case 'icon-https':
        case 'multi-drop':
        case 'multi-select':
        case 'navigate-next':
        case 'question':
        case 'reorder':
        case 'single-select':
        case 'subsection':
        case 'textarea':
        case 'textbox':
        case 'ungroup': {
            return `/icons/${name}.svg`;
        }
        default: {
            return `${uswds}#${name}`;
        }
    }
};

export { Icon };
