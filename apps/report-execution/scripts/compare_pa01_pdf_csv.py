#!/usr/bin/env python3
"""NOTE: VIBE CODED COMPARISON SCRIPT, PLEASE REMIND ME TO REMOVE IF YOU'RE SEEING THIS IN A PR!!!


Compare PA01 PDF report values with the generated PA01 CSV output.

The SAS PDF is a two-column report, so this parser is intentionally specific to
the PA01 layout. It uses `pdftotext -layout`, extracts worker sections, and
compares the rows currently emitted by `src/libraries/pa_01.py`.
"""

from __future__ import annotations

import argparse
import csv
import re
import shutil
import subprocess
import sys
from collections import defaultdict
from dataclasses import dataclass
from pathlib import Path

DEFAULT_PDF = (
    'apps/report-execution/PA01 CASE MANAGEMENT REPORT '
    '(INTERVIEW ASSIGN DATE) - HIV.pdf'
)
DEFAULT_CSV = 'apps/report-execution/out.csv'
LEFT_COLUMN_WIDTH = 60

CASE_ASSIGNMENTS = 'Case Assignments & Outcomes'
PARTNERS_CLUSTERS_INITIATED = 'Partners & Clusters Initiated'
DISPOSITIONS = 'Dispositions - New Partners & Clusters'
SPEED_OF_NOTIFICATION = 'Speed Of Notification - Partners & Clusters'


@dataclass(frozen=True)
class CsvValue:
    worker: str
    category_1: str
    category_2: str
    category_3: str
    count: str
    percentage: str
    index: str


@dataclass(frozen=True)
class ComparisonRow:
    worker: str
    category_1: str
    category_2: str
    category_3: str
    mismatch: str
    csv_value: str
    pdf_value: str


@dataclass(frozen=True)
class PdfEntry:
    category_1: str
    category_2: str
    category_3: str
    values: list[str]


@dataclass
class PdfContext:
    category_1: str | None = None
    category_2: str | None = None


def main() -> int:
    parser = argparse.ArgumentParser(
        description='Compare PA01 PDF values against generated PA01 CSV rows.'
    )
    parser.add_argument(
        '--pdf', default=DEFAULT_PDF, help='Path to the SAS PDF output.'
    )
    parser.add_argument(
        '--csv', default=DEFAULT_CSV, help='Path to the Python CSV output.'
    )
    parser.add_argument(
        '--worker',
        action='append',
        help='Limit comparison to one worker. Can be used more than once.',
    )
    parser.add_argument(
        '--show-matches',
        action='store_true',
        help='Include matching value rows in the output CSV.',
    )
    parser.add_argument(
        '--output',
        help='Optional path to write the comparison CSV. Defaults to stdout.',
    )
    args = parser.parse_args()

    pdf_path = Path(args.pdf)
    csv_path = Path(args.csv)

    if not pdf_path.exists():
        print(f'PDF not found: {pdf_path}', file=sys.stderr)
        return 2
    if not csv_path.exists():
        print(f'CSV not found: {csv_path}', file=sys.stderr)
        return 2
    if shutil.which('pdftotext') is None:
        print('pdftotext is required. Install Poppler and try again.', file=sys.stderr)
        return 2

    pdf_values = extract_pdf_values(pdf_path)
    csv_rows = read_csv_values(csv_path)
    workers = (
        {normalize_worker(worker) for worker in args.worker} if args.worker else None
    )

    output_rows: list[ComparisonRow] = []
    mismatch_count = 0
    compared_count = 0

    for row in csv_rows:
        if workers is not None and normalize_worker(row.worker) not in workers:
            continue

        pdf_worker_values = pdf_values.get(normalize_worker(row.worker), {})
        entries = pdf_worker_values.get(csv_key(row), [])
        values = entries[0].values if entries else []

        if not values:
            compared_count += 1
            mismatch_count += 1
            output_rows.append(
                ComparisonRow(
                    row.worker,
                    row.category_1,
                    row.category_2,
                    row.category_3,
                    f'{csv_label(row)} [Row]',
                    csv_display_value(row),
                    '<missing>',
                )
            )
            continue

        compared_count += expected_value_count(row)
        comparisons = compare_row(row, values, include_matches=args.show_matches)
        mismatch_count += sum(1 for comparison in comparisons if comparison.mismatch)
        output_rows.extend(comparisons)

    write_comparison_csv(output_rows, Path(args.output) if args.output else None)

    if mismatch_count:
        print(
            f'Compared {compared_count} value(s); {mismatch_count} mismatch(es).',
            file=sys.stderr,
        )
        return 1

    print(f'All compared values matched ({compared_count} value(s)).', file=sys.stderr)
    return 0


