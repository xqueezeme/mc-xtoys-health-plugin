package com.xqueezeme.xtoys.health.plugin;

import java.io.Serializable;

public class PlayerConfiguration implements Serializable {
    private static transient final long serialVersionUID = -1681012206529286330L;

    private String webhookId;
    private boolean disabled;

    public PlayerConfiguration() {
    }

    public PlayerConfiguration(String webhookId) {
        this.webhookId = webhookId;
    }

    public String getWebhookId() {
        return webhookId;
    }

    public void setWebhookId(String webhookId) {
        this.webhookId = webhookId;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
