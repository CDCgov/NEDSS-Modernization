const body = (requester: string) =>
    encodeURI(`A user with the email address ${requester} is requesting access to the NBS7 Demo site.`);

const template = {
    recipient: encodeURI('nbs@cdc.gov'),
    subject: encodeURI('Requesting access to NBS7 Demo site'),
    body
};

const signUp = (email: string) => {
    const message = `mailto:${template.recipient}?subject=${template.subject}&body=${template.body(email)}`;

    window.location.href = message;
};

type Interaction = {
    signUp: (email: string) => void;
};

const useSignUp = (): Interaction => {
    return { signUp };
};

export { useSignUp };
