import './Link.scss';

type LinkProps = { name: string; link: string; className?: string };

const Link = ({ name, link, className = '' }: LinkProps) => {
    return (
        <a href={link} className={`link text-normal margin-0 width-full ${className}`}>
            {name}
        </a>
    );
};

export { Link };
