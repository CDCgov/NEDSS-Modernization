/**
 * Redirect to NBS 6 pages that have not been modernized
 *
 * @param pathname - NBS6 path to redirect to (e.g. '/nbs/ManageReports.do')
 */
export const redirectToNBS6 = (pathname: string) => {
    // check if it can be a valid path
    const pathnameRegex = /^\/([a-zA-Z0-9_.-]+(?:\/[a-zA-Z0-9_.-]+)*)?$/;
    if (pathnameRegex.test(pathname)) {
        window.location.href = pathname;
    }
};
