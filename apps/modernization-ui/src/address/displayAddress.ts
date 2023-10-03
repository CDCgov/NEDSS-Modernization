type DisplayableAddress = {
    address?: string | null;
    address2?: string | null;
    city?: string | null;
    state?: string | null;
    zipcode?: string | null;
};

const displayAddress = ({ address, address2, city, state, zipcode }: DisplayableAddress) => {
    const location = [city, state, zipcode].filter((i) => i).join(' ');
    return [address, address2, location].filter((i) => i).join('\n');
};

export { displayAddress };
