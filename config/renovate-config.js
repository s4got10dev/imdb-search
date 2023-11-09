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
    automerge: false,
    automergeType: "pr",
    platformAutomerge: false,
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
}
