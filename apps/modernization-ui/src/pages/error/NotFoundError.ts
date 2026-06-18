// classes needed to match chape of RouteErrorResponse
class NotFoundError extends Error {
    public readonly status: number;
    public readonly statusText: string;
    public readonly data: string;

    constructor() {
        super('Not Found');

        this.name = 'NotFoundError';
        this.status = 404;
        this.statusText = 'Not found';
        this.data = '';
    }
}

export { NotFoundError };