def read_csv_values(csv_path: Path) -> list[CsvValue]:
    with csv_path.open(newline='') as csv_file:
        reader = csv.DictReader(csv_file)
        return [
            CsvValue(
                worker=row['Worker'],
                category_1=row['Category 1'],
                category_2=row['Category 2'],
                category_3=row['Category 3'],
                count=row['Count'],
                percentage=row['Percentage'],
                index=row['Index'],
            )
            for row in reader
        ]


def extract_pdf_values(
    pdf_path: Path,
) -> dict[str, dict[tuple[str, str, str], list[PdfEntry]]]:
    text = subprocess.check_output(
        ['pdftotext', '-layout', str(pdf_path), '-'], text=True
    )

    current_worker: str | None = None
    worker_values: dict[str, dict[tuple[str, str, str], list[PdfEntry]]] = defaultdict(
        lambda: defaultdict(list)
    )
    pending: dict[str, tuple[str, list[str]] | None] = {'left': None, 'right': None}
    contexts = {'left': PdfContext(), 'right': PdfContext()}

    for line in text.splitlines():
        worker = worker_from_line(line)
        if worker:
            if normalize_worker(worker) != normalize_worker(current_worker or ''):
                contexts = {'left': PdfContext(), 'right': PdfContext()}
            current_worker = worker
            pending = {'left': None, 'right': None}
            continue

        if current_worker is None or should_ignore_line(line):
            continue

        section = section_from_line(line)
        if section:
            for context in contexts.values():
                context.category_1 = section
                context.category_2 = None
            pending = {'left': None, 'right': None}
            continue

        left = line[:LEFT_COLUMN_WIDTH]
        right = line[LEFT_COLUMN_WIDTH:]
        for side, chunk in (('left', left), ('right', right)):
            parsed, pending[side] = parse_pdf_chunk(chunk, pending[side])
            if parsed is None:
                continue

            label, values = parsed
            entry = pdf_entry_for_label(contexts[side], side, label, values)
            if entry:
                worker_values[normalize_worker(current_worker)][
                    pdf_key(entry.category_1, entry.category_2, entry.category_3)
                ].append(entry)

    return worker_values


def worker_from_line(line: str) -> str | None:
    if 'WORKER: SUMMARY (ALL WORKERS)' in line:
        return 'ALL'

    match = re.search(r'WORKER:\s+SUMMARY OF\s+(.+?)\s*$', line)
    if match:
        return match.group(1).strip()

    return None


def should_ignore_line(line: str) -> bool:
    ignored_fragments = (
        'PA01 CASE',
        'REPORT RUN ON:',
        'DATA REFRESHED ON:',
        'THIS REPORT WAS BUILT USING',
        'PAGE ',
        '_____',
    )
    upper = line.upper()
    return not line.strip() or any(fragment in upper for fragment in ignored_fragments)


