/**
 * Redirect to NBS 6 pages that have not been modernized
 *
 * @param pathname - NBS6 path to redirect to (e.g. '/nbs/ManageReports.do')
 */
export const redirectToNBS6 = (pathname: string) => {
    window.location.href = pathname;
};
