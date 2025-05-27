module.exports = {
    extends: [
        // customized config:base see https://docs.renovatebot.com/presets-config/#configbase
        ":dependencyDashboard",
        ":ignoreModulesAndTests",
        ":autodetectPinVersions",
        ":prHourlyLimitNone",
        ":prConcurrentLimit10",
        "group:monorepos",
        "group:recommended",
        "workarounds:all"
    ],
    platform: "github",
    assignees: [],
    baseBranches: [ "main" ],
    automerge: true,
    automergeType: "pr",
    platformAutomerge: true,
    requiredStatusChecks: ["build"],
    gradle: {
        "enabled": true
    },
    lockFileMaintenance: {
        enabled: true,
    },
    bumpVersion: null,
    onboarding: false,
    requireConfig: false,
    rebaseWhen: 'behind-base-branch',
    branchPrefix: 'renovate/',
    commitMessagePrefix: "renovate: 🖌",
    commitBody: "renovate-bot",
    packageRules: [
        {
            matchPackagePatterns: [".*"],
            groupName: "all dependencies",
            groupSlug: "all-dependencies"
        }
    ]
}
