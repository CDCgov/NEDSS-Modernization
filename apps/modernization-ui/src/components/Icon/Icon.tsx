import './Icon.scss';

type IconProps = {
    name: string;
    size?: string;
    color?: string;
    alt?: string;
};

const IconList = [
    'calendar',
    'down-arrow-blue',
    'down-arrow-white',
    'drag',
    'expand',
    'expand-more',
    'folder',
    'group',
    'icon-dot-gov',
    'icon-https',
    'multi-drop',
    'multi-select',
    'navigate-next',
    'question',
    'reorder',
    'single-select',
    'subsection',
    'textarea',
    'textbox',
    'ungroup',
    'table'
];

export const Icon = ({ name, size, color, alt = '' }: IconProps) => {
    return IconList.indexOf(name) !== -1 ? (
        <img src={`/icons/${name}.svg`} className={`icon ${size} ${color}`} alt={alt} />
    ) : null;
};