def section_from_line(line: str) -> str | None:
    normalized = normalize_label(line)
    if 'CASE ASSIGNMENTS OUTCOMES' in normalized:
        return CASE_ASSIGNMENTS
    if 'PARTNERS CLUSTERS INITIATED' in normalized:
        return PARTNERS_CLUSTERS_INITIATED
    if 'DISPOSITIONS' in normalized and 'PARTNERS CLUSTERS' in normalized:
        return DISPOSITIONS
    if (
        'SPEED OF NOTIFICATION' in normalized or 'SPEED OF EXAM' in normalized
    ) and 'PARTNERS CLUSTERS' in normalized:
        return SPEED_OF_NOTIFICATION
    return None


def parse_pdf_chunk(
    chunk: str, pending: tuple[str, list[str]] | None
) -> tuple[tuple[str, list[str]] | None, tuple[str, list[str]] | None]:
    text = chunk.strip()
    if not text or is_section_header(text):
        return None, pending

    if ':' in text:
        before_colon, after_colon = text.split(':', 1)
        label_part = strip_values(before_colon).strip()
        values = find_values(after_colon)

        if pending and not values:
            pending_label, pending_values = pending
            return (f'{pending_label} {label_part}', pending_values), None

        if values:
            return (label_part, values), None

        return None, None

    values = find_values(text)
    if values:
        label = strip_values(text).strip()
        if label:
            return None, (label, values)

    return None, pending


def is_section_header(text: str) -> bool:
    return section_from_line(text) is not None


def find_values(text: str) -> list[str]:
    return re.findall(r'-?\d+(?:\.\d+)?%?', text)


def strip_values(text: str) -> str:
    return re.sub(r'(?:\s+-?\d+(?:\.\d+)?%?)+\s*$', '', text).strip()


def pdf_entry_for_label(
    context: PdfContext, side: str, label: str, values: list[str]
) -> PdfEntry | None:
    if context.category_1 == CASE_ASSIGNMENTS:
        return case_assignment_entry(label, values)
    if context.category_1 == PARTNERS_CLUSTERS_INITIATED:
        return partners_clusters_entry(context, label, values)
    if context.category_1 == DISPOSITIONS:
        return disposition_entry(context, side, label, values)
    if context.category_1 == SPEED_OF_NOTIFICATION:
        return speed_entry(context, side, label, values)
    return None


def case_assignment_entry(label: str, values: list[str]) -> PdfEntry | None:
    label_map = {
        'CASES ASSIGNED': 'Cases Assigned',
        'CASES CLOSED': 'Cases Closed',
        'CASES IXD': "Cases IX'D",
        'WITHIN 3 DAYS': ("Cases IX'D", 'Within 3 days'),
        'WITHIN 5 DAYS': ("Cases IX'D", 'Within 5 days'),
        'WITHIN 7 DAYS': ("Cases IX'D", 'Within 7 days'),
        'WITHIN 14 DAYS': ("Cases IX'D", 'Within 14 days'),
        'CASES REINTERVIEWED': 'Cases Reinterviewed',
        'HIV PREVIOUS POSITIVE': 'HIV Previous Positive',
        'HIV TESTED': 'HIV Tested',
        'HIV NEW POSITIVE': 'HIV New Positive',
        'HIV POSTTEST COUNSEL': 'HIV Posttest Counsel',
        'PARTNER NOTIFICATION INDEX': 'Partner Notification Index',
        'TESTING INDEX': 'Testing Index',
        'DISEASE INTERVENTION INDEX': 'Disease Intervention Index',
        'TREATMENT INDEX': 'Treatment Index',
        'CASES W SOURCE IDENTIFIED': 'Cases W/ Source Identified',
    }
    mapped = label_map.get(normalize_label(label))
    if mapped is None:
        return None
    category_2, category_3 = mapped if isinstance(mapped, tuple) else (mapped, '')
    return PdfEntry(CASE_ASSIGNMENTS, category_2, category_3, values)


