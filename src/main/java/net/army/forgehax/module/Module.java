package net.army.forgehax.module;

import java.util.ArrayList;

public abstract class Module {

    public String name;
    public int key;
    public Category category;
    public boolean toggled;

    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.toggled = false;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setToggled(boolean toggle) {
        this.toggled = toggle;
    }

    public void toggle() {
        this.toggled = !this.toggled;

        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void Update() {

    }

    public ArrayList<String> getWhitelist() {
        return null;
    }
}
