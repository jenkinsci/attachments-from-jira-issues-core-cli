multibranchPipelineJob('vmol-branch') {
    displayName('Vmol Branch and Pull Request Build')
    description('Build VMOL branches and Pull Requests as they are created')
    branchSources {
        branchSource {
            source {
                bitbucket {
                    id('vmol')
                    repoOwner('ids-dev')
                    repository('vmol')
                    serverUrl('https://bitbucket.org')
                    // the bitbucket branch source plugin does not seem to be able to
                    // use the repository token
                    credentialsId('BitbucketoAuthConsumer')
                    traits {
                        bitbucketBranchDiscovery { strategyId(1) }
                        bitbucketPullRequestDiscovery { strategyId(1)}
                        headRegexFilter {
                            regex('(.*feature|PR-).*')
                        }
                    }
                }
            }
            strategy {
                allBranchesSame {
                    props {
                        suppressAutomaticTriggering {
                            strategy('INDEXING')
                            triggeredBranchesRegex('.*')
                        }
                    }
                }
            }
        }
    }
    triggers {
        pollSCM {
            scmpoll_spec('H/15 * * * 1-5')
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(5)
            daysToKeep(5)
        }
    }
    factory {
        workflowBranchProjectFactory {
            // Relative location within the checkout of your Pipeline script.
            scriptPath('env2/Jenkinsfile.build')
        }
    }
}