def partners_clusters_entry(
    context: PdfContext, label: str, values: list[str]
) -> PdfEntry | None:
    normalized = normalize_label(label)
    if normalized == 'TOTAL PERIOD PARTNERS':
        return PdfEntry(
            PARTNERS_CLUSTERS_INITIATED, 'Total Period Partners', '', values
        )
    if normalized == 'TOTAL PARTNERS INITIATED':
        context.category_2 = 'Total Partners Initiated'
        return PdfEntry(PARTNERS_CLUSTERS_INITIATED, context.category_2, '', values)
    if normalized == 'FROM OI':
        return PdfEntry(
            PARTNERS_CLUSTERS_INITIATED, 'Total Partners Initiated', 'From OI', values
        )
    if normalized == 'FROM RI':
        return PdfEntry(
            PARTNERS_CLUSTERS_INITIATED, 'Total Partners Initiated', 'From RI', values
        )
    if normalized == 'CONTACT INDEX':
        return PdfEntry(PARTNERS_CLUSTERS_INITIATED, 'Contact Index', '', values)
    if normalized in {'CASES W NO PARTNERS'}:
        return PdfEntry(PARTNERS_CLUSTERS_INITIATED, 'Cases W/No Partners', '', values)
    if normalized == 'TOTAL CLUSTERS INITIATED':
        context.category_2 = 'Total Clusters Initiated'
        return PdfEntry(PARTNERS_CLUSTERS_INITIATED, context.category_2, '', values)
    if normalized == 'CLUSTER INDEX':
        return PdfEntry(
            PARTNERS_CLUSTERS_INITIATED,
            'Total Clusters Initiated',
            'Cluster Index',
            values,
        )
    if normalized == 'CASES W NO CLUSTERS':
        return PdfEntry(
            PARTNERS_CLUSTERS_INITIATED,
            'Total Clusters Initiated',
            'Cases /W No Clusters',
            values,
        )
    return None


def disposition_entry(
    context: PdfContext, side: str, label: str, values: list[str]
) -> PdfEntry | None:
    normalized = normalize_label(label)
    if side == 'left':
        if normalized == 'NEW PARTNERS NOTIFIED':
            context.category_2 = 'New Partners Notified'
            return PdfEntry(DISPOSITIONS, context.category_2, '', values)
        if normalized == 'NEW PARTNERS EXAMINED':
            context.category_2 = 'New Partners Examined'
            return PdfEntry(DISPOSITIONS, context.category_2, '', values)
        if normalized == 'NEW PARTNERS NOT NOTIFIED':
            context.category_2 = 'New Partners Not Notified'
            return PdfEntry(DISPOSITIONS, context.category_2, '', values)
        if normalized == 'NEW PARTNERS NO EXAM':
            context.category_2 = 'New Partners No Exam'
            return PdfEntry(DISPOSITIONS, context.category_2, '', values)
        if normalized == 'PREVIOUS RX':
            return PdfEntry(DISPOSITIONS, 'New Partners Previous RX', '', values)
        if normalized == 'PREVIOUS POS':
            return PdfEntry(DISPOSITIONS, 'New Partners Previous Pos', '', values)
        if normalized == 'OPEN':
            if is_std_partner_disposition_context(context):
                return PdfEntry(
                    DISPOSITIONS, 'New Partners Previous Open', '', values
                )
            return PdfEntry(DISPOSITIONS, 'New Partners Open', '', values)
    else:
        if normalized == 'NEW CLUSTERS NOTIFIED':
            context.category_2 = 'New Clusters Notified'
            return PdfEntry(DISPOSITIONS, context.category_2, '', values)
        if normalized == 'NEW CLUSTERS EXAMINED':
            context.category_2 = 'New Clusters Examined'
            return PdfEntry(DISPOSITIONS, context.category_2, '', values)
        if normalized == 'NEW CLUSTERS NOT NOTIFIED':
            context.category_2 = 'New Clusters Not Notified'
            return PdfEntry(DISPOSITIONS, context.category_2, '', values)
        if normalized == 'NEW CLUSTERS NO EXAM':
            context.category_2 = 'New Clusters No Exam'
            return PdfEntry(DISPOSITIONS, context.category_2, '', values)
        if normalized == 'PREVIOUS RX':
            return PdfEntry(DISPOSITIONS, 'New Clusters Previous RX', '', values)
        if normalized == 'PREVIOUS POS':
            return PdfEntry(DISPOSITIONS, 'New Clusters Previous Pos', '', values)
        if normalized == 'OPEN':
            if is_std_cluster_disposition_context(context):
                return PdfEntry(DISPOSITIONS, 'New Clusters Previous Open', '', values)
            return PdfEntry(DISPOSITIONS, 'New Clusters Open', '', values)

    category_3 = disposition_child_label(normalized)
    if category_3 and context.category_2:
        return PdfEntry(DISPOSITIONS, context.category_2, category_3, values)
    return None


