package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.schedule
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Build : BuildType({
    name = "Build"

    vcs {
        root(HttpsGithubComPiotrnajberekAnsible)
    }

    steps {
        script {
            name = "ping"
            id = "ping"
            scriptContent = "ansible-playbook -i ping/inventory/prod/hosts ping/playbooks/ping_icmp.yml"
        }
    }

    triggers {
        vcs {
        }
        schedule {
            schedulingPolicy = cron {
                minutes = "5"
            }
            triggerBuild = always()
            withPendingChangesOnly = false
        }
    }

    features {
        perfmon {
        }
    }
})