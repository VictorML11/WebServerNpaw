package me.victorml.npaw.logic;

public enum Parameters {

    ACCOUNT_CODE("accountCode"),
    TARGET_DEVICE("targetDevice"),
    PLUGIN_VERSION("pluginVersion");

    private String parameter;

    Parameters(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