def is_std_partner_disposition_context(context: PdfContext) -> bool:
    return context.category_2 in {
        'New Partners Examined',
        'New Partners No Exam',
    }


def is_std_cluster_disposition_context(context: PdfContext) -> bool:
    return context.category_2 in {
        'New Clusters Examined',
        'New Clusters No Exam',
    }


def speed_entry(
    context: PdfContext, side: str, label: str, values: list[str]
) -> PdfEntry | None:
    normalized = normalize_label(label)
    if side == 'left' and normalized in {
        'NEW PARTNERS NOTIFIED',
        'NEW PARTNERS EXAMINED',
    }:
        context.category_2 = 'New Partners Notified'
        return PdfEntry(SPEED_OF_NOTIFICATION, context.category_2, '', values)
    if side == 'right' and normalized in {
        'NEW CLUSTERS NOTIFIED',
        'NEW CLUSTERS EXAMINED',
    }:
        context.category_2 = 'New Clusters Notified'
        return PdfEntry(SPEED_OF_NOTIFICATION, context.category_2, '', values)
    within_match = re.fullmatch(r'WITHIN (\d+) DAYS', normalized)
    if within_match and context.category_2:
        return PdfEntry(
            SPEED_OF_NOTIFICATION,
            context.category_2,
            f'Within {within_match.group(1)} days',
            values,
        )
    return None


def disposition_child_label(normalized_label: str) -> str | None:
    label_map = {
        'PREV NEG NEW POS': 'Prev. Neg, New Pos',
        'PREV NEG STILL NEG': 'Prev. Neg, Still Neg',
        'PREV NEG NO TEST': 'Prev. Neg, No Test',
        'NO PREV TEST NEW POS': 'No Prev. Test, New Pos',
        'NO PREV TEST NEW NEG': 'No Prev. Test, New Neg',
        'NO PREV TEST NO TEST': 'No Prev. Test, No Test',
        'PREVENTATIVE RX': 'Preventative RX',
        'REFUSED PREV RX': 'Refused Prev. RX',
        'INFECTED RXD': "Infected, RX'D",
        'INFECTED NO RX': 'Infected, No RX',
        'NOT INFECTED': 'Not Infected',
        'PREVIOUS PREV RX': 'Previous Prev Rx',
        'INSUFFICIENT INFO': 'Insufficient Info',
        'UNABLE TO LOCATE': 'Unable to Locate',
        'REFUSED EXAM': 'Refused Exam',
        'OOJ': 'OOJ',
        'OTHER': 'Other',
        'DOMESTIC VIOLENCE RISK': 'Domestic Violence Risk',
        'PATIENT DECEASED': 'Patient Deceased',
    }
    return label_map.get(normalized_label)


def csv_label(row: CsvValue) -> str:
    return row.category_3 or row.category_2


