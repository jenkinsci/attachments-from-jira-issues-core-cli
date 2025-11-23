#!/bin/bash -e

# define local jenkins env
jenkins_url=https://localhost:8080
jenkins_home=/work/jenkins
jobs_dir=${jenkins_home}/jobs

# Auto Cleanup
function clean_on_exit() {
    [ -d "${TMPDIR}" ] && cd "${HOME}" && rm -rf "${TMPDIR}"
}
if [ ! -d "$TMPDIR" ]; then
    export TMPDIR=$(mktemp -d /tmp/$(basename $0).XXXXX)
    trap "clean_on_exit" EXIT
fi
cd "$TMPDIR"

# Check All jobs
for job_dir in ${jobs_dir}/*; do
    [ ! -d ${job_dir} ] && continue
    job=$(basename ${job_dir})
    echo "Checking $job..." >&2
    job_url=${jenkins_url}/job/${job}
    wget -q --no-check-certificate -O all ${job_url}/buildHistory/all
    gawk -F'#' '/ #/{print $2}' all > online_builds
    find ${jobs_dir}/${job}/builds/* -type l ! -name "last*" -prune | sed "s,${jobs_dir}/${job}/builds/,,g" > local_builds
    missing=$(gawk '
    BEGIN {first_file = ""}
    {
        if (first_file == "") first_file = FILENAME
        if (first_file == FILENAME) {build_list[$1]=1} else {delete build_list[$1]}
    }
    END {for (build in build_list) print build}
    ' local_builds online_builds)
    [ ! -z "${missing}" ] && echo Job $job missing builds: $missing
done | tee ${jenkins_home}/userContent/missing_builds.txt
echo "List updated in $(date)" >> ${jenkins_home}/userContent/missing_builds.txt

