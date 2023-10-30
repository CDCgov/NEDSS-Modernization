import './Link.scss';

type LinkProps = { name: string; link: string };

const Link = ({ name, link }: LinkProps) => {
    return (
        <div className="border-base-light grid-row flex-no-wrap border-top padding-y-2 margin-x-3 cursor-pointer width-full">
            <a href={link} className={`link text-normal margin-0 width-full font-sans-md padding-left-2`}>
                {name}
            </a>
        </div>
    );
};

export { Link };
