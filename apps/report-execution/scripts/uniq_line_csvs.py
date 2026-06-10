#!/usr/bin/env python3
"""Check the lines in two CSV files, output lines that differ in each. DOES NOT
care about ordering, file lines are put in a set and compared to one another.

    ./uniq_line_csvs.py <file_one> <file_two>
"""

import logging
from sys import argv

logging.basicConfig(level=logging.INFO, format='%(message)s')


def csv_to_line_set(fp):
    """Given a file path of a CSV file, put all of its lines into a set."""
    with open(fp) as fd:
        lines = fd.readlines()
        lines = [line.strip() for line in lines]

    return set(lines)


def get_csv_line_sets(first_csv, second_csv):
    """Given two CSV file paths, create sets of each of their lines."""
    first_set = csv_to_line_set(first_csv)
    second_set = csv_to_line_set(second_csv)

    return first_set, second_set


if __name__ == '__main__':
    if len(argv) != 3:
        logging.info(f'{argv[0]} <first_csv> <second_csv>')
        exit()

    fp1 = argv[1]
    fp2 = argv[2]

    first_set, second_set = get_csv_line_sets(fp1, fp2)

    logging.info(f'stats for [{fp1}]')
    logging.info(f'unique line count: {len(first_set)}')

    diff = first_set.difference(second_set)

    if not diff:
        logging.info(f'all lines in [{fp1}] are found in [{fp2}]')
    else:
        logging.info(
            f'{len(diff)} unique lines found in [{fp1}] that are NOT in [{fp2}]:'
        )
        for line in diff:
            logging.info(f'{line}')

    logging.info(32 * '*')

    logging.info(f'stats for [{fp2}]')
    logging.info(f'unique line count: {len(second_set)}')

    diff = second_set.difference(first_set)

    if not diff:
        logging.info(f'all lines in [{fp2}] are found in [{fp1}]')
    else:
        logging.info(
            f'{len(diff)} unique lines found in [{fp2}] that are NOT in [{fp1}]:'
        )
        for line in diff:
            logging.info(f'{line}')