def compare_row(
    row: CsvValue, pdf_values: list[str], include_matches: bool = False
) -> list[ComparisonRow]:
    rows: list[ComparisonRow] = []

    if row.count:
        pdf_count = pdf_values[0] if pdf_values else '<missing>'
        rows.extend(
            comparison_for_field(row, 'Count', row.count, pdf_count, include_matches)
        )

    if row.percentage:
        pdf_percentage = first_percentage(pdf_values)
        rows.extend(
            comparison_for_field(
                row, 'Percentage', row.percentage, pdf_percentage, include_matches
            )
        )

    if row.index:
        if row.count:
            pdf_index_values = [
                value for value in pdf_values if not value.endswith('%')
            ]
            pdf_index = (
                pdf_index_values[-1] if len(pdf_index_values) > 1 else '<missing>'
            )
        else:
            pdf_index = first_non_percentage(pdf_values)
        rows.extend(
            comparison_for_field(row, 'Index', row.index, pdf_index, include_matches)
        )

    return rows


def comparison_for_field(
    row: CsvValue,
    field: str,
    csv_value: str,
    pdf_value: str,
    include_matches: bool,
) -> list[ComparisonRow]:
    matches = values_equal(csv_value, pdf_value)
    if matches and not include_matches:
        return []

    return [
        ComparisonRow(
            row.worker,
            row.category_1,
            row.category_2,
            row.category_3,
            '' if matches else f'{csv_label(row)} [{field}]',
            csv_value,
            pdf_value,
        )
    ]


def first_percentage(values: list[str]) -> str:
    return next((value for value in values if value.endswith('%')), '<missing>')


def first_non_percentage(values: list[str]) -> str:
    return next((value for value in values if not value.endswith('%')), '<missing>')


def values_equal(left: str, right: str) -> bool:
    if right == '<missing>':
        return False

    left = left.strip()
    right = right.strip()
    left_is_percent = left.endswith('%')
    right_is_percent = right.endswith('%')

    if left_is_percent != right_is_percent:
        return False

    try:
        left_num = float(left.rstrip('%'))
        right_num = float(right.rstrip('%'))
    except ValueError:
        return left == right

    return abs(left_num - right_num) < 0.0001


def csv_display_value(row: CsvValue) -> str:
    parts = []
    if row.count:
        parts.append(f'count={row.count}')
    if row.percentage:
        parts.append(f'percentage={row.percentage}')
    if row.index:
        parts.append(f'index={row.index}')
    return ', '.join(parts) if parts else '<blank>'


def expected_value_count(row: CsvValue) -> int:
    return sum(1 for value in (row.count, row.percentage, row.index) if value)


def write_comparison_csv(rows: list[ComparisonRow], output_path: Path | None) -> None:
    fieldnames = [
        'worker',
        'category 1',
        'category 2',
        'category 3',
        'mismatch',
        'CSV value',
        'PDF value',
    ]

    output_file = output_path.open('w', newline='') if output_path else sys.stdout
    try:
        writer = csv.DictWriter(output_file, fieldnames=fieldnames)
        writer.writeheader()
        for row in rows:
            writer.writerow(
                {
                    'worker': row.worker,
                    'category 1': row.category_1,
                    'category 2': row.category_2,
                    'category 3': row.category_3,
                    'mismatch': row.mismatch,
                    'CSV value': row.csv_value,
                    'PDF value': row.pdf_value,
                }
            )
    finally:
        if output_path:
            output_file.close()


def csv_key(row: CsvValue) -> tuple[str, str, str]:
    return pdf_key(row.category_1, row.category_2, row.category_3)


def pdf_key(category_1: str, category_2: str, category_3: str) -> tuple[str, str, str]:
    return (
        normalize_label(category_1),
        normalize_label(category_2),
        normalize_label(category_3),
    )


def normalize_worker(worker: str) -> str:
    return worker.strip().upper()


def normalize_label(label: str) -> str:
    label = label.upper()
    label = label.replace('&', ' ')
    label = label.replace("'", '')
    label = re.sub(r'[/.,:()-]', ' ', label)
    label = re.sub(r'\s+', ' ', label)
    return label.strip()


if __name__ == '__main__':
    raise SystemExit(main())
