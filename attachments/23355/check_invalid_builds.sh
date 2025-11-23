#!/bin/bash -e

# define local jenkins env
jenkins_url=https://localhost:8080
jenkins_home=/work/jenkins
jobs_dir=${jenkins_home}/jobs

# build dir date pattern
date_pat="^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}_[0-9]{2}-[0-9]{2}-[0-9]{2}$"

# Check All jobs
for job_dir in ${jobs_dir}/*; do
    [ ! -d ${job_dir} ] && continue
    job=$(basename ${job_dir})
    echo "Checking $job..." >&2
    # check for invalid links
    for build_link in $(find ${jobs_dir}/${job}/builds/* -type l ! -name "last*" -prune); do
        [ -d ${build_link} ] && continue
        if [[ "$1" == "-c" ]]; then
            echo "Removing build_link: ${build_link}"  
            unlink "${build_link}"
        else
            echo "Invalid build_link: ${build_link}"
            ls -lad ${build_link}
        fi
    done
    # check for invalid build directories
    for build_dir in $(find ${jobs_dir}/${job}/builds/* -type d -prune); do
        build_xml=${build_dir}/build.xml
        # check for no build information file
        if [ ! -f ${build_dir}/build.xml ]; then
            if [[ "$1" == "-c" ]]; then
                echo "Removing build_dir: ${build_dir}"  
                rm -rf "${build_dir}"
            else
                echo "Invalid build_dir: ${build_dir}"
                ls -la ${build_dir}/
            fi
            continue
        fi
        build_number=$(perl -n -e'/^  <number>(\d+)<\/number>$/ && print $1' ${build_xml})
        build_link=${jobs_dir}/${job}/builds/${build_number}
        build_date=$(basename ${build_dir})
        # check non-existent symbolic link
        if [ ! -L ${build_link} ]; then
            # check directories with unparseable dates (JENKINS-15587)
            if [[ ! "${build_date}" =~ ${date_pat} ]]; then
                if [[ "$1" == "-c" ]]; then
                    echo "Removing build_dir: ${build_dir}"  
                    rm -rf ${build_dir}
                else
                    echo "Unparseable date from build_dir: ${build_dir}"
                    echo "     expected build_link: ${build_link}"
                fi
            elif [[ "$1" == "-c" ]]; then
                echo "Overriding build link in ${job} : ${build_number} -> ${build_date}"
                [ -d ${build_link} ] && rm -rf ${build_link}
                ln -s ${build_date} ${build_link}
            else
                echo "Missing build link in ${job} : ${build_number} -> ${build_date}"
            fi
        fi
    done
done | tee ${jenkins_home}/userContent/invalid_builds.txt
echo "List updated in $(date)" >> ${jenkins_home}/userContent/invalid_builds.txt
