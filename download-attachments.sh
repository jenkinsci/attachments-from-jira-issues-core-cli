#!/usr/bin/env bash

: "${JIRA_MIGRATION_JIRA_URL:? Missing Jira base URL (e.g., https://issues.jenkins.io)}"

INPUT_FILE="mappings/jira_attachments_id_filename.txt"

base_url="${JIRA_MIGRATION_JIRA_URL}/secure/attachment"

urlencode() {
    # URL-encode a string
    local LANG=C
    local length="${#1}"
    for (( i = 0; i < length; i++ )); do
        local c="${1:i:1}"
        case $c in
            [a-zA-Z0-9.~_-]) printf "%s" "${c}" ;;
            *) printf '%%%02X' "'${c}" ;;
        esac
    done
}

# Count non-empty lines
total=$(grep -vc '^$' "${INPUT_FILE}")
count=0

while IFS=: read -r id filename; do
    count=$((count+1))
    pct=$(printf "%.2f" "$(echo "${count}*100/${total}" | bc -l)")
    # skip empty lines
    [ -z "${id}" ] && continue

    # download target path
    target="attachments/${id}/${filename}"

    if [ -f "${target}" ]; then
        echo "[${count}/${total} | ${pct}%] Skipping $id (already downloaded)"
        continue
    fi

    # create directory
    mkdir -p "attachments/${id}"

    encoded_filename=$(urlencode "$filename")
    url="${base_url}/${id}/${encoded_filename}"


    echo "[${count}/${total} | ${pct}%] Downloading ${id} to ${target}"

    # download the file
    curl -s -L "${url}" -o "${target}"
done < "${INPUT_FILE}"
