'''
The script that was used to kill off all the job builds that were attempting to
resume straddling restarts of DockerJenkins.
'''

import re
import os
import shutil

STEP_XML_NAME_REGEX = re.compile('^(\d+).xml$')

# The root path to Jenkins
JENKINS_DIR = '/var/lib/jenkins'
# Whether or not the run will actually delete build invocations
DRY_RUN = True

def main():
	if DRY_RUN:
		print 'DRY RUN'
	crawlBuilds(JENKINS_DIR + '/jobs')

# Crawls through Jenkins build job definitions and cleans up jobs that are
# recognized as potential restarts
def crawlBuilds(directory):
	for build in getImmediateSubdirs(directory):
		buildDir = '%s/%s/builds' % (directory, build)
		if os.path.exists(buildDir):
			for run in [folder for folder in getImmediateSubdirs(buildDir) if
					representsInt(folder)]:
				runDir = '%s/%s' % (buildDir, run)
				if (isDirEvictable(runDir)):
					print 'Deleted ' + runDir
					if not DRY_RUN:
						shutil.rmtree(runDir)

def getImmediateSubdirs(directory):
	return [name for name in os.listdir(directory)
			if os.path.isdir(os.path.join(directory, name))]

# Checks if a build dir is evictable.
def isDirEvictable(dir):
	# Flags builds without a <result> tag (indicating not done) as evictable.
	# Additionally, checks for Finished: ABORTED in the logs, as jobs that are
	# force killed will not have <result> tag and do not need to be hard deleted.
	return doesBuildNotContainResult(dir) and not doesBuildContainFinishInLog(dir)

# Checks if a build (an invocation of a Jenkins job) contains resume text in the
# build log.
def doesBuildContainResumeInLog(dir):
	logPath = '%s/log' % (dir)
	if not os.path.exists(logPath):
		return False
	with open(logPath, 'r') as file:
		contents = file.read()
		return ('Resuming build at' in contents and
				'after Jenkins restart' in contents)

def doesBuildContainFinishInLog(dir):
	logPath = '%s/log' % (dir)
	if not os.path.exists(logPath):
		return False
	with open(logPath, 'r') as file:
		contents = file.read()
		return 'Finished: ABORTED' in contents

# Checks if a build (an invocation of a Jenkins job) contains a stage with a run
# result. A run that does not have a result post shutdown implies that it was
# interrupted in the shutdown and may resume.
def doesBuildNotContainResult(dir):
	workflowPath = '%s/workflow/' % (dir)
	if not os.path.exists(workflowPath):
		return False
	stepXMLs = [file for file in os.listdir(workflowPath) if
			STEP_XML_NAME_REGEX.search(file) is not None]
	if len(stepXMLs) == 0:
		return False
	def compareSteps(x, y):
		# Reverse order; highest first
		return (int(STEP_XML_NAME_REGEX.search(y).group(1)) -
				int(STEP_XML_NAME_REGEX.search(x).group(1)))
	stepXMLs = sorted(stepXMLs, cmp=compareSteps)
	with open('%s/workflow/%s' % (dir, stepXMLs[0]), 'r') as file:
		contents = file.read()
		return not ('<node class="org.jenkinsci.plugins.workflow.graph.FlowEndNode"'
				in contents and '<result>' in contents)

# Checks if a string represents an integer
def representsInt(s):
	try:
		int(s)
		return True
	except ValueError:
		return False

if __name__ == '__main__':
  main()
