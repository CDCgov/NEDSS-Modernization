#!/usr/bin/env bash
# Pulls the example library from the NEDSS-Custom-Library repo so we can run an tests 
# against it and make sure it actually runs.

set -e

SCRIPT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd -P)";
GH_URL="https://raw.githubusercontent.com/CDCgov/NEDSS-Custom-Library-Example/refs/heads/ab/initial/example_library.py";
FILE_NAME="custom_lib_repo_example.py"

curl --fail -s "${GH_URL}" -o "${SCRIPT_DIR}/../tests/integration/assets/${FILE_NAME}";
